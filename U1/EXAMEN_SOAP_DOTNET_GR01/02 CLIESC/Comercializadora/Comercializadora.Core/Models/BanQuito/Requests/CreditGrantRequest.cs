// En: Models/BanQuito/Requests/CreditGrantRequest.cs
namespace Comercializadora.Core.Models.BanQuito.Requests
{
    public class CreditGrantRequest
    {
        public string Cedula { get; set; }
        public decimal PrecioElectrodomestico { get; set; }
        public int NumeroCuotas { get; set; }
    }
}