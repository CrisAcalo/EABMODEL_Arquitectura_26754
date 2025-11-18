using System.Runtime.Serialization;

namespace Comer_CliCon_SOAP_DotNet_GR01.Models
{
    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Comercializadora_Soap_DotNet_GR01.DTOs")]
    public class CalculoFacturaDTO
    {
        [DataMember]
        public decimal Subtotal { get; set; }

        [DataMember]
        public decimal Descuento { get; set; }

        [DataMember]
        public decimal Total { get; set; }

        [DataMember]
        public string FormaPago { get; set; } = string.Empty;

        [DataMember]
        public List<DetalleCalculoDTO> Detalles { get; set; } = new();
    }

    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Comercializadora_Soap_DotNet_GR01.DTOs")]
    public class DetalleCalculoDTO
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
}
