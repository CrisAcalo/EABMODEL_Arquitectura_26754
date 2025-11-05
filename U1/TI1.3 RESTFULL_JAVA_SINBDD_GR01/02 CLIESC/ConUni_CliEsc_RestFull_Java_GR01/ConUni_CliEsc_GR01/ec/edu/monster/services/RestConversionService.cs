using System.Net.Http;
using System.Net.Http.Json;
using ConUni_CliEsc_GR01.ec.edu.monster.models;

namespace ConUni_CliEsc_GR01.ec.edu.monster.services;

/// <summary>
/// Implementación del servicio de conversión usando REST API
/// </summary>
public class RestConversionService : IConversionService
{
    private readonly HttpClient _httpClient;
    private readonly string _baseUrl;
    private readonly string _longitudPath;
    private readonly string _masaPath;
    private readonly string _temperaturaPath;

    public RestConversionService(string baseUrl, string longitudPath, string masaPath, string temperaturaPath)
    {
        _httpClient = new HttpClient
        {
            Timeout = TimeSpan.FromSeconds(30)
        };
        _baseUrl = baseUrl;
        _longitudPath = longitudPath;
        _masaPath = masaPath;
        _temperaturaPath = temperaturaPath;
    }

    public async Task<ConversionResultModel> ConvertirLongitudAsync(ConversionRequest request)
    {
        return await ConvertirAsync(request, _longitudPath);
    }

    public async Task<ConversionResultModel> ConvertirMasaAsync(ConversionRequest request)
    {
        return await ConvertirAsync(request, _masaPath);
    }

    public async Task<ConversionResultModel> ConvertirTemperaturaAsync(ConversionRequest request)
    {
        return await ConvertirAsync(request, _temperaturaPath);
    }

    private async Task<ConversionResultModel> ConvertirAsync(ConversionRequest request, string path)
    {
        try
        {
            var url = $"{_baseUrl}{path}";
            var response = await _httpClient.PostAsJsonAsync(url, request);

            if (response.IsSuccessStatusCode)
            {
                var result = await response.Content.ReadFromJsonAsync<ConversionResultModel>();
                return result ?? CreateErrorResult("Error al deserializar la respuesta del servidor");
            }
            else
            {
                var errorContent = await response.Content.ReadAsStringAsync();
                return CreateErrorResult($"Error HTTP {response.StatusCode}: {errorContent}");
            }
        }
        catch (HttpRequestException ex)
        {
            return CreateErrorResult($"Error de conexión: {ex.Message}");
        }
        catch (TaskCanceledException ex)
        {
            return CreateErrorResult($"Tiempo de espera agotado: {ex.Message}");
        }
        catch (Exception ex)
        {
            return CreateErrorResult($"Error inesperado: {ex.Message}");
        }
    }

    private ConversionResultModel CreateErrorResult(string mensaje)
    {
        return new ConversionResultModel
        {
            Exitoso = false,
            Error = new ConversionErrorModel
            {
                CodigoError = "CLIENT_ERROR",
                Mensaje = mensaje,
                TipoError = "Sistema",
                FechaError = DateTime.Now,
                Detalles = "Error generado en el cliente al comunicarse con la API"
            }
        };
    }
}
