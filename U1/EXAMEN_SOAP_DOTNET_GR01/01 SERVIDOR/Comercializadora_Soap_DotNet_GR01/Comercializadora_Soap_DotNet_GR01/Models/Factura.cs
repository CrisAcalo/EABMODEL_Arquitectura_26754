using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Comercializadora_Soap_DotNet_GR01.Models
{
    [Table("Factura")]
    public class Factura
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int FacturaId { get; set; }

        [Required]
        [MaxLength(20)]
        [Index("IX_Factura_NumeroFactura", IsUnique = true)]
        public string NumeroFactura { get; set; }

        [Required]
        [MaxLength(10)]
        public string CedulaCliente { get; set; }

        [Required]
        [MaxLength(200)]
        public string NombreCliente { get; set; }

        [Required]
        [MaxLength(20)]
        public string FormaPago { get; set; } // EFECTIVO o CREDITO

        [Required]
        public decimal Subtotal { get; set; }

        public decimal Descuento { get; set; }

        [Required]
        public decimal Total { get; set; }

        [MaxLength(20)]
        public string NumeroCredito { get; set; } // Solo si es crédito

        [Required]
        public DateTime FechaEmision { get; set; }

        // Navigation properties
        public virtual ICollection<DetalleFactura> Detalles { get; set; }

        public Factura()
        {
            FechaEmision = DateTime.Now;
            Descuento = 0;
            Detalles = new HashSet<DetalleFactura>();
        }
    }
}