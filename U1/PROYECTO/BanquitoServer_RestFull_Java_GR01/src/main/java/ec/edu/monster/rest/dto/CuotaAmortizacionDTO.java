package ec.edu.monster.rest.dto;

import java.math.BigDecimal;

/**
 * DTO para representar una cuota de la tabla de amortización
 */
public class CuotaAmortizacionDTO {
    private Integer numeroCuota;
    private BigDecimal valorCuota;
    private BigDecimal interes;
    private BigDecimal capitalPagado;
    private BigDecimal saldo;
    
    public CuotaAmortizacionDTO() {
    }
    
    public CuotaAmortizacionDTO(Integer numeroCuota, BigDecimal valorCuota, BigDecimal interes,
                                BigDecimal capitalPagado, BigDecimal saldo) {
        this.numeroCuota = numeroCuota;
        this.valorCuota = valorCuota;
        this.interes = interes;
        this.capitalPagado = capitalPagado;
        this.saldo = saldo;
    }
    
    // Getters y Setters
    public Integer getNumeroCuota() {
        return numeroCuota;
    }
    
    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }
    
    public BigDecimal getValorCuota() {
        return valorCuota;
    }
    
    public void setValorCuota(BigDecimal valorCuota) {
        this.valorCuota = valorCuota;
    }
    
    public BigDecimal getInteres() {
        return interes;
    }
    
    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }
    
    public BigDecimal getCapitalPagado() {
        return capitalPagado;
    }
    
    public void setCapitalPagado(BigDecimal capitalPagado) {
        this.capitalPagado = capitalPagado;
    }
    
    public BigDecimal getSaldo() {
        return saldo;
    }
    
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
