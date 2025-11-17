using System.Runtime.Serialization;

namespace BanquitoServer_Soap_DotNet_GR01.DTOs
{
    /// <summary>
    /// DTO para respuesta de monto máximo de crédito
    /// </summary>
    [DataContract]
    public class MontoMaximoCreditoDTO
    {
        [DataMember]
        public string Cedula { get; set; }

        [DataMember]
        public decimal MontoMaximo { get; set; }

        [DataMember]
        public decimal PromedioDepositos { get; set; }

        [DataMember]
        public decimal PromedioRetiros { get; set; }

        [DataMember]
        public string Mensaje { get; set; }
    }
}
