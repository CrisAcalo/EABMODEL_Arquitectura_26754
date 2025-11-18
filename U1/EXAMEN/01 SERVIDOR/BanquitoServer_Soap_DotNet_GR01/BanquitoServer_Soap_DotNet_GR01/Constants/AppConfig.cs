using System;
using System.Configuration;
using System.Globalization;

namespace BanquitoServer_Soap_DotNet_GR01.Constants
{
    /// <summary>
    /// Clase para centralizar la lectura de configuración desde Web.config
    /// </summary>
    public static class AppConfig
    {
        // Configuración de crédito
        public static decimal CreditoTasaAnual => GetDecimalConfig("CreditoTasaAnual", 0.16m);
        public static int CreditoPlazoMinimo => GetIntConfig("CreditoPlazoMinimo", 3);
        public static int CreditoPlazoMaximo => GetIntConfig("CreditoPlazoMaximo", 24);
        public static decimal CreditoPorcentajeCapacidad => GetDecimalConfig("CreditoPorcentajeCapacidad", 0.60m);
        public static int CreditoMultiplicador => GetIntConfig("CreditoMultiplicador", 9);

        /// <summary>
        /// Obtener valor decimal del Web.config con CultureInfo.InvariantCulture
        /// </summary>
        private static decimal GetDecimalConfig(string key, decimal defaultValue)
        {
            try
            {
                string value = ConfigurationManager.AppSettings[key];
                if (string.IsNullOrWhiteSpace(value))
                    return defaultValue;

                return decimal.Parse(value, CultureInfo.InvariantCulture);
            }
            catch
            {
                return defaultValue;
            }
        }

        /// <summary>
        /// Obtener valor entero del Web.config
        /// </summary>
        private static int GetIntConfig(string key, int defaultValue)
        {
            try
            {
                string value = ConfigurationManager.AppSettings[key];
                if (string.IsNullOrWhiteSpace(value))
                    return defaultValue;

                return int.Parse(value);
            }
            catch
            {
                return defaultValue;
            }
        }
    }
}
