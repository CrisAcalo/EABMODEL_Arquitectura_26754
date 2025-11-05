using System;
using System.Threading.Tasks;
using ConUni_CliEsc_GR01.ec.edu.monster.models;
using ConUni_CliEsc_GR01.MasaServiceReference;
using ConUni_CliEsc_GR01.LongitudServiceReference;
using ConUni_CliEsc_GR01.TemperaturaServiceReference;

namespace ConUni_CliEsc_GR01.ec.edu.monster.services;

/// <summary>
/// Implementación del servicio de conversión usando SOAP/WCF
/// Usa las referencias generadas automáticamente desde los WSDL
/// </summary>
public class SoapConversionService : IConversionService
{
    private readonly string _masaServiceUrl;
    private readonly string _longitudServiceUrl;
    private readonly string _temperaturaServiceUrl;

    public SoapConversionService(string masaServiceUrl, string longitudServiceUrl, string temperaturaServiceUrl)
    {
        _masaServiceUrl = masaServiceUrl;
        _longitudServiceUrl = longitudServiceUrl;
        _temperaturaServiceUrl = temperaturaServiceUrl;
    }

    public async Task<ConversionResultModel> ConvertirMasaAsync(ConversionRequest request)
    {
        try
        {
            using var client = new MasaServiceClient(
                MasaServiceClient.EndpointConfiguration.MasaServicePort,
                _masaServiceUrl
            );

            MasaServiceReference.conversionResult? result = null;

            // Mapear unidades a método SOAP específico
            var key = $"{request.UnidadOrigen}-{request.UnidadDestino}";
            switch (key)
            {
                case "Kilogramo-Quintal":
                    result = (await client.KilogramoAQuintalAsync(request.Valor))?.@return;
                    break;
                case "Quintal-Kilogramo":
                    result = (await client.QuintalAKilogramoAsync(request.Valor))?.@return;
                    break;
                case "Kilogramo-Libra":
                    result = (await client.KilogramoALibraAsync(request.Valor))?.@return;
                    break;
                case "Libra-Kilogramo":
                    result = (await client.LibraAKilogramoAsync(request.Valor))?.@return;
                    break;
                case "Quintal-Libra":
                    result = (await client.QuintalALibraAsync(request.Valor))?.@return;
                    break;
                case "Libra-Quintal":
                    result = (await client.LibraAQuintalAsync(request.Valor))?.@return;
                    break;
            }

            if (result == null)
            {
                return CreateUnsupportedConversionError(request.UnidadOrigen, request.UnidadDestino);
            }

            return ConvertMasaToClientModel(result);
        }
        catch (Exception ex)
        {
            return CreateErrorResult("Error al comunicarse con el servicio SOAP de Masa", ex);
        }
    }

    public async Task<ConversionResultModel> ConvertirLongitudAsync(ConversionRequest request)
    {
        try
        {
            using var client = new LongitudServiceClient(
                LongitudServiceClient.EndpointConfiguration.LongitudServicePort,
                _longitudServiceUrl
            );

            LongitudServiceReference.conversionResult? result = null;

            // Mapear unidades a método SOAP específico
            var key = $"{request.UnidadOrigen}-{request.UnidadDestino}";
            switch (key)
            {
                case "Milla-Metro":
                    result = (await client.MillaAMetroAsync(request.Valor))?.@return;
                    break;
                case "Metro-Milla":
                    result = (await client.MetroAMillaAsync(request.Valor))?.@return;
                    break;
                case "Milla-Pulgada":
                    result = (await client.MillaAPulgadaAsync(request.Valor))?.@return;
                    break;
                case "Pulgada-Milla":
                    result = (await client.PulgadaAMillaAsync(request.Valor))?.@return;
                    break;
                case "Metro-Pulgada":
                    result = (await client.MetroAPulgadaAsync(request.Valor))?.@return;
                    break;
                case "Pulgada-Metro":
                    result = (await client.PulgadaAMetroAsync(request.Valor))?.@return;
                    break;
            }

            if (result == null)
            {
                return CreateUnsupportedConversionError(request.UnidadOrigen, request.UnidadDestino);
            }

            return ConvertLongitudToClientModel(result);
        }
        catch (Exception ex)
        {
            return CreateErrorResult("Error al comunicarse con el servicio SOAP de Longitud", ex);
        }
    }

