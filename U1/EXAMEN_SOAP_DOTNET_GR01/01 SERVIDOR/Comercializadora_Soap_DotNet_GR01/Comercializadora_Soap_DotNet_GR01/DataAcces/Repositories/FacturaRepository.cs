using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using Comercializadora_Soap_DotNet_GR01.Models;

namespace Comercializadora_Soap_DotNet_GR01.DataAcces.Repositories
{
    public class FacturaRepository
    {
        private readonly ComercializadoraDbContext _context;

        public FacturaRepository()
        {
            _context = new ComercializadoraDbContext();
        }

        public FacturaRepository(ComercializadoraDbContext context)
        {
            _context = context;
        }

        // CREATE
        public Factura Create(Factura factura)
        {
            _context.Facturas.Add(factura);
            _context.SaveChanges();
            return factura;
        }

        // READ ALL
        public List<Factura> GetAll()
        {
            return _context.Facturas
                .Include(f => f.Detalles)
                .OrderByDescending(f => f.FechaEmision)
                .ToList();
        }

        // READ BY ID
        public Factura GetById(int id)
        {
            return _context.Facturas
                .Include(f => f.Detalles)
                .FirstOrDefault(f => f.FacturaId == id);
        }

        // READ BY NUMERO FACTURA
        public Factura GetByNumeroFactura(string numeroFactura)
        {
            return _context.Facturas
                .Include(f => f.Detalles)
                .FirstOrDefault(f => f.NumeroFactura == numeroFactura);
        }

        // READ BY CEDULA CLIENTE
        public List<Factura> GetByCedulaCliente(string cedula)
        {
            return _context.Facturas
                .Include(f => f.Detalles)
                .Where(f => f.CedulaCliente == cedula)
                .OrderByDescending(f => f.FechaEmision)
                .ToList();
        }

        // READ BY FORMA PAGO
        public List<Factura> GetByFormaPago(string formaPago)
        {
            return _context.Facturas
                .Include(f => f.Detalles)
                .Where(f => f.FormaPago == formaPago)
                .OrderByDescending(f => f.FechaEmision)
                .ToList();
        }

        // READ BY FECHA RANGE
        public List<Factura> GetByFechaRange(DateTime fechaInicio, DateTime fechaFin)
        {
            return _context.Facturas
                .Include(f => f.Detalles)
                .Where(f => f.FechaEmision >= fechaInicio && f.FechaEmision <= fechaFin)
                .OrderByDescending(f => f.FechaEmision)
                .ToList();
        }

        // Generar número de factura
        public string GenerarNumeroFactura()
        {
            var ultimaFactura = _context.Facturas
                .OrderByDescending(f => f.FacturaId)
                .FirstOrDefault();

            int consecutivo = 1;
            if (ultimaFactura != null)
            {
                // Extraer el número del formato FAC-YYYYMMDD-XXX
                var partes = ultimaFactura.NumeroFactura.Split('-');
                if (partes.Length == 3)
                {
                    int.TryParse(partes[2], out consecutivo);
                    consecutivo++;
                }
            }

            string fecha = DateTime.Now.ToString("yyyyMMdd");
            return $"FAC-{fecha}-{consecutivo:D3}";
        }

        // Verificar si existe número de factura
        public bool ExisteNumeroFactura(string numeroFactura)
        {
            return _context.Facturas.Any(f => f.NumeroFactura == numeroFactura);
        }

        // Obtener total ventas por fecha
        public decimal GetTotalVentasPorFecha(DateTime fecha)
        {
            return _context.Facturas
                .Where(f => DbFunctions.TruncateTime(f.FechaEmision) == fecha.Date)
                .Sum(f => (decimal?)f.Total) ?? 0;
        }

        // Obtener total ventas por mes
        public decimal GetTotalVentasPorMes(int año, int mes)
        {
            return _context.Facturas
                .Where(f => f.FechaEmision.Year == año && f.FechaEmision.Month == mes)
                .Sum(f => (decimal?)f.Total) ?? 0;
        }
    }
}