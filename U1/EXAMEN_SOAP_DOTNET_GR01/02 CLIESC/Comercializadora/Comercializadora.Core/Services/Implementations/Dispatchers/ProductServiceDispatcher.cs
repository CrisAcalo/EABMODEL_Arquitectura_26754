// En: Comercializadora.Core/Services/Implementations/Dispatchers/ProductServiceDispatcher.cs
using Comercializadora.Core.Managers;
using Comercializadora.Core.Models.Comercializadora;
using Comercializadora.Core.Models.Common;
using Comercializadora.Core.Services.Abstractions;
using Comercializadora.Core.Services.Implementations.Rest;
using Comercializadora.Core.Services.Implementations.Soap;

namespace Comercializadora.Core.Services.Implementations.Dispatchers
{
    public class ProductServiceDispatcher : IProductService
    {
        private readonly ApiServiceManager _apiManager;
        private readonly RestProductService _restService;
        private readonly SoapProductService _soapService;

        public ProductServiceDispatcher(
            ApiServiceManager apiManager,
            RestProductService restService,
            SoapProductService soapService)
        {
            _apiManager = apiManager;
            _restService = restService;
            _soapService = soapService;
        }

        private IProductService GetActiveService()
        {
            if (_apiManager.CurrentProtocol == ApiProtocol.Rest)
            {
                _restService.SetTarget(_apiManager.CurrentPlatform);
                return _restService;
            }
            else // SOAP
            {
                _soapService.SetTarget(_apiManager.CurrentPlatform);
                return _soapService;
            }
        }

        // Simplemente delegamos cada llamada al servicio activo
        public Task<IEnumerable<ProductDto>> ObtenerProductosAsync() => GetActiveService().ObtenerProductosAsync();
        public Task<ProductDto?> ObtenerProductoPorCodigoAsync(string codigo) => GetActiveService().ObtenerProductoPorCodigoAsync(codigo);
        public Task<IEnumerable<ProductDto>> ObtenerProductosPorCategoriaAsync(ProductCategory categoria) => GetActiveService().ObtenerProductosPorCategoriaAsync(categoria);
        public Task<IEnumerable<ProductDto>> ObtenerProductosPorPrecioAsync(decimal precioMin, decimal precioMax) => GetActiveService().ObtenerProductosPorPrecioAsync(precioMin, precioMax);
        public Task<ServiceResponse<ProductDto>> CrearProductoAsync(ProductDto producto) => GetActiveService().CrearProductoAsync(producto);
        public Task<ServiceResponse<ProductDto>> ActualizarProductoAsync(ProductDto producto) => GetActiveService().ActualizarProductoAsync(producto);
        public Task<ServiceResponse> EliminarProductoAsync(int id) => GetActiveService().EliminarProductoAsync(id);
    }
}