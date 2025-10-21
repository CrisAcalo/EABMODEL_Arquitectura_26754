namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.constants
{
    /// <summary>
    /// Constantes para las conversiones de longitud
    /// </summary>
    public static class LongitudConstants
    {
        // Factores de conversión de longitud (todas las conversiones posibles)
        // Milla como base de referencia
        public const double MILLA_A_METRO = 1609.34;
        public const double METRO_A_MILLA = 1.0 / 1609.34;
        
        public const double MILLA_A_PULGADA = 63360.0;
        public const double PULGADA_A_MILLA = 1.0 / 63360.0;
        
        // Metro como base de referencia
        public const double METRO_A_PULGADA = 39.3701;
        public const double PULGADA_A_METRO = 1.0 / 39.3701;

        // Nombres de unidades
        public const string MILLA = "Milla";
        public const string METRO = "Metro";
        public const string PULGADA = "Pulgada";
    }

    /// <summary>
    /// Constantes para las conversiones de masa
    /// </summary>
    public static class MasaConstants
    {
        // Factores de conversión de masa (todas las conversiones posibles)
        // Kilogramo como base de referencia
        public const double KILOGRAMO_A_QUINTAL = 0.01;
        public const double QUINTAL_A_KILOGRAMO = 100.0;
        
        public const double KILOGRAMO_A_LIBRA = 2.20462;
        public const double LIBRA_A_KILOGRAMO = 1.0 / 2.20462;
        
        // Quintal como base de referencia
        public const double QUINTAL_A_LIBRA = 220.462;
        public const double LIBRA_A_QUINTAL = 1.0 / 220.462;

        // Nombres de unidades
        public const string KILOGRAMO = "Kilogramo";
        public const string QUINTAL = "Quintal";
        public const string LIBRA = "Libra";
    }

    /// <summary>
    /// Constantes para las conversiones de temperatura
    /// </summary>
    public static class TemperaturaConstants
    {
        // Límites físicos (cero absoluto)
        public const double CERO_ABSOLUTO_CELSIUS = -273.15;
        public const double CERO_ABSOLUTO_FAHRENHEIT = -459.67;
        public const double CERO_ABSOLUTO_KELVIN = 0.0;

        // Constantes para conversiones
        public const double CELSIUS_KELVIN_OFFSET = 273.15;
        public const double FAHRENHEIT_CELSIUS_FACTOR = 5.0 / 9.0;
        public const double CELSIUS_FAHRENHEIT_FACTOR = 9.0 / 5.0;
        public const double FAHRENHEIT_CELSIUS_OFFSET = 32.0;

        // Nombres de unidades
        public const string CELSIUS = "Celsius";
        public const string FAHRENHEIT = "Fahrenheit";
        public const string KELVIN = "Kelvin";
    }
}