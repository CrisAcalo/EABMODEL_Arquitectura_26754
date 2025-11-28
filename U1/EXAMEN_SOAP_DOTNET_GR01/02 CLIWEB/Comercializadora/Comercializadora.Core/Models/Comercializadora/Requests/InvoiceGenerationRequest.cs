// En: Models/Comercializadora/Requests/InvoiceGenerationRequest.cs
namespace Comercializadora.Core.Models.Comercializadora.Requests
{
    public class InvoiceGenerationRequest
    {
        public string CedulaCliente { get; set; }
        public string NombreCliente { get; set; }
        public string FormaPago { get; set; } // "EFECTIVO" o "CREDITO"
        public string? NumeroCredito { get; set; }
        public List<InvoiceRequestItemDto> Items { get; set; } = new();
    }
}