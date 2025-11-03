/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.models;

/**
 *
 * @author jeffe
 */
import java.time.LocalDateTime;
public class ConversionErrorModel {
    private String codigoError;
    private String mensaje;
    private String tipoError;
    private Double valorProblematico;
    private String unidad;
    private LocalDateTime fechaError;
    private String detalles;

    public ConversionErrorModel() {
        this.fechaError = LocalDateTime.now();
    }

    public ConversionErrorModel(String codigoError, String mensaje, String tipoError, Double valorProblematico, String unidad, String detalles) {
        this.codigoError = codigoError;
        this.mensaje = mensaje;
        this.tipoError = tipoError;
        this.valorProblematico = valorProblematico;
        this.unidad = unidad;
        this.detalles = detalles;
        this.fechaError = LocalDateTime.now();
    }

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
