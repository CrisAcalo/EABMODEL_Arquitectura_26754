package ec.edu.monster.models;

import java.time.LocalDateTime;

/**
 * Modelo que representa un error en la conversión
 * DEBE coincidir EXACTAMENTE con ConversionErrorModel del servidor
 * 
 * @author jeffe
 */
public class ConversionError {
    
    private String codigoError;
    private String mensaje;
    private String tipoError;
    private Double valorProblematico;
    private String unidad;
    private LocalDateTime fechaError;
    private String detalles;
    
    // Constructor vacío (REQUERIDO para Gson)
    public ConversionError() {
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