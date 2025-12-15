using Newtonsoft.Json;

namespace Comercializadora.Core.Models.BanQuito.Requests
{
    public class CreditGrantRequest
    {
        [JsonProperty("cedula")]
        public string Cedula { get; set; } = "";

        [JsonProperty("precioElectrodomestico")]
        public decimal PrecioElectrodomestico { get; set; }

        [JsonProperty("numeroCuotas")]
        public int NumeroCuotas { get; set; }
    }
}