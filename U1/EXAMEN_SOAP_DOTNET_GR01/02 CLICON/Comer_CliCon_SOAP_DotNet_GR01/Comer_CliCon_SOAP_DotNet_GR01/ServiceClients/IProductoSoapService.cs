using System.ServiceModel;
using Comer_CliCon_SOAP_DotNet_GR01.Models;

namespace Comer_CliCon_SOAP_DotNet_GR01.ServiceClients
{
    [ServiceContract]
    public interface IProductoSoapService
    {
        [OperationContract]
        ProductoDTO CrearProducto(ProductoDTO producto);

        [OperationContract]
        List<ProductoDTO> ObtenerProductos();

        [OperationContract]
        ProductoDTO ObtenerProductoPorId(int id);

        [OperationContract]
        ProductoDTO ObtenerProductoPorCodigo(string codigo);

        [OperationContract]
        List<ProductoDTO> ObtenerProductosPorCategoria(string categoria);

        [OperationContract]
        List<ProductoDTO> ObtenerProductosPorPrecio(decimal precioMin, decimal precioMax);

        [OperationContract]
        ProductoDTO ActualizarProducto(ProductoDTO producto);

        [OperationContract]
        bool EliminarProducto(int id);
    }
}
