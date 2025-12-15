using Newtonsoft.Json;

namespace Comercializadora.Core.Models.BanQuito.Responses
{
    public class AmortizationItemDto
    {
        [JsonProperty("numeroCuota")]
        public int NumeroCuota { get; set; }

        [JsonProperty("valorCuota")]
        public decimal Cuota { get; set; }

        [JsonProperty("interes")]
        public decimal Interes { get; set; }

        [JsonProperty("capitalPagado")]
        public decimal Capital { get; set; }

        [JsonProperty("saldo")]
        public decimal SaldoPendiente { get; set; }
    }
}