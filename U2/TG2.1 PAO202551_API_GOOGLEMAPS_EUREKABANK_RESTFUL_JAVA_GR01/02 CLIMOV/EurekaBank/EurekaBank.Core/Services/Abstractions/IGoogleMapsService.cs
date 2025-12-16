namespace EurekaBank.Core.Services.Abstractions
{
    public interface IGoogleMapsService
    {
        /// <summary>
        /// Obtiene la URL del mapa estático de Google Maps
        /// </summary>
        /// <param name="latitud">Latitud de la ubicación</param>
        /// <param name="longitud">Longitud de la ubicación</param>
        /// <param name="zoom">Nivel de zoom (1-20)</param>
        /// <param name="tamaño">Tamaño del mapa (ej: "400x300")</param>
        /// <returns>URL del mapa estático</returns>
        string ObtenerMapaEstatico(decimal latitud, decimal longitud, int zoom = 15, string tamaño = "400x300");

        /// <summary>
        /// Obtiene la URL para abrir Google Maps en el navegador
        /// </summary>
        /// <param name="latitud">Latitud de la ubicación</param>
        /// <param name="longitud">Longitud de la ubicación</param>
        /// <returns>URL de Google Maps</returns>
        string ObtenerUrlGoogleMaps(decimal latitud, decimal longitud);

        /// <summary>
        /// Obtiene la URL para obtener direcciones a una ubicación
        /// </summary>
        /// <param name="latitud">Latitud de destino</param>
        /// <param name="longitud">Longitud de destino</param>
        /// <returns>URL de Google Maps con direcciones</returns>
        string ObtenerUrlDirecciones(decimal latitud, decimal longitud);

        /// <summary>
        /// Obtiene la URL para la vista Street View
        /// </summary>
        /// <param name="latitud">Latitud de la ubicación</param>
        /// <param name="longitud">Longitud de la ubicación</param>
        /// <param name="tamaño">Tamaño de la imagen (ej: "400x300")</param>
        /// <returns>URL de Street View</returns>
        string ObtenerStreetView(decimal latitud, decimal longitud, string tamaño = "400x300");

        /// <summary>
        /// Verifica si la API Key está configurada
        /// </summary>
        /// <returns>True si está configurada</returns>
        bool TieneApiKeyConfigurada();

        /// <summary>
        /// Obtiene la API Key configurada
        /// </summary>
        /// <returns>API Key</returns>
        string ObtenerApiKey();
    }
}