    public async Task<ConversionResultModel> ConvertirTemperaturaAsync(ConversionRequest request)
    {
        try
        {
            using var client = new TemperaturaServiceClient(
                TemperaturaServiceClient.EndpointConfiguration.TemperaturaServicePort,
                _temperaturaServiceUrl
            );

            TemperaturaServiceReference.conversionResult? result = null;

            // Mapear unidades a método SOAP específico
            var key = $"{request.UnidadOrigen}-{request.UnidadDestino}";
            switch (key)
            {
                case "Celsius-Fahrenheit":
                    result = (await client.CelsiusAFahrenheitAsync(request.Valor))?.@return;
                    break;
                case "Fahrenheit-Celsius":
                    result = (await client.FahrenheitACelsiusAsync(request.Valor))?.@return;
                    break;
                case "Fahrenheit-Kelvin":
                    result = (await client.FahrenheitAKelvinAsync(request.Valor))?.@return;
                    break;
                case "Kelvin-Fahrenheit":
                    result = (await client.KelvinAFahrenheitAsync(request.Valor))?.@return;
                    break;
                case "Kelvin-Celsius":
                    result = (await client.KelvinACelsiusAsync(request.Valor))?.@return;
                    break;
                case "Celsius-Kelvin":
                    result = (await client.CelsiusAKelvinAsync(request.Valor))?.@return;
                    break;
            }

            if (result == null)
            {
                return CreateUnsupportedConversionError(request.UnidadOrigen, request.UnidadDestino);
            }

            return ConvertTemperaturaToClientModel(result);
        }
        catch (Exception ex)
        {
            return CreateErrorResult("Error al comunicarse con el servicio SOAP de Temperatura", ex);
        }
    }

    /// <summary>
    /// Convierte el resultado SOAP de Masa a modelo del cliente
    /// </summary>
    private ConversionResultModel ConvertMasaToClientModel(MasaServiceReference.conversionResult soapResult)
    {
        if (soapResult == null)
        {
            return CreateNullResultError();
        }

        var clientResult = new ConversionResultModel
        {
            Exitoso = soapResult.Exitoso
        };

        if (soapResult.Exitoso && soapResult.Resultado != null)
        {
            clientResult.Resultado = new UnidadConversionModel
            {
                ValorOriginal = soapResult.Resultado.valorOriginal,
                ValorConvertidoExacto = soapResult.Resultado.valorConvertidoExacto,
                ValorConvertidoRedondeado = soapResult.Resultado.valorConvertidoRedondeado,
                UnidadOrigen = soapResult.Resultado.unidadOrigen ?? string.Empty,
                UnidadDestino = soapResult.Resultado.unidadDestino ?? string.Empty,
                FactorConversion = soapResult.Resultado.factorConversion,
                FechaConversion = DateTime.Now // localDateTime es una clase vacía en Java WSDL
            };
        }
        else if (soapResult.Error != null)
        {
            clientResult.Error = ConvertErrorToClientModel(soapResult.Error);
        }

        return clientResult;
    }

    /// <summary>
    /// Convierte el resultado SOAP de Longitud a modelo del cliente
    /// </summary>
    private ConversionResultModel ConvertLongitudToClientModel(LongitudServiceReference.conversionResult soapResult)
    {
        if (soapResult == null)
        {
            return CreateNullResultError();
        }

        var clientResult = new ConversionResultModel
        {
            Exitoso = soapResult.Exitoso
        };

        if (soapResult.Exitoso && soapResult.Resultado != null)
        {
            clientResult.Resultado = new UnidadConversionModel
            {
                ValorOriginal = soapResult.Resultado.valorOriginal,
                ValorConvertidoExacto = soapResult.Resultado.valorConvertidoExacto,
                ValorConvertidoRedondeado = soapResult.Resultado.valorConvertidoRedondeado,
                UnidadOrigen = soapResult.Resultado.unidadOrigen ?? string.Empty,
                UnidadDestino = soapResult.Resultado.unidadDestino ?? string.Empty,
                FactorConversion = soapResult.Resultado.factorConversion,
                FechaConversion = DateTime.Now // localDateTime es una clase vacía en Java WSDL
            };
        }
        else if (soapResult.Error != null)
        {
            clientResult.Error = ConvertErrorToClientModel(soapResult.Error);
        }

        return clientResult;
    }

