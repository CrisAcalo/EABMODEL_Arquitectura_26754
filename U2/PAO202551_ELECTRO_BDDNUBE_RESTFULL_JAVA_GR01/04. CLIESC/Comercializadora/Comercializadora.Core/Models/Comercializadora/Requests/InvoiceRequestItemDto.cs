using Newtonsoft.Json;

namespace Comercializadora.Core.Models.Comercializadora.Requests
{
    public class InvoiceRequestItemDto
    {
        [JsonProperty("productoId")]
        public int ProductoId { get; set; }

        [JsonProperty("cantidad")]
        public int Cantidad { get; set; }
    }
}