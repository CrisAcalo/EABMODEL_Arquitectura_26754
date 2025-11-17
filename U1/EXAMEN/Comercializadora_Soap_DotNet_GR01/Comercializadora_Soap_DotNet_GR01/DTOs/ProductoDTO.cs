using System;
using System.Runtime.Serialization;

namespace Comercializadora_Soap_DotNet_GR01.DTOs
{
    [DataContract]
    public class ProductoDTO
    {
        [DataMember]
        public int ProductoId { get; set; }

        [DataMember]
        public string Codigo { get; set; }

        [DataMember]
        public string Nombre { get; set; }

        [DataMember]
        public string Descripcion { get; set; }

        [DataMember]
        public decimal Precio { get; set; }

        [DataMember]
        public int Stock { get; set; }

        [DataMember]
        public string Categoria { get; set; }

        [DataMember]
        public string ImagenUrl { get; set; }

        [DataMember]
        public DateTime FechaRegistro { get; set; }

        [DataMember]
        public string Estado { get; set; }
    }
}