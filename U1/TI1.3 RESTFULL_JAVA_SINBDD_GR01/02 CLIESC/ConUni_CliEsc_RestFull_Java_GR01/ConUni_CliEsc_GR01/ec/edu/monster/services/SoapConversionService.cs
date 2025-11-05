using ConUni_CliEsc_GR01.ec.edu.monster.models;

namespace ConUni_CliEsc_GR01.ec.edu.monster.services;

/// <summary>
/// Implementación del servicio de conversión usando SOAP
/// TODO: Implementar cuando el servicio SOAP esté disponible
/// </summary>
public class SoapConversionService : IConversionService
{
    private readonly string _serviceUrl;

    public SoapConversionService(string serviceUrl)
    {
        _serviceUrl = serviceUrl;
    }

    public Task<ConversionResultModel> ConvertirLongitudAsync(ConversionRequest request)
    {
        return Task.FromResult(CreateNotImplementedResult());
    }

    public Task<ConversionResultModel> ConvertirMasaAsync(ConversionRequest request)
    {
        return Task.FromResult(CreateNotImplementedResult());
    }

    public Task<ConversionResultModel> ConvertirTemperaturaAsync(ConversionRequest request)
    {
        return Task.FromResult(CreateNotImplementedResult());
    }

    private ConversionResultModel CreateNotImplementedResult()
    {
        return new ConversionResultModel
        {
            Exitoso = false,
            Error = new ConversionErrorModel
            {
                CodigoError = "NOT_IMPLEMENTED",
                Mensaje = "El servicio SOAP aún no está implementado",
                TipoError = "Sistema",
                FechaError = DateTime.Now,
                Detalles = "Por favor configure el tipo de servicio como 'REST' en appsettings.json"
            }
        };
    }
}
