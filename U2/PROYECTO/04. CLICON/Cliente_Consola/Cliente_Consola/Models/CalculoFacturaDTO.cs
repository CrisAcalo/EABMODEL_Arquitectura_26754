namespace Comer_CliCon_SOAP_DotNet_GR01.Models
{
    public class CalculoFacturaDTO
    {
        public bool Exitoso { get; set; }
        public string Mensaje { get; set; } = string.Empty;
        public decimal Total { get; set; }
        public List<DetalleCalculoDTO> Detalles { get; set; } = new();
    }

    public class DetalleCalculoDTO
    {
        public int ProductoId { get; set; }
        public string NombreProducto { get; set; } = string.Empty;
        public int Cantidad { get; set; }
        public decimal PrecioUnitario { get; set; }
        public decimal Subtotal { get; set; }
    }

    public class SolicitudCalculoDTO
    {
        public List<ItemFacturaDTO> Items { get; set; } = new();
    }
}
