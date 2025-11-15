package ec.edu.monster.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Cuenta del sistema CORE
 */
@Entity
@Table(name = "cuenta")
public class Cuenta implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Long cuentaId;
    
    @Column(name = "numero_cuenta", nullable = false, unique = true, length = 20)
    private String numeroCuenta;
    
    @Column(name = "tipo_cuenta", nullable = false, length = 20)
    private String tipoCuenta; // AHORROS, CORRIENTE
    
    @Column(name = "saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo;
    
    @Column(name = "fecha_apertura", nullable = false)
    private LocalDate fechaApertura;
    
    @Column(name = "estado", nullable = false, length = 20)
    private String estado; // ACTIVA, INACTIVA, BLOQUEADA
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movimiento> movimientos = new ArrayList<>();
    
    // Constructores
    public Cuenta() {
    }
    
    public Cuenta(String numeroCuenta, String tipoCuenta, BigDecimal saldo, Cliente cliente) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.cliente = cliente;
        this.fechaApertura = LocalDate.now();
        this.estado = "ACTIVA";
    }
    
    // Getters y Setters
    public Long getCuentaId() {
        return cuentaId;
    }
    
    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }
    
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    
    public String getTipoCuenta() {
        return tipoCuenta;
    }
    
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    
    public BigDecimal getSaldo() {
        return saldo;
    }
    
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    public LocalDate getFechaApertura() {
        return fechaApertura;
    }
    
    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public List<Movimiento> getMovimientos() {
        return movimientos;
    }
    
    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
    
    @Override
    public String toString() {
        return "Cuenta{" +
                "cuentaId=" + cuentaId +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", saldo=" + saldo +
                ", estado='" + estado + '\'' +
                '}';
    }
}
