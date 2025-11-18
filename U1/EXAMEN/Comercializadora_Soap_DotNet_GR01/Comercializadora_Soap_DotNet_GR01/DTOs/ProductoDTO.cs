using System;
using System.Runtime.Serialization;

namespace Comercializadora_Soap_DotNet_GR01.DTOs
{
    [DataContract]
    public class ProductoDTO
    {
        [DataMember(Order = 1)]
        public int ProductoId { get; set; }

        [DataMember(Order = 2)]
        public string Codigo { get; set; }

        [DataMember(Order = 3)]
        public string Nombre { get; set; }

        [DataMember(Order = 4)]
        public string Descripcion { get; set; }

        [DataMember(Order = 5)]
        public decimal Precio { get; set; }

        [DataMember(Order = 6)]
        public int Stock { get; set; }

        [DataMember(Order = 7)]
        public string Categoria { get; set; }

        [DataMember(Order = 8)]
        public string ImagenUrl { get; set; }

        [DataMember(Order = 9)]
        public DateTime FechaRegistro { get; set; }

        [DataMember(Order = 10)]
        public string Estado { get; set; }
    }
}