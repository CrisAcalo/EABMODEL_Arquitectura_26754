package ec.edu.monster.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Credito para el módulo de créditos
 */
@Entity
@Table(name = "credito")
@NamedQueries({
    @NamedQuery(name = "Credito.findActivosByCedula",
                query = "SELECT c FROM Credito c WHERE c.cliente.cedula = :cedula " +
                        "AND c.estado = 'ACTIVO'"),
    @NamedQuery(name = "Credito.findByCedula",
                query = "SELECT c FROM Credito c WHERE c.cliente.cedula = :cedula " +
                        "ORDER BY c.fechaOtorgamiento DESC")
})
public class Credito implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credito_id")
    private Long creditoId;
    
    @Column(name = "numero_credito", nullable = false, unique = true, length = 20)
    private String numeroCredito;
    
    @Column(name = "monto_credito", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoCredito;
    
    @Column(name = "tasa_interes", nullable = false, precision = 5, scale = 4)
    private BigDecimal tasaInteres; // Tasa anual (ej: 0.1600 para 16%)
    
    @Column(name = "numero_cuotas", nullable = false)
    private Integer numeroCuotas;
    
    @Column(name = "cuota_mensual", nullable = false, precision = 12, scale = 2)
    private BigDecimal cuotaMensual;
    
    @Column(name = "fecha_otorgamiento", nullable = false)
    private LocalDate fechaOtorgamiento;
    
    @Column(name = "estado", nullable = false, length = 20)
    private String estado; // ACTIVO, PAGADO, CANCELADO
    
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @OneToMany(mappedBy = "credito", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("numeroCuota ASC")
    private List<CuotaAmortizacion> cuotasAmortizacion = new ArrayList<>();
    
    // Constructores
    public Credito() {
    }
    
    public Credito(String numeroCredito, BigDecimal montoCredito, BigDecimal tasaInteres, 
                   Integer numeroCuotas, BigDecimal cuotaMensual, Cliente cliente) {
        this.numeroCredito = numeroCredito;
        this.montoCredito = montoCredito;
        this.tasaInteres = tasaInteres;
        this.numeroCuotas = numeroCuotas;
        this.cuotaMensual = cuotaMensual;
        this.cliente = cliente;
        this.fechaOtorgamiento = LocalDate.now();
        this.estado = "ACTIVO";
    }
    
    // Getters y Setters
    public Long getCreditoId() {
        return creditoId;
    }
    
    public void setCreditoId(Long creditoId) {
        this.creditoId = creditoId;
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
    
    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }
    
    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
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
    
    public LocalDate getFechaOtorgamiento() {
        return fechaOtorgamiento;
    }
    
    public void setFechaOtorgamiento(LocalDate fechaOtorgamiento) {
        this.fechaOtorgamiento = fechaOtorgamiento;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public List<CuotaAmortizacion> getCuotasAmortizacion() {
        return cuotasAmortizacion;
    }
    
    public void setCuotasAmortizacion(List<CuotaAmortizacion> cuotasAmortizacion) {
        this.cuotasAmortizacion = cuotasAmortizacion;
    }
    
    public void addCuotaAmortizacion(CuotaAmortizacion cuota) {
        cuotasAmortizacion.add(cuota);
        cuota.setCredito(this);
    }
    
    @Override
    public String toString() {
        return "Credito{" +
                "creditoId=" + creditoId +
                ", numeroCredito='" + numeroCredito + '\'' +
                ", montoCredito=" + montoCredito +
                ", tasaInteres=" + tasaInteres +
                ", numeroCuotas=" + numeroCuotas +
                ", estado='" + estado + '\'' +
                '}';
    }
}
