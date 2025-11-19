using Comercializadora.Core.Models.Comercializadora.Requests;
using Comercializadora.Core.Models.Comercializadora.Responses;
using Comercializadora.Core.Services.Abstractions;
namespace Comercializadora.Core.Services.Implementations.Rest
{
    public class RestFacturacionService : IFacturacionService
    {
        // Métodos no implementados por ahora
        public Task<CalculationResponse> CalcularTotalFacturaAsync(CalculationRequest request) => throw new NotImplementedException();
        public Task<InvoiceDto?> GenerarFacturaAsync(InvoiceGenerationRequest request) => throw new NotImplementedException();
        public Task<InvoiceDto?> ObtenerFacturaPorNumeroAsync(string numeroFactura) => throw new NotImplementedException();
        public Task<IEnumerable<InvoiceDto>> ObtenerFacturasPorClienteAsync(string cedula) => throw new NotImplementedException();
    }
}