// En: Models/BanQuito/Responses/AmortizationItemDto.cs
namespace Comercializadora.Core.Models.BanQuito.Responses
{
    public class AmortizationItemDto
    {
        public int NumeroCuota { get; set; }
        public decimal ValorCuota { get; set; }
        public decimal Interes { get; set; }
        public decimal CapitalPagado { get; set; }
        public decimal Saldo { get; set; }
    }
}