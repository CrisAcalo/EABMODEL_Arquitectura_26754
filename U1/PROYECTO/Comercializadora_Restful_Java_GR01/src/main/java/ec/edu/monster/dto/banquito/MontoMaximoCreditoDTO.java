package ec.edu.monster.dto.banquito;

import java.math.BigDecimal;

/**
 * DTO para respuesta de monto máximo de crédito desde BanQuito
 */
public class MontoMaximoCreditoDTO {
    
    private String cedula;
    private BigDecimal montoMaximo;
    private BigDecimal promedioDepositos;
    private BigDecimal promedioRetiros;
    private String mensaje;
    
    public MontoMaximoCreditoDTO() {
    }
    
    public MontoMaximoCreditoDTO(String cedula, BigDecimal montoMaximo, BigDecimal promedioDepositos, 
                                  BigDecimal promedioRetiros, String mensaje) {
        this.cedula = cedula;
        this.montoMaximo = montoMaximo;
        this.promedioDepositos = promedioDepositos;
        this.promedioRetiros = promedioRetiros;
        this.mensaje = mensaje;
    }
    
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
