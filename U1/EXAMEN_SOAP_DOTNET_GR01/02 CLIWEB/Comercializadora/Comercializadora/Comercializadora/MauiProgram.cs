using Comercializadora.Core.Managers;
using Comercializadora.Core.Services.Abstractions;
using Comercializadora.Core.Services.Implementations.Dispatchers;
using Comercializadora.Core.Services.Implementations.Rest;
using Comercializadora.Core.Services.Implementations.Soap;
using Comercializadora.Services;
using Comercializadora.Shared.Services;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Configuration;
using System.Reflection;

namespace Comercializadora
{
    public static class MauiProgram
    {
        public static MauiApp CreateMauiApp()
        {
            var builder = MauiApp.CreateBuilder();
            builder
                .UseMauiApp<App>()
                .ConfigureFonts(fonts =>
                {
                    fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
                });

            var assembly = Assembly.GetExecutingAssembly();
            using var stream = assembly.GetManifestResourceStream("Comercializadora.appsettings.json");
            var config = new ConfigurationBuilder()
                        .AddJsonStream(stream)
                        .Build();
            builder.Configuration.AddConfiguration(config);

            // --- REGISTRO DE SERVICIOS CORE ---
            builder.Services.AddSingleton<ApiServiceManager>();
            
            // --- SERVICIO DE AUTENTICACIÓN ---
            builder.Services.AddSingleton<IAuthenticationService, AuthenticationService>();
            
            // Configurar HttpClient específico para MAUI con manejo de certificados
            builder.Services.AddHttpClient("ComercializadoraClient", client =>
            {
                client.Timeout = TimeSpan.FromMinutes(5);
                client.DefaultRequestHeaders.Add("User-Agent", "ComercializadoraMAUI/1.0");
                
                // Headers adicionales para evitar problemas con algunos servidores
                client.DefaultRequestHeaders.Add("Accept", "application/json, text/plain, */*");
                client.DefaultRequestHeaders.Add("Cache-Control", "no-cache");
            })
            .ConfigurePrimaryHttpMessageHandler(() =>
            {
#if ANDROID
                // Configuración específica para Android
                var handler = new HttpClientHandler();
                
                // En modo DEBUG, ignorar errores de certificado SSL
#if DEBUG
                handler.ServerCertificateCustomValidationCallback = (message, cert, chain, errors) => true;
#endif
                
                // Configuraciones adicionales para Android
                handler.UseCookies = false; // Evitar problemas con cookies
                handler.AutomaticDecompression = System.Net.DecompressionMethods.GZip | System.Net.DecompressionMethods.Deflate;
                
                return handler;
#elif IOS
                // Configuración específica para iOS
                var handler = new HttpClientHandler();
                
#if DEBUG
                handler.ServerCertificateCustomValidationCallback = (message, cert, chain, errors) => true;
#endif
                
                return handler;
#else
                // Configuración para otras plataformas (Windows, macOS)
                var handler = new HttpClientHandler();
                
#if DEBUG
                handler.ServerCertificateCustomValidationCallback = (message, cert, chain, errors) => true;
#endif
                
                return handler;
#endif
            });

            // HttpClient genérico para compatibilidad
            builder.Services.AddHttpClient();

            // --- REGISTRAR SERVICIOS DE PRODUCTOS ---
            builder.Services.AddSingleton<SoapProductService>();
            builder.Services.AddSingleton<RestProductService>();
            builder.Services.AddSingleton<IProductService, ProductServiceDispatcher>();

            // Servicios de Facturación
            builder.Services.AddSingleton<SoapFacturacionService>();
            builder.Services.AddSingleton<RestFacturacionService>();
            builder.Services.AddSingleton<IFacturacionService, FacturacionServiceDispatcher>();

            // Servicios de Crédito
            builder.Services.AddSingleton<SoapCreditoService>();
            builder.Services.AddSingleton<RestCreditoService>();
            builder.Services.AddSingleton<ICreditoService, CreditoServiceDispatcher>();

            // Add device-specific services used by the Comercializadora.Shared project
            builder.Services.AddSingleton<IFormFactor, FormFactor>();

            builder.Services.AddMauiBlazorWebView();

#if DEBUG
            builder.Services.AddBlazorWebViewDeveloperTools();
            builder.Logging.AddDebug();
            
            // Logging específico para conexiones HTTP en debug
            builder.Logging.AddFilter("System.Net.Http.HttpClient", LogLevel.Debug);
            builder.Logging.AddFilter("Comercializadora", LogLevel.Debug);
#endif

            return builder.Build();
        }
    }
}
