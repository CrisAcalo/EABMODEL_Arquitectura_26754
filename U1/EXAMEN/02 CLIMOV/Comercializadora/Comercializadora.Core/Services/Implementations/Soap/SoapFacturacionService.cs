// En: Comercializadora.Core/Services/Implementations/Soap/SoapFacturacionService.cs
using Comercializadora.Core.Models.Comercializadora.Requests;
using Comercializadora.Core.Models.Comercializadora.Responses;
using Comercializadora.Core.Services.Abstractions;
using Microsoft.Extensions.Configuration;
using System.ServiceModel;

// Importamos el namespace del cliente SOAP generado
using ComercializadoraSoapBilling;

namespace Comercializadora.Core.Services.Implementations.Soap
{
    public class SoapFacturacionService : IFacturacionService
    {
        private readonly IConfiguration _configuration;
        private static readonly BasicHttpBinding Binding = new BasicHttpBinding { MaxReceivedMessageSize = 2147483647 };

        public SoapFacturacionService(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        private FacturacionSoapServiceClient GetClient()
        {
            var url = _configuration["Hosts:Comercializadora:Soap:DotNet:Billing"]; // Usaremos esta clave en appsettings
            if (string.IsNullOrEmpty(url))
                throw new InvalidOperationException("La URL para FacturacionSoapService no está configurada.");

            return new FacturacionSoapServiceClient(Binding, new EndpointAddress(url));
        }

        public async Task<CalculationResponse> CalcularTotalFacturaAsync(CalculationRequest request)
        {
            try
            {
                var client = GetClient();
                // Mapeamos nuestra lista de items al tipo generado por WCF
                var soapRequest = new SolicitudCalculoDTO
                {
                    Items = request.Items.Select(item => new ItemFacturaDTO
                    {
                        ProductoId = item.ProductoId,
                        Cantidad = item.Cantidad
                    }).ToArray()
                };

                var soapResponse = await client.CalcularTotalFacturaAsync(soapRequest);

                return new CalculationResponse
                {
                    Exitoso = soapResponse.Exitoso,
                    Mensaje = soapResponse.Mensaje,
                    Total = soapResponse.Total,
                    Detalles = soapResponse.Detalles?.Select(d => new InvoiceDetailDto
                    {
                        ProductoId = d.ProductoId,
                        NombreProducto = d.NombreProducto,
                        Cantidad = d.Cantidad,
                        PrecioUnitario = d.PrecioUnitario,
                        Subtotal = d.Subtotal
                    }).ToList() ?? new List<InvoiceDetailDto>()
                };
            }
            catch (Exception ex)
            {
                return new CalculationResponse { Exitoso = false, Mensaje = $"Error de conexión: {ex.Message}" };
            }
        }

        public async Task<InvoiceDto?> GenerarFacturaAsync(InvoiceGenerationRequest request)
        {
            try
            {
                var client = GetClient();
                var soapRequest = new SolicitudFacturaDTO
                {
                    CedulaCliente = request.CedulaCliente,
                    NombreCliente = request.NombreCliente,
                    FormaPago = request.FormaPago,
                    NumeroCredito = request.NumeroCredito,
                    Items = request.Items.Select(item => new ItemFacturaDTO
                    {
                        ProductoId = item.ProductoId,
                        Cantidad = item.Cantidad
                    }).ToArray()
                };

                var soapResponse = await client.GenerarFacturaAsync(soapRequest);

                // Si la respuesta indica un error (ej. NumeroFactura == "ERROR"), devolvemos null
                if (soapResponse == null || soapResponse.NumeroFactura == "ERROR")
                {
                    // Opcional: Podríamos lanzar una excepción con el mensaje de error
                    // throw new Exception(soapResponse?.NombreCliente);
                    return null;
                }

                return MapInvoice(soapResponse);
            }
            catch (Exception)
            {
                return null;
            }
        }

        public async Task<InvoiceDto?> ObtenerFacturaPorNumeroAsync(string numeroFactura)
        {
            try
            {
                var client = GetClient();
                var soapResponse = await client.ObtenerFacturaPorNumeroAsync(numeroFactura);
                return MapInvoice(soapResponse);
            }
            catch (Exception)
            {
                return null;
            }
        }

        public async Task<IEnumerable<InvoiceDto>> ObtenerFacturasPorClienteAsync(string cedula)
        {
            try
            {
                var client = GetClient();
                var soapResponse = await client.ObtenerFacturasPorClienteAsync(cedula);

                if (soapResponse == null) return Enumerable.Empty<InvoiceDto>();

                return soapResponse.Select(MapInvoice).Where(f => f != null).ToList()!;
            }
            catch (Exception)
            {
                return Enumerable.Empty<InvoiceDto>();
            }
        }

        // --- MÉTODO DE MAPEO AUXILIAR ---
        private InvoiceDto? MapInvoice(FacturaDTO? soapInvoice)
        {
            if (soapInvoice == null) return null;

            return new InvoiceDto
            {
                FacturaId = soapInvoice.FacturaId,
                NumeroFactura = soapInvoice.NumeroFactura,
                FechaEmision = soapInvoice.FechaEmision,
                CedulaCliente = soapInvoice.CedulaCliente,
                NombreCliente = soapInvoice.NombreCliente,
                FormaPago = soapInvoice.FormaPago,
                NumeroCredito = soapInvoice.NumeroCredito,
                Subtotal = soapInvoice.Subtotal,
                Descuento = soapInvoice.Descuento,
                Total = soapInvoice.Total,
                Detalles = soapInvoice.Detalles?.Select(d => new InvoiceDetailDto
                {
                    ProductoId = d.ProductoId,
                    NombreProducto = d.NombreProducto,
                    Cantidad = d.Cantidad,
                    PrecioUnitario = d.PrecioUnitario,
                    Subtotal = d.Subtotal
                }).ToList() ?? new List<InvoiceDetailDto>()
            };
        }
    }
}