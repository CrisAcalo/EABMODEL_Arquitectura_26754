using System.Collections.Generic;
using System.ServiceModel;
using Comercializadora_Soap_DotNet_GR01.DTOs;

namespace Comercializadora_Soap_DotNet_GR01.WS
{
    [ServiceContract]
    public interface IProductoSoapService
    {
        [OperationContract]
        RespuestaDTO CrearProducto(ProductoDTO producto);

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
        RespuestaDTO ActualizarProducto(ProductoDTO producto);

        [OperationContract]
        RespuestaDTO EliminarProducto(int id);
    }
}