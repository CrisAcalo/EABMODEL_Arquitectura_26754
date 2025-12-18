using Newtonsoft.Json;

namespace Comercializadora.Core.Models.Comercializadora.Requests
{
    public class CalculationRequest
    {
        [JsonProperty("items")]
        public List<InvoiceRequestItemDto> Items { get; set; } = new();
    }
}