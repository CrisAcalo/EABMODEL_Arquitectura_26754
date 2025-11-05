using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.constants;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.models;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.validators;

namespace ConUni_Restfull_Dotnet_GR01.ec.edu.monster.services
{
    /// <summary>
    /// Servicio para realizar conversiones de unidades de temperatura
    /// Soporta: Celsius, Fahrenheit, Kelvin
    /// </summary>
    public class TemperaturaService
    {
        #region Métodos con validación de string

        /// <summary>
        /// Convierte de Celsius a Fahrenheit (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirCelsiusAFahrenheit(string celsiusString)
        {
            var error = TemperaturaValidator.ValidarStringTemperaturaCelsius(celsiusString, out double celsius);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirCelsiusAFahrenheit(celsius);
        }

        /// <summary>
        /// Convierte de Fahrenheit a Celsius (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirFahrenheitACelsius(string fahrenheitString)
        {
            var error = TemperaturaValidator.ValidarStringTemperaturaFahrenheit(fahrenheitString, out double fahrenheit);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirFahrenheitACelsius(fahrenheit);
        }

        /// <summary>
        /// Convierte de Celsius a Kelvin (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirCelsiusAKelvin(string celsiusString)
        {
            var error = TemperaturaValidator.ValidarStringTemperaturaCelsius(celsiusString, out double celsius);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirCelsiusAKelvin(celsius);
        }

        /// <summary>
        /// Convierte de Kelvin a Celsius (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirKelvinACelsius(string kelvinString)
        {
            var error = TemperaturaValidator.ValidarStringTemperaturaKelvin(kelvinString, out double kelvin);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirKelvinACelsius(kelvin);
        }

        /// <summary>
        /// Convierte de Fahrenheit a Kelvin (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirFahrenheitAKelvin(string fahrenheitString)
        {
            var error = TemperaturaValidator.ValidarStringTemperaturaFahrenheit(fahrenheitString, out double fahrenheit);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirFahrenheitAKelvin(fahrenheit);
        }

        /// <summary>
        /// Convierte de Kelvin a Fahrenheit (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirKelvinAFahrenheit(string kelvinString)
        {
            var error = TemperaturaValidator.ValidarStringTemperaturaKelvin(kelvinString, out double kelvin);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirKelvinAFahrenheit(kelvin);
        }

        #endregion

        #region Métodos internos con double (para reutilización)

        /// <summary>
        /// Convierte de Celsius a Fahrenheit
        /// </summary>
        private ConversionResultModel ConvertirCelsiusAFahrenheit(double celsius)
        {
            var error = TemperaturaValidator.ValidarTemperaturaCelsius(celsius);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            // Fórmula: F = C * (9/5) + 32
            double fahrenheit = (celsius * TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR) + TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET;

            var resultado = new UnidadConversionModel(
                celsius,
                fahrenheit,
                TemperaturaConstants.CELSIUS,
                TemperaturaConstants.FAHRENHEIT,
                "Temperatura",
                TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR
            );

            return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Fahrenheit a Celsius
        /// </summary>
        private ConversionResultModel ConvertirFahrenheitACelsius(double fahrenheit)
        {
            var error = TemperaturaValidator.ValidarTemperaturaFahrenheit(fahrenheit);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            // Fórmula: C = (F - 32) * (5/9)
            double celsius = (fahrenheit - TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET) * TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR;

            var resultado = new UnidadConversionModel(
                fahrenheit,
                celsius,
                TemperaturaConstants.FAHRENHEIT,
                TemperaturaConstants.CELSIUS,
                "Temperatura",
                TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR
            );

            return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Celsius a Kelvin
        /// </summary>
        private ConversionResultModel ConvertirCelsiusAKelvin(double celsius)
        {
            var error = TemperaturaValidator.ValidarTemperaturaCelsius(celsius);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            // Fórmula: K = C + 273.15
            double kelvin = celsius + TemperaturaConstants.CELSIUS_KELVIN_OFFSET;

            var resultado = new UnidadConversionModel(
                celsius,
                kelvin,
                TemperaturaConstants.CELSIUS,
                TemperaturaConstants.KELVIN,
                "Temperatura",
                1.0 // No hay factor de conversión multiplicativo, es suma
            );

            return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Kelvin a Celsius
        /// </summary>
        private ConversionResultModel ConvertirKelvinACelsius(double kelvin)
        {
            var error = TemperaturaValidator.ValidarTemperaturaKelvin(kelvin);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            // Fórmula: C = K - 273.15
            double celsius = kelvin - TemperaturaConstants.CELSIUS_KELVIN_OFFSET;

            var resultado = new UnidadConversionModel(
                kelvin,
                celsius,
                TemperaturaConstants.KELVIN,
                TemperaturaConstants.CELSIUS,
                "Temperatura",
                1.0 // No hay factor de conversión multiplicativo, es resta
            );

            return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Fahrenheit a Kelvin
        /// </summary>
        private ConversionResultModel ConvertirFahrenheitAKelvin(double fahrenheit)
        {
            var error = TemperaturaValidator.ValidarTemperaturaFahrenheit(fahrenheit);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            // Fórmula: K = (F - 32) * (5/9) + 273.15
            double celsius = (fahrenheit - TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET) * TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR;
            double kelvin = celsius + TemperaturaConstants.CELSIUS_KELVIN_OFFSET;

            var resultado = new UnidadConversionModel(
                fahrenheit,
                kelvin,
                TemperaturaConstants.FAHRENHEIT,
                TemperaturaConstants.KELVIN,
                "Temperatura",
                TemperaturaConstants.FAHRENHEIT_CELSIUS_FACTOR
            );

            return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Kelvin a Fahrenheit
        /// </summary>
        private ConversionResultModel ConvertirKelvinAFahrenheit(double kelvin)
        {
            var error = TemperaturaValidator.ValidarTemperaturaKelvin(kelvin);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            // Fórmula: F = (K - 273.15) * (9/5) + 32
            double celsius = kelvin - TemperaturaConstants.CELSIUS_KELVIN_OFFSET;
            double fahrenheit = (celsius * TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR) + TemperaturaConstants.FAHRENHEIT_CELSIUS_OFFSET;

            var resultado = new UnidadConversionModel(
                kelvin,
                fahrenheit,
                TemperaturaConstants.KELVIN,
                TemperaturaConstants.FAHRENHEIT,
                "Temperatura",
                TemperaturaConstants.CELSIUS_FAHRENHEIT_FACTOR
            );

            return ConversionResultModel.Exito(resultado);
        }

        #endregion
    }
}
