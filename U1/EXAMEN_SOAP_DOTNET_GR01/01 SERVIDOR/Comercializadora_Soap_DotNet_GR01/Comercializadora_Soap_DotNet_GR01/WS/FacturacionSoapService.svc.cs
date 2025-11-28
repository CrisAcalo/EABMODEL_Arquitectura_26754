using System;
using System.Collections.Generic;
using Comercializadora_Soap_DotNet_GR01.DTOs;
using Comercializadora_Soap_DotNet_GR01.Services;

namespace Comercializadora_Soap_DotNet_GR01.WS
{
    public class FacturacionSoapService : IFacturacionSoapService
    {
        private readonly FacturacionService _facturacionService;

        public FacturacionSoapService()
        {
            _facturacionService = new FacturacionService();
        }

        public CalculoFacturaDTO CalcularTotalFactura(SolicitudCalculoDTO solicitud)
        {
            // El servicio ya maneja los errores internamente y retorna un DTO con mensaje
            return _facturacionService.CalcularTotalFactura(solicitud);
        }

        public FacturaDTO GenerarFactura(SolicitudFacturaDTO solicitud)
        {
            try
            {
                return _facturacionService.GenerarFactura(solicitud);
            }
            catch (Exception ex)
            {
                // Devolver DTO con error en lugar de lanzar excepción
                return new FacturaDTO
                {
                    NumeroFactura = "ERROR",
                    CedulaCliente = solicitud?.CedulaCliente,
                    NombreCliente = $"ERROR: {ex.Message}",
                    FormaPago = "ERROR",
                    Subtotal = 0,
                    Descuento = 0,
                    Total = 0,
                    FechaEmision = DateTime.Now,
                    Detalles = new List<DetalleFacturaDTO>()
                };
            }
        }

        public List<FacturaDTO> ObtenerFacturasPorCliente(string cedula)
        {
            try
            {
                return _facturacionService.ObtenerFacturasPorCliente(cedula);
            }
            catch (Exception ex)
            {
                // Devolver lista vacía en caso de error
                return new List<FacturaDTO>();
            }
        }

        public FacturaDTO ObtenerFacturaPorNumero(string numeroFactura)
        {
            try
            {
                return _facturacionService.ObtenerFacturaPorNumero(numeroFactura);
            }
            catch (Exception ex)
            {
                // Devolver null en caso de error (factura no encontrada)
                return null;
            }
        }
    }
}