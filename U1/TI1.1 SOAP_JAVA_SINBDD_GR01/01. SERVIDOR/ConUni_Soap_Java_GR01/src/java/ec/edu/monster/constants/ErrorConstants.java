/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.constants;

/**
 * Constantes para códigos de error del sistema
 * @author Kewo
 */
public final class ErrorConstants {
    
    // Constructor privado para prevenir instanciación
    private ErrorConstants() {
        throw new AssertionError("Esta clase no debe ser instanciada");
    }
    
    // Códigos de error de validación
    public static final String VALOR_NEGATIVO = "VAL_001";
    public static final String VALOR_NAN = "VAL_002";
    public static final String VALOR_INFINITO = "VAL_003";
    public static final String TEMPERATURA_BAJO_CERO_ABSOLUTO = "VAL_004";
    public static final String VALOR_VACIO = "VAL_005";
    public static final String VALOR_NO_NUMERICO = "VAL_006";
    
    // Códigos de error de conversión
    public static final String ERROR_CONVERSION_LONGITUD = "CONV_001";
    public static final String ERROR_CONVERSION_MASA = "CONV_002";
    public static final String ERROR_CONVERSION_TEMPERATURA = "CONV_003";
    
    // Códigos de error del sistema
    public static final String ERROR_INTERNO = "SYS_001";
    public static final String ERROR_SERVICIO_NO_DISPONIBLE = "SYS_002";
    
    // Tipos de error
    public static final String TIPO_VALIDACION = "Validacion";
    public static final String TIPO_CONVERSION = "Conversion";
    public static final String TIPO_SISTEMA = "Sistema";
    
    // Mensajes base
    public static final String MSG_VALOR_NEGATIVO = "El valor no puede ser negativo";
    public static final String MSG_VALOR_NAN = "El valor no es un número válido";
    public static final String MSG_VALOR_INFINITO = "El valor no puede ser infinito";
    public static final String MSG_TEMPERATURA_INVALIDA = "La temperatura está por debajo del cero absoluto";
    public static final String MSG_ERROR_INTERNO = "Error interno del sistema";
    public static final String MSG_VALOR_VACIO = "El valor no puede estar vacío";
    public static final String MSG_VALOR_NO_NUMERICO = "El valor no es un número válido";
}
