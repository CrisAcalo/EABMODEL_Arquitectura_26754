using System.Runtime.Serialization;

namespace Comercializadora_Soap_DotNet_GR01.DTOs
{
    /// <summary>
    /// DTO para crear un producto (sin campos autogenerados)
    /// </summary>
    [DataContract]
    public class CrearProductoDTO
    {
        [DataMember(Order = 1)]
        public string Codigo { get; set; }

        [DataMember(Order = 2)]
        public string Nombre { get; set; }

        [DataMember(Order = 3)]
        public string Descripcion { get; set; }

        [DataMember(Order = 4)]
        public decimal Precio { get; set; }

        [DataMember(Order = 5)]
        public int Stock { get; set; }

        [DataMember(Order = 6)]
        public string Categoria { get; set; }

        [DataMember(Order = 7)]
        public string ImagenUrl { get; set; }
    }
}
