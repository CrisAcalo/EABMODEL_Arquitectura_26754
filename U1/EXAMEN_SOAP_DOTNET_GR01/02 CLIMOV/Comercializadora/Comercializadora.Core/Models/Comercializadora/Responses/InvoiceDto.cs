// En: Models/Comercializadora/Responses/InvoiceDto.cs
namespace Comercializadora.Core.Models.Comercializadora.Responses
{
    public class InvoiceDto
    {
        public int FacturaId { get; set; }
        public string? NumeroFactura { get; set; }
        public DateTime FechaEmision { get; set; }
        public string? CedulaCliente { get; set; }
        public string? NombreCliente { get; set; }
        public string? FormaPago { get; set; }
        public string? NumeroCredito { get; set; }
        public decimal Subtotal { get; set; }
        public decimal Descuento { get; set; }
        public decimal Total { get; set; }
        public List<InvoiceDetailDto> Detalles { get; set; } = new();
    }

    public class InvoiceDetailDto
    {
        public int ProductoId { get; set; }
        public string? NombreProducto { get; set; }
        public int Cantidad { get; set; }
        public decimal PrecioUnitario { get; set; }
        public decimal Subtotal { get; set; }
    }
}