using System.Text.Json.Serialization;

namespace EurekaBank.Core.Models.DTOs
{
    public class SucursalDto
    {
        [JsonPropertyName("codigo")]
        public int Codigo { get; set; }

        [JsonPropertyName("nombre")]
        public string Nombre { get; set; } = string.Empty;

        [JsonPropertyName("ciudad")]
        public string Ciudad { get; set; } = string.Empty;

        [JsonPropertyName("direccion")]
        public string Direccion { get; set; } = string.Empty;

        [JsonPropertyName("direccionCompleta")]
        public string DireccionCompleta { get; set; } = string.Empty;

        [JsonPropertyName("latitud")]
        public decimal Latitud { get; set; }

        [JsonPropertyName("longitud")]
        public decimal Longitud { get; set; }

        [JsonPropertyName("contadorCuentas")]
        public int ContadorCuentas { get; set; }

        [JsonPropertyName("tieneCoordenadas")]
        public bool TieneCoordenadas { get; set; }
    }
}