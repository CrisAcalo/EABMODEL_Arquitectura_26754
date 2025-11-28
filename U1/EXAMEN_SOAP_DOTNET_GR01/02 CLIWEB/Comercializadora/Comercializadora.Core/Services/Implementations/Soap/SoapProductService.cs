// En: Comercializadora.Core/Services/Implementations/Soap/SoapProductService.cs
using Comercializadora.Core.Managers;
using Comercializadora.Core.Models.Comercializadora;
using Comercializadora.Core.Models.Common;
using Comercializadora.Core.Services.Abstractions;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using System.ServiceModel;

// Importamos el namespace del cliente SOAP generado
using ComercializadoraSoapProducts;

namespace Comercializadora.Core.Services.Implementations.Soap
{
    public class SoapProductService : IProductService
    {
        private readonly IConfiguration _configuration;
        private readonly ILogger<SoapProductService>? _logger;
        private ApiPlatform _currentTarget = ApiPlatform.DotNet;
        private static readonly BasicHttpBinding Binding = new BasicHttpBinding
        {
            MaxReceivedMessageSize = 2147483647,
            MaxBufferSize = 2147483647,
            SendTimeout = TimeSpan.FromMinutes(5),
            ReceiveTimeout = TimeSpan.FromMinutes(5)
        };

        public SoapProductService(IConfiguration configuration, ILogger<SoapProductService>? logger = null)
        {
            _configuration = configuration;
            _logger = logger;
        }

        public void SetTarget(ApiPlatform target)
        {
            _currentTarget = target;
            _logger?.LogInformation("SOAP ProductService target set to: {Target}", target);
        }

        // --- MÉTODOS DE CONSULTA ---
        public async Task<IEnumerable<ProductDto>> ObtenerProductosAsync()
        {
            try
            {
                _logger?.LogInformation("Iniciando ObtenerProductosAsync con target: {Target}", _currentTarget);
                
                var client = GetDotNetClient();
                _logger?.LogInformation("Cliente SOAP creado exitosamente");
                
                var soapResponse = await client.ObtenerProductosAsync();
                _logger?.LogInformation("Respuesta SOAP recibida. Cantidad de productos: {Count}", soapResponse?.Count() ?? 0);
                
                var result = MapProductList(soapResponse);
                _logger?.LogInformation("Productos mapeados exitosamente. Total: {Count}", result.Count());
                
                return result;
            }
            catch (ArgumentNullException ex)
            {
                _logger?.LogError(ex, "Error de configuración - URL nula para target: {Target}", _currentTarget);
                return Enumerable.Empty<ProductDto>();
            }
            catch (EndpointNotFoundException ex)
            {
                _logger?.LogError(ex, "No se pudo conectar al endpoint SOAP: {Target}", _currentTarget);
                return Enumerable.Empty<ProductDto>();
            }
            catch (TimeoutException ex)
            {
                _logger?.LogError(ex, "Timeout al conectar con el servicio SOAP: {Target}", _currentTarget);
                return Enumerable.Empty<ProductDto>();
            }
            catch (CommunicationException ex)
            {
                _logger?.LogError(ex, "Error de comunicación SOAP: {Target}", _currentTarget);
                return Enumerable.Empty<ProductDto>();
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error inesperado en ObtenerProductosAsync: {Target}", _currentTarget);
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
                
                var client = GetDotNetClient();
                var soapResponse = await client.ObtenerProductoPorCodigoAsync(codigo);
                
                var result = MapSingleProduct(soapResponse);
                _logger?.LogInformation("Producto encontrado: {Found}", result != null);
                
                return result;
            }
            catch (ArgumentNullException ex)
            {
                _logger?.LogError(ex, "Error de configuración en ObtenerProductoPorCodigoAsync");
                return null;
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
                
                var client = GetDotNetClient();
                var soapResponse = await client.ObtenerProductosPorCategoriaAsync(categoria.ToString());
                
                var result = MapProductList(soapResponse);
                _logger?.LogInformation("Productos encontrados por categoría {Categoria}: {Count}", categoria, result.Count());
                
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
                
                var client = GetDotNetClient();
                var soapResponse = await client.ObtenerProductosPorPrecioAsync(precioMin, precioMax);
                
                var result = MapProductList(soapResponse);
                _logger?.LogInformation("Productos encontrados en rango de precio: {Count}", result.Count());
                
                return result;
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error en ObtenerProductosPorPrecioAsync para rango: {Min} - {Max}", precioMin, precioMax);
                return Enumerable.Empty<ProductDto>();
            }
        }

