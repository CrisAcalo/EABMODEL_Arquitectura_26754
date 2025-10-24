/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.constants;

/**
 *
 * Constantes para las conversiones de longitud
 * @author Kewo
 */
public final class LongitudConstants {
    
    // Constructor privado para prevenir instanciación
    private LongitudConstants() {
        throw new AssertionError("Esta clase no debe ser instanciada");
    }
    
    // Factores de conversión de longitud (todas las conversiones posibles)
    // Milla como base de referencia
    public static final double MILLA_A_METRO = 1609.34;
    public static final double METRO_A_MILLA = 1.0 / 1609.34;
    
    public static final double MILLA_A_PULGADA = 63360.0;
    public static final double PULGADA_A_MILLA = 1.0 / 63360.0;
    
    // Metro como base de referencia
    public static final double METRO_A_PULGADA = 39.3701;
    public static final double PULGADA_A_METRO = 1.0 / 39.3701;
    
    // Nombres de unidades
    public static final String MILLA = "Milla";
    public static final String METRO = "Metro";
    public static final String PULGADA = "Pulgada";
    
}
