using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BanquitoServer_Soap_DotNet_GR01.Models
{
    /// <summary>
    /// Entidad Crédito
    /// </summary>
    [Table("Credito")]
    public class Credito
    {
        [Key]
        [Column("CreditoId")]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long CreditoId { get; set; }

        [Required]
        [Column("NumeroCredito")]
        [MaxLength(20)]
        [Index("IX_Credito_NumeroCredito", IsUnique = true)]
        public string NumeroCredito { get; set; }

        [Required]
        [Column("MontoCredito")]
        public decimal MontoCredito { get; set; }

        [Required]
        [Column("TasaInteres")]
        public decimal TasaInteres { get; set; }

        [Required]
        [Column("NumeroCuotas")]
        public int NumeroCuotas { get; set; }

        [Required]
        [Column("CuotaMensual")]
        public decimal CuotaMensual { get; set; }

        [Required]
        [Column("FechaOtorgamiento")]
        public DateTime FechaOtorgamiento { get; set; }

        [Required]
        [Column("Estado")]
        [MaxLength(20)]
        [Index("IX_Credito_Estado")]
        public string Estado { get; set; }

        [Column("Descripcion")]
        [MaxLength(200)]
        public string Descripcion { get; set; }

        // Foreign Key
        [Required]
        [Column("ClienteId")]
        [ForeignKey("Cliente")]
        [Index("IX_Credito_ClienteId")]
        public long ClienteId { get; set; }

        // Propiedades de navegación
        public virtual Cliente Cliente { get; set; }
        public virtual ICollection<CuotaAmortizacion> CuotasAmortizacion { get; set; }

        public Credito()
        {
            CuotasAmortizacion = new HashSet<CuotaAmortizacion>();
            Estado = "ACTIVO";
            FechaOtorgamiento = DateTime.Now;
        }
    }
}
