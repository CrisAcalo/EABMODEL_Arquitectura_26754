// En: Models/BanQuito/Responses/MaxCreditAmountResponse.cs
namespace Comercializadora.Core.Models.BanQuito.Responses
{
    public class MaxCreditAmountResponse
    {
        public decimal MontoMaximo { get; set; }
        public string? Mensaje { get; set; }
        public string? Cedula { get; set; }
        public decimal PromedioDepositos { get; set; }
        public decimal PromedioRetiros { get; set; }
    }
}