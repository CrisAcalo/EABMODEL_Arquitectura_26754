/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.services;

import ec.edu.monster.constants.ErrorConstants;
import ec.edu.monster.constants.MasaConstants;
import ec.edu.monster.models.ConversionError;
import ec.edu.monster.models.ConversionResult;
import ec.edu.monster.models.UnidadConversion;
import ec.edu.monster.validators.BaseValidator;
import ec.edu.monster.validators.ValidationResult;

/**
 *
 * Servicio de negocio para conversiones de masa
 * @author Kewo
 */
public class MasaBusinessService {
    // ==================== Conversiones Kilogramo <-> Quintal ====================
    
    /**
     * Convierte kilogramos a quintales y retorna resultado con manejo de errores
     * @param kilogramosString Valor en kilogramos como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirKilogramoAQuintal(String kilogramosString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(kilogramosString, MasaConstants.KILOGRAMO);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double kilogramos = validacion.getValor();
        
        try {
            double resultadoExacto = kilogramos * MasaConstants.KILOGRAMO_A_QUINTAL;
            
            UnidadConversion conversion = new UnidadConversion(
                kilogramos,
                resultadoExacto,
                MasaConstants.KILOGRAMO,
                MasaConstants.QUINTAL,
                MasaConstants.KILOGRAMO_A_QUINTAL
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_MASA,
                "Error en la conversión de kilogramos a quintales",
                ErrorConstants.TIPO_CONVERSION,
                kilogramos,
                MasaConstants.KILOGRAMO,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    /**
     * Convierte quintales a kilogramos y retorna resultado con manejo de errores
     * @param quintalesString Valor en quintales como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirQuintalAKilogramo(String quintalesString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(quintalesString, MasaConstants.QUINTAL);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double quintales = validacion.getValor();
        
        try {
            double resultadoExacto = quintales * MasaConstants.QUINTAL_A_KILOGRAMO;
            
            UnidadConversion conversion = new UnidadConversion(
                quintales,
                resultadoExacto,
                MasaConstants.QUINTAL,
                MasaConstants.KILOGRAMO,
                MasaConstants.QUINTAL_A_KILOGRAMO
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_MASA,
                "Error en la conversión de quintales a kilogramos",
                ErrorConstants.TIPO_CONVERSION,
                quintales,
                MasaConstants.QUINTAL,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    // ==================== Conversiones Kilogramo <-> Libra ====================
    
    /**
     * Convierte kilogramos a libras y retorna resultado con manejo de errores
     * @param kilogramosString Valor en kilogramos como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirKilogramoALibra(String kilogramosString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(kilogramosString, MasaConstants.KILOGRAMO);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double kilogramos = validacion.getValor();
        
        try {
            double resultadoExacto = kilogramos * MasaConstants.KILOGRAMO_A_LIBRA;
            
            UnidadConversion conversion = new UnidadConversion(
                kilogramos,
                resultadoExacto,
                MasaConstants.KILOGRAMO,
                MasaConstants.LIBRA,
                MasaConstants.KILOGRAMO_A_LIBRA
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_MASA,
                "Error en la conversión de kilogramos a libras",
                ErrorConstants.TIPO_CONVERSION,
                kilogramos,
                MasaConstants.KILOGRAMO,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    /**
     * Convierte libras a kilogramos y retorna resultado con manejo de errores
     * @param librasString Valor en libras como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirLibraAKilogramo(String librasString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(librasString, MasaConstants.LIBRA);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double libras = validacion.getValor();
        
        try {
            double resultadoExacto = libras * MasaConstants.LIBRA_A_KILOGRAMO;
            
            UnidadConversion conversion = new UnidadConversion(
                libras,
                resultadoExacto,
                MasaConstants.LIBRA,
                MasaConstants.KILOGRAMO,
                MasaConstants.LIBRA_A_KILOGRAMO
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_MASA,
                "Error en la conversión de libras a kilogramos",
                ErrorConstants.TIPO_CONVERSION,
                libras,
                MasaConstants.LIBRA,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    // ==================== Conversiones Quintal <-> Libra ====================
    
    /**
     * Convierte quintales a libras y retorna resultado con manejo de errores
     * @param quintalesString Valor en quintales como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirQuintalALibra(String quintalesString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(quintalesString, MasaConstants.QUINTAL);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double quintales = validacion.getValor();
        
        try {
            double resultadoExacto = quintales * MasaConstants.QUINTAL_A_LIBRA;
            
            UnidadConversion conversion = new UnidadConversion(
                quintales,
                resultadoExacto,
                MasaConstants.QUINTAL,
                MasaConstants.LIBRA,
                MasaConstants.QUINTAL_A_LIBRA
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_MASA,
                "Error en la conversión de quintales a libras",
                ErrorConstants.TIPO_CONVERSION,
                quintales,
                MasaConstants.QUINTAL,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    /**
     * Convierte libras a quintales y retorna resultado con manejo de errores
     * @param librasString Valor en libras como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirLibraAQuintal(String librasString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(librasString, MasaConstants.LIBRA);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double libras = validacion.getValor();
        
        try {
            double resultadoExacto = libras * MasaConstants.LIBRA_A_QUINTAL;
            
            UnidadConversion conversion = new UnidadConversion(
                libras,
                resultadoExacto,
                MasaConstants.LIBRA,
                MasaConstants.QUINTAL,
                MasaConstants.LIBRA_A_QUINTAL
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_MASA,
                "Error en la conversión de libras a quintales",
                ErrorConstants.TIPO_CONVERSION,
                libras,
                MasaConstants.LIBRA,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
}
