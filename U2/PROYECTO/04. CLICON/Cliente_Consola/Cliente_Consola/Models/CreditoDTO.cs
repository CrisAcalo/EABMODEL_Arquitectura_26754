namespace Comer_CliCon_SOAP_DotNet_GR01.Models
{
    public class ValidacionCreditoDTO
    {
        public bool EsValido { get; set; }
        public string Mensaje { get; set; } = string.Empty;
        public string Cedula { get; set; } = string.Empty;
        public string? NombreCompleto { get; set; }
    }

    public class MontoMaximoCreditoDTO
    {
        public string Cedula { get; set; } = string.Empty;
        public decimal MontoMaximo { get; set; }
        public decimal PromedioDepositos { get; set; }
        public decimal PromedioRetiros { get; set; }
        public string Mensaje { get; set; } = string.Empty;
    }

    public class RespuestaCreditoDTO
    {
        public bool Exito { get; set; }
        public string Mensaje { get; set; } = string.Empty;
        public string Cedula { get; set; } = string.Empty;
        public string NumeroCredito { get; set; } = string.Empty;
        public decimal MontoCredito { get; set; }
        public int NumeroCuotas { get; set; }
        public decimal CuotaMensual { get; set; }
        public decimal TasaInteres { get; set; }
        public List<CuotaAmortizacionDTO> TablaAmortizacion { get; set; } = new();
    }

    public class CuotaAmortizacionDTO
    {
        public int NumeroCuota { get; set; }
        public decimal ValorCuota { get; set; }
        public decimal Interes { get; set; }
        public decimal CapitalPagado { get; set; }
        public decimal Saldo { get; set; }
    }

    public class SolicitudCreditoDTO
    {
        public string Cedula { get; set; } = string.Empty;
        public string PrecioElectrodomestico { get; set; } = string.Empty;
        public string NumeroCuotas { get; set; } = string.Empty;
    }
}
