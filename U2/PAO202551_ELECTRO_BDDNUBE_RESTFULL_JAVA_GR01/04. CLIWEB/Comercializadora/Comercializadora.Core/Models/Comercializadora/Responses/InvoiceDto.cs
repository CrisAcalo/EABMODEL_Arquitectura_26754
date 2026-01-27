using Newtonsoft.Json;

namespace Comercializadora.Core.Models.Comercializadora.Responses
{
    public class InvoiceDto
    {
        [JsonProperty("facturaId")]
        public int FacturaId { get; set; }

        [JsonProperty("numeroFactura")]
        public string? NumeroFactura { get; set; }

        [JsonProperty("fechaEmision")]
        public DateTime FechaEmision { get; set; }

        [JsonProperty("cedulaCliente")]
        public string? CedulaCliente { get; set; }

        [JsonProperty("nombreCliente")]
        public string? NombreCliente { get; set; }

        [JsonProperty("formaPago")]
        public string? FormaPago { get; set; }

        [JsonProperty("numeroCredito")]
        public string? NumeroCredito { get; set; }

        [JsonProperty("subtotal")]
        public decimal Subtotal { get; set; }

        [JsonProperty("descuento")]
        public decimal Descuento { get; set; }

        [JsonProperty("total")]
        public decimal Total { get; set; }

        [JsonProperty("detalles")]
        public List<InvoiceDetailDto> Detalles { get; set; } = new();
    }

    public class InvoiceDetailDto
    {
        [JsonProperty("productoId")]
        public int ProductoId { get; set; }

        [JsonProperty("nombreProducto")]
        public string? NombreProducto { get; set; }

        [JsonProperty("cantidad")]
        public int Cantidad { get; set; }

        [JsonProperty("precioUnitario")]
        public decimal PrecioUnitario { get; set; }

        [JsonProperty("subtotal")]
        public decimal Subtotal { get; set; }
    }
}