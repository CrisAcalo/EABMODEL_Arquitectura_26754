using System;
using System.Collections.Generic;
using System.Linq;
using BanquitoServer_Soap_DotNet_GR01.Models;

namespace BanquitoServer_Soap_DotNet_GR01.DataAccess.Repositories
{
    /// <summary>
    /// Repositorio para operaciones de Movimiento
    /// </summary>
    public class MovimientoRepository
    {
        private readonly BanquitoDbContext _context;

        public MovimientoRepository()
        {
            _context = new BanquitoDbContext();
        }

        public MovimientoRepository(BanquitoDbContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Verificar si el cliente tiene al menos un depósito en el último mes
        /// </summary>
        public bool TieneDepositoEnUltimoMes(string cedula)
        {
            var fechaInicio = DateTime.Now.AddMonths(-1);

            var tieneDeposito = (from m in _context.Movimientos
                                 join c in _context.Cuentas on m.CuentaId equals c.CuentaId
                                 join cl in _context.Clientes on c.ClienteId equals cl.ClienteId
                                 where cl.Cedula == cedula
                                    && m.TipoMovimiento == "DEPOSITO"
                                    && m.FechaMovimiento >= fechaInicio
                                 select m).Any();

            return tieneDeposito;
        }

        /// <summary>
        /// Obtener depósitos de un cliente en un período
        /// </summary>
        public List<Movimiento> FindDepositosByClienteAndPeriodo(string cedula, DateTime fechaInicio, DateTime fechaFin)
        {
            var depositos = (from m in _context.Movimientos
                            join c in _context.Cuentas on m.CuentaId equals c.CuentaId
                            join cl in _context.Clientes on c.ClienteId equals cl.ClienteId
                            where cl.Cedula == cedula
                               && m.TipoMovimiento == "DEPOSITO"
                               && m.FechaMovimiento >= fechaInicio
                               && m.FechaMovimiento <= fechaFin
                            select m).ToList();

            return depositos;
        }

        /// <summary>
        /// Obtener retiros de un cliente en un período
        /// </summary>
        public List<Movimiento> FindRetirosByClienteAndPeriodo(string cedula, DateTime fechaInicio, DateTime fechaFin)
        {
            var retiros = (from m in _context.Movimientos
                          join c in _context.Cuentas on m.CuentaId equals c.CuentaId
                          join cl in _context.Clientes on c.ClienteId equals cl.ClienteId
                          where cl.Cedula == cedula
                             && m.TipoMovimiento == "RETIRO"
                             && m.FechaMovimiento >= fechaInicio
                             && m.FechaMovimiento <= fechaFin
                          select m).ToList();

            return retiros;
        }

        /// <summary>
        /// Calcular promedio de depósitos en un período
        /// </summary>
        public decimal PromedioDepositosPorPeriodo(string cedula, DateTime fechaInicio, DateTime fechaFin)
        {
            var depositos = FindDepositosByClienteAndPeriodo(cedula, fechaInicio, fechaFin);

            if (depositos.Count == 0)
                return 0;

            return depositos.Average(d => d.Monto);
        }

        /// <summary>
        /// Calcular promedio de retiros en un período
        /// </summary>
        public decimal PromedioRetirosPorPeriodo(string cedula, DateTime fechaInicio, DateTime fechaFin)
        {
            var retiros = FindRetirosByClienteAndPeriodo(cedula, fechaInicio, fechaFin);

            if (retiros.Count == 0)
                return 0;

            return retiros.Average(r => r.Monto);
        }
    }
}
