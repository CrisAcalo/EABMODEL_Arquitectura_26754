// En: Comercializadora.Core/Services/Implementations/Dispatchers/CreditoServiceDispatcher.cs
using Comercializadora.Core.Managers;
using Comercializadora.Core.Models.BanQuito.Requests;
using Comercializadora.Core.Models.BanQuito.Responses;
using Comercializadora.Core.Services.Abstractions;
using Comercializadora.Core.Services.Implementations.Rest; // Asumimos que existirá
using Comercializadora.Core.Services.Implementations.Soap;

namespace Comercializadora.Core.Services.Implementations.Dispatchers
{
    public class CreditoServiceDispatcher : ICreditoService
    {
        private readonly ApiServiceManager _apiManager;
        // private readonly RestCreditoService _restService;
        private readonly SoapCreditoService _soapService;

        public CreditoServiceDispatcher(
            ApiServiceManager apiManager,
            // RestCreditoService restService,
            SoapCreditoService soapService)
        {
            _apiManager = apiManager;
            // _restService = restService;
            _soapService = soapService;
        }

        private ICreditoService GetActiveService()
        {
            // Por ahora, solo devolvemos el servicio SOAP
            return _soapService;
        }

        public Task<CreditValidationResponse> ValidarSujetoCreditoAsync(string cedula) => GetActiveService().ValidarSujetoCreditoAsync(cedula);
        public Task<MaxCreditAmountResponse> ObtenerMontoMaximoAsync(string cedula) => GetActiveService().ObtenerMontoMaximoAsync(cedula);
        public Task<CreditGrantResponse> OtorgarCreditoAsync(CreditGrantRequest request) => GetActiveService().OtorgarCreditoAsync(request);
        public Task<IEnumerable<AmortizationItemDto>> ObtenerTablaAmortizacionAsync(string numeroCredito) => GetActiveService().ObtenerTablaAmortizacionAsync(numeroCredito);
    }
}