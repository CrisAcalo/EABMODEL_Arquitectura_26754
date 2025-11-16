using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BanquitoServer_Soap_DotNet_GR01.Models
{
    /// <summary>
    /// Entidad Movimiento (depósitos y retiros)
    /// </summary>
    [Table("Movimiento")]
    public class Movimiento
    {
        [Key]
        [Column("MovimientoId")]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long MovimientoId { get; set; }

        [Required]
        [Column("TipoMovimiento")]
        [MaxLength(20)]
        [Index("IX_Movimiento_Tipo")]
        public string TipoMovimiento { get; set; }

        [Required]
        [Column("Monto")]
        public decimal Monto { get; set; }

        [Required]
        [Column("FechaMovimiento")]
        [Index("IX_Movimiento_Fecha")]
        public DateTime FechaMovimiento { get; set; }

        [Column("Descripcion")]
        [MaxLength(200)]
        public string Descripcion { get; set; }

        [Column("SaldoAnterior")]
        public decimal? SaldoAnterior { get; set; }

        [Column("SaldoNuevo")]
        public decimal? SaldoNuevo { get; set; }

        // Foreign Key
        [Required]
        [Column("CuentaId")]
        [ForeignKey("Cuenta")]
        [Index("IX_Movimiento_CuentaId")]
        public long CuentaId { get; set; }

        // Propiedad de navegación
        public virtual Cuenta Cuenta { get; set; }
    }
}
