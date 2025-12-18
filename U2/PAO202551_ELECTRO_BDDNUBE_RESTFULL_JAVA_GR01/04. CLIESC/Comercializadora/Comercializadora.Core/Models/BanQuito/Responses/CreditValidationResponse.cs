using Newtonsoft.Json;

namespace Comercializadora.Core.Models.BanQuito.Responses
{
    public class CreditValidationResponse
    {
        [JsonProperty("esValido")]
        public bool EsValido { get; set; }

        [JsonProperty("mensaje")]
        public string? Mensaje { get; set; }

        [JsonProperty("nombreCompleto")]
        public string? NombreCompleto { get; set; }

        [JsonProperty("cedula")]
        public string? Cedula { get; set; }
    }
}