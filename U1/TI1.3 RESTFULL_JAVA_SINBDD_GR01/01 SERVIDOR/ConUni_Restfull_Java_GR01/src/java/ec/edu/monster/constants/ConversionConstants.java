/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.constants;

/**
 *
 * @author jeffe
 */
public final class ConversionConstants {

    private ConversionConstants() {}
    
    // --- Longitud Constants ---
    public static final class Longitud {
        private Longitud() {}
        public static final double MILLA_A_METRO = 1609.34;
        public static final double METRO_A_MILLA = 1.0 / 1609.34;
        public static final double MILLA_A_PULGADA = 63360.0;
        public static final double PULGADA_A_MILLA = 1.0 / 63360.0;
        public static final double METRO_A_PULGADA = 39.3701;
        public static final double PULGADA_A_METRO = 1.0 / 39.3701;
        public static final String MILLA = "Milla";
        public static final String METRO = "Metro";
        public static final String PULGADA = "Pulgada";
    }

    // --- Masa Constants ---
    public static final class Masa {
        private Masa() {}
        public static final double KILOGRAMO_A_QUINTAL = 0.01;
        public static final double QUINTAL_A_KILOGRAMO = 100.0;
        public static final double KILOGRAMO_A_LIBRA = 2.20462;
        public static final double LIBRA_A_KILOGRAMO = 1.0 / 2.20462;
        public static final double QUINTAL_A_LIBRA = 220.462;
        public static final double LIBRA_A_QUINTAL = 1.0 / 220.462;
        public static final String KILOGRAMO = "Kilogramo";
        public static final String QUINTAL = "Quintal";
        public static final String LIBRA = "Libra";
    }

    // --- Temperatura Constants ---
    public static final class Temperatura {
        private Temperatura() {}
        public static final double CERO_ABSOLUTO_CELSIUS = -273.15;
        public static final double CERO_ABSOLUTO_FAHRENHEIT = -459.67;
        public static final double CERO_ABSOLUTO_KELVIN = 0.0;
        public static final double CELSIUS_KELVIN_OFFSET = 273.15;
        public static final double FAHRENHEIT_CELSIUS_FACTOR = 5.0 / 9.0;
        public static final double CELSIUS_FAHRENHEIT_FACTOR = 9.0 / 5.0;
        public static final double FAHRENHEIT_CELSIUS_OFFSET = 32.0;
        public static final String CELSIUS = "Celsius";
        public static final String FAHRENHEIT = "Fahrenheit";
        public static final String KELVIN = "Kelvin";
    }
    
    
}
