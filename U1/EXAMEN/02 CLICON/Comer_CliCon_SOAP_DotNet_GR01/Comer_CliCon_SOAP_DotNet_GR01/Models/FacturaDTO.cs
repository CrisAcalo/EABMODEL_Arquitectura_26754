using System.Runtime.Serialization;

namespace Comer_CliCon_SOAP_DotNet_GR01.Models
{
    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Comercializadora_Soap_DotNet_GR01.DTOs")]
    public class FacturaDTO
    {
        [DataMember]
        public int FacturaId { get; set; }

        [DataMember]
        public string NumeroFactura { get; set; } = string.Empty;

        [DataMember]
        public string CedulaCliente { get; set; } = string.Empty;

        [DataMember]
        public string NombreCliente { get; set; } = string.Empty;

        [DataMember]
        public string FormaPago { get; set; } = string.Empty;

        [DataMember]
        public decimal Subtotal { get; set; }

        [DataMember]
        public decimal Descuento { get; set; }

        [DataMember]
        public decimal Total { get; set; }

        [DataMember]
        public string NumeroCredito { get; set; } = string.Empty;

        [DataMember]
        public DateTime FechaEmision { get; set; }

        [DataMember]
        public List<DetalleFacturaDTO> Detalles { get; set; } = new();
    }

    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Comercializadora_Soap_DotNet_GR01.DTOs")]
    public class DetalleFacturaDTO
    {
        [DataMember]
        public int ProductoId { get; set; }

        [DataMember]
        public string NombreProducto { get; set; } = string.Empty;

        [DataMember]
        public int Cantidad { get; set; }

        [DataMember]
        public decimal PrecioUnitario { get; set; }

        [DataMember]
        public decimal Subtotal { get; set; }
    }

    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Comercializadora_Soap_DotNet_GR01.DTOs")]
    public class SolicitudFacturaDTO
    {
        [DataMember(IsRequired = false, Order = 1)]
        public string CedulaCliente { get; set; } = string.Empty;

        [DataMember(IsRequired = false, Order = 2)]
        public string FormaPago { get; set; } = string.Empty;

        [DataMember(IsRequired = false, Order = 3)]
        public List<ItemFacturaDTO> Items { get; set; } = new();

        [DataMember(IsRequired = false, Order = 4)]
        public string NombreCliente { get; set; } = string.Empty;

        [DataMember(IsRequired = false, Order = 5)]
        public string NumeroCredito { get; set; } = string.Empty;
    }

    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Comercializadora_Soap_DotNet_GR01.DTOs")]
    public class ItemFacturaDTO
    {
        [DataMember(Order = 1)]
        public int Cantidad { get; set; }

        [DataMember(Order = 2)]
        public int ProductoId { get; set; }
    }
}
