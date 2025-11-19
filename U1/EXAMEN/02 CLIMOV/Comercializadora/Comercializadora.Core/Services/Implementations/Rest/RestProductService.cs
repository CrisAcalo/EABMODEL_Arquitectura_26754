// En: Comercializadora.Core/Services/Implementations/Rest/RestProductService.cs
using Comercializadora.Core.Managers;
using Comercializadora.Core.Models.Comercializadora;
using Comercializadora.Core.Models.Common;
using Comercializadora.Core.Services.Abstractions;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using System.Text;

namespace Comercializadora.Core.Services.Implementations.Rest
{
    public class RestProductService : IProductService
    {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly IConfiguration _configuration;
        private readonly ILogger<RestProductService>? _logger;
        private ApiPlatform _currentTarget = ApiPlatform.Java;

        public RestProductService(IHttpClientFactory httpClientFactory, IConfiguration configuration, ILogger<RestProductService>? logger = null)
        {
            _httpClientFactory = httpClientFactory;
            _configuration = configuration;
            _logger = logger;
        }

        public void SetTarget(ApiPlatform target)
        {
            _currentTarget = target;
            _logger?.LogInformation("REST ProductService target set to: {Target}", target);
        }

        // --- MÉTODOS PÚBLICOS DE LA INTERFAZ ---

        public async Task<IEnumerable<ProductDto>> ObtenerProductosAsync()
        {
            try
            {
                _logger?.LogInformation("Iniciando ObtenerProductosAsync con target: {Target}", _currentTarget);
                
                var result = await GetAsync<IEnumerable<ProductDto>>("productos/activos") ?? Enumerable.Empty<ProductDto>();
                
                _logger?.LogInformation("Productos obtenidos: {Count}", result.Count());
                return result;
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en ObtenerProductosAsync");
                return Enumerable.Empty<ProductDto>();
            }
        }

        public async Task<ProductDto?> ObtenerProductoPorCodigoAsync(string codigo)
        {
            try
            {
                if (string.IsNullOrWhiteSpace(codigo))
                {
                    _logger?.LogWarning("Código de producto nulo o vacío");
                    return null;
                }

                _logger?.LogInformation("Buscando producto por código: {Codigo}", codigo);
                
                var result = await GetAsync<ProductDto>($"productos/codigo/{codigo}");
                
                _logger?.LogInformation("Producto encontrado: {Found}", result != null);
                return result;
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en ObtenerProductoPorCodigoAsync para código: {Codigo}", codigo);
                return null;
            }
        }

        public async Task<IEnumerable<ProductDto>> ObtenerProductosPorCategoriaAsync(ProductCategory categoria)
        {
            try
            {
                _logger?.LogInformation("Buscando productos por categoría: {Categoria}", categoria);
                
                var result = await GetAsync<IEnumerable<ProductDto>>($"productos/categoria/{categoria}") ?? Enumerable.Empty<ProductDto>();
                
                _logger?.LogInformation("Productos encontrados por categoría: {Count}", result.Count());
                return result;
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en ObtenerProductosPorCategoriaAsync para categoría: {Categoria}", categoria);
                return Enumerable.Empty<ProductDto>();
            }
        }

        public async Task<IEnumerable<ProductDto>> ObtenerProductosPorPrecioAsync(decimal precioMin, decimal precioMax)
        {
            try
            {
                if (precioMin < 0 || precioMax < 0 || precioMin > precioMax)
                {
                    _logger?.LogWarning("Rango de precios inválido: {Min} - {Max}", precioMin, precioMax);
                    return Enumerable.Empty<ProductDto>();
                }

                _logger?.LogInformation("Buscando productos por precio: {Min} - {Max}", precioMin, precioMax);
                
                var result = await GetAsync<IEnumerable<ProductDto>>($"productos/precio?min={precioMin}&max={precioMax}") ?? Enumerable.Empty<ProductDto>();
                
                _logger?.LogInformation("Productos encontrados en rango de precio: {Count}", result.Count());
                return result;
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en ObtenerProductosPorPrecioAsync para rango: {Min} - {Max}", precioMin, precioMax);
                return Enumerable.Empty<ProductDto>();
            }
        }

