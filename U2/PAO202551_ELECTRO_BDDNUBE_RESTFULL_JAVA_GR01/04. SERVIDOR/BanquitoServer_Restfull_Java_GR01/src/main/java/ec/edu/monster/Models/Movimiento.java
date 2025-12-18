package ec.edu.monster.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad Movimiento bancario
 */
@Entity
@Table(name = "Movimiento")
@Data
@NoArgsConstructor
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MovimientoId")
    private Long movimientoId;

    @Column(name = "TipoMovimiento", nullable = false, length = 20)
    private String tipoMovimiento;

    @Column(name = "Monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Column(name = "FechaMovimiento", nullable = false)
    private LocalDateTime fechaMovimiento;

    @Column(name = "SaldoAnterior", precision = 12, scale = 2)
    private BigDecimal saldoAnterior;

    @Column(name = "SaldoNuevo", precision = 12, scale = 2)
    private BigDecimal saldoNuevo;

    @Column(name = "Descripcion", length = 200)
    private String descripcion;

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CuentaId", nullable = false)
    private Cuenta cuenta;
}
