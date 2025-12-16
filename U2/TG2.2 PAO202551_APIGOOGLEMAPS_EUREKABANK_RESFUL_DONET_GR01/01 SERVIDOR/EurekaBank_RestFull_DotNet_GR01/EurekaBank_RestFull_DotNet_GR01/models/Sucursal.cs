using System;
using System.Runtime.Serialization;
using System.ComponentModel.DataAnnotations;

namespace EurekaBank_RestFull_DotNet_GR01.Models
{
    /// <summary>
    /// Representa una sucursal del banco
    /// </summary>
    [DataContract]
    public class Sucursal
    {
        [DataMember]
        public int Codigo { get; set; }

        [DataMember]
        [Required(ErrorMessage = "El nombre de la sucursal es requerido")]
        [StringLength(50, ErrorMessage = "El nombre debe tener máximo 50 caracteres")]
        public string Nombre { get; set; }

        [DataMember]
        [Required(ErrorMessage = "La ciudad es requerida")]
        [StringLength(30, ErrorMessage = "La ciudad debe tener máximo 30 caracteres")]
        public string Ciudad { get; set; }

        [DataMember]
        [StringLength(50, ErrorMessage = "La dirección debe tener máximo 50 caracteres")]
        public string Direccion { get; set; }

        [DataMember]
        public int ContadorCuentas { get; set; }

        [DataMember]
        [Range(-90, 90, ErrorMessage = "La latitud debe estar entre -90 y 90 grados")]
        public decimal? Latitud { get; set; }

        [DataMember]
        [Range(-180, 180, ErrorMessage = "La longitud debe estar entre -180 y 180 grados")]
        public decimal? Longitud { get; set; }

        /// <summary>
        /// Indica si la sucursal tiene coordenadas de geolocalización
        /// </summary>
        public bool TieneCoordenadas => Latitud.HasValue && Longitud.HasValue;
    }
}
