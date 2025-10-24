/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.models;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * Resultado de operación que puede contener éxito o error
 * @author Kewo
 */
@XmlRootElement(name = "ConversionResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConversionResult {
    /**
     * Indica si la operación fue exitosa
     */
    @XmlElement(name = "Exitoso")
    private boolean exitoso;
    
    /**
     * Resultado de la conversión (solo si es exitoso)
     */
    @XmlElement(name = "Resultado")
    private UnidadConversion resultado;
    
    /**
     * Información del error (solo si no es exitoso)
     */
    @XmlElement(name = "Error")
    private ConversionError error;
    
    /**
     * Constructor por defecto
     */
    public ConversionResult() {
    }
    
    /**
     * Constructor privado para factory methods
     */
    private ConversionResult(boolean exitoso, UnidadConversion resultado, ConversionError error) {
        this.exitoso = exitoso;
        this.resultado = resultado;
        this.error = error;
    }
    
    /**
     * Factory method para resultado exitoso
     */
    public static ConversionResult exito(UnidadConversion conversion) {
        return new ConversionResult(true, conversion, null);
    }
    
    /**
     * Factory method para resultado con error
     */
    public static ConversionResult fallo(ConversionError error) {
        return new ConversionResult(false, null, error);
    }
    
    // Getters y Setters
    
    public boolean isExitoso() {
        return exitoso;
    }
    
    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }
    
    public UnidadConversion getResultado() {
        return resultado;
    }
    
    public void setResultado(UnidadConversion resultado) {
        this.resultado = resultado;
    }
    
    public ConversionError getError() {
        return error;
    }
    
    public void setError(ConversionError error) {
        this.error = error;
    }
}
