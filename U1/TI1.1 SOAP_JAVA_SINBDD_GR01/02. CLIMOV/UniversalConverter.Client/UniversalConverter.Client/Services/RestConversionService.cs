using System;
using System.Collections.Generic;
using System.Net.Http.Json;
using System.Text;
using UniversalConverter.Client.Models;
using Microsoft.Extensions.Configuration;

namespace UniversalConverter.Client.Services
{
    public class RestConversionService : IConversionService
    {
        private readonly HttpClient _httpClient;
        private readonly IConfiguration _configuration;

        // URLs base para cada servidor REST
        private string JavaBaseUrl => $"http://{_configuration["Hosts"]}:8081/ConUni_Restfull_Java_GR01/api";
        private string DotNetBaseUrl => $"http://{_configuration["Hosts"]}:8083/api";

        // Se puede cambiar dinámicamente
        private string _currentBaseUrl;

        public RestConversionService(IConfiguration configuration)
        {
            _httpClient = new HttpClient();
            _configuration = configuration;
            // Por defecto, apuntamos a Java. Esto se puede configurar.
            _currentBaseUrl = JavaBaseUrl;
        }

        public async Task<ConversionResponse> ConvertAsync(ConversionRequest request)
        {
            string endpoint = GetEndpointForConversionType(request.TipoConversion);
            var fullUrl = $"{_currentBaseUrl}/{endpoint}";

            // Para .NET, el endpoint es diferente
            if (_currentBaseUrl == DotNetBaseUrl)
            {
                fullUrl += "/convertir";
            }

            try
            {
                var response = await _httpClient.PostAsJsonAsync(fullUrl, request);
                response.EnsureSuccessStatusCode();

                var conversionResponse = await response.Content.ReadFromJsonAsync<ConversionResponse>();
                return conversionResponse;
            }
            catch (Exception ex)
            {
                // Manejar errores de conexión, etc.
                return new ConversionResponse
                {
                    Exitoso = false,
                    Error = new ErrorData { Mensaje = $"Error de conexión: {ex.Message}" }
                };
            }
        }

        private string GetEndpointForConversionType(ConversionType type)
        {
            return type switch
            {
                ConversionType.Longitud => "longitud",
                ConversionType.Masa => "masa",
                ConversionType.Temperatura => "temperatura",
                _ => throw new ArgumentOutOfRangeException(nameof(type), $"Tipo no soportado: {type}")
            };
        }

        // Método para cambiar de servidor (Java <-> .NET)
        public void SetTarget(ApiTarget target)
        {
            _currentBaseUrl = target == ApiTarget.Java ? JavaBaseUrl : DotNetBaseUrl;
        }
    }

    public enum ApiTarget { Java, DotNet }
}
