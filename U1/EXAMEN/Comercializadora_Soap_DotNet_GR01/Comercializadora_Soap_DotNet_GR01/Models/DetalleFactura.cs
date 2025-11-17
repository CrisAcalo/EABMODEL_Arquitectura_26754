using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Comercializadora_Soap_DotNet_GR01.Models
{
    [Table("DetalleFactura")]
    public class DetalleFactura
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int DetalleId { get; set; }

        [Required]
        public int FacturaId { get; set; }

        [Required]
        public int ProductoId { get; set; }

        [Required]
        public int Cantidad { get; set; }

        [Required]
        [Column(TypeName = "decimal(12,2)")]
        public decimal PrecioUnitario { get; set; }

        [Required]
        [Column(TypeName = "decimal(12,2)")]
        public decimal Subtotal { get; set; }

        // Navigation properties
        [ForeignKey("FacturaId")]
        public virtual Factura Factura { get; set; }

        [ForeignKey("ProductoId")]
        public virtual Producto Producto { get; set; }
    }
}