// En: Models/BanQuito/Responses/CreditGrantResponse.cs
namespace Comercializadora.Core.Models.BanQuito.Responses
{
    public class CreditGrantResponse
    {
        public bool Exito { get; set; }
        public string? Mensaje { get; set; }
        public string? NumeroCredito { get; set; }
        public decimal MontoCredito { get; set; }
        public int NumeroCuotas { get; set; }
        public decimal TasaInteres { get; set; }
        public decimal CuotaMensual { get; set; }
        public List<AmortizationItemDto>? TablaAmortizacion { get; set; }
    }
}