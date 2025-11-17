package ec.edu.monster.dto.banquito;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para respuesta de crédito otorgado desde BanQuito
 */
public class RespuestaCreditoDTO {
    
    private Boolean exito;
    private String mensaje;
    private String cedula;
    private String numeroCredito;
    private BigDecimal montoCredito;
    private Integer numeroCuotas;
    private BigDecimal cuotaMensual;
    private BigDecimal tasaInteres;
    private List<CuotaAmortizacionDTO> tablaAmortizacion;
    
    public RespuestaCreditoDTO() {
    }
    
    public RespuestaCreditoDTO(Boolean exito, String mensaje, String cedula, String numeroCredito, 
                                BigDecimal montoCredito, Integer numeroCuotas, BigDecimal cuotaMensual, 
                                BigDecimal tasaInteres, List<CuotaAmortizacionDTO> tablaAmortizacion) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.cedula = cedula;
        this.numeroCredito = numeroCredito;
        this.montoCredito = montoCredito;
        this.numeroCuotas = numeroCuotas;
        this.cuotaMensual = cuotaMensual;
        this.tasaInteres = tasaInteres;
        this.tablaAmortizacion = tablaAmortizacion;
    }
    
    public Boolean getExito() {
        return exito;
    }
    
    public void setExito(Boolean exito) {
        this.exito = exito;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String getNumeroCredito() {
        return numeroCredito;
    }
    
    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
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
