using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BanquitoServer_Soap_DotNet_GR01.Models
{
    /// <summary>
    /// Entidad Cliente del banco
    /// </summary>
    [Table("Cliente")]
    public class Cliente
    {
        [Key]
        [Column("ClienteId")]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long ClienteId { get; set; }

        [Required]
        [Column("Cedula")]
        [MaxLength(10)]
        [Index("IX_Cliente_Cedula", IsUnique = true)]
        public string Cedula { get; set; }

        [Required]
        [Column("Nombres")]
        [MaxLength(100)]
        public string Nombres { get; set; }

        [Required]
        [Column("Apellidos")]
        [MaxLength(100)]
        public string Apellidos { get; set; }

        [Required]
        [Column("FechaNacimiento")]
        public DateTime FechaNacimiento { get; set; }

        [Column("EstadoCivil")]
        [MaxLength(20)]
        public string EstadoCivil { get; set; }

        [Column("Direccion")]
        [MaxLength(200)]
        public string Direccion { get; set; }

        [Column("Telefono")]
        [MaxLength(20)]
        public string Telefono { get; set; }

        [Column("Email")]
        [MaxLength(100)]
        public string Email { get; set; }

        // Propiedades de navegación
        public virtual ICollection<Cuenta> Cuentas { get; set; }
        public virtual ICollection<Credito> Creditos { get; set; }

        // Propiedad calculada
        [NotMapped]
        public string NombreCompleto
        {
            get { return $"{Nombres} {Apellidos}"; }
        }

        public Cliente()
        {
            Cuentas = new HashSet<Cuenta>();
            Creditos = new HashSet<Credito>();
        }
    }
}
