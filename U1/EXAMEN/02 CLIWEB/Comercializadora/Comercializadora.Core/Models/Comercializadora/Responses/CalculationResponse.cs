// En: Models/Comercializadora/Responses/CalculationResponse.cs
namespace Comercializadora.Core.Models.Comercializadora.Responses
{
    public class CalculationResponse
    {
        public bool Exitoso { get; set; }
        public string? Mensaje { get; set; }
        public decimal Total { get; set; }
        public List<InvoiceDetailDto> Detalles { get; set; } = new();
    }
}