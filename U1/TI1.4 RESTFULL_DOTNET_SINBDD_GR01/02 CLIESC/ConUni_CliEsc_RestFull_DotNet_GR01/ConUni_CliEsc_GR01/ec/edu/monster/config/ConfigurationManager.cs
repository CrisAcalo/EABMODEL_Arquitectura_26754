using System.IO;
using Microsoft.Extensions.Configuration;
using ConUni_CliEsc_GR01.ec.edu.monster.services;

namespace ConUni_CliEsc_GR01.ec.edu.monster.config;

/// <summary>
/// Administrador de configuración de la aplicación
/// Lee appsettings.json y crea el servicio apropiado
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
    /// Obtiene el servicio de conversión configurado (.NET REST)
    /// </summary>
    /// <returns>Instancia del servicio de conversión</returns>
    public static IConversionService GetConversionService()
    {
        if (_conversionService != null)
            return _conversionService;

        if (_configuration == null)
            Initialize();

        // Leer configuración simplificada para .NET REST
        var baseUrl = _configuration?["ApiConfiguration:BaseUrl"];
        var longitudPath = _configuration?["ApiConfiguration:LongitudPath"];
        var masaPath = _configuration?["ApiConfiguration:MasaPath"];
        var temperaturaPath = _configuration?["ApiConfiguration:TemperaturaPath"];

        if (string.IsNullOrEmpty(baseUrl))
            throw new InvalidOperationException("No se encontró la configuración de BaseUrl en appsettings.json");

        _conversionService = new RestConversionService(
            baseUrl,
            longitudPath ?? "/longitud/convertir",
            masaPath ?? "/masa/convertir",
            temperaturaPath ?? "/temperatura/convertir"
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
