using System.Runtime.Serialization;

namespace EurekaBank_Soap_DotNet_GR01.Models.DTOs
{
    /// <summary>
    /// DTO para resultado de operación de retiro
    /// </summary>
    [DataContract]
    public class RetiroResultDTO
    {
        [DataMember]
        public decimal SaldoAnterior { get; set; }

        [DataMember]
        public decimal SaldoNuevo { get; set; }

        [DataMember]
        public decimal ImporteRetiro { get; set; }

        [DataMember]
        public decimal ITF { get; set; }

        [DataMember]
        public decimal CostoPorMovimiento { get; set; }

        [DataMember]
        public decimal TotalDescontado { get; set; }
    }
}
