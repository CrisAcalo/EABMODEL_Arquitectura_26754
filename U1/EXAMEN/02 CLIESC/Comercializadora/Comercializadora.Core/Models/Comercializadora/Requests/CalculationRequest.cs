// En: Models/Comercializadora/Requests/CalculationRequest.cs
namespace Comercializadora.Core.Models.Comercializadora.Requests
{
    public class CalculationRequest
    {
        public List<InvoiceRequestItemDto> Items { get; set; } = new();
    }
}