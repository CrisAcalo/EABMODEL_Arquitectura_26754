// En: Models/BanQuito/Responses/CreditValidationResponse.cs
namespace Comercializadora.Core.Models.BanQuito.Responses
{
    public class CreditValidationResponse
    {
        public bool EsValido { get; set; }
        public string? Mensaje { get; set; }
        public string? Cedula { get; set; }
        public string? NombreCompleto { get; set; }
    }
}