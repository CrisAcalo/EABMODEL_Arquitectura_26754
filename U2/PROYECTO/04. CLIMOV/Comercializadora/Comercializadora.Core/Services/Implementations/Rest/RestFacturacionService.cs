using Comercializadora.Core.Managers;
using Comercializadora.Core.Models.Comercializadora;
using Comercializadora.Core.Models.Comercializadora.Requests;
using Comercializadora.Core.Models.Comercializadora.Responses;
using Comercializadora.Core.Services.Abstractions;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using System.Text;

namespace Comercializadora.Core.Services.Implementations.Rest
{
    public class RestFacturacionService : IFacturacionService
    {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly IConfiguration _configuration;
        private readonly ILogger<RestFacturacionService>? _logger;
        private ApiPlatform _currentTarget = ApiPlatform.Java;

        public RestFacturacionService(IHttpClientFactory httpClientFactory, IConfiguration configuration, ILogger<RestFacturacionService>? logger = null)
        {
            _httpClientFactory = httpClientFactory;
            _configuration = configuration;
            _logger = logger;
        }

        public void SetTarget(ApiPlatform target)
        {
            _currentTarget = target;
            _logger?.LogInformation("REST FacturacionService target set to: {Target}", target);
        }

        public async Task<CalculationResponse> CalcularTotalFacturaAsync(CalculationRequest request)
        {
            try
            {
                _logger?.LogInformation("Calculando total factura");
                
                var result = await PostAsync<CalculationRequest, CalculationResponse>(request, "facturas/calcular");

                if (result != null)
                {
                    _logger?.LogInformation("Cálculo exitoso. Total: {Total}", result.TotalCalculado);
                    return result;
                }

                return new CalculationResponse { TotalCalculado = 0, Descuento = 0, Subtotal = 0 };
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en CalcularTotalFacturaAsync");
                throw;
            }
        }

        public async Task<InvoiceDto?> GenerarFacturaAsync(InvoiceGenerationRequest request)
        {
            try
            {
                _logger?.LogInformation("Generando factura para cliente: {Cedula}", request.CedulaCliente);

                var result = await PostAsync<InvoiceGenerationRequest, InvoiceDto>(request, "facturas");

                if (result != null)
                {
                    _logger?.LogInformation("Factura generada exitosamente. Número: {Numero}", result.NumeroFactura);
                }
                else
                {
                    _logger?.LogError("Error al generar factura: respuesta nula");
                }

                return result;
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en GenerarFacturaAsync");
                throw;
            }
        }

        public async Task<InvoiceDto?> ObtenerFacturaPorNumeroAsync(string numeroFactura)
        {
            try
            {
                _logger?.LogInformation("Buscando factura: {Numero}", numeroFactura);
                return await GetAsync<InvoiceDto>($"facturas/{numeroFactura}");
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en ObtenerFacturaPorNumeroAsync");
                return null;
            }
        }

        public async Task<IEnumerable<InvoiceDto>> ObtenerFacturasPorClienteAsync(string cedula)
        {
            try
            {
                _logger?.LogInformation("Buscando facturas para cliente: {Cedula}", cedula);
                return await GetAsync<IEnumerable<InvoiceDto>>($"facturas/cliente/{cedula}") ?? Enumerable.Empty<InvoiceDto>();
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en ObtenerFacturasPorClienteAsync");
                return Enumerable.Empty<InvoiceDto>();
            }
        }

        // --- Helpers privados ---

        private string GetBaseUrl()
        {
            string hostKey = _currentTarget == ApiPlatform.Java 
                ? "Hosts:Comercializadora:Rest:Java" 
                : "Hosts:Comercializadora:Rest:DotNet";
            
            var url = _configuration[hostKey];
            
            if (string.IsNullOrWhiteSpace(url))
                throw new InvalidOperationException($"URL no configurada en {hostKey}");
                
            return url;
        }

        private async Task<T?> GetAsync<T>(string endpoint) where T : class
        {
            var httpClient = _httpClientFactory.CreateClient("ComercializadoraClient");
            var baseUrl = GetBaseUrl();
            var fullUrl = $"{baseUrl}/api/{endpoint}";

            try 
            {
                var response = await httpClient.GetAsync(fullUrl);
                if (!response.IsSuccessStatusCode) return null;

                var content = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<T>(content);
            }
            catch(Exception ex)
            {
                _logger?.LogError(ex, "Error GET {Url}", fullUrl);
                return null;
            }
        }

        private async Task<TResponse?> PostAsync<TRequest, TResponse>(TRequest data, string endpoint) where TResponse : class
        {
            var httpClient = _httpClientFactory.CreateClient("ComercializadoraClient");
            var baseUrl = GetBaseUrl();
            var fullUrl = $"{baseUrl}/api/{endpoint}";

            try
            {
                var json = JsonConvert.SerializeObject(data);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                
                var response = await httpClient.PostAsync(fullUrl, content);
                var responseContent = await response.Content.ReadAsStringAsync();

                if (!response.IsSuccessStatusCode)
                {
                     _logger?.LogWarning("POST fallido {StatusCode}: {Content}", response.StatusCode, responseContent);
                     return null;
                }

                return JsonConvert.DeserializeObject<TResponse>(responseContent);
            }
            catch(Exception ex)
            {
                _logger?.LogError(ex, "Error POST {Url}", fullUrl);
                return null;
            }
        }
    }
}