        public async Task<ServiceResponse<ProductDto>> CrearProductoAsync(ProductDto producto)
        {
            try
            {
                if (producto == null)
                {
                    return new ServiceResponse<ProductDto>
                    {
                        Exito = false,
                        Mensaje = "El producto no puede ser nulo"
                    };
                }

                if (string.IsNullOrWhiteSpace(producto.Codigo) || string.IsNullOrWhiteSpace(producto.Nombre))
                {
                    return new ServiceResponse<ProductDto>
                    {
                        Exito = false,
                        Mensaje = "El código y nombre del producto son requeridos"
                    };
                }

                _logger?.LogInformation("Creando producto: {Codigo} - {Nombre}", producto.Codigo, producto.Nombre);
                
                // Crear DTO específico para creación (sin FechaRegistro ni ProductoId)
                var createProductDto = new CreateProductDto
                {
                    Codigo = producto.Codigo,
                    Nombre = producto.Nombre,
                    Descripcion = producto.Descripcion,
                    Precio = producto.Precio,
                    Stock = producto.Stock,
                    Categoria = producto.Categoria,
                    ImagenUrl = producto.ImagenUrl,
                    Estado = producto.Estado
                };
                
                // Log específico de la URL completa que se usará
                var baseUrl = GetBaseUrl();
                var fullUrl = $"{baseUrl}/api/productos";
                _logger?.LogInformation("📡 URL COMPLETA PARA CREAR PRODUCTO: {FullUrl}", fullUrl);
                
                // Log específico del body JSON que se enviará (sin FechaRegistro)
                var jsonBody = JsonConvert.SerializeObject(createProductDto, Formatting.Indented);
                _logger?.LogInformation("📄 BODY JSON ENVIADO: {JsonBody}", jsonBody);

                var result = await PostAsync<CreateProductDto, ServiceResponse<ProductDto>>(createProductDto, "productos");
                
                if (result != null)
                {
                    _logger?.LogInformation("Producto creado - Éxito: {Exito}, Mensaje: {Mensaje}", result.Exito, result.Mensaje);
                    return result;
                }

                return new ServiceResponse<ProductDto>
                {
                    Exito = false,
                    Mensaje = "No se recibió respuesta del servidor"
                };
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en CrearProductoAsync para producto: {Codigo}", producto?.Codigo);
                return new ServiceResponse<ProductDto>
                {
                    Exito = false,
                    Mensaje = $"Error al crear producto: {ex.Message}"
                };
            }
        }

        public async Task<ServiceResponse<ProductDto>> ActualizarProductoAsync(ProductDto producto)
        {
            try
            {
                if (producto == null || producto.ProductoId <= 0)
                {
                    return new ServiceResponse<ProductDto>
                    {
                        Exito = false,
                        Mensaje = "Producto inválido o ID no válido"
                    };
                }

                _logger?.LogInformation("Actualizando producto ID: {Id} - {Codigo}", producto.ProductoId, producto.Codigo);
                
                // Crear DTO específico para actualización con formato JSON correcto
                var updateProductDto = new UpdateProductDto
                {
                    ProductoId = producto.ProductoId,
                    Codigo = producto.Codigo,
                    Nombre = producto.Nombre,
                    Descripcion = producto.Descripcion,
                    Precio = producto.Precio,
                    Stock = producto.Stock,
                    Categoria = producto.Categoria,
                    ImagenUrl = producto.ImagenUrl,
                    Estado = producto.Estado
                };
                
                var result = await PutAsync<UpdateProductDto, ServiceResponse<ProductDto>>(updateProductDto, $"productos/{producto.ProductoId}");
                
                if (result != null)
                {
                    _logger?.LogInformation("Producto actualizado - Éxito: {Exito}, Mensaje: {Mensaje}", result.Exito, result.Mensaje);
                    return result;
                }

                return new ServiceResponse<ProductDto>
                {
                    Exito = false,
                    Mensaje = "No se recibió respuesta del servidor"
                };
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en ActualizarProductoAsync para producto ID: {Id}", producto?.ProductoId);
                return new ServiceResponse<ProductDto>
                {
                    Exito = false,
                    Mensaje = $"Error al actualizar producto: {ex.Message}"
                };
            }
        }

