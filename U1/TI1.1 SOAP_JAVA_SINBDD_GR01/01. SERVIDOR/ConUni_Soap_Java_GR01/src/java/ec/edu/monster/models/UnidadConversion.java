/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.models;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Kewo
 */
@XmlRootElement(name = "UnidadConversion")
@XmlAccessorType(XmlAccessType.FIELD)
public class UnidadConversion {
    /**
     * Valor original antes de la conversión
     */
    private double valorOriginal;
    
    /**
     * Valor convertido exacto después de la conversión (sin redondeo)
     */
    private double valorConvertidoExacto;
    
    /**
     * Valor convertido redondeado a 2 decimales
     */
    private double valorConvertidoRedondeado;
    
    /**
     * Unidad de origen
     */
    private String unidadOrigen;
    
    /**
     * Unidad de destino
     */
    private String unidadDestino;
    
    /**
     * Factor de conversión utilizado
     */
    private double factorConversion;
    
    /**
     * Timestamp de cuándo se realizó la conversión
     */
    private LocalDateTime fechaConversion;
    
    /**
     * Constructor por defecto
     */
    public UnidadConversion() {
        this.fechaConversion = LocalDateTime.now();
    }
    
    /**
     * Constructor con parámetros
     */
    public UnidadConversion(double valorOriginal, double valorConvertidoExacto, 
                           String unidadOrigen, String unidadDestino, double factorConversion) {
        this.valorOriginal = valorOriginal;
        this.valorConvertidoExacto = valorConvertidoExacto;
        this.valorConvertidoRedondeado = redondear(valorConvertidoExacto, 2);
        this.unidadOrigen = unidadOrigen;
        this.unidadDestino = unidadDestino;
        this.factorConversion = factorConversion;
        this.fechaConversion = LocalDateTime.now();
    }
    
    /**
     * Método auxiliar para redondear a un número específico de decimales
     */
    private double redondear(double valor, int decimales) {
        BigDecimal bd = new BigDecimal(Double.toString(valor));
        bd = bd.setScale(decimales, RoundingMode.HALF_UP);
        return bd.doubleValue();
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
    
    /**
     * Obtiene una representación en texto de la conversión
     * @return String con la información de la conversión
     */
    @Override
    public String toString() {
        return String.format("%f %s = %f %s (Exacto: %f)", 
            valorOriginal, unidadOrigen, valorConvertidoRedondeado, 
            unidadDestino, valorConvertidoExacto);
    }
}
