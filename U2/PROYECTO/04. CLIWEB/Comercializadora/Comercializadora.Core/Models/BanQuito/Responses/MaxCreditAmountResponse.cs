using Newtonsoft.Json;

namespace Comercializadora.Core.Models.BanQuito.Responses
{
    public class MaxCreditAmountResponse
    {
        [JsonProperty("cedula")]
        public string? Cedula { get; set; }

        [JsonProperty("montoMaximo")]
        public decimal MontoMaximo { get; set; }

        [JsonProperty("mensaje")]
        public string? Mensaje { get; set; }
    }
}