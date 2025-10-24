/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.models;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Modelo para representar errores en las conversiones
 * Serializable para servicios web SOAP/REST
 * @author Kewo
 */
@XmlRootElement(name = "ConversionError")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConversionError {
    /**
     * Código de error único
     */
    @XmlElement(name = "CodigoError")
    private String codigoError;
    
    /**
     * Mensaje descriptivo del error
     */
    @XmlElement(name = "Mensaje")
    private String mensaje;
    
    /**
     * Tipo de error (Validacion, Conversion, Sistema)
     */
    @XmlElement(name = "TipoError")
    private String tipoError;
    
    /**
     * Valor que causó el error (puede ser null)
     */
    @XmlElement(name = "ValorProblematico")
    private Double valorProblematico;
    
    /**
     * Unidad del valor problemático
     */
    @XmlElement(name = "Unidad")
    private String unidad;
    
    /**
     * Timestamp cuando ocurrió el error
     */
    @XmlElement(name = "FechaError")
    private LocalDateTime fechaError;
    
    /**
     * Detalles adicionales del error
     */
    @XmlElement(name = "Detalles")
    private String detalles;
    
    /**
     * Constructor por defecto para serialización
     */
    public ConversionError() {
        this.fechaError = LocalDateTime.now();
    }
    
    /**
     * Constructor con parámetros
     * @param codigoError
     * @param mensaje
     * @param tipoError
     * @param valorProblematico
     * @param unidad
     * @param detalles
     */
    public ConversionError(String codigoError, String mensaje, String tipoError, 
                          Double valorProblematico, String unidad, String detalles) {
        this.codigoError = codigoError;
        this.mensaje = mensaje;
        this.tipoError = tipoError;
        this.valorProblematico = valorProblematico;
        this.unidad = unidad;
        this.detalles = detalles;
        this.fechaError = LocalDateTime.now();
    }
    
    /**
     * Constructor con parámetros opcionales (sobrecarga simplificada)
     * @param codigoError
     * @param mensaje
     * @param tipoError
     */
    public ConversionError(String codigoError, String mensaje, String tipoError) {
        this(codigoError, mensaje, tipoError, null, null, null);
    }
    
    // Getters y Setters
    
    public String getCodigoError() {
        return codigoError;
    }
    
    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getTipoError() {
        return tipoError;
    }
    
    public void setTipoError(String tipoError) {
        this.tipoError = tipoError;
    }
    
    public Double getValorProblematico() {
        return valorProblematico;
    }
    
    public void setValorProblematico(Double valorProblematico) {
        this.valorProblematico = valorProblematico;
    }
    
    public String getUnidad() {
        return unidad;
    }
    
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    
    public LocalDateTime getFechaError() {
        return fechaError;
    }
    
    public void setFechaError(LocalDateTime fechaError) {
        this.fechaError = fechaError;
    }
    
    public String getDetalles() {
        return detalles;
    }
    
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
