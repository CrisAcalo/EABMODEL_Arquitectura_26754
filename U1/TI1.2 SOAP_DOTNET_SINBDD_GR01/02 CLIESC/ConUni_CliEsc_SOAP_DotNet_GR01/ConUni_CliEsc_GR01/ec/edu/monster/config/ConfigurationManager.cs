using System.IO;
using Microsoft.Extensions.Configuration;
using ConUni_CliEsc_GR01.ec.edu.monster.services;

namespace ConUni_CliEsc_GR01.ec.edu.monster.config;

/// <summary>
/// Administrador de configuración de la aplicación
/// Lee appsettings.json y crea el servicio SOAP apropiado
/// </summary>
public class ConfigurationManager
{
    private static IConfiguration? _configuration;
    private static IConversionService? _conversionService;

    /// <summary>
    /// Inicializa la configuración desde appsettings.json
    /// </summary>
    public static void Initialize()
    {
        var builder = new ConfigurationBuilder()
            .SetBasePath(Directory.GetCurrentDirectory())
            .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true);

        _configuration = builder.Build();
    }

    /// <summary>
    /// Obtiene el servicio de conversión configurado (SOAP)
    /// </summary>
    /// <returns>Instancia del servicio de conversión SOAP</returns>
    public static IConversionService GetConversionService()
    {
        if (_conversionService != null)
            return _conversionService;

        if (_configuration == null)
            Initialize();

        // Leer configuración SOAP
        var baseUrl = _configuration?["SoapConfiguration:BaseUrl"];
        var masaPath = _configuration?["SoapConfiguration:MasaServicePath"];
        var longitudPath = _configuration?["SoapConfiguration:LongitudServicePath"];
        var temperaturaPath = _configuration?["SoapConfiguration:TemperaturaServicePath"];

        if (string.IsNullOrEmpty(baseUrl))
            throw new InvalidOperationException("No se encontró la configuración de BaseUrl en appsettings.json");

        if (string.IsNullOrEmpty(masaPath))
            throw new InvalidOperationException("No se encontró la configuración de MasaServicePath en appsettings.json");

        if (string.IsNullOrEmpty(longitudPath))
            throw new InvalidOperationException("No se encontró la configuración de LongitudServicePath en appsettings.json");

        if (string.IsNullOrEmpty(temperaturaPath))
            throw new InvalidOperationException("No se encontró la configuración de TemperaturaServicePath en appsettings.json");

        // Construir URLs completas para cada servicio
        var masaServiceUrl = $"{baseUrl}{masaPath}";
        var longitudServiceUrl = $"{baseUrl}{longitudPath}";
        var temperaturaServiceUrl = $"{baseUrl}{temperaturaPath}";

        _conversionService = new SoapConversionService(
            masaServiceUrl,
            longitudServiceUrl,
            temperaturaServiceUrl
        );

        return _conversionService;
    }

    /// <summary>
    /// Obtiene un valor de configuración
    /// </summary>
    public static string? GetValue(string key)
    {
        if (_configuration == null)
            Initialize();

        return _configuration?[key];
    }

    /// <summary>
    /// Reinicia el servicio (útil si se cambia la configuración)
    /// </summary>
    public static void ResetService()
    {
        _conversionService = null;
    }
}
