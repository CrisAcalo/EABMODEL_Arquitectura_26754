using System;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.models;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.services;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.constants;

namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.ws
{
    /// <summary>
    /// Servicio WCF para conversiones de temperatura
    /// Actúa como capa de presentación delegando la lógica de negocio
    /// </summary>
    public class TemperaturaService : ITemperaturaService
    {
        private readonly TemperaturaBusinessService _temperaturaBusinessService;

        /// <summary>
        /// Constructor que inicializa el servicio de negocio
        /// </summary>
        public TemperaturaService()
        {
            _temperaturaBusinessService = new TemperaturaBusinessService();
        }

        #region Conversiones Celsius <-> Fahrenheit

        /// <summary>
        /// Convierte grados Celsius a Fahrenheit
        /// </summary>
        /// <param name="celsius">Valor en grados Celsius como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult CelsiusAFahrenheit(string celsius)
        {
            try
            {
                return _temperaturaBusinessService.ConvertirCelsiusAFahrenheit(celsius);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    TemperaturaConstants.CELSIUS,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        /// <summary>
        /// Convierte grados Fahrenheit a Celsius
        /// </summary>
        /// <param name="fahrenheit">Valor en grados Fahrenheit como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult FahrenheitACelsius(string fahrenheit)
        {
            try
            {
                return _temperaturaBusinessService.ConvertirFahrenheitACelsius(fahrenheit);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    TemperaturaConstants.FAHRENHEIT,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        #endregion

        #region Conversiones Fahrenheit <-> Kelvin

        /// <summary>
        /// Convierte grados Fahrenheit a Kelvin
        /// </summary>
        /// <param name="fahrenheit">Valor en grados Fahrenheit como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult FahrenheitAKelvin(string fahrenheit)
        {
            try
            {
                return _temperaturaBusinessService.ConvertirFahrenheitAKelvin(fahrenheit);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    TemperaturaConstants.FAHRENHEIT,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        /// <summary>
        /// Convierte Kelvin a grados Fahrenheit
        /// </summary>
        /// <param name="kelvin">Valor en Kelvin como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult KelvinAFahrenheit(string kelvin)
        {
            try
            {
                return _temperaturaBusinessService.ConvertirKelvinAFahrenheit(kelvin);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    TemperaturaConstants.KELVIN,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        #endregion

        #region Conversiones Kelvin <-> Celsius

        /// <summary>
        /// Convierte Kelvin a grados Celsius
        /// </summary>
        /// <param name="kelvin">Valor en Kelvin como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult KelvinACelsius(string kelvin)
        {
            try
            {
                return _temperaturaBusinessService.ConvertirKelvinACelsius(kelvin);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    TemperaturaConstants.KELVIN,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        /// <summary>
        /// Convierte grados Celsius a Kelvin
        /// </summary>
        /// <param name="celsius">Valor en grados Celsius como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult CelsiusAKelvin(string celsius)
        {
            try
            {
                return _temperaturaBusinessService.ConvertirCelsiusAKelvin(celsius);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    TemperaturaConstants.CELSIUS,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        #endregion
    }
}
