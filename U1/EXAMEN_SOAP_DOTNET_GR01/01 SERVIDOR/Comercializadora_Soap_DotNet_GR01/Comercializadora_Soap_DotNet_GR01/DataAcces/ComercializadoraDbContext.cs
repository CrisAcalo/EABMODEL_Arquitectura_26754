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
            // Configuración de Producto
            modelBuilder.Entity<Producto>()
                .Property(p => p.Precio)
                .HasPrecision(12, 2);

            modelBuilder.Entity<Producto>()
                .HasMany(p => p.DetallesFactura)
                .WithRequired(d => d.Producto)
                .HasForeignKey(d => d.ProductoId)
                .WillCascadeOnDelete(false);

            // Configuración de Factura
            modelBuilder.Entity<Factura>()
                .Property(f => f.Subtotal)
                .HasPrecision(12, 2);

            modelBuilder.Entity<Factura>()
                .Property(f => f.Descuento)
                .HasPrecision(12, 2);

            modelBuilder.Entity<Factura>()
                .Property(f => f.Total)
                .HasPrecision(12, 2);

            modelBuilder.Entity<Factura>()
                .HasMany(f => f.Detalles)
                .WithRequired(d => d.Factura)
                .HasForeignKey(d => d.FacturaId)
                .WillCascadeOnDelete(true);

            // Configuración de DetalleFactura
            modelBuilder.Entity<DetalleFactura>()
                .Property(d => d.PrecioUnitario)
                .HasPrecision(12, 2);

            modelBuilder.Entity<DetalleFactura>()
                .Property(d => d.Subtotal)
                .HasPrecision(12, 2);

            base.OnModelCreating(modelBuilder);
        }
    }
}