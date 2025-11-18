using System.Runtime.Serialization;

namespace Comer_CliCon_SOAP_DotNet_GR01.Models
{
    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Comercializadora_Soap_DotNet_GR01.DTOs")]
    public class RespuestaDTO
    {
        [DataMember]
        public bool Exito { get; set; }

        [DataMember]
        public string Mensaje { get; set; } = string.Empty;

        [DataMember]
        public ProductoDTO? Datos { get; set; }
    }

    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Comercializadora_Soap_DotNet_GR01.DTOs")]
    public class CrearProductoDTO
    {
        [DataMember(Order = 1)]
        public string Codigo { get; set; } = string.Empty;

        [DataMember(Order = 2)]
        public string Nombre { get; set; } = string.Empty;

        [DataMember(Order = 3)]
        public string Descripcion { get; set; } = string.Empty;

        [DataMember(Order = 4)]
        public decimal Precio { get; set; }

        [DataMember(Order = 5)]
        public int Stock { get; set; }

        [DataMember(Order = 6)]
        public string Categoria { get; set; } = string.Empty;

        [DataMember(Order = 7)]
        public string ImagenUrl { get; set; } = string.Empty;
    }

    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Comercializadora_Soap_DotNet_GR01.DTOs")]
    public class ActualizarProductoDTO
    {
        [DataMember(Order = 1)]
        public int ProductoId { get; set; }

        [DataMember(Order = 2)]
        public string Codigo { get; set; } = string.Empty;

        [DataMember(Order = 3)]
        public string Nombre { get; set; } = string.Empty;

        [DataMember(Order = 4)]
        public string Descripcion { get; set; } = string.Empty;

        [DataMember(Order = 5)]
        public decimal Precio { get; set; }

        [DataMember(Order = 6)]
        public int Stock { get; set; }

        [DataMember(Order = 7)]
        public string Categoria { get; set; } = string.Empty;

        [DataMember(Order = 8)]
        public string ImagenUrl { get; set; } = string.Empty;

        [DataMember(Order = 9)]
        public string Estado { get; set; } = string.Empty;
    }
}
