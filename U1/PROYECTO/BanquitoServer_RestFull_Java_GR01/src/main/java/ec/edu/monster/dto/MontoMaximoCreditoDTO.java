package ec.edu.monster.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO para respuesta de monto máximo de crédito
 * 
 * @author Javi
 */
public class MontoMaximoCreditoDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String cedula;
    private String nombreCliente;
    private BigDecimal montoMaximo;
    private BigDecimal promedioDepositos;
    private BigDecimal promedioRetiros;
    private String mensaje;
    private boolean aprobado;
    
    // Constructores
    public MontoMaximoCreditoDTO() {
    }
    
    public MontoMaximoCreditoDTO(BigDecimal montoMaximo, String mensaje) {
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
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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
    
    public boolean isAprobado() {
        return aprobado;
    }
    
    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
}
