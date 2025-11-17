package ec.edu.monster.rest.dto;

import java.math.BigDecimal;

/**
 * DTO para la solicitud de otorgamiento de crédito
 */
public class SolicitudCreditoDTO {
    private String cedula;
    private BigDecimal precioElectrodomestico;
    private Integer numeroCuotas;
    
    public SolicitudCreditoDTO() {
    }
    
    public SolicitudCreditoDTO(String cedula, BigDecimal precioElectrodomestico, Integer numeroCuotas) {
        this.cedula = cedula;
        this.precioElectrodomestico = precioElectrodomestico;
        this.numeroCuotas = numeroCuotas;
    }
    
    // Getters y Setters
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public BigDecimal getPrecioElectrodomestico() {
        return precioElectrodomestico;
    }
    
    public void setPrecioElectrodomestico(BigDecimal precioElectrodomestico) {
        this.precioElectrodomestico = precioElectrodomestico;
    }
    
    public Integer getNumeroCuotas() {
        return numeroCuotas;
    }
    
    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }
}
