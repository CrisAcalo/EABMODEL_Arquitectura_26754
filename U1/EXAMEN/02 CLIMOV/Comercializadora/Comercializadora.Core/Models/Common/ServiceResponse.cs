// En: Comercializadora.Core/Models/Common/ServiceResponse.cs
using System.Text.Json.Serialization;

namespace Comercializadora.Core.Models.Common
{
    // Un modelo de respuesta genérico para operaciones CRUD
    public class ServiceResponse<T>
    {
        // En SOAP usan 'Exito', en Java REST usan 'exito'.
        [JsonPropertyName("exito")]
        public bool Exito { get; set; }

        [JsonPropertyName("mensaje")]
        public string? Mensaje { get; set; }

        [JsonPropertyName("datos")]
        public T? Datos { get; set; }
    }

    // Una versión no genérica para operaciones como Eliminar, que no devuelven datos.
    public class ServiceResponse
    {
        [JsonPropertyName("exito")]
        public bool Exito { get; set; }

        [JsonPropertyName("mensaje")]
        public string? Mensaje { get; set; }
    }
}