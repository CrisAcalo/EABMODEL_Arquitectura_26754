using System;
using System.Data.Entity.Validation;
using System.Linq;
using System.Text;
using BanquitoServer_Soap_DotNet_GR01.Models;

namespace BanquitoServer_Soap_DotNet_GR01.DataAccess.Repositories
{
    /// <summary>
    /// Repositorio para operaciones de Crédito
    /// </summary>
    public class CreditoRepository
    {
        private readonly BanquitoDbContext _context;

        public CreditoRepository()
        {
            _context = new BanquitoDbContext();
        }

        public CreditoRepository(BanquitoDbContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Verificar si el cliente tiene un crédito activo
        /// </summary>
        public bool TieneCreditoActivo(string cedula)
        {
            var tieneCredito = (from cr in _context.Creditos
                               join cl in _context.Clientes on cr.ClienteId equals cl.ClienteId
                               where cl.Cedula == cedula
                                  && cr.Estado == "ACTIVO"
                               select cr).Any();

            return tieneCredito;
        }

        /// <summary>
        /// Guardar un crédito con su tabla de amortización
        /// </summary>
        public void Save(Credito credito)
        {
            try
            {
                _context.Creditos.Add(credito);
                _context.SaveChanges();
            }
            catch (DbEntityValidationException ex)
            {
                // Construir mensaje detallado de errores de validación
                StringBuilder sb = new StringBuilder();
                sb.AppendLine("Errores de validación de Entity Framework:");

                foreach (var validationErrors in ex.EntityValidationErrors)
                {
                    foreach (var validationError in validationErrors.ValidationErrors)
                    {
                        sb.AppendLine($"  - Propiedad: {validationError.PropertyName}, Error: {validationError.ErrorMessage}");
                    }
                }

                throw new Exception(sb.ToString(), ex);
            }
        }

        /// <summary>
        /// Buscar crédito por número de crédito
        /// </summary>
        public Credito FindByNumeroCredito(string numeroCredito)
        {
            return _context.Creditos
                .Include("CuotasAmortizacion")
                .FirstOrDefault(c => c.NumeroCredito == numeroCredito);
        }
    }
}
