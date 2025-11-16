package ec.edu.monster.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO para una fila de la tabla de amortización
 * 
 * @author Javi
 */
public class FilaAmortizacionDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer numeroCuota;
    private BigDecimal valorCuota;
    private BigDecimal interesPagado;
    private BigDecimal capitalPagado;
    private BigDecimal saldo;
    
    // Constructores
    public FilaAmortizacionDTO() {
    }
    
    public FilaAmortizacionDTO(Integer numeroCuota, BigDecimal valorCuota, 
                               BigDecimal interesPagado, BigDecimal capitalPagado, 
                               BigDecimal saldo) {
        this.numeroCuota = numeroCuota;
        this.valorCuota = valorCuota;
        this.interesPagado = interesPagado;
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
    
    public BigDecimal getInteresPagado() {
        return interesPagado;
    }
    
    public void setInteresPagado(BigDecimal interesPagado) {
        this.interesPagado = interesPagado;
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
    
    @Override
    public String toString() {
        return "FilaAmortizacionDTO{" +
                "numeroCuota=" + numeroCuota +
                ", valorCuota=" + valorCuota +
                ", interes=" + interesPagado +
                ", capital=" + capitalPagado +
                ", saldo=" + saldo +
                '}';
    }
}
