using System;
using System.Collections.Generic;
using System.Linq;
using Comercializadora_Soap_DotNet_GR01.DataAcces.Repositories;
using Comercializadora_Soap_DotNet_GR01.DTOs;
using Comercializadora_Soap_DotNet_GR01.Models;

namespace Comercializadora_Soap_DotNet_GR01.Services
{
    public class ProductoService
    {
        private readonly ProductoRepository _productoRepository;

        public ProductoService()
        {
            _productoRepository = new ProductoRepository();
        }

        public ProductoService(ProductoRepository productoRepository)
        {
            _productoRepository = productoRepository;
        }

        // Crear producto
        public RespuestaDTO CrearProducto(CrearProductoDTO productoDto)
        {
            try
            {
                // Validaciones
                if (string.IsNullOrWhiteSpace(productoDto.Codigo))
                    return new RespuestaDTO { Exito = false, Mensaje = "El código del producto es requerido" };

                if (string.IsNullOrWhiteSpace(productoDto.Nombre))
                    return new RespuestaDTO { Exito = false, Mensaje = "El nombre del producto es requerido" };

                if (productoDto.Precio <= 0)
                    return new RespuestaDTO { Exito = false, Mensaje = "El precio debe ser mayor a cero" };

                if (_productoRepository.ExisteCodigo(productoDto.Codigo))
                    return new RespuestaDTO { Exito = false, Mensaje = $"Ya existe un producto con el código {productoDto.Codigo}" };

                // Mapear DTO a Model (ProductoId y FechaRegistro se autogeneran)
                var producto = new Producto
                {
                    Codigo = productoDto.Codigo,
                    Nombre = productoDto.Nombre,
                    Descripcion = productoDto.Descripcion,
                    Precio = productoDto.Precio,
                    Stock = productoDto.Stock,
                    Categoria = productoDto.Categoria,
                    ImagenUrl = productoDto.ImagenUrl,
                    Estado = "ACTIVO",
                    FechaRegistro = DateTime.Now
                };

                var productoCreado = _productoRepository.Create(producto);

                if (productoCreado == null || productoCreado.ProductoId == 0)
                {
                    return new RespuestaDTO { Exito = false, Mensaje = "Error: El producto no se guardó correctamente en la base de datos" };
                }

                var productoDTO = MapearADTO(productoCreado);

                return new RespuestaDTO
                {
                    Exito = true,
                    Mensaje = $"Producto creado exitosamente con ID: {productoCreado.ProductoId}",
                    Datos = productoDTO
                };
            }
            catch (Exception ex)
            {
                return new RespuestaDTO { Exito = false, Mensaje = $"Error al crear producto: {ex.Message}" };
            }
        }

        // Listar todos los productos
        public List<ProductoDTO> ObtenerProductos()
        {
            var productos = _productoRepository.GetAll();
            return productos.Select(p => MapearADTO(p)).ToList();
        }

        // Obtener producto por ID
        public ProductoDTO ObtenerProductoPorId(int id)
        {
            var producto = _productoRepository.GetById(id);
            return producto != null ? MapearADTO(producto) : null;
        }

        // Obtener producto por código
        public ProductoDTO ObtenerProductoPorCodigo(string codigo)
        {
            var producto = _productoRepository.GetByCodigo(codigo);
            return producto != null ? MapearADTO(producto) : null;
        }

        // Obtener productos por categoría
        public List<ProductoDTO> ObtenerProductosPorCategoria(string categoria)
        {
            var productos = _productoRepository.GetByCategoria(categoria);
            return productos.Select(p => MapearADTO(p)).ToList();
        }

        // Obtener productos por rango de precio
        public List<ProductoDTO> ObtenerProductosPorPrecio(decimal precioMin, decimal precioMax)
        {
            var productos = _productoRepository.GetByPrecioRange(precioMin, precioMax);
            return productos.Select(p => MapearADTO(p)).ToList();
        }

        // Actualizar producto
        public RespuestaDTO ActualizarProducto(ActualizarProductoDTO productoDto)
        {
            try
            {
                // Validaciones
                if (productoDto.ProductoId <= 0)
                    return new RespuestaDTO { Exito = false, Mensaje = "ID de producto inválido" };

                var productoExistente = _productoRepository.GetById(productoDto.ProductoId);
                if (productoExistente == null)
                    return new RespuestaDTO { Exito = false, Mensaje = "Producto no encontrado" };

                if (string.IsNullOrWhiteSpace(productoDto.Nombre))
                    return new RespuestaDTO { Exito = false, Mensaje = "El nombre del producto es requerido" };

                if (productoDto.Precio <= 0)
                    return new RespuestaDTO { Exito = false, Mensaje = "El precio debe ser mayor a cero" };

                if (_productoRepository.ExisteCodigo(productoDto.Codigo, productoDto.ProductoId))
                    return new RespuestaDTO { Exito = false, Mensaje = $"Ya existe otro producto con el código {productoDto.Codigo}" };

                // Actualizar campos (FechaRegistro NO se modifica)
                productoExistente.Codigo = productoDto.Codigo;
                productoExistente.Nombre = productoDto.Nombre;
                productoExistente.Descripcion = productoDto.Descripcion;
                productoExistente.Precio = productoDto.Precio;
                productoExistente.Stock = productoDto.Stock;
                productoExistente.Categoria = productoDto.Categoria;
                productoExistente.ImagenUrl = productoDto.ImagenUrl;
                productoExistente.Estado = productoDto.Estado;

                var productoActualizado = _productoRepository.Update(productoExistente);

                return new RespuestaDTO
                {
                    Exito = true,
                    Mensaje = "Producto actualizado exitosamente",
                    Datos = MapearADTO(productoActualizado)
                };
            }
            catch (Exception ex)
            {
                return new RespuestaDTO { Exito = false, Mensaje = $"Error al actualizar producto: {ex.Message}" };
            }
        }

        // Eliminar producto
        public RespuestaDTO EliminarProducto(int id)
        {
            try
            {
                var producto = _productoRepository.GetById(id);
                if (producto == null)
                    return new RespuestaDTO { Exito = false, Mensaje = "Producto no encontrado" };

                _productoRepository.Delete(id);

                return new RespuestaDTO
                {
                    Exito = true,
                    Mensaje = "Producto eliminado exitosamente"
                };
            }
            catch (Exception ex)
            {
                return new RespuestaDTO { Exito = false, Mensaje = $"Error al eliminar producto: {ex.Message}" };
            }
        }

        // Mapear Model a DTO
        private ProductoDTO MapearADTO(Producto producto)
        {
            return new ProductoDTO
            {
                ProductoId = producto.ProductoId,
                Codigo = producto.Codigo,
                Nombre = producto.Nombre,
                Descripcion = producto.Descripcion,
                Precio = producto.Precio,
                Stock = producto.Stock,
                Categoria = producto.Categoria,
                ImagenUrl = producto.ImagenUrl,
                FechaRegistro = producto.FechaRegistro,
                Estado = producto.Estado
            };
        }
    }
}