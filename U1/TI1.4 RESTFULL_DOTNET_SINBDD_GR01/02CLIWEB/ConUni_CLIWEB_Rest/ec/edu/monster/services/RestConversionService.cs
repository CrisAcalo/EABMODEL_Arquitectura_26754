using System;
using System.IO;
using System.Net;
using System.Text;
using ConUni_CLIWEB_Rest.ec.edu.monster.models;
using ConUni_CLIWEB_Rest.ec.edu.monster.utils;
using Newtonsoft.Json;

namespace ConUni_CLIWEB_Rest.ec.edu.monster.services
{
    /// <summary>
    /// Servicio para consumir la API REST de conversiones
    /// </summary>
    public class RestConversionService
    {
        private readonly string _baseUrl;
        private readonly int _timeout;

        public RestConversionService()
        {
            _baseUrl = ConfigManager.GetBaseURL();
            _timeout = ConfigManager.GetTimeout();
        }

        /// <summary>
        /// Convierte unidades de longitud
        /// </summary>
        public ConversionResult ConvertirLongitud(string tipoConversion, string valor)
        {
            var (unidadOrigen, unidadDestino) = MapearConversionLongitud(tipoConversion);
            return RealizarConversion("Longitud", valor, unidadOrigen, unidadDestino);
        }

        /// <summary>
        /// Convierte unidades de masa
        /// </summary>
        public ConversionResult ConvertirMasa(string tipoConversion, string valor)
        {
            var (unidadOrigen, unidadDestino) = MapearConversionMasa(tipoConversion);
            return RealizarConversion("Masa", valor, unidadOrigen, unidadDestino);
        }

        /// <summary>
        /// Convierte unidades de temperatura
        /// </summary>
        public ConversionResult ConvertirTemperatura(string tipoConversion, string valor)
        {
            var (unidadOrigen, unidadDestino) = MapearConversionTemperatura(tipoConversion);
            return RealizarConversion("Temperatura", valor, unidadOrigen, unidadDestino);
        }

        /// <summary>
        /// Realiza la conversión llamando al API REST
        /// </summary>
        private ConversionResult RealizarConversion(string endpoint, string valor, string unidadOrigen, string unidadDestino)
        {
            try
            {
                var request = new ConversionRequest
                {
                    Valor = valor,
                    UnidadOrigen = unidadOrigen,
                    UnidadDestino = unidadDestino
                };

                string jsonRequest = JsonConvert.SerializeObject(request);
                string url = $"{_baseUrl}/{endpoint}/convertir";

                string jsonResponse = HacerRequestPOST(url, jsonRequest);

                var resultado = JsonConvert.DeserializeObject<ConversionResult>(jsonResponse);
                return resultado ?? CrearResultadoError("Error al deserializar la respuesta");
            }
            catch (WebException ex)
            {
                return CrearResultadoError($"Error de conexión: {ex.Message}. Verifica que el servidor REST esté corriendo en {_baseUrl}");
            }
            catch (Exception ex)
            {
                return CrearResultadoError($"Error inesperado: {ex.Message}");
            }
        }

        /// <summary>
        /// Hace un request HTTP POST al servidor REST
        /// </summary>
        private string HacerRequestPOST(string url, string jsonBody)
        {
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
            request.Method = "POST";
            request.ContentType = "application/json; charset=UTF-8";
            request.Accept = "application/json";
            request.Timeout = _timeout * 1000;

            // Enviar body
            byte[] bodyBytes = Encoding.UTF8.GetBytes(jsonBody);
            request.ContentLength = bodyBytes.Length;

            using (Stream requestStream = request.GetRequestStream())
            {
                requestStream.Write(bodyBytes, 0, bodyBytes.Length);
            }

            // Leer respuesta
            using (HttpWebResponse response = (HttpWebResponse)request.GetResponse())
            {
                using (StreamReader reader = new StreamReader(response.GetResponseStream(), Encoding.UTF8))
                {
                    return reader.ReadToEnd();
                }
            }
        }

        /// <summary>
        /// Mapea el tipo de conversión de longitud a unidades
        /// </summary>
        private (string origen, string destino) MapearConversionLongitud(string tipoConversion)
        {
            switch (tipoConversion)
            {
                case "millaMetro": return ("Milla", "Metro");
                case "metroMilla": return ("Metro", "Milla");
                case "millaPulgada": return ("Milla", "Pulgada");
                case "pulgadaMilla": return ("Pulgada", "Milla");
                case "metroPulgada": return ("Metro", "Pulgada");
                case "pulgadaMetro": return ("Pulgada", "Metro");
                default: return ("", "");
            }
        }

        /// <summary>
        /// Mapea el tipo de conversión de masa a unidades
        /// </summary>
        private (string origen, string destino) MapearConversionMasa(string tipoConversion)
        {
            switch (tipoConversion)
            {
                case "kilogramoQuintal": return ("Kilogramo", "Quintal");
                case "quintalKilogramo": return ("Quintal", "Kilogramo");
                case "kilogramoLibra": return ("Kilogramo", "Libra");
                case "libraKilogramo": return ("Libra", "Kilogramo");
                case "quintalLibra": return ("Quintal", "Libra");
                case "libraQuintal": return ("Libra", "Quintal");
                default: return ("", "");
            }
        }

        /// <summary>
        /// Mapea el tipo de conversión de temperatura a unidades
        /// </summary>
        private (string origen, string destino) MapearConversionTemperatura(string tipoConversion)
        {
            switch (tipoConversion)
            {
                case "celsiusFahrenheit": return ("Celsius", "Fahrenheit");
                case "celsiusKelvin": return ("Celsius", "Kelvin");
                case "fahrenheitCelsius": return ("Fahrenheit", "Celsius");
                case "fahrenheitKelvin": return ("Fahrenheit", "Kelvin");
                case "kelvinCelsius": return ("Kelvin", "Celsius");
                case "kelvinFahrenheit": return ("Kelvin", "Fahrenheit");
                default: return ("", "");
            }
        }

        /// <summary>
        /// Crea un resultado de error
        /// </summary>
        private ConversionResult CrearResultadoError(string mensaje)
        {
            return new ConversionResult
            {
                Exitoso = false,
                Error = new ErrorConversion
                {
                    Mensaje = mensaje,
                    TipoError = "Sistema",
                    FechaError = DateTime.Now
                }
            };
        }
    }
}