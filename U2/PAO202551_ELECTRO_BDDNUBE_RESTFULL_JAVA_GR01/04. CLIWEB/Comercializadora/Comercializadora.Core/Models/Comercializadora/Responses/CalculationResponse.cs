using Newtonsoft.Json;

namespace Comercializadora.Core.Models.Comercializadora.Responses
{
    public class CalculationResponse
    {
        [JsonProperty("exitoso")]
        public bool Exitoso { get; set; }

        [JsonProperty("mensaje")]
        public string? Mensaje { get; set; }

        [JsonProperty("total")]
        public decimal Total { get; set; }

        [JsonProperty("detalles")]
        public List<CalculationDetailDto>? Detalles { get; set; }

        // Alias para compatibilidad con la UI existente
        [JsonIgnore]
        public decimal Subtotal 
        { 
            get => Total; 
            set { } // Setter vacío para evitar error de solo lectura
        }

        [JsonIgnore]
        public decimal Descuento 
        { 
            get => 0; 
            set { } // Setter vacío para evitar error de solo lectura
        }

        [JsonIgnore]
        public decimal TotalCalculado 
        { 
            get => Total; 
            set { } // Setter vacío para evitar error de solo lectura
        }
    }

    public class CalculationDetailDto
    {
        [JsonProperty("productoId")]
        public int ProductoId { get; set; }

        [JsonProperty("nombreProducto")]
        public string? NombreProducto { get; set; }

        [JsonProperty("cantidad")]
        public int Cantidad { get; set; }

        [JsonProperty("precioUnitario")]
        public decimal PrecioUnitario { get; set; }

        [JsonProperty("subtotal")]
        public decimal Subtotal { get; set; }
    }
}