using System.ServiceModel;
using Comer_CliCon_SOAP_DotNet_GR01.Models;

namespace Comer_CliCon_SOAP_DotNet_GR01.ServiceClients
{
    public class ProductoServiceClient : IDisposable
    {
        private readonly ChannelFactory<IProductoSoapService> _factory;
        private readonly IProductoSoapService _client;
        private const string ServiceUrl = "http://localhost:8006/WS/ProductoSoapService.svc";

        public ProductoServiceClient()
        {
            var binding = new BasicHttpBinding
            {
                MaxReceivedMessageSize = 2147483647,
                MaxBufferSize = 2147483647
            };
            var endpoint = new EndpointAddress(ServiceUrl);
            _factory = new ChannelFactory<IProductoSoapService>(binding, endpoint);
            _client = _factory.CreateChannel();
        }

        public ProductoDTO CrearProducto(ProductoDTO producto) => _client.CrearProducto(producto);
        public List<ProductoDTO> ObtenerProductos() => _client.ObtenerProductos();
        public ProductoDTO ObtenerProductoPorId(int id) => _client.ObtenerProductoPorId(id);
        public ProductoDTO ObtenerProductoPorCodigo(string codigo) => _client.ObtenerProductoPorCodigo(codigo);
        public List<ProductoDTO> ObtenerProductosPorCategoria(string categoria) => _client.ObtenerProductosPorCategoria(categoria);
        public List<ProductoDTO> ObtenerProductosPorPrecio(decimal precioMin, decimal precioMax) => _client.ObtenerProductosPorPrecio(precioMin, precioMax);
        public ProductoDTO ActualizarProducto(ProductoDTO producto) => _client.ActualizarProducto(producto);
        public bool EliminarProducto(int id) => _client.EliminarProducto(id);

        public void Dispose()
        {
            try
            {
                if (_client is IClientChannel channel)
                {
                    if (channel.State == CommunicationState.Faulted)
                        channel.Abort();
                    else
                        channel.Close();
                }
                _factory?.Close();
            }
            catch
            {
                _factory?.Abort();
            }
        }
    }
}
