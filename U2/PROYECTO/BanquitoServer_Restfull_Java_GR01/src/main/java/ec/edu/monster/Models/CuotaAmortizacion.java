package ec.edu.monster.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Entidad Cuota de Amortización
 */
@Entity
@Table(name = "CuotaAmortizacion")
@Data
@NoArgsConstructor
public class CuotaAmortizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CuotaId")
    private Long cuotaId;

    @Column(name = "NumeroCuota", nullable = false)
    private Integer numeroCuota;

    @Column(name = "ValorCuota", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorCuota;

    @Column(name = "Interes", nullable = false, precision = 12, scale = 2)
    private BigDecimal interes;

    @Column(name = "CapitalPagado", nullable = false, precision = 12, scale = 2)
    private BigDecimal capitalPagado;

    @Column(name = "Saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo;

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CreditoId", nullable = false)
    private Credito credito;
}
