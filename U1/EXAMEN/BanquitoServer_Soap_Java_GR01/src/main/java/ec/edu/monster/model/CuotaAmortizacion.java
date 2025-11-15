package ec.edu.monster.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidad CuotaAmortizacion para la tabla de amortización del crédito
 */
@Entity
@Table(name = "cuota_amortizacion")
public class CuotaAmortizacion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuota_id")
    private Long cuotaId;
    
    @Column(name = "numero_cuota", nullable = false)
    private Integer numeroCuota;
    
    @Column(name = "valor_cuota", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorCuota;
    
    @Column(name = "interes", nullable = false, precision = 12, scale = 2)
    private BigDecimal interes;
    
    @Column(name = "capital_pagado", nullable = false, precision = 12, scale = 2)
    private BigDecimal capitalPagado;
    
    @Column(name = "saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credito_id", nullable = false)
    private Credito credito;
    
    // Constructores
    public CuotaAmortizacion() {
    }
    
    public CuotaAmortizacion(Integer numeroCuota, BigDecimal valorCuota, BigDecimal interes,
                             BigDecimal capitalPagado, BigDecimal saldo) {
        this.numeroCuota = numeroCuota;
        this.valorCuota = valorCuota;
        this.interes = interes;
        this.capitalPagado = capitalPagado;
        this.saldo = saldo;
    }
    
    // Getters y Setters
    public Long getCuotaId() {
        return cuotaId;
    }
    
    public void setCuotaId(Long cuotaId) {
        this.cuotaId = cuotaId;
    }
    
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
    
    public Credito getCredito() {
        return credito;
    }
    
    public void setCredito(Credito credito) {
        this.credito = credito;
    }
    
    @Override
    public String toString() {
        return "CuotaAmortizacion{" +
                "cuotaId=" + cuotaId +
                ", numeroCuota=" + numeroCuota +
                ", valorCuota=" + valorCuota +
                ", interes=" + interes +
                ", capitalPagado=" + capitalPagado +
                ", saldo=" + saldo +
                '}';
    }
}
