/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para respuesta de cálculo de factura (sin generarla)
 * Permite conocer el total antes de solicitar crédito
 */
public class CalculoFacturaDTO implements Serializable {

    private Boolean exitoso;
    private String mensaje;
    private BigDecimal total;
    private List<DetalleCalculoDTO> detalles;

    // Constructores
    public CalculoFacturaDTO() {
        this.detalles = new ArrayList<>();
    }

    public CalculoFacturaDTO(Boolean exitoso, String mensaje, BigDecimal total, List<DetalleCalculoDTO> detalles) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.total = total;
        this.detalles = detalles != null ? detalles : new ArrayList<>();
    }

    // Getters y Setters
    public Boolean getExitoso() {
        return exitoso;
    }

    public void setExitoso(Boolean exitoso) {
        this.exitoso = exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<DetalleCalculoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCalculoDTO> detalles) {
        this.detalles = detalles;
    }
}
