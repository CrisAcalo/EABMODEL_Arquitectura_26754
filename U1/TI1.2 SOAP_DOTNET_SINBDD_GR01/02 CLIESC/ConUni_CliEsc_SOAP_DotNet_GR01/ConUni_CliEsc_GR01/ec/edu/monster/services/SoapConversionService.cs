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
                MasaServiceClient.EndpointConfiguration.BasicHttpBinding_IMasaService,
                _masaServiceUrl
            );

            MasaServiceReference.ConversionResult? result = null;

            // Mapear unidades a método SOAP específico
            var key = $"{request.UnidadOrigen}-{request.UnidadDestino}";
            result = key switch
            {
                "Kilogramo-Quintal" => await client.KilogramoAQuintalAsync(request.Valor),
                "Quintal-Kilogramo" => await client.QuintalAKilogramoAsync(request.Valor),
                "Kilogramo-Libra" => await client.KilogramoALibraAsync(request.Valor),
                "Libra-Kilogramo" => await client.LibraAKilogramoAsync(request.Valor),
                "Quintal-Libra" => await client.QuintalALibraAsync(request.Valor),
                "Libra-Quintal" => await client.LibraAQuintalAsync(request.Valor),
                _ => null
            };

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
                LongitudServiceClient.EndpointConfiguration.BasicHttpBinding_ILongitudService,
                _longitudServiceUrl
            );

            LongitudServiceReference.ConversionResult? result = null;

            // Mapear unidades a método SOAP específico
            var key = $"{request.UnidadOrigen}-{request.UnidadDestino}";
            result = key switch
            {
                "Milla-Metro" => await client.MillaAMetroAsync(request.Valor),
                "Metro-Milla" => await client.MetroAMillaAsync(request.Valor),
                "Milla-Pulgada" => await client.MillaAPulgadaAsync(request.Valor),
                "Pulgada-Milla" => await client.PulgadaAMillaAsync(request.Valor),
                "Metro-Pulgada" => await client.MetroAPulgadaAsync(request.Valor),
                "Pulgada-Metro" => await client.PulgadaAMetroAsync(request.Valor),
                _ => null
            };

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
                TemperaturaServiceClient.EndpointConfiguration.BasicHttpBinding_ITemperaturaService,
                _temperaturaServiceUrl
            );

            TemperaturaServiceReference.ConversionResult? result = null;

            // Mapear unidades a método SOAP específico
            var key = $"{request.UnidadOrigen}-{request.UnidadDestino}";
            result = key switch
            {
                "Celsius-Fahrenheit" => await client.CelsiusAFahrenheitAsync(request.Valor),
                "Fahrenheit-Celsius" => await client.FahrenheitACelsiusAsync(request.Valor),
                "Fahrenheit-Kelvin" => await client.FahrenheitAKelvinAsync(request.Valor),
                "Kelvin-Fahrenheit" => await client.KelvinAFahrenheitAsync(request.Valor),
                "Kelvin-Celsius" => await client.KelvinACelsiusAsync(request.Valor),
                "Celsius-Kelvin" => await client.CelsiusAKelvinAsync(request.Valor),
                _ => null
            };

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
    private ConversionResultModel ConvertMasaToClientModel(MasaServiceReference.ConversionResult soapResult)
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
                ValorOriginal = soapResult.Resultado.ValorOriginal,
                ValorConvertidoExacto = soapResult.Resultado.ValorConvertidoExacto,
                ValorConvertidoRedondeado = soapResult.Resultado.ValorConvertidoRedondeado,
                UnidadOrigen = soapResult.Resultado.UnidadOrigen ?? string.Empty,
                UnidadDestino = soapResult.Resultado.UnidadDestino ?? string.Empty,
                FactorConversion = soapResult.Resultado.FactorConversion,
                FechaConversion = soapResult.Resultado.FechaConversion
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
    private ConversionResultModel ConvertLongitudToClientModel(LongitudServiceReference.ConversionResult soapResult)
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
                ValorOriginal = soapResult.Resultado.ValorOriginal,
                ValorConvertidoExacto = soapResult.Resultado.ValorConvertidoExacto,
                ValorConvertidoRedondeado = soapResult.Resultado.ValorConvertidoRedondeado,
                UnidadOrigen = soapResult.Resultado.UnidadOrigen ?? string.Empty,
                UnidadDestino = soapResult.Resultado.UnidadDestino ?? string.Empty,
                FactorConversion = soapResult.Resultado.FactorConversion,
                FechaConversion = soapResult.Resultado.FechaConversion
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
    private ConversionResultModel ConvertTemperaturaToClientModel(TemperaturaServiceReference.ConversionResult soapResult)
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
                ValorOriginal = soapResult.Resultado.ValorOriginal,
                ValorConvertidoExacto = soapResult.Resultado.ValorConvertidoExacto,
                ValorConvertidoRedondeado = soapResult.Resultado.ValorConvertidoRedondeado,
                UnidadOrigen = soapResult.Resultado.UnidadOrigen ?? string.Empty,
                UnidadDestino = soapResult.Resultado.UnidadDestino ?? string.Empty,
                FactorConversion = soapResult.Resultado.FactorConversion,
                FechaConversion = soapResult.Resultado.FechaConversion
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
    private ConversionErrorModel ConvertErrorToClientModel(MasaServiceReference.ConversionError soapError)
    {
        return new ConversionErrorModel
        {
            CodigoError = soapError.CodigoError ?? "UNKNOWN",
            Mensaje = soapError.Mensaje ?? "Error desconocido",
            TipoError = soapError.TipoError ?? "Sistema",
            ValorProblematico = soapError.ValorProblematico,
            Unidad = soapError.Unidad,
            FechaError = soapError.FechaError,
            Detalles = soapError.Detalles
        };
    }

    /// <summary>
    /// Convierte el error SOAP a modelo del cliente (sobrecarga para LongitudServiceReference)
    /// </summary>
    private ConversionErrorModel ConvertErrorToClientModel(LongitudServiceReference.ConversionError soapError)
    {
        return new ConversionErrorModel
        {
            CodigoError = soapError.CodigoError ?? "UNKNOWN",
            Mensaje = soapError.Mensaje ?? "Error desconocido",
            TipoError = soapError.TipoError ?? "Sistema",
            ValorProblematico = soapError.ValorProblematico,
            Unidad = soapError.Unidad,
            FechaError = soapError.FechaError,
            Detalles = soapError.Detalles
        };
    }

    /// <summary>
    /// Convierte el error SOAP a modelo del cliente (sobrecarga para TemperaturaServiceReference)
    /// </summary>
    private ConversionErrorModel ConvertErrorToClientModel(TemperaturaServiceReference.ConversionError soapError)
    {
        return new ConversionErrorModel
        {
            CodigoError = soapError.CodigoError ?? "UNKNOWN",
            Mensaje = soapError.Mensaje ?? "Error desconocido",
            TipoError = soapError.TipoError ?? "Sistema",
            ValorProblematico = soapError.ValorProblematico,
            Unidad = soapError.Unidad,
            FechaError = soapError.FechaError,
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
