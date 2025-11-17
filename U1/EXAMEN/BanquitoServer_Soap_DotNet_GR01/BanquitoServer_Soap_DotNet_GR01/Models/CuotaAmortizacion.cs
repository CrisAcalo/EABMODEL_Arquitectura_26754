using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BanquitoServer_Soap_DotNet_GR01.Models
{
    /// <summary>
    /// Entidad CuotaAmortizacion - Representa cada cuota de la tabla de amortización
    /// </summary>
    [Table("CuotaAmortizacion")]
    public class CuotaAmortizacion
    {
        [Key]
        [Column("CuotaId")]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long CuotaId { get; set; }

        [Required]
        [Column("NumeroCuota")]
        [Index("IX_CuotaAmortizacion_NumeroCuota")]
        public int NumeroCuota { get; set; }

        [Required]
        [Column("ValorCuota")]
        public decimal ValorCuota { get; set; }

        [Required]
        [Column("Interes")]
        public decimal Interes { get; set; }

        [Required]
        [Column("CapitalPagado")]
        public decimal CapitalPagado { get; set; }

        [Required]
        [Column("Saldo")]
        public decimal Saldo { get; set; }

        // Foreign Key
        [Column("CreditoId")]
        [ForeignKey("Credito")]
        [Index("IX_CuotaAmortizacion_CreditoId")]
        public long CreditoId { get; set; }

        // Propiedad de navegación
        public virtual Credito Credito { get; set; }
    }
}
