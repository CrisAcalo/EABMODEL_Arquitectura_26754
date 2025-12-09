using System.Runtime.Serialization;

namespace Comer_CliCon_SOAP_DotNet_GR01.Models
{
    /// <summary>
    /// DTO para respuesta de cálculo de factura (sin generarla)
    /// Permite conocer el total antes de solicitar crédito
    /// </summary>
    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Comercializadora_Soap_DotNet_GR01.DTOs")]
    public class CalculoFacturaDTO
    {
        [DataMember(Order = 1)]
        public bool Exitoso { get; set; }

        [DataMember(Order = 2)]
        public string Mensaje { get; set; } = string.Empty;

        [DataMember(Order = 3)]
        public decimal Total { get; set; }

        [DataMember(Order = 4)]
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
