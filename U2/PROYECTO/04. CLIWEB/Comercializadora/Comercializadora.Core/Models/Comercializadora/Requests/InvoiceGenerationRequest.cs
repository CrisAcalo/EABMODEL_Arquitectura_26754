using Newtonsoft.Json;

namespace Comercializadora.Core.Models.Comercializadora.Requests
{
    public class InvoiceGenerationRequest
    {
        [JsonProperty("cedulaCliente")]
        public string CedulaCliente { get; set; } = "";

        [JsonProperty("nombreCliente")]
        public string NombreCliente { get; set; } = "";

        [JsonProperty("formaPago")]
        public string FormaPago { get; set; } = ""; // "EFECTIVO" o "CREDITO"

        [JsonProperty("numeroCredito")]
        public string? NumeroCredito { get; set; }

        [JsonProperty("items")]
        public List<InvoiceRequestItemDto> Items { get; set; } = new();
    }
}