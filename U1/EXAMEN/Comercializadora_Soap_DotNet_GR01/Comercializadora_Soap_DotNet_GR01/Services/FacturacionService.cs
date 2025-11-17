using System;
using System.Collections.Generic;
using System.Linq;
using Comercializadora_Soap_DotNet_GR01.DataAcces.Repositories;
using Comercializadora_Soap_DotNet_GR01.DTOs;
using Comercializadora_Soap_DotNet_GR01.Models;
using Comercializadora_Soap_DotNet_GR01.BanquitoSoap; // TODO: Agregar Service Reference

namespace Comercializadora_Soap_DotNet_GR01.Services
{
    public class FacturacionService
    {
        private readonly ProductoRepository _productoRepository;
        private readonly FacturaRepository _facturaRepository;
        private readonly CreditoSoapServiceClient _banquitoClient; // TODO: Cliente SOAP BanQuito

        public FacturacionService()
        {
            _productoRepository = new ProductoRepository();
            _facturaRepository = new FacturaRepository();
            _banquitoClient = new CreditoSoapServiceClient(); // TODO
        }

        /// <summary>
        /// Genera una factura con pago en efectivo (33% descuento)
        /// </summary>
        public FacturaDTO GenerarFacturaEfectivo(SolicitudFacturaDTO solicitud)
        {
            try
            {
                // Validaciones
                if (solicitud == null || solicitud.Items == null || !solicitud.Items.Any())
                    throw new Exception("La solicitud de factura no contiene productos");

                if (string.IsNullOrWhiteSpace(solicitud.CedulaCliente))
                    throw new Exception("La cédula del cliente es requerida");

                if (string.IsNullOrWhiteSpace(solicitud.NombreCliente))
                    throw new Exception("El nombre del cliente es requerido");

                // Crear factura
                var factura = new Factura
                {
                    NumeroFactura = _facturaRepository.GenerarNumeroFactura(),
                    CedulaCliente = solicitud.CedulaCliente,
                    NombreCliente = solicitud.NombreCliente,
                    FormaPago = "EFECTIVO",
                    FechaEmision = DateTime.Now
                };

                decimal subtotal = 0;
                var detalles = new List<DetalleFactura>();

                // Procesar cada item
                foreach (var item in solicitud.Items)
                {
                    var producto = _productoRepository.GetById(item.ProductoId);
                    if (producto == null)
                        throw new Exception($"Producto con ID {item.ProductoId} no encontrado");

                    if (!_productoRepository.TieneStock(item.ProductoId, item.Cantidad))
                        throw new Exception($"Stock insuficiente para el producto {producto.Nombre}");

                    var subtotalItem = producto.Precio * item.Cantidad;
                    subtotal += subtotalItem;

                    var detalle = new DetalleFactura
                    {
                        ProductoId = item.ProductoId,
                        Cantidad = item.Cantidad,
                        PrecioUnitario = producto.Precio,
                        Subtotal = subtotalItem
                    };

                    detalles.Add(detalle);

                    // Actualizar stock
                    _productoRepository.ActualizarStock(item.ProductoId, item.Cantidad);
                }

                // Aplicar descuento del 33%
                factura.Subtotal = subtotal;
                factura.Descuento = subtotal * 0.33m;
                factura.Total = subtotal - factura.Descuento;
                factura.Detalles = detalles;

                // Guardar factura
                var facturaCreada = _facturaRepository.Create(factura);

                // Mapear a DTO
                return MapearFacturaADTO(facturaCreada);
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al generar factura en efectivo: {ex.Message}");
            }
        }

