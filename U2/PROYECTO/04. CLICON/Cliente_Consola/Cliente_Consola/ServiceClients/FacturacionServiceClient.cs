using System.Net.Http.Json;
using Comer_CliCon_SOAP_DotNet_GR01.Models;

namespace Comer_CliCon_SOAP_DotNet_GR01.ServiceClients
{
    /// <summary>
    /// Cliente REST para el servicio de Facturación (Comercializadora Java)
    /// URL Base: http://localhost:8081/api/facturas
    /// </summary>
    public class FacturacionServiceClient : RestClientBase
    {
        private const string BaseUrl = "http://localhost:8081/Comercializadora_RestFul_Java_GR01/";

        public FacturacionServiceClient() : base(BaseUrl) { }

        /// <summary>
        /// Calcular total de factura SIN generarla
        /// POST /api/facturas/calcular
        /// </summary>
        public CalculoFacturaDTO CalcularTotalFactura(SolicitudCalculoDTO solicitud)
        {
            try
            {
                var resultado = PostAsync<CalculoFacturaDTO>("api/facturas/calcular", solicitud).Result;
                return resultado ?? new CalculoFacturaDTO { Exitoso = false, Mensaje = "Error al calcular" };
            }
            catch (Exception ex)
            {
                return new CalculoFacturaDTO { Exitoso = false, Mensaje = ex.Message };
            }
        }

        /// <summary>
        /// Generar factura
        /// POST /api/facturas
        /// </summary>
        public FacturaDTO GenerarFactura(SolicitudFacturaDTO solicitud)
        {
            try
            {
                var response = _httpClient.PostAsJsonAsync("api/facturas", solicitud, JsonOptions).Result;
                
                if (response.IsSuccessStatusCode)
                {
                    var factura = response.Content.ReadFromJsonAsync<FacturaDTO>(JsonOptions).Result;
                    return factura ?? new FacturaDTO { NumeroFactura = "ERROR", NombreCliente = "Respuesta vacía del servidor" };
                }
                else
                {
                    var errorContent = response.Content.ReadAsStringAsync().Result;
                    return new FacturaDTO 
                    { 
                        NumeroFactura = "ERROR", 
                        NombreCliente = $"HTTP {(int)response.StatusCode}: {errorContent}" 
                    };
                }
            }
            catch (Exception ex)
            {
                return new FacturaDTO { NumeroFactura = "ERROR", NombreCliente = ex.InnerException?.Message ?? ex.Message };
            }
        }

        /// <summary>
        /// Obtener facturas por cliente
        /// GET /api/facturas/cliente/{cedula}
        /// </summary>
        public List<FacturaDTO> ObtenerFacturasPorCliente(string cedula)
        {
            try
            {
                return GetAsync<List<FacturaDTO>>($"api/facturas/cliente/{cedula}").Result ?? new List<FacturaDTO>();
            }
            catch
            {
                return new List<FacturaDTO>();
            }
        }

        /// <summary>
        /// Obtener factura por número
        /// GET /api/facturas/{numeroFactura}
        /// </summary>
        public FacturaDTO? ObtenerFacturaPorNumero(string numeroFactura)
        {
            try
            {
                return GetAsync<FacturaDTO>($"api/facturas/{numeroFactura}").Result;
            }
            catch
            {
                return null;
            }
        }
    }
}
