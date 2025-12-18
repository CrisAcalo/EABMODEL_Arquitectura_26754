package ec.edu.monster.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.math.BigDecimal;

/**
 * Entidad DetalleFactura
 */
@Entity
@Table(name = "DetalleFactura")
@Data
@NoArgsConstructor
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DetalleId")
    private Integer detalleId;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "PrecioUnitario", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "Subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    // Relación con Factura
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FacturaId", nullable = false)
    @ToString.Exclude
    private Factura factura;

    // Relación con Producto
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductoId", nullable = false)
    private Producto producto;
}
