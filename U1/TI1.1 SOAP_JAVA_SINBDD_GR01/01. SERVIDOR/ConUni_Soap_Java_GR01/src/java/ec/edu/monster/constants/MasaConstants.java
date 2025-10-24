/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.constants;

/**
 *
 * Constantes para las conversiones de masa
 * @author Kewo
 */
public final class MasaConstants {
    
    // Constructor privado para prevenir instanciación
    private MasaConstants() {
        throw new AssertionError("Esta clase no debe ser instanciada");
    }
    
    // Factores de conversión de masa (todas las conversiones posibles)
    // Kilogramo como base de referencia
    public static final double KILOGRAMO_A_QUINTAL = 0.01;
    public static final double QUINTAL_A_KILOGRAMO = 100.0;
    
    public static final double KILOGRAMO_A_LIBRA = 2.20462;
    public static final double LIBRA_A_KILOGRAMO = 1.0 / 2.20462;
    
    // Quintal como base de referencia
    public static final double QUINTAL_A_LIBRA = 220.462;
    public static final double LIBRA_A_QUINTAL = 1.0 / 220.462;
    
    // Nombres de unidades
    public static final String KILOGRAMO = "Kilogramo";
    public static final String QUINTAL = "Quintal";
    public static final String LIBRA = "Libra";
    
}
