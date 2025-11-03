/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.models;

import java.time.LocalDateTime;

/**
 *
 * @author jeffe
 */
public class UnidadConversionModel {
    private double valorOriginal;
    private double valorConvertidoExacto;
    private double valorConvertidoRedondeado;
    private String unidadOrigen;
    private String unidadDestino;
    private String tipoConversion;
    private double factorConversion;
    private LocalDateTime fechaConversion;

    public UnidadConversionModel() {
        this.fechaConversion = LocalDateTime.now();
    }

    public UnidadConversionModel(double valorOriginal, double valorConvertidoExacto, String unidadOrigen, String unidadDestino, String tipoConversion, double factorConversion) {
        this.valorOriginal = valorOriginal;
        this.valorConvertidoExacto = valorConvertidoExacto;
        this.valorConvertidoRedondeado = Math.round(valorConvertidoExacto * 100.0) / 100.0; // Redondeo a 2 decimales
        this.unidadOrigen = unidadOrigen;
        this.unidadDestino = unidadDestino;
        this.tipoConversion = tipoConversion;
        this.factorConversion = factorConversion;
        this.fechaConversion = LocalDateTime.now();
    }

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
