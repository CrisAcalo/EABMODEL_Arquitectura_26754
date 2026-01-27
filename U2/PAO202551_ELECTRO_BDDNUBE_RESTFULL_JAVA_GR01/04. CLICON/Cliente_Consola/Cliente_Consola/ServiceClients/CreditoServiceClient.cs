using Comer_CliCon_SOAP_DotNet_GR01.Models;

namespace Comer_CliCon_SOAP_DotNet_GR01.ServiceClients
{
    /// <summary>
    /// Cliente REST para el servicio de Créditos (BanQuito Java)
    /// URL Base: http://localhost:8080/api/creditos
    /// </summary>
    public class CreditoServiceClient : RestClientBase
    {
        private const string BaseUrl = "http://localhost:8080/BanquitoServer_Restfull_Java_GR01/";

        public CreditoServiceClient() : base(BaseUrl) { }

        /// <summary>
        /// Validar si una persona es sujeto de crédito
        /// GET /api/creditos/validar/{cedula}
        /// </summary>
        public ValidacionCreditoDTO ValidarSujetoCredito(string cedula)
        {
            try
            {
                var resultado = GetAsync<ValidacionCreditoDTO>($"api/creditos/validar/{cedula}").Result;
                return resultado ?? new ValidacionCreditoDTO { EsValido = false, Mensaje = "Error de conexión" };
            }
            catch (Exception ex)
            {
                return new ValidacionCreditoDTO { EsValido = false, Mensaje = ex.Message };
            }
        }

        /// <summary>
        /// Obtener monto máximo de crédito
        /// GET /api/creditos/monto-maximo/{cedula}
        /// </summary>
        public MontoMaximoCreditoDTO ObtenerMontoMaximo(string cedula)
        {
            try
            {
                var resultado = GetAsync<MontoMaximoCreditoDTO>($"api/creditos/monto-maximo/{cedula}").Result;
                return resultado ?? new MontoMaximoCreditoDTO { MontoMaximo = 0, Mensaje = "Error de conexión" };
            }
            catch (Exception ex)
            {
                return new MontoMaximoCreditoDTO { MontoMaximo = 0, Mensaje = ex.Message };
            }
        }

        /// <summary>
        /// Otorgar un crédito
        /// POST /api/creditos
        /// </summary>
        public RespuestaCreditoDTO OtorgarCredito(SolicitudCreditoDTO solicitud)
        {
            try
            {
                var resultado = PostAsync<RespuestaCreditoDTO>("api/creditos", solicitud).Result;
                return resultado ?? new RespuestaCreditoDTO { Exito = false, Mensaje = "Error de conexión" };
            }
            catch (Exception ex)
            {
                return new RespuestaCreditoDTO { Exito = false, Mensaje = ex.Message };
            }
        }

        /// <summary>
        /// Obtener tabla de amortización de un crédito
        /// GET /api/creditos/{numeroCredito}/amortizacion
        /// </summary>
        public List<CuotaAmortizacionDTO> ObtenerTablaAmortizacion(string numeroCredito)
        {
            try
            {
                return GetAsync<List<CuotaAmortizacionDTO>>($"api/creditos/{numeroCredito}/amortizacion").Result ?? new List<CuotaAmortizacionDTO>();
            }
            catch
            {
                return new List<CuotaAmortizacionDTO>();
            }
        }
    }
}
