using System.Runtime.Serialization;

namespace Comer_CliCon_SOAP_DotNet_GR01.Models
{
    /// <summary>
    /// DTO específico para calcular total de factura
    /// Solo requiere los productos (Items)
    /// </summary>
    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/Comercializadora_Soap_DotNet_GR01.DTOs")]
    public class SolicitudCalculoDTO
    {
        [DataMember(IsRequired = true, Order = 1)]
        public List<ItemFacturaDTO> Items { get; set; } = new();
    }
}
