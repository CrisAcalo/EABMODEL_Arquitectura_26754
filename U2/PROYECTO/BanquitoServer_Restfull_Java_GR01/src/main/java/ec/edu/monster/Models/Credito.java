package ec.edu.monster.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Crédito
 */
@Entity
@Table(name = "Credito")
@Data
@NoArgsConstructor
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CreditoId")
    private Long creditoId;

    @Column(name = "NumeroCredito", nullable = false, length = 20, unique = true)
    private String numeroCredito;

    @Column(name = "MontoCredito", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoCredito;

    @Column(name = "TasaInteres", nullable = false, precision = 5, scale = 4)
    private BigDecimal tasaInteres;

    @Column(name = "NumeroCuotas", nullable = false)
    private Integer numeroCuotas;

    @Column(name = "CuotaMensual", nullable = false, precision = 12, scale = 2)
    private BigDecimal cuotaMensual;

    @Column(name = "FechaOtorgamiento", nullable = false)
    private LocalDateTime fechaOtorgamiento;

    @Column(name = "Estado", nullable = false, length = 20)
    private String estado = "ACTIVO";

    @Column(name = "Descripcion", length = 200)
    private String descripcion;

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteId", nullable = false)
    private Cliente cliente;

    // Relación con cuotas de amortización
    @OneToMany(mappedBy = "credito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CuotaAmortizacion> cuotasAmortizacion = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (fechaOtorgamiento == null) {
            fechaOtorgamiento = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "ACTIVO";
        }
    }
}