        public async Task<ServiceResponse> EliminarProductoAsync(int id)
        {
            try
            {
                if (id <= 0)
                {
                    return new ServiceResponse
                    {
                        Exito = false,
                        Mensaje = "ID de producto no válido"
                    };
                }

                _logger?.LogInformation("Eliminando producto ID: {Id}", id);
                
                var result = await DeleteAsync<ServiceResponse>($"productos/{id}");
                
                if (result != null)
                {
                    _logger?.LogInformation("Producto eliminado - Éxito: {Exito}, Mensaje: {Mensaje}", result.Exito, result.Mensaje);
                    return result;
                }

                return new ServiceResponse
                {
                    Exito = false,
                    Mensaje = "No se recibió respuesta del servidor"
                };
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en EliminarProductoAsync para ID: {Id}", id);
                return new ServiceResponse
                {
                    Exito = false,
                    Mensaje = $"Error al eliminar producto: {ex.Message}"
                };
            }
        }

        // --- MÉTODOS AUXILIARES GENÉRICOS ---

        private string GetBaseUrl()
        {
            try
            {
                string hostKey = _currentTarget == ApiPlatform.Java 
                    ? "Hosts:Comercializadora:Rest:Java" 
                    : "Hosts:Comercializadora:Rest:DotNet";
                
                var url = _configuration[hostKey];
                
                _logger?.LogInformation("Configuración buscada en: {ConfigKey}", hostKey);
                _logger?.LogInformation("URL obtenida: {Url}", url ?? "NULL");
                
                // Log adicional para debugging
                _logger?.LogInformation("Target actual: {Target}", _currentTarget);
                _logger?.LogInformation("Todas las secciones de configuración disponibles:");
                
                // Verificar si existe la sección Hosts
                var hostsSection = _configuration.GetSection("Hosts");
                if (!hostsSection.Exists())
                {
                    _logger?.LogError("❌ La sección 'Hosts' no existe en la configuración");
                }
                else
                {
                    _logger?.LogInformation("✅ Sección 'Hosts' encontrada");
                    
                    // Verificar sección Comercializadora
                    var comercializadoraSection = hostsSection.GetSection("Comercializadora");
                    if (!comercializadoraSection.Exists())
                    {
                        _logger?.LogError("❌ La sección 'Hosts:Comercializadora' no existe");
                    }
                    else
                    {
                        _logger?.LogInformation("✅ Sección 'Hosts:Comercializadora' encontrada");
                        
                        // Verificar sección Rest
                        var restSection = comercializadoraSection.GetSection("Rest");
                        if (!restSection.Exists())
                        {
                            _logger?.LogError("❌ La sección 'Hosts:Comercializadora:Rest' no existe");
                        }
                        else
                        {
                            _logger?.LogInformation("✅ Sección 'Hosts:Comercializadora:Rest' encontrada");
                            
                            // Log de todas las URLs disponibles
                            var javaUrl = restSection["Java"];
                            var dotNetUrl = restSection["DotNet"];
                            _logger?.LogInformation("🌐 Java URL disponible: {JavaUrl}", javaUrl ?? "NULL");
                            _logger?.LogInformation("🔧 DotNet URL disponible: {DotNetUrl}", dotNetUrl ?? "NULL");
                        }
                    }
                }
                
                if (string.IsNullOrWhiteSpace(url))
                {
                    var errorMsg = $"URL del servicio REST no configurada en '{hostKey}'. Verifique appsettings.json";
                    _logger?.LogError("❌ {Error}", errorMsg);
                    throw new InvalidOperationException(errorMsg);
                }

                if (!Uri.IsWellFormedUriString(url, UriKind.Absolute))
                {
                    var errorMsg = $"URL del servicio REST no es válida: '{url}'";
                    _logger?.LogError("❌ {Error}", errorMsg);
                    throw new InvalidOperationException(errorMsg);
                }

                _logger?.LogInformation("✅ URL válida obtenida: {Url}", url);
                return url;
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "❌ Error al obtener URL base");
                throw;
            }
        }

