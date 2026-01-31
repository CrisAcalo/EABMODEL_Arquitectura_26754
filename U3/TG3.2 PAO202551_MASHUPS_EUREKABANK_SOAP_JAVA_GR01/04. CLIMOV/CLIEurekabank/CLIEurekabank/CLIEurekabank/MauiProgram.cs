using CLIEurekabank.Services;
using CLIEurekabank.Shared.Services;
using Microsoft.Extensions.Logging;

namespace CLIEurekabank
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

            // Configuration
            builder.Services.AddSingleton<CLIEurekabank.Shared.Config.AppConfig>();

            // Servicios de Aplicación (mismos que en Web)
            builder.Services.AddSingleton<IFormFactor, FormFactor>();

            // EurekaBank Services
            builder.Services.AddScoped<CLIEurekabank.Shared.Services.AppState>();
            builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.AuthService>();
            builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.BloqueoWebSocketService>();
            builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.CuentaService>();
            builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.TransaccionService>();
            builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.ClienteService>();
            builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.ReporteService>();
            builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.MonedaService>();
            builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.SucursalService>();
            builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.VentanillaService>();
            builder.Services.AddScoped<CLIEurekabank.Shared.Services.BloqueoService>();

            builder.Services.AddMauiBlazorWebView();

#if DEBUG
            builder.Services.AddBlazorWebViewDeveloperTools();
            builder.Logging.AddDebug();
#endif

            return builder.Build();
        }
    }
}
