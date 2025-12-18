using Comercializadora.Core.Managers;
using Comercializadora.Core.Models.BanQuito.Requests;
using Comercializadora.Core.Models.BanQuito.Responses;
using Comercializadora.Core.Services.Abstractions;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using System.Text;

namespace Comercializadora.Core.Services.Implementations.Rest
{
    public class RestCreditoService : ICreditoService
    {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly IConfiguration _configuration;
        private readonly ILogger<RestCreditoService>? _logger;
        private ApiPlatform _currentTarget = ApiPlatform.Java;

        public RestCreditoService(IHttpClientFactory httpClientFactory, IConfiguration configuration, ILogger<RestCreditoService>? logger = null)
        {
            _httpClientFactory = httpClientFactory;
            _configuration = configuration;
            _logger = logger;
        }

        public void SetTarget(ApiPlatform target)
        {
            _currentTarget = target;
            _logger?.LogInformation("REST CreditoService target set to: {Target}", target);
        }

        public async Task<CreditValidationResponse> ValidarSujetoCreditoAsync(string cedula)
        {
            try
            {
                _logger?.LogInformation("Validando sujeto de crédito: {Cedula}", cedula);
                var result = await GetAsync<CreditValidationResponse>($"creditos/validar/{cedula}");
                
                return result ?? new CreditValidationResponse { EsValido = false, Mensaje = "Error de comunicación" };
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en ValidarSujetoCreditoAsync");
                return new CreditValidationResponse { EsValido = false, Mensaje = ex.Message };
            }
        }

        public async Task<MaxCreditAmountResponse> ObtenerMontoMaximoAsync(string cedula)
        {
            try
            {
                _logger?.LogInformation("Obteniendo monto máximo: {Cedula}", cedula);
                var result = await GetAsync<MaxCreditAmountResponse>($"creditos/monto-maximo/{cedula}");
                
                return result ?? new MaxCreditAmountResponse { MontoMaximo = 0, Mensaje = "Error de comunicación" };
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en ObtenerMontoMaximoAsync");
                return new MaxCreditAmountResponse { MontoMaximo = 0, Mensaje = ex.Message };
            }
        }

        public async Task<CreditGrantResponse> OtorgarCreditoAsync(CreditGrantRequest request)
        {
            try
            {
                _logger?.LogInformation("Solicitando crédito para: {Cedula}, Monto: {Monto}", request.Cedula, request.PrecioElectrodomestico);
                var result = await PostAsync<CreditGrantRequest, CreditGrantResponse>(request, "creditos");

                return result ?? new CreditGrantResponse { Exito = false, Mensaje = "Error de comunicación" };
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en OtorgarCreditoAsync");
                return new CreditGrantResponse { Exito = false, Mensaje = ex.Message };
            }
        }

        public async Task<IEnumerable<AmortizationItemDto>> ObtenerTablaAmortizacionAsync(string numeroCredito)
        {
             try
            {
                _logger?.LogInformation("Obteniendo tabla de amortización: {Numero}", numeroCredito);
                return await GetAsync<IEnumerable<AmortizationItemDto>>($"creditos/{numeroCredito}/amortizacion") 
                       ?? Enumerable.Empty<AmortizationItemDto>();
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en ObtenerTablaAmortizacionAsync");
                return Enumerable.Empty<AmortizationItemDto>();
            }
        }

        // --- Helpers privados ---

        private string GetBaseUrl()
        {
            // Nota: Aquí la clave es Hosts:BanQuito:Rest:Java
            string hostKey = _currentTarget == ApiPlatform.Java 
                ? "Hosts:BanQuito:Rest:Java" 
                : "Hosts:BanQuito:Rest:DotNet";
            
            var url = _configuration[hostKey];
            
            if (string.IsNullOrWhiteSpace(url))
                throw new InvalidOperationException($"URL no configurada en {hostKey}");
                
            return url;
        }

        private async Task<T?> GetAsync<T>(string endpoint) where T : class
        {
            var httpClient = _httpClientFactory.CreateClient("BanQuitoClient"); // Podría usarse un cliente nombrado diferente si se configura
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
            var httpClient = _httpClientFactory.CreateClient("BanQuitoClient");
            var baseUrl = GetBaseUrl();
            var fullUrl = $"{baseUrl}/api/{endpoint}";

            try
            {
                var json = JsonConvert.SerializeObject(data);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                
                var response = await httpClient.PostAsync(fullUrl, content);
                var responseContent = await response.Content.ReadAsStringAsync();
                
                // Log para debug de errores de validación
                if (!response.IsSuccessStatusCode)
                {
                    _logger?.LogWarning("POST fallido {StatusCode}: {Content}", response.StatusCode, responseContent);
                     // Intentar deserializar incluso si falla, porque Banquito devuelve mensajes de error en el mismo DTO
                     try {
                        return JsonConvert.DeserializeObject<TResponse>(responseContent);
                     } catch {
                        return null;
                     }
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