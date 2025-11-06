using Microsoft.Extensions.Logging;
using UniversalConverter.Client.Services;
using UniversalConverter.Client.ViewModels;
using UniversalConverter.Client.Views;
using System.Reflection; // <-- Añadir este using
using Microsoft.Extensions.Configuration; // <-- Y este

namespace UniversalConverter.Client
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
                    fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
                });



#if DEBUG
    		builder.Logging.AddDebug();
#endif

            // ===== INICIO DE LA CONFIGURACIÓN =====

            // 1. Definimos el nombre del archivo de configuración
            var appSettingsFileName = "appsettings.json";

            // 2. Obtenemos el ensamblado actual (nuestra app)
            var assembly = Assembly.GetExecutingAssembly();

            // 3. Construimos el nombre completo del recurso incrustado
            // Es el "NamespacePorDefecto.NombreDelArchivo"
            var resourceName = $"{assembly.GetName().Name}.{appSettingsFileName}";

            // 4. Usamos un Stream para leer el archivo incrustado
            using var stream = assembly.GetManifestResourceStream(resourceName);

            // 5. Creamos un IConfiguration que lee los datos del stream
            var config = new ConfigurationBuilder()
                        .AddJsonStream(stream)
                        .Build();

            // 6. Añadimos la configuración al contenedor de dependencias de la app
            builder.Configuration.AddConfiguration(config);

            // ===== FIN DE LA CONFIGURACIÓN =====

            // Servicios
            builder.Services.AddSingleton<RestConversionService>();
            builder.Services.AddSingleton<SoapConversionService>();
            
            // ViewModels
            builder.Services.AddSingleton<MainViewModel>(); 
            builder.Services.AddSingleton<LoginViewModel>();
            
            // Pages
            builder.Services.AddSingleton<MainPage>();
            builder.Services.AddSingleton<LoginPage>();

            return builder.Build();
        }
    }
}
