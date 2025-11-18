using System.Collections.Generic;
using System.Runtime.Serialization;

namespace Comercializadora_Soap_DotNet_GR01.DTOs
{
    /// <summary>
    /// DTO para respuesta de cálculo de factura (sin generarla)
    /// Permite conocer el total antes de solicitar crédito
    /// </summary>
    [DataContract]
    public class CalculoFacturaDTO
    {
        [DataMember]
        public decimal Subtotal { get; set; }

        [DataMember]
        public decimal Total { get; set; }

        [DataMember]
        public List<DetalleCalculoDTO> Detalles { get; set; }
    }

    /// <summary>
    /// Detalle de cada item en el cálculo
    /// </summary>
    [DataContract]
    public class DetalleCalculoDTO
    {
        [DataMember]
        public int ProductoId { get; set; }

        [DataMember]
        public string NombreProducto { get; set; }

        [DataMember]
        public int Cantidad { get; set; }

        [DataMember]
        public decimal PrecioUnitario { get; set; }

        [DataMember]
        public decimal Subtotal { get; set; }
    }
}
