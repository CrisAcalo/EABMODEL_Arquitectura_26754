using Microsoft.Extensions.Logging;
using UniversalConverter.Client.Services;
using UniversalConverter.Client.ViewModels;

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

            builder.Services.AddSingleton<RestConversionService>();
            builder.Services.AddSingleton<SoapConversionService>();
            builder.Services.AddSingleton<MainViewModel>(); 
            builder.Services.AddSingleton<MainPage>();      
            return builder.Build();
        }
    }
}
