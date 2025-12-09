// En: Comercializadora.Core/Models/Comercializadora/ProductDto.cs
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace Comercializadora.Core.Models.Comercializadora
{
    public class ProductDto
    {
        [JsonPropertyName("productoId")]
        public int ProductoId { get; set; }

        [Required(ErrorMessage = "El código es requerido")]
        [StringLength(20, MinimumLength = 3, ErrorMessage = "El código debe tener entre 3 y 20 caracteres")]
        [JsonPropertyName("codigo")]
        public string? Codigo { get; set; }

        [Required(ErrorMessage = "El nombre es requerido")]
        [StringLength(100, MinimumLength = 3, ErrorMessage = "El nombre debe tener entre 3 y 100 caracteres")]
        [JsonPropertyName("nombre")]
        public string? Nombre { get; set; }

        [Required(ErrorMessage = "La descripción es requerida")]
        [StringLength(500, MinimumLength = 10, ErrorMessage = "La descripción debe tener entre 10 y 500 caracteres")]
        [JsonPropertyName("descripcion")]
        public string? Descripcion { get; set; }

        [Required(ErrorMessage = "El precio es requerido")]
        [Range(0.01, 999999.99, ErrorMessage = "El precio debe estar entre 0.01 y 999,999.99")]
        [JsonPropertyName("precio")]
        public decimal Precio { get; set; }

        [Required(ErrorMessage = "El stock es requerido")]
        [Range(0, int.MaxValue, ErrorMessage = "El stock no puede ser negativo")]
        [JsonPropertyName("stock")]
        public int Stock { get; set; }

        [Required(ErrorMessage = "La categoría es requerida")]
        [JsonPropertyName("categoria")]
        public string? Categoria { get; set; }

        [Url(ErrorMessage = "Debe ser una URL válida")]
        [JsonPropertyName("imagen_url")]
        public string? ImagenUrl { get; set; }

        [JsonPropertyName("fechaRegistro")]
        public DateTime FechaRegistro { get; set; }

        [Required(ErrorMessage = "El estado es requerido")]
        [JsonPropertyName("estado")]
        public string? Estado { get; set; }
    }
}