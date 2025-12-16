using EurekaBank.Core.Managers;
using EurekaBank.Core.Models.DTOs;
using EurekaBank.Core.Models.Requests;
using EurekaBank.Core.Models.Responses;
using EurekaBank.Core.Services.Abstractions;
using Microsoft.Extensions.Configuration;
using System.Net.Http.Json;
using System.Text;
using System.Text.Json;

namespace EurekaBank.Core.Services.Implementations
{
    public class RestSucursalService : ISucursalService
    {
        private readonly HttpClient _httpClient;
        private readonly IConfiguration _configuration;
        private ApiPlatform _currentTarget = ApiPlatform.Java;

        public RestSucursalService(HttpClient httpClient, IConfiguration configuration)
        {
            _httpClient = httpClient;
            _configuration = configuration;
        }

        public void SetTarget(ApiPlatform target)
        {
            _currentTarget = target;
        }

        private string GetBaseUrl()
        {
            return _currentTarget == ApiPlatform.Java
                ? _configuration["Hosts:Rest:Java"] ?? ""
                : _configuration["Hosts:Rest:DotNet"] ?? "";
        }

        private string GetSucursalEndpoint()
        {
            return _currentTarget == ApiPlatform.Java ? "api/sucursal" : "api/Sucursal";
        }

        public async Task<SucursalesListResponse> ObtenerSucursalesAsync()
        {
            try
            {
                var baseUrl = GetBaseUrl();
                var endpoint = GetSucursalEndpoint();
                var url = $"{baseUrl}/{endpoint}";

                System.Diagnostics.Debug.WriteLine($"=== REST SUCURSALES GET ALL ===");
                System.Diagnostics.Debug.WriteLine($"URL: {url}");
                System.Diagnostics.Debug.WriteLine($"Target: {_currentTarget}");

                var response = await _httpClient.GetAsync(url);
                var jsonContent = await response.Content.ReadAsStringAsync();

                System.Diagnostics.Debug.WriteLine($"Response Status: {response.StatusCode}");
                System.Diagnostics.Debug.WriteLine($"Response Content: {jsonContent}");

                if (response.IsSuccessStatusCode)
                {
                    var result = JsonSerializer.Deserialize<SucursalesListResponse>(jsonContent, new JsonSerializerOptions
                    {
                        PropertyNameCaseInsensitive = true
                    });

                    return result ?? new SucursalesListResponse
                    {
                        Exitoso = false,
                        Mensaje = "Error al deserializar la respuesta"
                    };
                }
                else
                {
                    // Intentar deserializar error
                    try
                    {
                        var errorResponse = JsonSerializer.Deserialize<SucursalesListResponse>(jsonContent, new JsonSerializerOptions
                        {
                            PropertyNameCaseInsensitive = true
                        });
                        return errorResponse ?? new SucursalesListResponse
                        {
                            Exitoso = false,
                            Mensaje = $"Error HTTP: {response.StatusCode}"
                        };
                    }
                    catch
                    {
                        return new SucursalesListResponse
                        {
                            Exitoso = false,
                            Mensaje = $"Error HTTP: {response.StatusCode}"
                        };
                    }
                }
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error en ObtenerSucursalesAsync: {ex.Message}");
                return new SucursalesListResponse
                {
                    Exitoso = false,
                    Mensaje = $"Error de conexión: {ex.Message}"
                };
            }
        }

        public async Task<SucursalResponse> ObtenerSucursalAsync(int codigo)
        {
            try
            {
                var baseUrl = GetBaseUrl();
                var endpoint = GetSucursalEndpoint();
                var url = $"{baseUrl}/{endpoint}/{codigo}";

                System.Diagnostics.Debug.WriteLine($"=== REST SUCURSAL GET BY ID ===");
                System.Diagnostics.Debug.WriteLine($"URL: {url}");
                System.Diagnostics.Debug.WriteLine($"Codigo: {codigo}");

                var response = await _httpClient.GetAsync(url);
                var jsonContent = await response.Content.ReadAsStringAsync();

                System.Diagnostics.Debug.WriteLine($"Response Status: {response.StatusCode}");
                System.Diagnostics.Debug.WriteLine($"Response Content: {jsonContent}");

                var result = JsonSerializer.Deserialize<SucursalResponse>(jsonContent, new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                });

                return result ?? new SucursalResponse
                {
                    Exitoso = false,
                    Mensaje = "Error al deserializar la respuesta"
                };
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error en ObtenerSucursalAsync: {ex.Message}");
                return new SucursalResponse
                {
                    Exitoso = false,
                    Mensaje = $"Error de conexión: {ex.Message}"
                };
            }
        }