        // --- MÉTODOS CRUD ---
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
                
                var client = GetDotNetClient();
                var soapRequest = new ComercializadoraSoapProducts.CrearProductoDTO
                {
                    Codigo = producto.Codigo,
                    Nombre = producto.Nombre,
                    Descripcion = producto.Descripcion,
                    Precio = producto.Precio,
                    Stock = producto.Stock,
                    Categoria = producto.Categoria,
                    ImagenUrl = producto.ImagenUrl
                };
                
                var soapResponse = await client.CrearProductoAsync(soapRequest);
                var result = MapServiceResponse(soapResponse);
                
                _logger?.LogInformation("Producto creado - Éxito: {Exito}, Mensaje: {Mensaje}", result.Exito, result.Mensaje);
                
                return result;
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
                
                var client = GetDotNetClient();
                var soapRequest = new ComercializadoraSoapProducts.ActualizarProductoDTO
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
                
                var soapResponse = await client.ActualizarProductoAsync(soapRequest);
                var result = MapServiceResponse(soapResponse);
                
                _logger?.LogInformation("Producto actualizado - Éxito: {Exito}, Mensaje: {Mensaje}", result.Exito, result.Mensaje);
                
                return result;
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
                
                var client = GetDotNetClient();
                var soapResponse = await client.EliminarProductoAsync(id);
                var result = MapServiceResponse<object>(soapResponse);
                
                _logger?.LogInformation("Producto eliminado - Éxito: {Exito}, Mensaje: {Mensaje}", result.Exito, result.Mensaje);
                
                return result;
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

        // --- CLIENT FACTORY Y MÉTODOS DE MAPEO ---
        private ProductoSoapServiceClient GetDotNetClient()
        {
            try
            {
                string configKey = "Hosts:Comercializadora:Soap:DotNet:Products";
                var url = _configuration[configKey];
                
                _logger?.LogInformation("Configuración SOAP buscada en: {ConfigKey}", configKey);
                _logger?.LogInformation("URL SOAP obtenida: {Url}", url ?? "NULL");
                
                // Log adicional para debugging de configuración SOAP
                _logger?.LogInformation("Target actual SOAP: DotNet");
                
                // Verificar si existe la sección Hosts
                var hostsSection = _configuration.GetSection("Hosts");
                if (!hostsSection.Exists())
                {
                    _logger?.LogError("❌ La sección 'Hosts' no existe en la configuración SOAP");
                }
                else
                {
                    _logger?.LogInformation("✅ Sección 'Hosts' encontrada para SOAP");
                    
                    // Verificar sección Comercializadora
                    var comercializadoraSection = hostsSection.GetSection("Comercializadora");
                    if (!comercializadoraSection.Exists())
                    {
                        _logger?.LogError("❌ La sección 'Hosts:Comercializadora' no existe para SOAP");
                    }
                    else
                    {
                        _logger?.LogInformation("✅ Sección 'Hosts:Comercializadora' encontrada para SOAP");
                        
                        // Verificar sección Soap
                        var soapSection = comercializadoraSection.GetSection("Soap");
                        if (!soapSection.Exists())
                        {
                            _logger?.LogError("❌ La sección 'Hosts:Comercializadora:Soap' no existe");
                        }
                        else
                        {
                            _logger?.LogInformation("✅ Sección 'Hosts:Comercializadora:Soap' encontrada");
                            
                            // Verificar sección DotNet
                            var dotNetSection = soapSection.GetSection("DotNet");
                            if (!dotNetSection.Exists())
                            {
                                _logger?.LogError("❌ La sección 'Hosts:Comercializadora:Soap:DotNet' no existe");
                            }
                            else
                            {
                                _logger?.LogInformation("✅ Sección 'Hosts:Comercializadora:Soap:DotNet' encontrada");
                                
                                // Log de todas las URLs SOAP disponibles
                                var productsUrl = dotNetSection["Products"];
                                _logger?.LogInformation("📡 Products SOAP URL disponible: {ProductsUrl}", productsUrl ?? "NULL");
                                
                                // Verificar también la sección Java
                                var javaSection = soapSection.GetSection("Java");
                                if (javaSection.Exists())
                                {
                                    var javaProductsUrl = javaSection["Products"];
                                    _logger?.LogInformation("☕ Java Products SOAP URL disponible: {JavaProductsUrl}", javaProductsUrl ?? "NULL");
                                }
                            }
                        }
                    }
                }
                
                if (string.IsNullOrWhiteSpace(url))
                {
                    var errorMsg = $"URL del servicio SOAP no configurada en '{configKey}'. Verifique appsettings.json";
                    _logger?.LogError("❌ {Error}", errorMsg);
                    throw new InvalidOperationException(errorMsg);
                }

                if (!Uri.IsWellFormedUriString(url, UriKind.Absolute))
                {
                    var errorMsg = $"URL del servicio SOAP no es válida: '{url}'";
                    _logger?.LogError("❌ {Error}", errorMsg);
                    throw new InvalidOperationException(errorMsg);
                }

                _logger?.LogInformation("✅ Creando cliente SOAP para URL: {Url}", url);
                
                var endpoint = new EndpointAddress(url);
                var client = new ProductoSoapServiceClient(Binding, endpoint);
                
                _logger?.LogInformation("✅ Cliente SOAP creado exitosamente");
                
                return client;
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "❌ Error al crear cliente SOAP");
                throw;
            }
        }

