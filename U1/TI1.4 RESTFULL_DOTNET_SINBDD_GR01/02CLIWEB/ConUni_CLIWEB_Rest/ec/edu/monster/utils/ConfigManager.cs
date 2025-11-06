using System;
using System.Configuration;

namespace ConUni_CLIWEB_Rest.ec.edu.monster.utils
{
    /// <summary>
    /// Gestor de configuración para REST API
    /// </summary>
    public static class ConfigManager
    {
        /// <summary>
        /// Obtiene la URL base del servidor REST desde Web.config
        /// </summary>
        public static string GetBaseURL()
        {
            string baseUrl = ConfigurationManager.AppSettings["RestAPI_BaseURL"];
            if (string.IsNullOrEmpty(baseUrl))
            {
                return "http://localhost:5150/api"; // Valor por defecto
            }
            return baseUrl;
        }

        /// <summary>
        /// Obtiene el timeout en segundos
        /// </summary>
        public static int GetTimeout()
        {
            string timeoutStr = ConfigurationManager.AppSettings["RestAPI_Timeout"];
            if (int.TryParse(timeoutStr, out int timeout))
            {
                return timeout;
            }
            return 30; // Valor por defecto: 30 segundos
        }

        /// <summary>
        /// Obtiene la URL completa de un endpoint
        /// </summary>
        public static string GetEndpointURL(string endpoint)
        {
            return $"{GetBaseURL()}/{endpoint}";
        }
    }
}