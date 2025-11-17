using System.Linq;
using BanquitoServer_Soap_DotNet_GR01.Models;

namespace BanquitoServer_Soap_DotNet_GR01.DataAccess.Repositories
{
    /// <summary>
    /// Repositorio para operaciones de Cliente
    /// </summary>
    public class ClienteRepository
    {
        private readonly BanquitoDbContext _context;

        public ClienteRepository()
        {
            _context = new BanquitoDbContext();
        }

        public ClienteRepository(BanquitoDbContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Buscar cliente por cédula
        /// </summary>
        public Cliente FindByCedula(string cedula)
        {
            return _context.Clientes
                .FirstOrDefault(c => c.Cedula == cedula);
        }

        /// <summary>
        /// Obtener cliente por ID con sus relaciones
        /// </summary>
        public Cliente GetById(long clienteId)
        {
            return _context.Clientes
                .Include("Cuentas")
                .Include("Creditos")
                .FirstOrDefault(c => c.ClienteId == clienteId);
        }
    }
}
