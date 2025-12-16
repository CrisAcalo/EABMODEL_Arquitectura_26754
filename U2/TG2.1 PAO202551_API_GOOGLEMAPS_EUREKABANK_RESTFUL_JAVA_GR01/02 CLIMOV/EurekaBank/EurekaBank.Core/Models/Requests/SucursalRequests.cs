using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace EurekaBank.Core.Models.Requests
{
    public class CreateSucursalRequest
    {
        [JsonPropertyName("nombre")]
        [Required(ErrorMessage = "El nombre de la sucursal es requerido")]
        [StringLength(100, MinimumLength = 2, ErrorMessage = "El nombre debe tener entre 2 y 100 caracteres")]
        public string Nombre { get; set; } = string.Empty;

        [JsonPropertyName("ciudad")]
        [Required(ErrorMessage = "La ciudad es requerida")]
        [StringLength(50, MinimumLength = 2, ErrorMessage = "La ciudad debe tener entre 2 y 50 caracteres")]
        public string Ciudad { get; set; } = string.Empty;

        [JsonPropertyName("direccion")]
        [Required(ErrorMessage = "La dirección es requerida")]
        [StringLength(200, MinimumLength = 5, ErrorMessage = "La dirección debe tener entre 5 y 200 caracteres")]
        public string Direccion { get; set; } = string.Empty;

        [JsonPropertyName("latitud")]
        [Required(ErrorMessage = "La latitud es requerida")]
        [Range(-90, 90, ErrorMessage = "La latitud debe estar entre -90 y 90")]
        public decimal Latitud { get; set; }

        [JsonPropertyName("longitud")]
        [Required(ErrorMessage = "La longitud es requerida")]
        [Range(-180, 180, ErrorMessage = "La longitud debe estar entre -180 y 180")]
        public decimal Longitud { get; set; }
    }

    public class UpdateSucursalRequest
    {
        [JsonPropertyName("nombre")]
        [StringLength(100, MinimumLength = 2, ErrorMessage = "El nombre debe tener entre 2 y 100 caracteres")]
        public string? Nombre { get; set; }

        [JsonPropertyName("ciudad")]
        [StringLength(50, MinimumLength = 2, ErrorMessage = "La ciudad debe tener entre 2 y 50 caracteres")]
        public string? Ciudad { get; set; }

        [JsonPropertyName("direccion")]
        [StringLength(200, MinimumLength = 5, ErrorMessage = "La dirección debe tener entre 5 y 200 caracteres")]
        public string? Direccion { get; set; }

        [JsonPropertyName("latitud")]
        [Range(-90, 90, ErrorMessage = "La latitud debe estar entre -90 y 90")]
        public decimal? Latitud { get; set; }

        [JsonPropertyName("longitud")]
        [Range(-180, 180, ErrorMessage = "La longitud debe estar entre -180 y 180")]
        public decimal? Longitud { get; set; }
    }
}