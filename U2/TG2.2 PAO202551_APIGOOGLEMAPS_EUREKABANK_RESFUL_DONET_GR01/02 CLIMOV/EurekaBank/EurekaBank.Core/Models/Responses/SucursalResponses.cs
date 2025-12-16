using EurekaBank.Core.Models.DTOs;
using System.Text.Json.Serialization;

namespace EurekaBank.Core.Models.Responses
{
    public class SucursalesListResponse
    {
        [JsonPropertyName("datos")]
        public List<SucursalDto> Datos { get; set; } = new();

        [JsonPropertyName("exitoso")]
        public bool Exitoso { get; set; } = true;

        [JsonPropertyName("mensaje")]
        public string? Mensaje { get; set; }
    }

    public class SucursalResponse
    {
        [JsonPropertyName("datos")]
        public SucursalDto? Datos { get; set; }

        [JsonPropertyName("exitoso")]
        public bool Exitoso { get; set; }

        [JsonPropertyName("mensaje")]
        public string? Mensaje { get; set; }

        [JsonPropertyName("codigoError")]
        public string? CodigoError { get; set; }
    }

    public class DeleteSucursalResponse
    {
        [JsonPropertyName("exitoso")]
        public bool Exitoso { get; set; }

        [JsonPropertyName("mensaje")]
        public string? Mensaje { get; set; }

        [JsonPropertyName("datos")]
        public string? Datos { get; set; } // Solo para errores

        [JsonPropertyName("codigoError")]
        public string? CodigoError { get; set; }
    }
}