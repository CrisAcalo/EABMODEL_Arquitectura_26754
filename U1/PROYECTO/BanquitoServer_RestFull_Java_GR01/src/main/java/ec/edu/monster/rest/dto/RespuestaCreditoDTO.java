package ec.edu.monster.rest.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para la respuesta de otorgamiento de crédito
 */
public class RespuestaCreditoDTO {
    private boolean exito;
    private String mensaje;
    private String numeroCredito;
    private String cedula;
    private BigDecimal montoCredito;
    private Integer numeroCuotas;
    private BigDecimal cuotaMensual;
    private BigDecimal tasaInteres;
    private List<CuotaAmortizacionDTO> tablaAmortizacion = new ArrayList<>();
    
    public RespuestaCreditoDTO() {
    }
    
    public RespuestaCreditoDTO(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    public boolean isExito() {
        return exito;
    }
    
    public void setExito(boolean exito) {
        this.exito = exito;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getNumeroCredito() {
        return numeroCredito;
    }
    
    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
    }
    
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public BigDecimal getMontoCredito() {
        return montoCredito;
    }
    
    public void setMontoCredito(BigDecimal montoCredito) {
        this.montoCredito = montoCredito;
    }
    
    public Integer getNumeroCuotas() {
        return numeroCuotas;
    }
    
    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }
    
    public BigDecimal getCuotaMensual() {
        return cuotaMensual;
    }
    
    public void setCuotaMensual(BigDecimal cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }
    
    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }
    
    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }
    
    public List<CuotaAmortizacionDTO> getTablaAmortizacion() {
        return tablaAmortizacion;
    }
    
    public void setTablaAmortizacion(List<CuotaAmortizacionDTO> tablaAmortizacion) {
        this.tablaAmortizacion = tablaAmortizacion;
    }
}
