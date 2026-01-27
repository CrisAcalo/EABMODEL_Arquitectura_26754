package ec.edu.monster.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad Cuenta bancaria
 */
@Entity
@Table(name = "Cuenta")
@Data
@NoArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CuentaId")
    private Long cuentaId;

    @Column(name = "NumeroCuenta", nullable = false, length = 20, unique = true)
    private String numeroCuenta;

    @Column(name = "Saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo;

    @Column(name = "TipoCuenta", length = 20)
    private String tipoCuenta;

    @Column(name = "Estado", length = 20)
    private String estado;

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteId", nullable = false)
    private Cliente cliente;

    // Relaciones
    @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY)
    private Set<Movimiento> movimientos = new HashSet<>();
}
