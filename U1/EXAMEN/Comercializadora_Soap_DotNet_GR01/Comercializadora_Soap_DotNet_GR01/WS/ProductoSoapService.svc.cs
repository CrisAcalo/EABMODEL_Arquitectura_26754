using System.Collections.Generic;
using Comercializadora_Soap_DotNet_GR01.DTOs;
using Comercializadora_Soap_DotNet_GR01.Services;

namespace Comercializadora_Soap_DotNet_GR01.WS
{
    public class ProductoSoapService : IProductoSoapService
    {
        private readonly ProductoService _productoService;

        public ProductoSoapService()
        {
            _productoService = new ProductoService();
        }

        public RespuestaDTO CrearProducto(ProductoDTO producto)
        {
            return _productoService.CrearProducto(producto);
        }

        public List<ProductoDTO> ObtenerProductos()
        {
            return _productoService.ObtenerProductos();
        }

        public ProductoDTO ObtenerProductoPorId(int id)
        {
            return _productoService.ObtenerProductoPorId(id);
        }

        public ProductoDTO ObtenerProductoPorCodigo(string codigo)
        {
            return _productoService.ObtenerProductoPorCodigo(codigo);
        }

        public List<ProductoDTO> ObtenerProductosPorCategoria(string categoria)
        {
            return _productoService.ObtenerProductosPorCategoria(categoria);
        }

        public List<ProductoDTO> ObtenerProductosPorPrecio(decimal precioMin, decimal precioMax)
        {
            return _productoService.ObtenerProductosPorPrecio(precioMin, precioMax);
        }

        public RespuestaDTO ActualizarProducto(ProductoDTO producto)
        {
            return _productoService.ActualizarProducto(producto);
        }

        public RespuestaDTO EliminarProducto(int id)
        {
            return _productoService.EliminarProducto(id);
        }
    }
}