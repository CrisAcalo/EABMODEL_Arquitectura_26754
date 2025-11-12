using System.Runtime.Serialization;

namespace EurekaBank_Soap_DotNet_GR01.Models.DTOs
{
    /// <summary>
    /// DTO para resultado de operación de transferencia
    /// </summary>
    [DataContract]
    public class TransferenciaResultDTO
    {
        [DataMember]
        public CuentaResumenDTO CuentaOrigen { get; set; }

        [DataMember]
        public CuentaResumenDTO CuentaDestino { get; set; }

        [DataMember]
        public decimal ImporteTransferido { get; set; }

        [DataMember]
        public decimal ITF { get; set; }

        [DataMember]
        public decimal CostoPorMovimiento { get; set; }

        [DataMember]
        public decimal TotalDescontado { get; set; }
    }
}