        public async Task<SucursalResponse> CrearSucursalAsync(CreateSucursalRequest request)
        {
            try
            {
                var baseUrl = GetBaseUrl();
                var endpoint = GetSucursalEndpoint();
                var url = $"{baseUrl}/{endpoint}";

                System.Diagnostics.Debug.WriteLine($"=== REST SUCURSAL CREATE ===");
                System.Diagnostics.Debug.WriteLine($"URL: {url}");
                System.Diagnostics.Debug.WriteLine($"Request: {JsonSerializer.Serialize(request)}");

                var json = JsonSerializer.Serialize(request);
                var content = new StringContent(json, Encoding.UTF8, "application/json");

                var response = await _httpClient.PostAsync(url, content);
                var jsonContent = await response.Content.ReadAsStringAsync();

                System.Diagnostics.Debug.WriteLine($"Response Status: {response.StatusCode}");
                System.Diagnostics.Debug.WriteLine($"Response Content: {jsonContent}");

                var result = JsonSerializer.Deserialize<SucursalResponse>(jsonContent, new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                });

                return result ?? new SucursalResponse
                {
                    Exitoso = false,
                    Mensaje = "Error al deserializar la respuesta"
                };
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error en CrearSucursalAsync: {ex.Message}");
                return new SucursalResponse
                {
                    Exitoso = false,
                    Mensaje = $"Error de conexión: {ex.Message}"
                };
            }
        }

        public async Task<SucursalResponse> ActualizarSucursalAsync(int codigo, UpdateSucursalRequest request)
        {
            try
            {
                var baseUrl = GetBaseUrl();
                var endpoint = GetSucursalEndpoint();
                var url = $"{baseUrl}/{endpoint}/{codigo}";

                System.Diagnostics.Debug.WriteLine($"=== REST SUCURSAL UPDATE ===");
                System.Diagnostics.Debug.WriteLine($"URL: {url}");
                System.Diagnostics.Debug.WriteLine($"Codigo: {codigo}");
                System.Diagnostics.Debug.WriteLine($"Request: {JsonSerializer.Serialize(request)}");

                var json = JsonSerializer.Serialize(request);
                var content = new StringContent(json, Encoding.UTF8, "application/json");

                var response = await _httpClient.PatchAsync(url, content);
                var jsonContent = await response.Content.ReadAsStringAsync();

                System.Diagnostics.Debug.WriteLine($"Response Status: {response.StatusCode}");
                System.Diagnostics.Debug.WriteLine($"Response Content: {jsonContent}");

                var result = JsonSerializer.Deserialize<SucursalResponse>(jsonContent, new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                });

                return result ?? new SucursalResponse
                {
                    Exitoso = false,
                    Mensaje = "Error al deserializar la respuesta"
                };
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error en ActualizarSucursalAsync: {ex.Message}");
                return new SucursalResponse
                {
                    Exitoso = false,
                    Mensaje = $"Error de conexión: {ex.Message}"
                };
            }
        }

        public async Task<DeleteSucursalResponse> EliminarSucursalAsync(int codigo)
        {
            try
            {
                var baseUrl = GetBaseUrl();
                var endpoint = GetSucursalEndpoint();
                var url = $"{baseUrl}/{endpoint}/{codigo}";

                System.Diagnostics.Debug.WriteLine($"=== REST SUCURSAL DELETE ===");
                System.Diagnostics.Debug.WriteLine($"URL: {url}");
                System.Diagnostics.Debug.WriteLine($"Codigo: {codigo}");

                var response = await _httpClient.DeleteAsync(url);
                var jsonContent = await response.Content.ReadAsStringAsync();

                System.Diagnostics.Debug.WriteLine($"Response Status: {response.StatusCode}");
                System.Diagnostics.Debug.WriteLine($"Response Content: {jsonContent}");

                var result = JsonSerializer.Deserialize<DeleteSucursalResponse>(jsonContent, new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                });

                return result ?? new DeleteSucursalResponse
                {
                    Exitoso = false,
                    Mensaje = "Error al deserializar la respuesta"
                };
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error en EliminarSucursalAsync: {ex.Message}");
                return new DeleteSucursalResponse
                {
                    Exitoso = false,
                    Mensaje = $"Error de conexión: {ex.Message}"
                };
            }
        }
    }
}