    /// <summary>
    /// Convierte el resultado SOAP de Temperatura a modelo del cliente
    /// </summary>
    private ConversionResultModel ConvertTemperaturaToClientModel(TemperaturaServiceReference.conversionResult soapResult)
    {
        if (soapResult == null)
        {
            return CreateNullResultError();
        }

        var clientResult = new ConversionResultModel
        {
            Exitoso = soapResult.Exitoso
        };

        if (soapResult.Exitoso && soapResult.Resultado != null)
        {
            clientResult.Resultado = new UnidadConversionModel
            {
                ValorOriginal = soapResult.Resultado.valorOriginal,
                ValorConvertidoExacto = soapResult.Resultado.valorConvertidoExacto,
                ValorConvertidoRedondeado = soapResult.Resultado.valorConvertidoRedondeado,
                UnidadOrigen = soapResult.Resultado.unidadOrigen ?? string.Empty,
                UnidadDestino = soapResult.Resultado.unidadDestino ?? string.Empty,
                FactorConversion = soapResult.Resultado.factorConversion,
                FechaConversion = DateTime.Now // localDateTime es una clase vacía en Java WSDL
            };
        }
        else if (soapResult.Error != null)
        {
            clientResult.Error = ConvertErrorToClientModel(soapResult.Error);
        }

        return clientResult;
    }

    /// <summary>
    /// Convierte el error SOAP a modelo del cliente (sobrecarga para MasaServiceReference)
    /// </summary>
    private ConversionErrorModel ConvertErrorToClientModel(MasaServiceReference.conversionError soapError)
    {
        return new ConversionErrorModel
        {
            CodigoError = soapError.CodigoError ?? "UNKNOWN",
            Mensaje = soapError.Mensaje ?? "Error desconocido",
            TipoError = soapError.TipoError ?? "Sistema",
            ValorProblematico = soapError.ValorProblematico,
            Unidad = soapError.Unidad,
            FechaError = DateTime.Now, // localDateTime es una clase vacía en Java WSDL
            Detalles = soapError.Detalles
        };
    }

    /// <summary>
    /// Convierte el error SOAP a modelo del cliente (sobrecarga para LongitudServiceReference)
    /// </summary>
    private ConversionErrorModel ConvertErrorToClientModel(LongitudServiceReference.conversionError soapError)
    {
        return new ConversionErrorModel
        {
            CodigoError = soapError.CodigoError ?? "UNKNOWN",
            Mensaje = soapError.Mensaje ?? "Error desconocido",
            TipoError = soapError.TipoError ?? "Sistema",
            ValorProblematico = soapError.ValorProblematico,
            Unidad = soapError.Unidad,
            FechaError = DateTime.Now, // localDateTime es una clase vacía en Java WSDL
            Detalles = soapError.Detalles
        };
    }

    /// <summary>
    /// Convierte el error SOAP a modelo del cliente (sobrecarga para TemperaturaServiceReference)
    /// </summary>
    private ConversionErrorModel ConvertErrorToClientModel(TemperaturaServiceReference.conversionError soapError)
    {
        return new ConversionErrorModel
        {
            CodigoError = soapError.CodigoError ?? "UNKNOWN",
            Mensaje = soapError.Mensaje ?? "Error desconocido",
            TipoError = soapError.TipoError ?? "Sistema",
            ValorProblematico = soapError.ValorProblematico,
            Unidad = soapError.Unidad,
            FechaError = DateTime.Now, // localDateTime es una clase vacía en Java WSDL
            Detalles = soapError.Detalles
        };
    }

    /// <summary>
    /// Crea un resultado de error para resultados nulos
    /// </summary>
    private ConversionResultModel CreateNullResultError()
    {
        return new ConversionResultModel
        {
            Exitoso = false,
            Error = new ConversionErrorModel
            {
                CodigoError = "NULL_RESULT",
                Mensaje = "El servidor SOAP devolvió un resultado nulo",
                TipoError = "Sistema",
                FechaError = DateTime.Now
            }
        };
    }

    /// <summary>
    /// Crea un resultado de error para conversiones no soportadas
    /// </summary>
    private ConversionResultModel CreateUnsupportedConversionError(string unidadOrigen, string unidadDestino)
    {
        return new ConversionResultModel
        {
            Exitoso = false,
            Error = new ConversionErrorModel
            {
                CodigoError = "CONVERSION_NOT_SUPPORTED",
                Mensaje = $"No se soporta la conversión de {unidadOrigen} a {unidadDestino}",
                TipoError = "Validacion",
                FechaError = DateTime.Now,
                Detalles = "Las unidades especificadas no tienen una conversión definida"
            }
        };
    }

    /// <summary>
    /// Crea un resultado de error para excepciones
    /// </summary>
    private ConversionResultModel CreateErrorResult(string mensaje, Exception ex)
    {
        return new ConversionResultModel
        {
            Exitoso = false,
            Error = new ConversionErrorModel
            {
                CodigoError = "SOAP_ERROR",
                Mensaje = mensaje,
                TipoError = "Sistema",
                FechaError = DateTime.Now,
                Detalles = ex.Message
            }
        };
    }
}
