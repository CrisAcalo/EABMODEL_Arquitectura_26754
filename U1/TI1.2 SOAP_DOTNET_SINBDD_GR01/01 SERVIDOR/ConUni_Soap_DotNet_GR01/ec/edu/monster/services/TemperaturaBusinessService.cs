using ConUni_Soap_DotNet_GR01.ec.edu.monster.constants;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.models;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.validators;

namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.services
{
    /// <summary>
    /// Servicio de negocio para conversiones de temperatura
    /// </summary>
    public class TemperaturaBusinessService
    {
        /// <summary>
        /// Convierte Celsius a Fahrenheit y retorna resultado con manejo de errores
        /// </summary>
        public ConversionResult ConvertirCelsiusAFahrenheit(string celsiusString)
        {
            // Validar y convertir string a double
            var error = TemperaturaValidator.ValidarStringTemperaturaCelsius(celsiusString, out double celsius);
            if (error != null)
                return ConversionResult.Fallo(error);

            try
            {
                // °F = (°C × 9/5) + 32
                double resultadoExacto = (celsius * TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR) + TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET;
                
                var conversion = new UnidadConversion(
                    celsius, 
                    resultadoExacto, 
                    TemperaturaConstants.CELSIUS, 
                    TemperaturaConstants.FAHRENHEIT, 
                    TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR
                );

                return ConversionResult.Exito(conversion);
            }
            catch (System.Exception ex)
            {
                var errorInterno = new ConversionError(
                    ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                    "Error en la conversión de Celsius a Fahrenheit",
                    ErrorConstants.TIPO_CONVERSION,
                    celsius,
                    TemperaturaConstants.CELSIUS,
                    ex.Message
                );
                return ConversionResult.Fallo(errorInterno);
            }
        }

        /// <summary>
        /// Convierte Fahrenheit a Celsius y retorna resultado con manejo de errores
        /// </summary>
        public ConversionResult ConvertirFahrenheitACelsius(string fahrenheitString)
        {
            // Validar y convertir string a double
            var error = TemperaturaValidator.ValidarStringTemperaturaFahrenheit(fahrenheitString, out double fahrenheit);
            if (error != null)
                return ConversionResult.Fallo(error);

            try
            {
                // °C = (°F - 32) × 5/9
                double resultadoExacto = (fahrenheit - TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET) * TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR;
                
                var conversion = new UnidadConversion(
                    fahrenheit, 
                    resultadoExacto, 
                    TemperaturaConstants.FAHRENHEIT, 
                    TemperaturaConstants.CELSIUS, 
                    TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR
                );

                return ConversionResult.Exito(conversion);
            }
            catch (System.Exception ex)
            {
                var errorInterno = new ConversionError(
                    ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                    "Error en la conversión de Fahrenheit a Celsius",
                    ErrorConstants.TIPO_CONVERSION,
                    fahrenheit,
                    TemperaturaConstants.FAHRENHEIT,
                    ex.Message
                );
                return ConversionResult.Fallo(errorInterno);
            }
        }

        /// <summary>
        /// Convierte Fahrenheit a Kelvin y retorna resultado con manejo de errores
        /// </summary>
        public ConversionResult ConvertirFahrenheitAKelvin(string fahrenheitString)
        {
            // Validar y convertir string a double
            var error = TemperaturaValidator.ValidarStringTemperaturaFahrenheit(fahrenheitString, out double fahrenheit);
            if (error != null)
                return ConversionResult.Fallo(error);

            try
            {
                // K = (°F - 32) × 5/9 + 273.15
                double resultadoExacto = (fahrenheit - TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET) * TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR + TemperaturaConstants.CELSIUS_KELVIN_OFFSET;
                
                var conversion = new UnidadConversion(
                    fahrenheit, 
                    resultadoExacto, 
                    TemperaturaConstants.FAHRENHEIT, 
                    TemperaturaConstants.KELVIN, 
                    TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR
                );

                return ConversionResult.Exito(conversion);
            }
            catch (System.Exception ex)
            {
                var errorInterno = new ConversionError(
                    ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                    "Error en la conversión de Fahrenheit a Kelvin",
                    ErrorConstants.TIPO_CONVERSION,
                    fahrenheit,
                    TemperaturaConstants.FAHRENHEIT,
                    ex.Message
                );
                return ConversionResult.Fallo(errorInterno);
            }
        }

        /// <summary>
        /// Convierte Kelvin a Fahrenheit y retorna resultado con manejo de errores
        /// </summary>
        public ConversionResult ConvertirKelvinAFahrenheit(string kelvinString)
        {
            // Validar y convertir string a double
            var error = TemperaturaValidator.ValidarStringTemperaturaKelvin(kelvinString, out double kelvin);
            if (error != null)
                return ConversionResult.Fallo(error);

            try
            {
                // °F = (K - 273.15) × 9/5 + 32
                double resultadoExacto = (kelvin - TemperaturaConstants.CELSIUS_KELVIN_OFFSET) * TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR + TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET;
                
                var conversion = new UnidadConversion(
                    kelvin, 
                    resultadoExacto, 
                    TemperaturaConstants.KELVIN, 
                    TemperaturaConstants.FAHRENHEIT, 
                    TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR
                );

                return ConversionResult.Exito(conversion);
            }
            catch (System.Exception ex)
            {
                var errorInterno = new ConversionError(
                    ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                    "Error en la conversión de Kelvin a Fahrenheit",
                    ErrorConstants.TIPO_CONVERSION,
                    kelvin,
                    TemperaturaConstants.KELVIN,
                    ex.Message
                );
                return ConversionResult.Fallo(errorInterno);
            }
        }

        /// <summary>
        /// Convierte Kelvin a Celsius y retorna resultado con manejo de errores
        /// </summary>
        public ConversionResult ConvertirKelvinACelsius(string kelvinString)
        {
            // Validar y convertir string a double
            var error = TemperaturaValidator.ValidarStringTemperaturaKelvin(kelvinString, out double kelvin);
            if (error != null)
                return ConversionResult.Fallo(error);

            try
            {
                // °C = K - 273.15
                double resultadoExacto = kelvin - TemperaturaConstants.CELSIUS_KELVIN_OFFSET;
                
                var conversion = new UnidadConversion(
                    kelvin, 
                    resultadoExacto, 
                    TemperaturaConstants.KELVIN, 
                    TemperaturaConstants.CELSIUS, 
                    1.0
                );

                return ConversionResult.Exito(conversion);
            }
            catch (System.Exception ex)
            {
                var errorInterno = new ConversionError(
                    ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                    "Error en la conversión de Kelvin a Celsius",
                    ErrorConstants.TIPO_CONVERSION,
                    kelvin,
                    TemperaturaConstants.KELVIN,
                    ex.Message
                );
                return ConversionResult.Fallo(errorInterno);
            }
        }

        /// <summary>
        /// Convierte Celsius a Kelvin y retorna resultado con manejo de errores
        /// </summary>
        public ConversionResult ConvertirCelsiusAKelvin(string celsiusString)
        {
            // Validar y convertir string a double
            var error = TemperaturaValidator.ValidarStringTemperaturaCelsius(celsiusString, out double celsius);
            if (error != null)
                return ConversionResult.Fallo(error);

            try
            {
                // K = °C + 273.15
                double resultadoExacto = celsius + TemperaturaConstants.CELSIUS_KELVIN_OFFSET;
                
                var conversion = new UnidadConversion(
                    celsius, 
                    resultadoExacto, 
                    TemperaturaConstants.CELSIUS, 
                    TemperaturaConstants.KELVIN, 
                    1.0
                );

                return ConversionResult.Exito(conversion);
            }
            catch (System.Exception ex)
            {
                var errorInterno = new ConversionError(
                    ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                    "Error en la conversión de Celsius a Kelvin",
                    ErrorConstants.TIPO_CONVERSION,
                    celsius,
                    TemperaturaConstants.CELSIUS,
                    ex.Message
                );
                return ConversionResult.Fallo(errorInterno);
            }
        }
    }
}