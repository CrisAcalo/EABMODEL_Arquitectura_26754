using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Comercializadora_Soap_DotNet_GR01.Models
{
    [Table("Producto")]
    public class Producto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int ProductoId { get; set; }

        [Required]
        [MaxLength(20)]
        [Index("IX_Producto_Codigo", IsUnique = true)]
        public string Codigo { get; set; }

        [Required]
        [MaxLength(100)]
        public string Nombre { get; set; }

        [MaxLength(500)]
        public string Descripcion { get; set; }

        [Required]
        [Column(TypeName = "decimal(12,2)")]
        public decimal Precio { get; set; }

        [Required]
        public int Stock { get; set; }

        [MaxLength(50)]
        public string Categoria { get; set; }

        [MaxLength(500)]
        public string ImagenUrl { get; set; }

        [Required]
        public DateTime FechaRegistro { get; set; }

        [Required]
        [MaxLength(20)]
        public string Estado { get; set; }

        // Navigation properties
        public virtual ICollection<DetalleFactura> DetallesFactura { get; set; }

        public Producto()
        {
            FechaRegistro = DateTime.Now;
            Estado = "ACTIVO";
            Stock = 0;
            DetallesFactura = new HashSet<DetalleFactura>();
        }
    }
}