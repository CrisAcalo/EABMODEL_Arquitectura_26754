using Comer_CliCon_SOAP_DotNet_GR01.Models;

namespace Comer_CliCon_SOAP_DotNet_GR01.ServiceClients
{
    /// <summary>
    /// Cliente REST para el servicio de Productos (Comercializadora Java)
    /// URL Base: http://localhost:8081/api/productos
    /// </summary>
    public class ProductoServiceClient : RestClientBase
    {
        private const string BaseUrl = "http://localhost:8081/Comercializadora_RestFul_Java_GR01/";

        public ProductoServiceClient() : base(BaseUrl) { }

        /// <summary>
        /// Obtener todos los productos activos
        /// GET /api/productos
        /// </summary>
        public List<ProductoDTO> ObtenerProductos()
        {
            try
            {
                var response = _httpClient.GetAsync("api/productos").Result;
                var jsonContent = response.Content.ReadAsStringAsync().Result;
                
                if (response.IsSuccessStatusCode)
                {
                    var productos = System.Text.Json.JsonSerializer.Deserialize<List<ProductoDTO>>(jsonContent, JsonOptions);
                    return productos ?? new List<ProductoDTO>();
                }
                else
                {
                    Console.WriteLine($"[DEBUG] Error HTTP: {response.StatusCode} - {jsonContent}");
                    return new List<ProductoDTO>();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"[DEBUG] Excepción: {ex.InnerException?.Message ?? ex.Message}");
                return new List<ProductoDTO>();
            }
        }

        /// <summary>
        /// Obtener producto por ID
        /// GET /api/productos/{id}
        /// </summary>
        public ProductoDTO? ObtenerProductoPorId(int id)
        {
            try
            {
                return GetAsync<ProductoDTO>($"api/productos/{id}").Result;
            }
            catch
            {
                return null;
            }
        }

        /// <summary>
        /// Obtener producto por código
        /// GET /api/productos/codigo/{codigo}
        /// </summary>
        public ProductoDTO? ObtenerProductoPorCodigo(string codigo)
        {
            try
            {
                return GetAsync<ProductoDTO>($"api/productos/codigo/{codigo}").Result;
            }
            catch
            {
                return null;
            }
        }

        /// <summary>
        /// Obtener productos por categoría
        /// GET /api/productos/categoria/{categoria}
        /// </summary>
        public List<ProductoDTO> ObtenerProductosPorCategoria(string categoria)
        {
            try
            {
                return GetAsync<List<ProductoDTO>>($"api/productos/categoria/{categoria}").Result ?? new List<ProductoDTO>();
            }
            catch
            {
                return new List<ProductoDTO>();
            }
        }

        /// <summary>
        /// Obtener productos por rango de precio
        /// GET /api/productos/precio?precioMin=X&precioMax=Y
        /// </summary>
        public List<ProductoDTO> ObtenerProductosPorPrecio(decimal precioMin, decimal precioMax)
        {
            try
            {
                return GetAsync<List<ProductoDTO>>($"api/productos/precio?precioMin={precioMin}&precioMax={precioMax}").Result ?? new List<ProductoDTO>();
            }
            catch
            {
                return new List<ProductoDTO>();
            }
        }

        /// <summary>
        /// Crear nuevo producto
        /// POST /api/productos
        /// </summary>
        public ProductoDTO CrearProducto(ProductoDTO producto)
        {
            try
            {
                var crearDto = new CrearProductoDTO
                {
                    Codigo = producto.Codigo,
                    Nombre = producto.Nombre,
                    Descripcion = producto.Descripcion,
                    Precio = producto.Precio,
                    Stock = producto.Stock,
                    Categoria = producto.Categoria
                };
                var respuesta = PostAsync<RespuestaDTO>("api/productos", crearDto).Result;
                if (respuesta?.Exito == true)
                {
                    // Retornar el producto recién creado
                    return producto;
                }
                return producto;
            }
            catch
            {
                return producto;
            }
        }

        /// <summary>
        /// Actualizar producto existente
        /// PATCH /api/productos/{id}
        /// </summary>
        public ProductoDTO ActualizarProducto(ProductoDTO producto)
        {
            try
            {
                var actualizarDto = new ActualizarProductoDTO
                {
                    Codigo = producto.Codigo,
                    Nombre = producto.Nombre,
                    Descripcion = producto.Descripcion,
                    Precio = producto.Precio,
                    Stock = producto.Stock,
                    Categoria = producto.Categoria
                };
                PatchAsync<RespuestaDTO>($"api/productos/{producto.ProductoId}", actualizarDto).Wait();
                return producto;
            }
            catch
            {
                return producto;
            }
        }

        /// <summary>
        /// Eliminar producto
        /// DELETE /api/productos/{id}
        /// </summary>
        public bool EliminarProducto(int id)
        {
            try
            {
                return DeleteAsync($"api/productos/{id}").Result;
            }
            catch
            {
                return false;
            }
        }
    }
}
