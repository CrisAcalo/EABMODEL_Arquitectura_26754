using System;
using System.IO;
using System.Text.Json;

namespace ConUni_Client_DotNet.Config
{
    /// <summary>
    /// Configuración del servidor REST
    /// </summary>
    public class ServerConfiguration
    {
        public string Ip { get; set; } = "localhost";
        public string Puerto { get; set; } = "5150";
        public bool UseHttps { get; set; } = false;
    }

    /// <summary>
    /// Configuración de autenticación
    /// </summary>
    public class Authentication
    {
        public string Usuario { get; set; } = "Monster";
        public string Contrasena { get; set; } = "Monster9";
        public int MaxIntentos { get; set; } = 3;
    }

    /// <summary>
    /// Configuración completa de la aplicación
    /// </summary>
    public class AppConfiguration
    {
        public ServerConfiguration ServerConfiguration { get; set; } = new ServerConfiguration();
        public Authentication Authentication { get; set; } = new Authentication();

        /// <summary>
        /// Obtiene la URL base del servidor
        /// </summary>
        public string GetBaseUrl()
        {
            string protocol = ServerConfiguration.UseHttps ? "https" : "http";
            return $"{protocol}://{ServerConfiguration.Ip}:{ServerConfiguration.Puerto}/api";
        }

        /// <summary>
        /// Carga la configuración desde appsettings.json
        /// </summary>
        public static AppConfiguration LoadConfiguration()
        {
            string configFile = "appsettings.json";

            try
            {
                if (!File.Exists(configFile))
                {
                    Console.WriteLine("⚠️  Archivo appsettings.json no encontrado. Usando valores por defecto.");
                    return new AppConfiguration();
                }

                string jsonContent = File.ReadAllText(configFile);
                var config = JsonSerializer.Deserialize<AppConfiguration>(jsonContent, new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                });

                if (config != null)
                {
                    Console.WriteLine("✓ Configuración cargada desde appsettings.json");
                    return config;
                }

                Console.WriteLine("⚠️  Error al deserializar configuración. Usando valores por defecto.");
                return new AppConfiguration();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"⚠️  Error al cargar configuración: {ex.Message}");
                Console.WriteLine("   Usando valores por defecto.");
                return new AppConfiguration();
            }
        }
    }
}