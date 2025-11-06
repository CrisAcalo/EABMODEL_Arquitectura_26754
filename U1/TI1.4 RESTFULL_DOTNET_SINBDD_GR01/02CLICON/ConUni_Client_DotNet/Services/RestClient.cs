using System;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using ConUni_Client_DotNet.Models;

namespace ConUni_Client_DotNet.Services
{
    /// <summary>
    /// Cliente REST para consumir servicios de conversión de unidades
    /// </summary>
    public class RestClient : IDisposable
    {
        private readonly HttpClient _httpClient;
        private readonly string _baseUrl;
        private readonly JsonSerializerOptions _jsonOptions;

        public RestClient(string baseUrl)
        {
            _baseUrl = baseUrl;
            _httpClient = new HttpClient
            {
                Timeout = TimeSpan.FromSeconds(30)
            };

            _jsonOptions = new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true,
                PropertyNamingPolicy = JsonNamingPolicy.CamelCase
            };
        }

        /// <summary>
        /// Conversión de longitud (Milla, Metro, Pulgada)
        /// </summary>
        public async Task<ConversionResult> ConvertirLongitud(string valor, string unidadOrigen, string unidadDestino)
        {
            return await Convertir("/Longitud/convertir", valor, unidadOrigen, unidadDestino);
        }

        /// <summary>
        /// Conversión de masa (Kilogramo, Quintal, Libra)
        /// </summary>
        public async Task<ConversionResult> ConvertirMasa(string valor, string unidadOrigen, string unidadDestino)
        {
            return await Convertir("/Masa/convertir", valor, unidadOrigen, unidadDestino);
        }

        /// <summary>
        /// Conversión de temperatura (Celsius, Fahrenheit, Kelvin)
        /// </summary>
        public async Task<ConversionResult> ConvertirTemperatura(string valor, string unidadOrigen, string unidadDestino)
        {
            return await Convertir("/Temperatura/convertir", valor, unidadOrigen, unidadDestino);
        }

        /// <summary>
        /// Método genérico para realizar conversiones
        /// </summary>
        private async Task<ConversionResult> Convertir(string endpoint, string valor, string unidadOrigen, string unidadDestino)
        {
            try
            {
                // Crear el request
                var request = new ConversionRequest(valor, unidadOrigen, unidadDestino);

                // Serializar a JSON
                string jsonContent = JsonSerializer.Serialize(request, _jsonOptions);
                var content = new StringContent(jsonContent, Encoding.UTF8, "application/json");

                // Hacer el POST request
                string url = _baseUrl + endpoint;
                HttpResponseMessage response = await _httpClient.PostAsync(url, content);

                // Leer la respuesta
                string responseBody = await response.Content.ReadAsStringAsync();

                // El servidor siempre devuelve 200 OK, incluso con errores de validación
                if (response.IsSuccessStatusCode)
                {
                    // Deserializar la respuesta
                    var result = JsonSerializer.Deserialize<ConversionResult>(responseBody, _jsonOptions);
                    return result ?? CreateErrorResult("Respuesta vacía del servidor");
                }
                else
                {
                    return CreateErrorResult($"Error HTTP: {response.StatusCode} - {responseBody}");
                }
            }
            catch (HttpRequestException ex)
            {
                return CreateErrorResult($"Error de conexión: {ex.Message}");
            }
            catch (TaskCanceledException ex)
            {
                return CreateErrorResult($"Timeout: {ex.Message}");
            }
            catch (Exception ex)
            {
                return CreateErrorResult($"Error inesperado: {ex.Message}");
            }
        }

        /// <summary>
        /// Crea un resultado de error
        /// </summary>
        private ConversionResult CreateErrorResult(string mensaje)
        {
            return new ConversionResult
            {
                Exitoso = false,
                Resultado = null,
                Error = new ConversionError
                {
                    CodigoError = "CLIENT_ERROR",
                    Mensaje = mensaje,
                    TipoError = "Cliente",
                    FechaError = DateTime.Now
                }
            };
        }

        public void Dispose()
        {
            _httpClient?.Dispose();
        }
    }
}