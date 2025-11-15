package ec.edu.monster.rest.dto;

import java.math.BigDecimal;

/**
 * DTO para la respuesta de monto máximo de crédito
 */
public class MontoMaximoCreditoDTO {
    private String cedula;
    private BigDecimal montoMaximo;
    private BigDecimal promedioDepositos;
    private BigDecimal promedioRetiros;
    private String mensaje;
    
    public MontoMaximoCreditoDTO() {
    }
    
    public MontoMaximoCreditoDTO(String cedula, BigDecimal montoMaximo, String mensaje) {
        this.cedula = cedula;
        this.montoMaximo = montoMaximo;
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public BigDecimal getMontoMaximo() {
        return montoMaximo;
    }
    
    public void setMontoMaximo(BigDecimal montoMaximo) {
        this.montoMaximo = montoMaximo;
    }
    
    public BigDecimal getPromedioDepositos() {
        return promedioDepositos;
    }
    
    public void setPromedioDepositos(BigDecimal promedioDepositos) {
        this.promedioDepositos = promedioDepositos;
    }
    
    public BigDecimal getPromedioRetiros() {
        return promedioRetiros;
    }
    
    public void setPromedioRetiros(BigDecimal promedioRetiros) {
        this.promedioRetiros = promedioRetiros;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
