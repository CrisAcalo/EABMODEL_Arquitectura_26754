package ec.edu.monster.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO para la solicitud de otorgamiento de crédito
 * 
 * @author Javi
 */
public class SolicitudCreditoDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String cedula;
    private BigDecimal precioElectrodomestico;
    private Integer numeroCuotas;
    
    // Constructores
    public SolicitudCreditoDTO() {
    }
    
    public SolicitudCreditoDTO(String cedula, BigDecimal precioElectrodomestico, 
                               Integer numeroCuotas) {
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
    
    @Override
    public String toString() {
        return "SolicitudCreditoDTO{" +
                "cedula='" + cedula + '\'' +
                ", precio=" + precioElectrodomestico +
                ", cuotas=" + numeroCuotas +
                '}';
    }
}
