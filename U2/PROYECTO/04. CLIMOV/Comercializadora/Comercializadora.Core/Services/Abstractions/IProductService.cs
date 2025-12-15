// En: Comercializadora.Core/Services/Abstractions/IProductService.cs
using Comercializadora.Core.Models.Comercializadora;
using Comercializadora.Core.Models.Common;

namespace Comercializadora.Core.Services.Abstractions
{
    public interface IProductService
    {
        // Métodos de Consulta (devuelven listas o un solo producto)
        Task<IEnumerable<ProductDto>> ObtenerProductosAsync();
        Task<ProductDto?> ObtenerProductoPorCodigoAsync(string codigo);
        Task<IEnumerable<ProductDto>> ObtenerProductosPorCategoriaAsync(ProductCategory categoria);
        Task<IEnumerable<ProductDto>> ObtenerProductosPorPrecioAsync(decimal precioMin, decimal precioMax);

        // Métodos CRUD (devuelven una respuesta estándar)
        Task<ServiceResponse<ProductDto>> CrearProductoAsync(ProductDto producto);
        Task<ServiceResponse<ProductDto>> ActualizarProductoAsync(ProductDto producto);
        Task<ServiceResponse> EliminarProductoAsync(int id);
    }
}