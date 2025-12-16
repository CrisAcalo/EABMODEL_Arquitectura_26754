using System.Text.Json.Serialization;

namespace EurekaBank.Core.Models.Requests
{
    public class CreateSucursalRequest
    {
        [JsonPropertyName("nombre")]
        public string Nombre { get; set; } = string.Empty;

        [JsonPropertyName("ciudad")]
        public string Ciudad { get; set; } = string.Empty;

        [JsonPropertyName("direccion")]
        public string Direccion { get; set; } = string.Empty;

        [JsonPropertyName("latitud")]
        public decimal Latitud { get; set; }

        [JsonPropertyName("longitud")]
        public decimal Longitud { get; set; }
    }

    public class UpdateSucursalRequest
    {
        [JsonPropertyName("nombre")]
        public string? Nombre { get; set; }

        [JsonPropertyName("ciudad")]
        public string? Ciudad { get; set; }

        [JsonPropertyName("direccion")]
        public string? Direccion { get; set; }

        [JsonPropertyName("latitud")]
        public decimal? Latitud { get; set; }

        [JsonPropertyName("longitud")]
        public decimal? Longitud { get; set; }
    }
}