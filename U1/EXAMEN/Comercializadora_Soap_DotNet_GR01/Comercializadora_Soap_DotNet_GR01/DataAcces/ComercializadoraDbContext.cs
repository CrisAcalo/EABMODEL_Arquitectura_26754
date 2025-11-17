using System.Data.Entity;
using Comercializadora_Soap_DotNet_GR01.Models;

namespace Comercializadora_Soap_DotNet_GR01.DataAcces
{
    public class ComercializadoraDbContext : DbContext
    {
        public ComercializadoraDbContext() : base("name=ComercializadoraDb")
        {
            Database.SetInitializer<ComercializadoraDbContext>(null);
        }

        public virtual DbSet<Producto> Productos { get; set; }
        public virtual DbSet<Factura> Facturas { get; set; }
        public virtual DbSet<DetalleFactura> DetallesFactura { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            // Configuraciones adicionales si son necesarias
            modelBuilder.Entity<Producto>()
                .HasMany(p => p.DetallesFactura)
                .WithRequired(d => d.Producto)
                .HasForeignKey(d => d.ProductoId)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Factura>()
                .HasMany(f => f.Detalles)
                .WithRequired(d => d.Factura)
                .HasForeignKey(d => d.FacturaId)
                .WillCascadeOnDelete(true);

            base.OnModelCreating(modelBuilder);
        }
    }
}