using Comercializadora.Core.Models.BanQuito.Requests;
using Comercializadora.Core.Models.BanQuito.Responses;
using Comercializadora.Core.Services.Abstractions;
namespace Comercializadora.Core.Services.Implementations.Rest
{
    public class RestCreditoService : ICreditoService
    {
        public Task<CreditValidationResponse> ValidarSujetoCreditoAsync(string cedula) => throw new NotImplementedException();
        public Task<MaxCreditAmountResponse> ObtenerMontoMaximoAsync(string cedula) => throw new NotImplementedException();
        public Task<CreditGrantResponse> OtorgarCreditoAsync(CreditGrantRequest request) => throw new NotImplementedException();
        public Task<IEnumerable<AmortizationItemDto>> ObtenerTablaAmortizacionAsync(string numeroCredito) => throw new NotImplementedException();
    }
}