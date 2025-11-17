using System.Runtime.Serialization;

namespace Comercializadora_Soap_DotNet_GR01.DTOs
{
    [DataContract]
    public class RespuestaDTO
    {
        [DataMember]
        public bool Exito { get; set; }

        [DataMember]
        public string Mensaje { get; set; }

        [DataMember]
        public ProductoDTO Datos { get; set; }
    }
}