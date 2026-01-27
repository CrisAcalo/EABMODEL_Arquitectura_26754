using System.Net.Http.Json;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace Comer_CliCon_SOAP_DotNet_GR01.ServiceClients
{
    /// <summary>
    /// Clase base para clientes REST con HttpClient compartido
    /// </summary>
    public abstract class RestClientBase : IDisposable
    {
        protected readonly HttpClient _httpClient;
        protected static readonly JsonSerializerOptions JsonOptions = new()
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            PropertyNameCaseInsensitive = true,
            DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull
        };

        protected RestClientBase(string baseUrl)
        {
            _httpClient = new HttpClient
            {
                BaseAddress = new Uri(baseUrl),
                Timeout = TimeSpan.FromSeconds(30)
            };
            _httpClient.DefaultRequestHeaders.Accept.Add(
                new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));
        }

        protected async Task<T?> GetAsync<T>(string endpoint)
        {
            var response = await _httpClient.GetAsync(endpoint);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<T>(JsonOptions);
        }

        protected async Task<T?> PostAsync<T>(string endpoint, object? content = null)
        {
            var response = await _httpClient.PostAsJsonAsync(endpoint, content, JsonOptions);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<T>(JsonOptions);
        }

        protected async Task<T?> PatchAsync<T>(string endpoint, object content)
        {
            var json = JsonSerializer.Serialize(content, JsonOptions);
            var httpContent = new StringContent(json, System.Text.Encoding.UTF8, "application/json");
            var response = await _httpClient.PatchAsync(endpoint, httpContent);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<T>(JsonOptions);
        }

        protected async Task<bool> DeleteAsync(string endpoint)
        {
            var response = await _httpClient.DeleteAsync(endpoint);
            return response.IsSuccessStatusCode;
        }

        public void Dispose()
        {
            _httpClient?.Dispose();
            GC.SuppressFinalize(this);
        }
    }
}
