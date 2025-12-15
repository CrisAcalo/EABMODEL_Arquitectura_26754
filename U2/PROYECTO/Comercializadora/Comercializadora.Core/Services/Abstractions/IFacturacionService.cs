// En: Comercializadora.Core/Services/Abstractions/IFacturacionService.cs
using Comercializadora.Core.Models.Comercializadora.Requests;
using Comercializadora.Core.Models.Comercializadora.Responses;

namespace Comercializadora.Core.Services.Abstractions
{
    public interface IFacturacionService
    {
        // Operación de pre-venta: Calcula totales y valida stock antes de facturar
        Task<CalculationResponse> CalcularTotalFacturaAsync(CalculationRequest request);

        // Operación de venta: Genera la factura oficial
        Task<InvoiceDto?> GenerarFacturaAsync(InvoiceGenerationRequest request);

        // Consultas
        Task<InvoiceDto?> ObtenerFacturaPorNumeroAsync(string numeroFactura);
        Task<IEnumerable<InvoiceDto>> ObtenerFacturasPorClienteAsync(string cedula);
    }
}