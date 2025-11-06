package ec.edu.monster.models;

import java.time.LocalDateTime;

/**
 * Modelo que representa los detalles de una conversión exitosa
 * DEBE coincidir EXACTAMENTE con UnidadConversionModel del servidor
 * 
 * @author jeffe
 */
public class UnidadConversion {
    
    private double valorOriginal;
    private double valorConvertidoExacto;
    private double valorConvertidoRedondeado;
    private String unidadOrigen;
    private String unidadDestino;
    private String tipoConversion;
    private double factorConversion;
    private LocalDateTime fechaConversion;
    
    // Constructor vacío (REQUERIDO para Gson)
    public UnidadConversion() {
    }
    
    // Getters y Setters
    public double getValorOriginal() {
        return valorOriginal;
    }
    
    public void setValorOriginal(double valorOriginal) {
        this.valorOriginal = valorOriginal;
    }
    
    public double getValorConvertidoExacto() {
        return valorConvertidoExacto;
    }
    
    public void setValorConvertidoExacto(double valorConvertidoExacto) {
        this.valorConvertidoExacto = valorConvertidoExacto;
    }
    
    public double getValorConvertidoRedondeado() {
        return valorConvertidoRedondeado;
    }
    
    public void setValorConvertidoRedondeado(double valorConvertidoRedondeado) {
        this.valorConvertidoRedondeado = valorConvertidoRedondeado;
    }
    
    public String getUnidadOrigen() {
        return unidadOrigen;
    }
    
    public void setUnidadOrigen(String unidadOrigen) {
        this.unidadOrigen = unidadOrigen;
    }
    
    public String getUnidadDestino() {
        return unidadDestino;
    }
    
    public void setUnidadDestino(String unidadDestino) {
        this.unidadDestino = unidadDestino;
    }
    
    public String getTipoConversion() {
        return tipoConversion;
    }
    
    public void setTipoConversion(String tipoConversion) {
        this.tipoConversion = tipoConversion;
    }
    
    public double getFactorConversion() {
        return factorConversion;
    }
    
    public void setFactorConversion(double factorConversion) {
        this.factorConversion = factorConversion;
    }
    
    public LocalDateTime getFechaConversion() {
        return fechaConversion;
    }
    
    public void setFechaConversion(LocalDateTime fechaConversion) {
        this.fechaConversion = fechaConversion;
    }
}