        private IEnumerable<ProductDto> MapProductList(IEnumerable<ComercializadoraSoapProducts.ProductoDTO>? soapProducts)
        {
            try
            {
                if (soapProducts == null)
                {
                    _logger?.LogWarning("Lista de productos SOAP es nula");
                    return Enumerable.Empty<ProductDto>();
                }

                var result = soapProducts.Select(MapSingleProduct).Where(p => p != null).Cast<ProductDto>().ToList();
                _logger?.LogInformation("Productos mapeados: {Count} de {Total}", result.Count, soapProducts.Count());
                
                return result;
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error al mapear lista de productos");
                return Enumerable.Empty<ProductDto>();
            }
        }

        private ProductDto? MapSingleProduct(ComercializadoraSoapProducts.ProductoDTO? soapProduct)
        {
            try
            {
                if (soapProduct == null)
                {
                    return null;
                }

                return new ProductDto
                {
                    ProductoId = soapProduct.ProductoId,
                    Codigo = soapProduct.Codigo ?? string.Empty,
                    Nombre = soapProduct.Nombre ?? string.Empty,
                    Descripcion = soapProduct.Descripcion,
                    Precio = soapProduct.Precio,
                    Stock = soapProduct.Stock,
                    Categoria = soapProduct.Categoria,
                    ImagenUrl = soapProduct.ImagenUrl,
                    FechaRegistro = soapProduct.FechaRegistro,
                    Estado = soapProduct.Estado ?? "ACTIVO"
                };
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error al mapear producto individual");
                return null;
            }
        }

        private ServiceResponse<ProductDto> MapServiceResponse(ComercializadoraSoapProducts.RespuestaDTO? soapResponse)
        {
            try
            {
                if (soapResponse == null)
                {
                    return new ServiceResponse<ProductDto>
                    {
                        Exito = false,
                        Mensaje = "Respuesta del servicio SOAP es nula"
                    };
                }

                var response = new ServiceResponse<ProductDto>
                {
                    Exito = soapResponse.Exito,
                    Mensaje = soapResponse.Mensaje ?? (soapResponse.Exito ? "Operación exitosa" : "Error en la operación")
                };

                if (soapResponse.Exito && soapResponse.Datos is ComercializadoraSoapProducts.ProductoDTO productData)
                {
                    response.Datos = MapSingleProduct(productData);
                }

                return response;
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error al mapear respuesta del servicio");
                return new ServiceResponse<ProductDto>
                {
                    Exito = false,
                    Mensaje = $"Error al procesar respuesta: {ex.Message}"
                };
            }
        }

        private ServiceResponse MapServiceResponse<T>(ComercializadoraSoapProducts.RespuestaDTO? soapResponse)
        {
            try
            {
                if (soapResponse == null)
                {
                    return new ServiceResponse
                    {
                        Exito = false,
                        Mensaje = "Respuesta del servicio SOAP es nula"
                    };
                }

                return new ServiceResponse
                {
                    Exito = soapResponse.Exito,
                    Mensaje = soapResponse.Mensaje ?? (soapResponse.Exito ? "Operación exitosa" : "Error en la operación")
                };
            }
            catch (Exception ex)
            {
                _logger?.LogError(ex, "Error al mapear respuesta del servicio");
                return new ServiceResponse
                {
                    Exito = false,
                    Mensaje = $"Error al procesar respuesta: {ex.Message}"
                };
            }
        }
    }
}