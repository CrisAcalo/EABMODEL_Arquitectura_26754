using EurekaBank.Core.Services.Abstractions;
using Microsoft.Extensions.Configuration;
using System.Globalization;

namespace EurekaBank.Core.Services.Implementations
{
    public class GoogleMapsService : IGoogleMapsService
    {
        private readonly string? _apiKey;

        public GoogleMapsService(IConfiguration configuration)
        {
            _apiKey = configuration["GoogleMaps:ApiKey"];
        }

        public bool TieneApiKeyConfigurada()
        {
            return !string.IsNullOrEmpty(_apiKey) && _apiKey != "TU_API_KEY_AQUI";
        }

        public string ObtenerMapaEstatico(decimal latitud, decimal longitud, int zoom = 15, string tamaño = "400x300")
        {
            if (!TieneApiKeyConfigurada())
            {
                return string.Empty;
            }

            var lat = latitud.ToString("F6", CultureInfo.InvariantCulture);
            var lng = longitud.ToString("F6", CultureInfo.InvariantCulture);

            return $"https://maps.googleapis.com/maps/api/staticmap?" +
                   $"center={lat},{lng}" +
                   $"&zoom={zoom}" +
                   $"&size={tamaño}" +
                   $"&markers=color:red%7Clabel:S%7C{lat},{lng}" +
                   $"&key={_apiKey}";
        }

        public string ObtenerUrlGoogleMaps(decimal latitud, decimal longitud)
        {
            var lat = latitud.ToString("F6", CultureInfo.InvariantCulture);
            var lng = longitud.ToString("F6", CultureInfo.InvariantCulture);

            return $"https://www.google.com/maps?q={lat},{lng}";
        }

        public string ObtenerUrlDirecciones(decimal latitud, decimal longitud)
        {
            var lat = latitud.ToString("F6", CultureInfo.InvariantCulture);
            var lng = longitud.ToString("F6", CultureInfo.InvariantCulture);

            return $"https://www.google.com/maps/dir/?api=1&destination={lat},{lng}";
        }

        public string ObtenerStreetView(decimal latitud, decimal longitud, string tamaño = "400x300")
        {
            if (!TieneApiKeyConfigurada())
            {
                return string.Empty;
            }

            var lat = latitud.ToString("F6", CultureInfo.InvariantCulture);
            var lng = longitud.ToString("F6", CultureInfo.InvariantCulture);

            return $"https://maps.googleapis.com/maps/api/streetview?" +
                   $"size={tamaño}" +
                   $"location={lat},{lng}" +
                   $"heading=151.78" +
                   $"&pitch=-0.76" +
                   $"&key={_apiKey}";
        }
    }
}