using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BanquitoServer_Soap_DotNet_GR01.Models
{
    /// <summary>
    /// Entidad Cuenta bancaria
    /// </summary>
    [Table("Cuenta")]
    public class Cuenta
    {
        [Key]
        [Column("CuentaId")]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long CuentaId { get; set; }

        [Required]
        [Column("NumeroCuenta")]
        [MaxLength(20)]
        [Index("IX_Cuenta_NumeroCuenta", IsUnique = true)]
        public string NumeroCuenta { get; set; }

        [Required]
        [Column("TipoCuenta")]
        [MaxLength(20)]
        public string TipoCuenta { get; set; }

        [Required]
        [Column("Saldo")]
        public decimal Saldo { get; set; }

        [Required]
        [Column("FechaApertura")]
        public DateTime FechaApertura { get; set; }

        [Required]
        [Column("Estado")]
        [MaxLength(20)]
        public string Estado { get; set; }

        // Foreign Key
        [Required]
        [Column("ClienteId")]
        [ForeignKey("Cliente")]
        [Index("IX_Cuenta_ClienteId")]
        public long ClienteId { get; set; }

        // Propiedades de navegación
        public virtual Cliente Cliente { get; set; }
        public virtual ICollection<Movimiento> Movimientos { get; set; }

        public Cuenta()
        {
            Movimientos = new HashSet<Movimiento>();
            Estado = "ACTIVA";
            Saldo = 0;
        }
    }
}
