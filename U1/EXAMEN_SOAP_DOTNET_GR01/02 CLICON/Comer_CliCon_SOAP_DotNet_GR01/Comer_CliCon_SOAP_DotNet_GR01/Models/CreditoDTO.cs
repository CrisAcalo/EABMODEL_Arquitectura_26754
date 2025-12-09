using System.Runtime.Serialization;

namespace Comer_CliCon_SOAP_DotNet_GR01.Models
{
    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/BanquitoServer_Soap_DotNet_GR01.DTOs")]
    public class ValidacionCreditoDTO
    {
        [DataMember]
        public bool EsValido { get; set; }

        [DataMember]
        public string Mensaje { get; set; } = string.Empty;

        [DataMember]
        public string Cedula { get; set; } = string.Empty;

        [DataMember]
        public string NombreCompleto { get; set; } = string.Empty;
    }

    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/BanquitoServer_Soap_DotNet_GR01.DTOs")]
    public class MontoMaximoCreditoDTO
    {
        [DataMember]
        public string Cedula { get; set; } = string.Empty;

        [DataMember]
        public decimal MontoMaximo { get; set; }

        [DataMember]
        public decimal PromedioDepositos { get; set; }

        [DataMember]
        public decimal PromedioRetiros { get; set; }

        [DataMember]
        public string Mensaje { get; set; } = string.Empty;
    }

    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/BanquitoServer_Soap_DotNet_GR01.DTOs")]
    public class RespuestaCreditoDTO
    {
        [DataMember]
        public bool Exito { get; set; }

        [DataMember]
        public string Mensaje { get; set; } = string.Empty;

        [DataMember]
        public string Cedula { get; set; } = string.Empty;

        [DataMember]
        public string NumeroCredito { get; set; } = string.Empty;

        [DataMember]
        public decimal MontoCredito { get; set; }

        [DataMember]
        public int NumeroCuotas { get; set; }

        [DataMember]
        public decimal CuotaMensual { get; set; }

        [DataMember]
        public decimal TasaInteres { get; set; }

        [DataMember]
        public List<CuotaAmortizacionDTO> TablaAmortizacion { get; set; } = new();
    }

    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/BanquitoServer_Soap_DotNet_GR01.DTOs")]
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

    [DataContract(Namespace = "http://schemas.datacontract.org/2004/07/BanquitoServer_Soap_DotNet_GR01.DTOs")]
    public class SolicitudCreditoDTO
    {
        [DataMember]
        public string Cedula { get; set; } = string.Empty;

        [DataMember]
        public string PrecioElectrodomestico { get; set; } = string.Empty;

        [DataMember]
        public string NumeroCuotas { get; set; } = string.Empty;
    }
}
