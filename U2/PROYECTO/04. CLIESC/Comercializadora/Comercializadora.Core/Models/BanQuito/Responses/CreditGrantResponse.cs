using Newtonsoft.Json;

namespace Comercializadora.Core.Models.BanQuito.Responses
{
    public class CreditGrantResponse
    {
        [JsonProperty("exito")]
        public bool Exito { get; set; }

        [JsonProperty("mensaje")]
        public string? Mensaje { get; set; }

        [JsonProperty("numeroCredito")]
        public string? NumeroCredito { get; set; }

        [JsonProperty("montoCredito")]
        public decimal MontoCredito { get; set; }

        [JsonProperty("numeroCuotas")]
        public int NumeroCuotas { get; set; }

        [JsonProperty("tasaInteres")]
        public decimal TasaInteres { get; set; }

        [JsonProperty("cuotaMensual")]
        public decimal CuotaMensual { get; set; }

        [JsonProperty("tablaAmortizacion")]
        public List<AmortizationItemDto>? TablaAmortizacion { get; set; }
    }
}