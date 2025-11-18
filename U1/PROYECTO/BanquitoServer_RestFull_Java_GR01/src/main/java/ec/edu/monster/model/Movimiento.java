package ec.edu.monster.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad Movimiento (Transacciones de depósito/retiro)
 */
@Entity
@Table(name = "movimiento")
@NamedQueries({
    @NamedQuery(name = "Movimiento.findByClienteAndPeriodo", 
                query = "SELECT m FROM Movimiento m WHERE m.cuenta.cliente.cedula = :cedula " +
                        "AND m.fechaMovimiento BETWEEN :fechaInicio AND :fechaFin " +
                        "ORDER BY m.fechaMovimiento DESC"),
    @NamedQuery(name = "Movimiento.findDepositosByClienteAndPeriodo",
                query = "SELECT m FROM Movimiento m WHERE m.cuenta.cliente.cedula = :cedula " +
                        "AND m.tipoMovimiento = 'DEPOSITO' " +
                        "AND m.fechaMovimiento BETWEEN :fechaInicio AND :fechaFin"),
    @NamedQuery(name = "Movimiento.findRetirosByClienteAndPeriodo",
                query = "SELECT m FROM Movimiento m WHERE m.cuenta.cliente.cedula = :cedula " +
                        "AND m.tipoMovimiento = 'RETIRO' " +
                        "AND m.fechaMovimiento BETWEEN :fechaInicio AND :fechaFin")
})
public class Movimiento implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private Long movimientoId;
    
    @Column(name = "tipo_movimiento", nullable = false, length = 20)
    private String tipoMovimiento; // DEPOSITO, RETIRO
    
    @Column(name = "monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;
    
    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDateTime fechaMovimiento;
    
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    
    @Column(name = "saldo_anterior", precision = 12, scale = 2)
    private BigDecimal saldoAnterior;
    
    @Column(name = "saldo_nuevo", precision = 12, scale = 2)
    private BigDecimal saldoNuevo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;
    
    // Constructores
    public Movimiento() {
    }
    
    public Movimiento(String tipoMovimiento, BigDecimal monto, String descripcion, Cuenta cuenta) {
        this.tipoMovimiento = tipoMovimiento;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuenta = cuenta;
        this.fechaMovimiento = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getMovimientoId() {
        return movimientoId;
    }
    
    public void setMovimientoId(Long movimientoId) {
        this.movimientoId = movimientoId;
    }
    
    public String getTipoMovimiento() {
        return tipoMovimiento;
    }
    
    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
    
    public BigDecimal getMonto() {
        return monto;
    }
    
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }
    
    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }
    
    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }
    
    public BigDecimal getSaldoNuevo() {
        return saldoNuevo;
    }
    
    public void setSaldoNuevo(BigDecimal saldoNuevo) {
        this.saldoNuevo = saldoNuevo;
    }
    
    public Cuenta getCuenta() {
        return cuenta;
    }
    
    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    @Override
    public String toString() {
        return "Movimiento{" +
                "movimientoId=" + movimientoId +
                ", tipoMovimiento='" + tipoMovimiento + '\'' +
                ", monto=" + monto +
                ", fechaMovimiento=" + fechaMovimiento +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
