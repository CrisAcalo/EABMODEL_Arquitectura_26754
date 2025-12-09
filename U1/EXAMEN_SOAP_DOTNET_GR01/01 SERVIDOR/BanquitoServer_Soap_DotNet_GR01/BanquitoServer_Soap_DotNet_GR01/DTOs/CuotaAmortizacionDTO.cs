using System.Runtime.Serialization;

namespace BanquitoServer_Soap_DotNet_GR01.DTOs
{
    /// <summary>
    /// DTO para cuota de amortización
    /// </summary>
    [DataContract]
    public class CuotaAmortizacionDTO
    {
        [DataMember]
        public int NumeroCuota { get; set; }

        [DataMember]
        public decimal ValorCuota { get; set; }

        [DataMember]
        public decimal Interes { get; set; }

        [DataMember]
        public decimal CapitalPagado { get; set; }

        [DataMember]
        public decimal Saldo { get; set; }
    }
}
