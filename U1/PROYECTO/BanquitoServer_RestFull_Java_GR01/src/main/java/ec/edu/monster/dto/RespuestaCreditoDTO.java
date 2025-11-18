package ec.edu.monster.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para la respuesta de otorgamiento de crédito
 * 
 * @author Javi
 */
public class RespuestaCreditoDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private boolean aprobado;
    private String mensaje;
    private String numeroCredito;
    private String cedula;
    private String nombreCliente;
    private BigDecimal montoCredito;
    private Integer numeroCuotas;
    private BigDecimal cuotaMensual;
    private BigDecimal tasaInteresAnual;
    private BigDecimal totalAPagar;
    private BigDecimal totalIntereses;
    private List<FilaAmortizacionDTO> tablaAmortizacion = new ArrayList<>();
    
    // Constructores
    public RespuestaCreditoDTO() {
    }
    
    public RespuestaCreditoDTO(boolean aprobado, String mensaje) {
        this.aprobado = aprobado;
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    
    public boolean isAprobado() {
        return aprobado;
    }
    
    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
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
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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
    
    public BigDecimal getTasaInteresAnual() {
        return tasaInteresAnual;
    }
    
    public void setTasaInteresAnual(BigDecimal tasaInteresAnual) {
        this.tasaInteresAnual = tasaInteresAnual;
    }
    
    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }
    
    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }
    
    public BigDecimal getTotalIntereses() {
        return totalIntereses;
    }
    
    public void setTotalIntereses(BigDecimal totalIntereses) {
        this.totalIntereses = totalIntereses;
    }
    
    public List<FilaAmortizacionDTO> getTablaAmortizacion() {
        return tablaAmortizacion;
    }
    
    public void setTablaAmortizacion(List<FilaAmortizacionDTO> tablaAmortizacion) {
        this.tablaAmortizacion = tablaAmortizacion;
    }
}
