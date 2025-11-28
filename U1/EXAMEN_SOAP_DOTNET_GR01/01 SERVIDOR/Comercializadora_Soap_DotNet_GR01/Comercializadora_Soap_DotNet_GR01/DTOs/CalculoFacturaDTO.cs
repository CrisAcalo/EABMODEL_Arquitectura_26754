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
        [DataMember(Order = 1)]
        public bool Exitoso { get; set; }

        [DataMember(Order = 2)]
        public string Mensaje { get; set; }

        [DataMember(Order = 3)]
        public decimal Total { get; set; }

        [DataMember(Order = 4)]
        public List<DetalleCalculoDTO> Detalles { get; set; }

        public CalculoFacturaDTO()
        {
            Detalles = new List<DetalleCalculoDTO>();
        }
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
