using System.Runtime.Serialization;

namespace BanquitoServer_Soap_DotNet_GR01.DTOs
{
    /// <summary>
    /// DTO para solicitud de crédito
    /// Usa strings para validación manual y mensajes de error amigables
    /// </summary>
    [DataContract]
    public class SolicitudCreditoDTO
    {
        [DataMember]
        public string Cedula { get; set; }

        [DataMember]
        public string PrecioElectrodomestico { get; set; }

        [DataMember]
        public string NumeroCuotas { get; set; }
    }
}
