using System.Runtime.Serialization;

namespace BanquitoServer_Soap_DotNet_GR01.DTOs
{
    /// <summary>
    /// DTO para respuesta de validación de crédito
    /// </summary>
    [DataContract]
    public class ValidacionCreditoDTO
    {
        [DataMember]
        public bool EsValido { get; set; }

        [DataMember]
        public string Mensaje { get; set; }

        [DataMember]
        public string Cedula { get; set; }

        [DataMember]
        public string NombreCompleto { get; set; }
    }
}
