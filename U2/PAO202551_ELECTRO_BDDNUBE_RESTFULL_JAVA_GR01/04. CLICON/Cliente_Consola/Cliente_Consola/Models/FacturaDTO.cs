namespace Comer_CliCon_SOAP_DotNet_GR01.Models
{
    public class FacturaDTO
    {
        public int FacturaId { get; set; }
        public string NumeroFactura { get; set; } = string.Empty;
        public string CedulaCliente { get; set; } = string.Empty;
        public string NombreCliente { get; set; } = string.Empty;
        public string FormaPago { get; set; } = string.Empty;
        public decimal Subtotal { get; set; }
        public decimal Descuento { get; set; }
        public decimal Total { get; set; }
        public string NumeroCredito { get; set; } = string.Empty;
        public DateTime FechaEmision { get; set; }
        public List<DetalleFacturaDTO> Detalles { get; set; } = new();
    }

    public class DetalleFacturaDTO
    {
        public int ProductoId { get; set; }
        public string NombreProducto { get; set; } = string.Empty;
        public int Cantidad { get; set; }
        public decimal PrecioUnitario { get; set; }
        public decimal Subtotal { get; set; }
    }

    public class SolicitudFacturaDTO
    {
        public string CedulaCliente { get; set; } = string.Empty;
        public string NombreCliente { get; set; } = string.Empty;
        public string FormaPago { get; set; } = string.Empty;
        public string NumeroCredito { get; set; } = string.Empty;
        public List<ItemFacturaDTO> Items { get; set; } = new();
    }

    public class ItemFacturaDTO
    {
        public int ProductoId { get; set; }
        public int Cantidad { get; set; }
    }
}
