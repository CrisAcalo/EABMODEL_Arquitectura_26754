package ec.edu.monster.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad Tabla de Amortización (Detalle de cuotas del crédito)
 * 
 * @author Javi
 */
@Entity
@Table(name = "tabla_amortizacion")
public class TablaAmortizacion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_cuota", nullable = false)
    private Integer numeroCuota;
    
    @Column(name = "valor_cuota", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorCuota;
    
    @Column(name = "interes_pagado", nullable = false, precision = 12, scale = 2)
    private BigDecimal interesPagado;
    
    @Column(name = "capital_pagado", nullable = false, precision = 12, scale = 2)
    private BigDecimal capitalPagado;
    
    @Column(name = "saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo;
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    @Column(name = "estado", length = 20)
    private String estado = "PENDIENTE"; // PENDIENTE, PAGADO
    
    @Column(name = "fecha_pago")
    private LocalDate fechaPago;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credito_id", nullable = false)
    private Credito credito;
    
    // Constructores
    public TablaAmortizacion() {
    }
    
    public TablaAmortizacion(Integer numeroCuota, BigDecimal valorCuota, 
                             BigDecimal interesPagado, BigDecimal capitalPagado, 
                             BigDecimal saldo) {
        this.numeroCuota = numeroCuota;
        this.valorCuota = valorCuota;
        this.interesPagado = interesPagado;
        this.capitalPagado = capitalPagado;
        this.saldo = saldo;
    }
    
    // Métodos de utilidad
    
    /**
     * Verifica si la cuota está pagada
     */
    public boolean estaPagada() {
        return "PAGADO".equals(this.estado);
    }
    
    /**
     * Verifica si la cuota está pendiente
     */
    public boolean estaPendiente() {
        return "PENDIENTE".equals(this.estado);
    }
    
    /**
     * Marca la cuota como pagada
     */
    public void marcarComoPagada() {
        this.estado = "PAGADO";
        this.fechaPago = LocalDate.now();
    }
    
    // Getters y Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public LocalDate getFechaPago() {
        return fechaPago;
    }
    
    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    public Credito getCredito() {
        return credito;
    }
    
    public void setCredito(Credito credito) {
        this.credito = credito;
    }
    
    @Override
    public String toString() {
        return "TablaAmortizacion{" +
                "numeroCuota=" + numeroCuota +
                ", valorCuota=" + valorCuota +
                ", interesPagado=" + interesPagado +
                ", capitalPagado=" + capitalPagado +
                ", saldo=" + saldo +
                ", estado='" + estado + '\'' +
                '}';
    }
}
