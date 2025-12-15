// En: Comercializadora.Core/Services/Abstractions/ICreditoService.cs
using Comercializadora.Core.Models.BanQuito.Requests;
using Comercializadora.Core.Models.BanQuito.Responses;

namespace Comercializadora.Core.Services.Abstractions
{
    public interface ICreditoService
    {
        // Paso 1 del flujo: Verificar si el cliente puede recibir crédito
        Task<CreditValidationResponse> ValidarSujetoCreditoAsync(string cedula);

        // Paso 2 del flujo: Verificar cuánto se le puede prestar
        Task<MaxCreditAmountResponse> ObtenerMontoMaximoAsync(string cedula);

        // Paso 3 del flujo (Final): Crear el crédito y comprometer la deuda
        Task<CreditGrantResponse> OtorgarCreditoAsync(CreditGrantRequest request);

        // Consulta posterior: Ver la tabla de pagos de un crédito existente
        Task<IEnumerable<AmortizationItemDto>> ObtenerTablaAmortizacionAsync(string numeroCredito);
    }
}