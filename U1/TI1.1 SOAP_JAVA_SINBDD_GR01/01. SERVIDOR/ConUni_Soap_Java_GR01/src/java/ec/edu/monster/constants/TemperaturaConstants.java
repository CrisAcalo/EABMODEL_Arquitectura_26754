/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.constants;

/**
 *
 * Constantes para las conversiones de temperatura
 * @author Kewo
 */
public final class TemperaturaConstants {
    
    // Constructor privado para prevenir instanciación
    private TemperaturaConstants() {
        throw new AssertionError("Esta clase no debe ser instanciada");
    }
    
    // Límites físicos (cero absoluto)
    public static final double CERO_ABSOLUTO_CELSIUS = -273.15;
    public static final double CERO_ABSOLUTO_FAHRENHEIT = -459.67;
    public static final double CERO_ABSOLUTO_KELVIN = 0.0;
    
    // Constantes para conversiones
    public static final double CELSIUS_KELVIN_OFFSET = 273.15;
    public static final double FAHRENHEIT_CELSIUS_FACTOR = 5.0 / 9.0;
    public static final double CELSIUS_FAHRENHEIT_FACTOR = 9.0 / 5.0;
    public static final double FAHRENHEIT_CELSIUS_OFFSET = 32.0;
    
    // Nombres de unidades
    public static final String CELSIUS = "Celsius";
    public static final String FAHRENHEIT = "Fahrenheit";
    public static final String KELVIN = "Kelvin";
}
