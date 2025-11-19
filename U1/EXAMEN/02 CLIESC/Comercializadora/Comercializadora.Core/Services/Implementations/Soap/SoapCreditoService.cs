// En: Comercializadora.Core/Services/Implementations/Soap/SoapCreditoService.cs
// Importamos el namespace del cliente SOAP generado
using BanQuitoSoapCredit;
using Comercializadora.Core.Managers;
using Comercializadora.Core.Models.BanQuito.Requests;
using Comercializadora.Core.Models.BanQuito.Responses;
using Comercializadora.Core.Services.Abstractions;
using Microsoft.Extensions.Configuration;
using System.Globalization;
using System.ServiceModel;

namespace Comercializadora.Core.Services.Implementations.Soap
{
    public class SoapCreditoService : ICreditoService
    {
        private readonly IConfiguration _configuration;
        private static readonly BasicHttpBinding Binding = new BasicHttpBinding { MaxReceivedMessageSize = 2147483647 };

        public SoapCreditoService(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        private CreditoSoapServiceClient GetClient()
        {
            var url = _configuration["Hosts:BanQuito:Soap:DotNet:Credito"];
            if (string.IsNullOrEmpty(url))
                throw new InvalidOperationException("La URL para CreditoSoapService no está configurada.");

            return new CreditoSoapServiceClient(Binding, new EndpointAddress(url));
        }

        public async Task<CreditValidationResponse> ValidarSujetoCreditoAsync(string cedula)
        {
            try
            {
                var client = GetClient();
                var soapResponse = await client.ValidarSujetoCreditoAsync(cedula);

                return new CreditValidationResponse
                {
                    EsValido = soapResponse.EsValido,
                    Mensaje = soapResponse.Mensaje,
                    Cedula = soapResponse.Cedula,
                    NombreCompleto = soapResponse.NombreCompleto
                };
            }
            catch (Exception ex)
            {
                // Devolvemos un objeto de respuesta de fallo en caso de error de conexión
                return new CreditValidationResponse { EsValido = false, Mensaje = $"Error de conexión: {ex.Message}" };
            }
        }

        public async Task<MaxCreditAmountResponse> ObtenerMontoMaximoAsync(string cedula)
        {
            try
            {
                var client = GetClient();
                var soapResponse = await client.ObtenerMontoMaximoAsync(cedula);

                return new MaxCreditAmountResponse
                {
                    MontoMaximo = soapResponse.MontoMaximo,
                    Mensaje = soapResponse.Mensaje,
                    Cedula = soapResponse.Cedula,
                    PromedioDepositos = soapResponse.PromedioDepositos,
                    PromedioRetiros = soapResponse.PromedioRetiros
                };
            }
            catch (Exception ex)
            {
                return new MaxCreditAmountResponse { MontoMaximo = 0, Mensaje = $"Error de conexión: {ex.Message}" };
            }
        }

        public async Task<CreditGrantResponse> OtorgarCreditoAsync(CreditGrantRequest request)
        {
            try
            {
                var client = GetClient();
                // Mapeamos nuestro objeto de petición genérico al tipo específico generado por WCF
                var soapRequest = new SolicitudCreditoDTO
                {
                    Cedula = request.Cedula,
                    PrecioElectrodomestico = request.PrecioElectrodomestico.ToString(CultureInfo.InvariantCulture),
                    NumeroCuotas = request.NumeroCuotas.ToString()
                };

                var soapResponse = await client.OtorgarCreditoAsync(soapRequest);

                return new CreditGrantResponse
                {
                    Exito = soapResponse.Exito,
                    Mensaje = soapResponse.Mensaje,
                    NumeroCredito = soapResponse.NumeroCredito,
                    MontoCredito = soapResponse.MontoCredito,
                    NumeroCuotas = soapResponse.NumeroCuotas,
                    TasaInteres = soapResponse.TasaInteres,
                    CuotaMensual = soapResponse.CuotaMensual,
                    // Mapeamos la lista de cuotas
                    TablaAmortizacion = soapResponse.TablaAmortizacion?.Select(c => new AmortizationItemDto
                    {
                        NumeroCuota = c.NumeroCuota,
                        ValorCuota = c.ValorCuota,
                        Interes = c.Interes,
                        CapitalPagado = c.CapitalPagado,
                        Saldo = c.Saldo
                    }).ToList()
                };
            }
            catch (Exception ex)
            {
                return new CreditGrantResponse { Exito = false, Mensaje = $"Error de conexión: {ex.Message}" };
            }
        }

        public async Task<IEnumerable<AmortizationItemDto>> ObtenerTablaAmortizacionAsync(string numeroCredito)
        {
            try
            {
                var client = GetClient();
                var soapResponse = await client.ObtenerTablaAmortizacionAsync(numeroCredito);

                if (soapResponse == null) return Enumerable.Empty<AmortizationItemDto>();

                // Mapeamos la lista de cuotas generada a nuestra lista de DTOs unificados
                return soapResponse.Select(c => new AmortizationItemDto
                {
                    NumeroCuota = c.NumeroCuota,
                    ValorCuota = c.ValorCuota,
                    Interes = c.Interes,
                    CapitalPagado = c.CapitalPagado,
                    Saldo = c.Saldo
                }).ToList();
            }
            catch (Exception)
            {
                return Enumerable.Empty<AmortizationItemDto>();
            }
        }
    }
}