        private async Task<T?> GetAsync<T>(string endpoint) where T : class
        {
            var httpClient = _httpClientFactory.CreateClient("ComercializadoraClient");
            string fullUrl = string.Empty;
            
            try
            {
                var baseUrl = GetBaseUrl();
                fullUrl = $"{baseUrl}/api/{endpoint}";
                
                _logger?.LogInformation("GET Request: {Url}", fullUrl);
                
                var response = await httpClient.GetAsync(fullUrl);
                
                _logger?.LogInformation("GET Response: {StatusCode} para {Url}", response.StatusCode, fullUrl);
                
                if (!response.IsSuccessStatusCode)
                {
                    var errorContent = await response.Content.ReadAsStringAsync();
                    _logger?.LogWarning("GET falló: {StatusCode} - {Content}", response.StatusCode, errorContent);
                    return null;
                }

                var jsonResponse = await response.Content.ReadAsStringAsync();
                _logger?.LogDebug("GET Response Content: {Content}", jsonResponse);
                
                return JsonConvert.DeserializeObject<T>(jsonResponse);
            }
            catch (HttpRequestException ex)
            {
                _logger?.LogError(ex, "Error de HTTP en GET: {Url}", fullUrl);
                return null;
            }
            catch (TaskCanceledException ex)
            {
                _logger?.LogError(ex, "Timeout en GET: {Url}", fullUrl);
                return null;
            }
            catch (JsonException ex)
            {
                _logger?.LogError(ex, "Error de JSON en GET: {Url}", fullUrl);
                return null;
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error inesperado en GET: {Url}", fullUrl);
                return null;
            }
        }

        private async Task<TResponse?> PostAsync<TRequest, TResponse>(TRequest data, string endpoint) where TResponse : class
        {
            var httpClient = _httpClientFactory.CreateClient("ComercializadoraClient");
            string fullUrl = string.Empty;
            
            try
            {
                var baseUrl = GetBaseUrl();
                fullUrl = $"{baseUrl}/api/{endpoint}";
                
                var jsonRequest = JsonConvert.SerializeObject(data);
                _logger?.LogInformation("POST Request: {Url} - Data: {Data}", fullUrl, jsonRequest);
                
                var content = new StringContent(jsonRequest, Encoding.UTF8, "application/json");
                var response = await httpClient.PostAsync(fullUrl, content);
                
                var jsonResponse = await response.Content.ReadAsStringAsync();
                _logger?.LogInformation("POST Response: {StatusCode} - {Content}", response.StatusCode, jsonResponse);
                
                return JsonConvert.DeserializeObject<TResponse>(jsonResponse);
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en POST: {Url}", fullUrl);
                return null;
            }
        }

        private async Task<TResponse?> PutAsync<TRequest, TResponse>(TRequest data, string endpoint) where TResponse : class
        {
            var httpClient = _httpClientFactory.CreateClient("ComercializadoraClient");
            string fullUrl = string.Empty;
            
            try
            {
                var baseUrl = GetBaseUrl();
                fullUrl = $"{baseUrl}/api/{endpoint}";
                
                var jsonRequest = JsonConvert.SerializeObject(data);
                _logger?.LogInformation("PUT Request: {Url} - Data: {Data}", fullUrl, jsonRequest);
                
                var content = new StringContent(jsonRequest, Encoding.UTF8, "application/json");
                var response = await httpClient.PutAsync(fullUrl, content);
                
                var jsonResponse = await response.Content.ReadAsStringAsync();
                _logger?.LogInformation("PUT Response: {StatusCode} - {Content}", response.StatusCode, jsonResponse);
                
                return JsonConvert.DeserializeObject<TResponse>(jsonResponse);
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en PUT: {Url}", fullUrl);
                return null;
            }
        }

        private async Task<TResponse?> DeleteAsync<TResponse>(string endpoint) where TResponse : class
        {
            var httpClient = _httpClientFactory.CreateClient("ComercializadoraClient");
            string fullUrl = string.Empty;
            
            try
            {
                var baseUrl = GetBaseUrl();
                fullUrl = $"{baseUrl}/api/{endpoint}";
                
                _logger?.LogInformation("DELETE Request: {Url}", fullUrl);
                
                var response = await httpClient.DeleteAsync(fullUrl);
                
                var jsonResponse = await response.Content.ReadAsStringAsync();
                _logger?.LogInformation("DELETE Response: {StatusCode} - {Content}", response.StatusCode, jsonResponse);
                
                return JsonConvert.DeserializeObject<TResponse>(jsonResponse);
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en DELETE: {Url}", fullUrl);
                return null;
            }
        }
    }
}