// En: Comercializadora.Core/Models/Comercializadora/UpdateProductDto.cs
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Newtonsoft.Json;

namespace Comercializadora.Core.Models.Comercializadora
{
    public class UpdateProductDto
    {
        [Required(ErrorMessage = "El ID es requerido")]
        [JsonProperty("productoId")]
        [JsonPropertyName("productoId")]
        public int ProductoId { get; set; }

        [Required(ErrorMessage = "El código es requerido")]
        [StringLength(20, MinimumLength = 3, ErrorMessage = "El código debe tener entre 3 y 20 caracteres")]
        [JsonProperty("codigo")]
        [JsonPropertyName("codigo")]
        public string? Codigo { get; set; }

        [Required(ErrorMessage = "El nombre es requerido")]
        [StringLength(100, MinimumLength = 3, ErrorMessage = "El nombre debe tener entre 3 y 100 caracteres")]
        [JsonProperty("nombre")]
        [JsonPropertyName("nombre")]
        public string? Nombre { get; set; }

        [Required(ErrorMessage = "La descripción es requerida")]
        [StringLength(500, MinimumLength = 10, ErrorMessage = "La descripción debe tener entre 10 y 500 caracteres")]
        [JsonProperty("descripcion")]
        [JsonPropertyName("descripcion")]
        public string? Descripcion { get; set; }

        [Required(ErrorMessage = "El precio es requerido")]
        [Range(0.01, 999999.99, ErrorMessage = "El precio debe estar entre 0.01 y 999,999.99")]
        [JsonProperty("precio")]
        [JsonPropertyName("precio")]
        public decimal Precio { get; set; }

        [Required(ErrorMessage = "El stock es requerido")]
        [Range(0, int.MaxValue, ErrorMessage = "El stock no puede ser negativo")]
        [JsonProperty("stock")]
        [JsonPropertyName("stock")]
        public int Stock { get; set; }

        [Required(ErrorMessage = "La categoría es requerida")]
        [JsonProperty("categoria")]
        [JsonPropertyName("categoria")]
        public string? Categoria { get; set; }

        [Url(ErrorMessage = "Debe ser una URL válida")]
        [JsonProperty("imagen_url")]
        [JsonPropertyName("imagen_url")]
        public string? ImagenUrl { get; set; }

        [Required(ErrorMessage = "El estado es requerido")]
        [JsonProperty("estado")]
        [JsonPropertyName("estado")]
        public string? Estado { get; set; }
    }
}