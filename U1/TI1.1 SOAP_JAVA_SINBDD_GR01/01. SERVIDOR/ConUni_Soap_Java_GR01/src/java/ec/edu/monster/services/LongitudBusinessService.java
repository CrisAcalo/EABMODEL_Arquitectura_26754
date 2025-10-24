/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.services;

import ec.edu.monster.constants.ErrorConstants;
import ec.edu.monster.constants.LongitudConstants;
import ec.edu.monster.models.ConversionError;
import ec.edu.monster.models.ConversionResult;
import ec.edu.monster.models.UnidadConversion;
import ec.edu.monster.validators.BaseValidator;
import ec.edu.monster.validators.ValidationResult;

/**
 *
 * Servicio de negocio para conversiones de longitud
 * @author Kewo
 */
public class LongitudBusinessService {
    // ==================== Conversiones Milla <-> Metro ====================
    
    /**
     * Convierte millas a metros y retorna resultado con manejo de errores
     * @param millasString Valor en millas como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirMillaAMetro(String millasString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(millasString, LongitudConstants.MILLA);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double millas = validacion.getValor();
        
        try {
            double resultadoExacto = millas * LongitudConstants.MILLA_A_METRO;
            
            UnidadConversion conversion = new UnidadConversion(
                millas,
                resultadoExacto,
                LongitudConstants.MILLA,
                LongitudConstants.METRO,
                LongitudConstants.MILLA_A_METRO
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_LONGITUD,
                "Error en la conversión de millas a metros",
                ErrorConstants.TIPO_CONVERSION,
                millas,
                LongitudConstants.MILLA,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    /**
     * Convierte metros a millas y retorna resultado con manejo de errores
     * @param metrosString Valor en metros como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirMetroAMilla(String metrosString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(metrosString, LongitudConstants.METRO);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double metros = validacion.getValor();
        
        try {
            double resultadoExacto = metros * LongitudConstants.METRO_A_MILLA;
            
            UnidadConversion conversion = new UnidadConversion(
                metros,
                resultadoExacto,
                LongitudConstants.METRO,
                LongitudConstants.MILLA,
                LongitudConstants.METRO_A_MILLA
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_LONGITUD,
                "Error en la conversión de metros a millas",
                ErrorConstants.TIPO_CONVERSION,
                metros,
                LongitudConstants.METRO,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    // ==================== Conversiones Milla <-> Pulgada ====================
    
    /**
     * Convierte millas a pulgadas y retorna resultado con manejo de errores
     * @param millasString Valor en millas como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirMillaAPulgada(String millasString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(millasString, LongitudConstants.MILLA);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double millas = validacion.getValor();
        
        try {
            double resultadoExacto = millas * LongitudConstants.MILLA_A_PULGADA;
            
            UnidadConversion conversion = new UnidadConversion(
                millas,
                resultadoExacto,
                LongitudConstants.MILLA,
                LongitudConstants.PULGADA,
                LongitudConstants.MILLA_A_PULGADA
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_LONGITUD,
                "Error en la conversión de millas a pulgadas",
                ErrorConstants.TIPO_CONVERSION,
                millas,
                LongitudConstants.MILLA,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    /**
     * Convierte pulgadas a millas y retorna resultado con manejo de errores
     * @param pulgadasString Valor en pulgadas como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirPulgadaAMilla(String pulgadasString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(pulgadasString, LongitudConstants.PULGADA);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double pulgadas = validacion.getValor();
        
        try {
            double resultadoExacto = pulgadas * LongitudConstants.PULGADA_A_MILLA;
            
            UnidadConversion conversion = new UnidadConversion(
                pulgadas,
                resultadoExacto,
                LongitudConstants.PULGADA,
                LongitudConstants.MILLA,
                LongitudConstants.PULGADA_A_MILLA
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_LONGITUD,
                "Error en la conversión de pulgadas a millas",
                ErrorConstants.TIPO_CONVERSION,
                pulgadas,
                LongitudConstants.PULGADA,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    // ==================== Conversiones Metro <-> Pulgada ====================
    
    /**
     * Convierte metros a pulgadas y retorna resultado con manejo de errores
     * @param metrosString Valor en metros como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirMetroAPulgada(String metrosString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(metrosString, LongitudConstants.METRO);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double metros = validacion.getValor();
        
        try {
            double resultadoExacto = metros * LongitudConstants.METRO_A_PULGADA;
            
            UnidadConversion conversion = new UnidadConversion(
                metros,
                resultadoExacto,
                LongitudConstants.METRO,
                LongitudConstants.PULGADA,
                LongitudConstants.METRO_A_PULGADA
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_LONGITUD,
                "Error en la conversión de metros a pulgadas",
                ErrorConstants.TIPO_CONVERSION,
                metros,
                LongitudConstants.METRO,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
    
    /**
     * Convierte pulgadas a metros y retorna resultado con manejo de errores
     * @param pulgadasString Valor en pulgadas como string
     * @return ConversionResult con el resultado o error
     */
    public ConversionResult convertirPulgadaAMetro(String pulgadasString) {
        // Validar y convertir string a double
        ValidationResult validacion = BaseValidator.validarStringPositivo(pulgadasString, LongitudConstants.PULGADA);
        if (validacion.getError() != null) {
            return ConversionResult.fallo(validacion.getError());
        }
        
        double pulgadas = validacion.getValor();
        
        try {
            double resultadoExacto = pulgadas * LongitudConstants.PULGADA_A_METRO;
            
            UnidadConversion conversion = new UnidadConversion(
                pulgadas,
                resultadoExacto,
                LongitudConstants.PULGADA,
                LongitudConstants.METRO,
                LongitudConstants.PULGADA_A_METRO
            );
            
            return ConversionResult.exito(conversion);
        } catch (Exception ex) {
            ConversionError errorInterno = new ConversionError(
                ErrorConstants.ERROR_CONVERSION_LONGITUD,
                "Error en la conversión de pulgadas a metros",
                ErrorConstants.TIPO_CONVERSION,
                pulgadas,
                LongitudConstants.PULGADA,
                ex.getMessage()
            );
            return ConversionResult.fallo(errorInterno);
        }
    }
}
