using System;
using System.Configuration;

namespace ClienteWebConversion.Helpers
{
    /// <summary>
    /// Helper para gestionar la configuración del servidor
    /// </summary>
    public static class ConfigHelper
    {
        private static string servidorURL;

        /// <summary>
        /// Inicializa la configuración
        /// </summary>
        static ConfigHelper()
        {
            CargarConfiguracion();
        }

        /// <summary>
        /// Carga la configuración desde Web.config
        /// </summary>
        private static void CargarConfiguracion()
        {
            try
            {
                string ip = ConfigurationManager.AppSettings["servidor.ip"] ?? "192.168.0.10";
                string puerto = ConfigurationManager.AppSettings["servidor.puerto"] ?? "8082";
                string ruta = ConfigurationManager.AppSettings["servidor.ruta"] ?? "ec/edu/monster/ws";

                servidorURL = $"http://{ip}:{puerto}/{ruta}";

                System.Diagnostics.Debug.WriteLine($"✓ Configuración cargada - Servidor: {servidorURL}");
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"⚠ Error al cargar configuración: {ex.Message}");
                servidorURL = "http://192.168.0.10:8082/ec/edu/monster/ws";
            }
        }

        /// <summary>
        /// Obtiene la URL base del servidor
        /// </summary>
        public static string ServidorURL
        {
            get { return servidorURL; }
        }

        /// <summary>
        /// Obtiene el valor de una propiedad específica
        /// </summary>
        public static string GetProperty(string key)
        {
            return ConfigurationManager.AppSettings[key];
        }

        /// <summary>
        /// Obtiene la URL completa de un servicio
        /// </summary>
        public static string GetServiceURL(string serviceName)
        {
            string servicio = ConfigurationManager.AppSettings[$"servicio.{serviceName}"];
            return $"{servidorURL}/{servicio}";
        }

        /// <summary>
        /// Obtiene la URL del WSDL de un servicio
        /// </summary>
        public static string GetWSDLURL(string serviceName)
        {
            return GetServiceURL(serviceName);
        }
    }
}