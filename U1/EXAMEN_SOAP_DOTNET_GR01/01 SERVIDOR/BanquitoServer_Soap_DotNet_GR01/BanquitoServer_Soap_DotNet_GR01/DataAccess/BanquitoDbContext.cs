using System.Data.Entity;
using BanquitoServer_Soap_DotNet_GR01.Models;

namespace BanquitoServer_Soap_DotNet_GR01.DataAccess
{
    /// <summary>
    /// DbContext para el sistema BanQuito
    /// Maneja la conexión con SQL Server y las operaciones de Entity Framework
    /// </summary>
    public class BanquitoDbContext : DbContext
    {
        /// <summary>
        /// Constructor - usa la cadena de conexión "BanquitoDb" del Web.config
        /// </summary>
        public BanquitoDbContext() : base("name=BanquitoDb")
        {
            // Deshabilitar inicializador de base de datos
            // (no queremos que EF cree o modifique la BD automáticamente)
            Database.SetInitializer<BanquitoDbContext>(null);
        }

        // DbSets - representan las tablas de la base de datos
        public virtual DbSet<Cliente> Clientes { get; set; }
        public virtual DbSet<Cuenta> Cuentas { get; set; }
        public virtual DbSet<Movimiento> Movimientos { get; set; }
        public virtual DbSet<Credito> Creditos { get; set; }
        public virtual DbSet<CuotaAmortizacion> CuotasAmortizacion { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            // Configurar precisión para campos decimales
            modelBuilder.Entity<Cuenta>()
                .Property(c => c.Saldo)
                .HasPrecision(12, 2);

            modelBuilder.Entity<Movimiento>()
                .Property(m => m.Monto)
                .HasPrecision(12, 2);

            modelBuilder.Entity<Movimiento>()
                .Property(m => m.SaldoAnterior)
                .HasPrecision(12, 2);

            modelBuilder.Entity<Movimiento>()
                .Property(m => m.SaldoNuevo)
                .HasPrecision(12, 2);

            modelBuilder.Entity<Credito>()
                .Property(c => c.MontoCredito)
                .HasPrecision(12, 2);

            modelBuilder.Entity<Credito>()
                .Property(c => c.TasaInteres)
                .HasPrecision(5, 4);

            modelBuilder.Entity<Credito>()
                .Property(c => c.CuotaMensual)
                .HasPrecision(12, 2);

            modelBuilder.Entity<CuotaAmortizacion>()
                .Property(c => c.ValorCuota)
                .HasPrecision(12, 2);

            modelBuilder.Entity<CuotaAmortizacion>()
                .Property(c => c.Interes)
                .HasPrecision(12, 2);

            modelBuilder.Entity<CuotaAmortizacion>()
                .Property(c => c.CapitalPagado)
                .HasPrecision(12, 2);

            modelBuilder.Entity<CuotaAmortizacion>()
                .Property(c => c.Saldo)
                .HasPrecision(12, 2);

            // Configurar relaciones
            modelBuilder.Entity<Cuenta>()
                .HasRequired(c => c.Cliente)
                .WithMany(cl => cl.Cuentas)
                .HasForeignKey(c => c.ClienteId)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Movimiento>()
                .HasRequired(m => m.Cuenta)
                .WithMany(c => c.Movimientos)
                .HasForeignKey(m => m.CuentaId)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Credito>()
                .HasRequired(cr => cr.Cliente)
                .WithMany(cl => cl.Creditos)
                .HasForeignKey(cr => cr.ClienteId)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<CuotaAmortizacion>()
                .HasRequired(ca => ca.Credito)
                .WithMany(cr => cr.CuotasAmortizacion)
                .HasForeignKey(ca => ca.CreditoId)
                .WillCascadeOnDelete(true);  // Cascade delete para cuotas
        }
    }
}