        /// <summary>
        /// Genera una factura con pago a crédito (consume servicio SOAP BanQuito)
        /// </summary>
        public FacturaDTO GenerarFacturaCredito(SolicitudFacturaDTO solicitud)
        {
            try
            {
                // Validaciones
                if (solicitud == null || solicitud.Items == null || !solicitud.Items.Any())
                    throw new Exception("La solicitud de factura no contiene productos");

                if (string.IsNullOrWhiteSpace(solicitud.CedulaCliente))
                    throw new Exception("La cédula del cliente es requerida");

                if (string.IsNullOrWhiteSpace(solicitud.NombreCliente))
                    throw new Exception("El nombre del cliente es requerido");

                if (solicitud.NumeroCuotas < 3 || solicitud.NumeroCuotas > 24)
                    throw new Exception("El número de cuotas debe estar entre 3 y 24");

                // Calcular monto total de la compra
                decimal montoTotal = 0;
                var productosFactura = new List<(Producto producto, int cantidad)>();

                foreach (var item in solicitud.Items)
                {
                    var producto = _productoRepository.GetById(item.ProductoId);
                    if (producto == null)
                        throw new Exception($"Producto con ID {item.ProductoId} no encontrado");

                    if (!_productoRepository.TieneStock(item.ProductoId, item.Cantidad))
                        throw new Exception($"Stock insuficiente para el producto {producto.Nombre}");

                    montoTotal += producto.Precio * item.Cantidad;
                    productosFactura.Add((producto, item.Cantidad));
                }

                // ========================================
                // CONSUMIR SERVICIOS SOAP DE BANQUITO
                // ========================================

                // 1. Validar si el cliente es sujeto de crédito
                var validacionCredito = _banquitoClient.ValidarSujetoCredito(solicitud.CedulaCliente);
                if (!validacionCredito.EsValido)
                {
                    throw new Exception($"Cliente no es sujeto de crédito: {validacionCredito.Mensaje}");
                }

                // 2. Obtener monto máximo de crédito
                var montoMaximoResponse = _banquitoClient.ObtenerMontoMaximo(solicitud.CedulaCliente);
                if (montoTotal > montoMaximoResponse.MontoMaximo)
                {
                    throw new Exception($"El monto de la compra (${montoTotal:N2}) excede el monto máximo aprobado (${montoMaximoResponse.MontoMaximo:N2})");
                }

                // 3. Otorgar el crédito
                var solicitudCredito = new SolicitudCreditoDTO
                {
                    Cedula = solicitud.CedulaCliente,
                    PrecioElectrodomestico = montoTotal,
                    NumeroCuotas = solicitud.NumeroCuotas
                };

                var respuestaCredito = _banquitoClient.OtorgarCredito(solicitudCredito);
                if (!respuestaCredito.Exito)
                {
                    throw new Exception($"Error al otorgar crédito: {respuestaCredito.Mensaje}");
                }

                string numeroCredito = respuestaCredito.NumeroCredito;

                // ========================================
                // CREAR FACTURA
                // ========================================

                var factura = new Factura
                {
                    NumeroFactura = _facturaRepository.GenerarNumeroFactura(),
                    CedulaCliente = solicitud.CedulaCliente,
                    NombreCliente = solicitud.NombreCliente,
                    FormaPago = "CREDITO",
                    Subtotal = montoTotal,
                    Descuento = 0, // No hay descuento en crédito
                    Total = montoTotal,
                    NumeroCredito = numeroCredito,
                    FechaEmision = DateTime.Now
                };

                var detalles = new List<DetalleFactura>();

                // Crear detalles y actualizar stock
                foreach (var (producto, cantidad) in productosFactura)
                {
                    var detalle = new DetalleFactura
                    {
                        ProductoId = producto.ProductoId,
                        Cantidad = cantidad,
                        PrecioUnitario = producto.Precio,
                        Subtotal = producto.Precio * cantidad
                    };

                    detalles.Add(detalle);

                    // Actualizar stock
                    _productoRepository.ActualizarStock(producto.ProductoId, cantidad);
                }

                factura.Detalles = detalles;

                // Guardar factura
                var facturaCreada = _facturaRepository.Create(factura);

                // Mapear a DTO
                return MapearFacturaADTO(facturaCreada);
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al generar factura a crédito: {ex.Message}");
            }
        }

        /// <summary>
        /// Obtener facturas por cédula de cliente
        /// </summary>
        public List<FacturaDTO> ObtenerFacturasPorCliente(string cedula)
        {
            var facturas = _facturaRepository.GetByCedulaCliente(cedula);
            return facturas.Select(f => MapearFacturaADTO(f)).ToList();
        }

        /// <summary>
        /// Obtener factura por número
        /// </summary>
        public FacturaDTO ObtenerFacturaPorNumero(string numeroFactura)
        {
            var factura = _facturaRepository.GetByNumeroFactura(numeroFactura);
            return factura != null ? MapearFacturaADTO(factura) : null;
        }

        // Mapear Factura a DTO
        private FacturaDTO MapearFacturaADTO(Factura factura)
        {
            var facturaDto = new FacturaDTO
            {
                FacturaId = factura.FacturaId,
                NumeroFactura = factura.NumeroFactura,
                CedulaCliente = factura.CedulaCliente,
                NombreCliente = factura.NombreCliente,
                FormaPago = factura.FormaPago,
                Subtotal = factura.Subtotal,
                Descuento = factura.Descuento,
                Total = factura.Total,
                NumeroCredito = factura.NumeroCredito,
                FechaEmision = factura.FechaEmision,
                Detalles = new List<DetalleFacturaDTO>()
            };

            if (factura.Detalles != null)
            {
                foreach (var detalle in factura.Detalles)
                {
                    var producto = _productoRepository.GetById(detalle.ProductoId);
                    facturaDto.Detalles.Add(new DetalleFacturaDTO
                    {
                        ProductoId = detalle.ProductoId,
                        NombreProducto = producto?.Nombre ?? "Producto no encontrado",
                        Cantidad = detalle.Cantidad,
                        PrecioUnitario = detalle.PrecioUnitario,
                        Subtotal = detalle.Subtotal
                    });
                }
            }

            return facturaDto;
        }
    }
}