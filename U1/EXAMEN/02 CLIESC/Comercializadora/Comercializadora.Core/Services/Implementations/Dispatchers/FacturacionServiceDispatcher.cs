// En: Comercializadora.Core/Services/Implementations/Dispatchers/FacturacionServiceDispatcher.cs
using Comercializadora.Core.Managers;
using Comercializadora.Core.Models.Comercializadora.Requests;
using Comercializadora.Core.Models.Comercializadora.Responses;
using Comercializadora.Core.Services.Abstractions;
using Comercializadora.Core.Services.Implementations.Rest; // Asumimos que existirá
using Comercializadora.Core.Services.Implementations.Soap;

namespace Comercializadora.Core.Services.Implementations.Dispatchers
{
    public class FacturacionServiceDispatcher : IFacturacionService
    {
        private readonly ApiServiceManager _apiManager;
        // private readonly RestFacturacionService _restService; // Preparado para el futuro
        private readonly SoapFacturacionService _soapService;

        public FacturacionServiceDispatcher(
            ApiServiceManager apiManager,
            // RestFacturacionService restService,
            SoapFacturacionService soapService)
        {
            _apiManager = apiManager;
            // _restService = restService;
            _soapService = soapService;
        }

        private IFacturacionService GetActiveService()
        {
            // Por ahora, solo devolvemos el servicio SOAP
            // En el futuro, aquí iría la lógica del if/else
            return _soapService;
        }

        public Task<CalculationResponse> CalcularTotalFacturaAsync(CalculationRequest request) => GetActiveService().CalcularTotalFacturaAsync(request);
        public Task<InvoiceDto?> GenerarFacturaAsync(InvoiceGenerationRequest request) => GetActiveService().GenerarFacturaAsync(request);
        public Task<InvoiceDto?> ObtenerFacturaPorNumeroAsync(string numeroFactura) => GetActiveService().ObtenerFacturaPorNumeroAsync(numeroFactura);
        public Task<IEnumerable<InvoiceDto>> ObtenerFacturasPorClienteAsync(string cedula) => GetActiveService().ObtenerFacturasPorClienteAsync(cedula);
    }
}