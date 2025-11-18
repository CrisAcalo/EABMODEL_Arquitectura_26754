using System;
using System.Collections.Generic;
using System.Linq;
using Comercializadora_Soap_DotNet_GR01.Models;

namespace Comercializadora_Soap_DotNet_GR01.DataAcces.Repositories
{
    public class ProductoRepository
    {
        private readonly ComercializadoraDbContext _context;

        public ProductoRepository()
        {
            _context = new ComercializadoraDbContext();
        }

        public ProductoRepository(ComercializadoraDbContext context)
        {
            _context = context;
        }

        // CREATE
        public Producto Create(Producto producto)
        {
            _context.Productos.Add(producto);
            _context.SaveChanges();
            // El ID ya se asigna automáticamente por Identity después de SaveChanges
            return producto;
        }

        // READ ALL
        public List<Producto> GetAll()
        {
            return _context.Productos
                .Where(p => p.Estado == "ACTIVO")
                .OrderBy(p => p.Nombre)
                .ToList();
        }

        // READ BY ID
        public Producto GetById(int id)
        {
            return _context.Productos.Find(id);
        }

        // READ BY CODIGO
        public Producto GetByCodigo(string codigo)
        {
            return _context.Productos
                .FirstOrDefault(p => p.Codigo == codigo);
        }

        // READ BY CATEGORIA
        public List<Producto> GetByCategoria(string categoria)
        {
            return _context.Productos
                .Where(p => p.Categoria == categoria && p.Estado == "ACTIVO")
                .OrderBy(p => p.Nombre)
                .ToList();
        }

        // READ BY PRECIO RANGE
        public List<Producto> GetByPrecioRange(decimal precioMin, decimal precioMax)
        {
            return _context.Productos
                .Where(p => p.Precio >= precioMin && p.Precio <= precioMax && p.Estado == "ACTIVO")
                .OrderBy(p => p.Precio)
                .ToList();
        }

        // UPDATE
        public Producto Update(Producto producto)
        {
            var existing = _context.Productos.Find(producto.ProductoId);
            if (existing != null)
            {
                _context.Entry(existing).CurrentValues.SetValues(producto);
                _context.SaveChanges();
                return existing;
            }
            return null;
        }

        // DELETE (Logical)
        public bool Delete(int id)
        {
            var producto = _context.Productos.Find(id);
            if (producto != null)
            {
                producto.Estado = "INACTIVO";
                _context.SaveChanges();
                return true;
            }
            return false;
        }

        // DELETE (Physical)
        public bool DeletePermanent(int id)
        {
            var producto = _context.Productos.Find(id);
            if (producto != null)
            {
                _context.Productos.Remove(producto);
                _context.SaveChanges();
                return true;
            }
            return false;
        }

        // Verificar si código existe
        public bool ExisteCodigo(string codigo, int? excludeId = null)
        {
            var query = _context.Productos.Where(p => p.Codigo == codigo);
            if (excludeId.HasValue)
            {
                query = query.Where(p => p.ProductoId != excludeId.Value);
            }
            return query.Any();
        }

        // Verificar stock disponible
        public bool TieneStock(int productoId, int cantidad)
        {
            var producto = _context.Productos.Find(productoId);
            return producto != null && producto.Stock >= cantidad;
        }

        // Actualizar stock
        public bool ActualizarStock(int productoId, int cantidad)
        {
            var producto = _context.Productos.Find(productoId);
            if (producto != null && producto.Stock >= cantidad)
            {
                producto.Stock -= cantidad;
                _context.SaveChanges();
                return true;
            }
            return false;
        }
    }
}