package ec.edu.monster.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Factura
 */
@Entity
@Table(name = "Factura")
@Data
@NoArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FacturaId")
    private Integer facturaId;

    @Column(name = "NumeroFactura", nullable = false, length = 20, unique = true)
    private String numeroFactura;

    @Column(name = "CedulaCliente", nullable = false, length = 10)
    private String cedulaCliente;

    @Column(name = "NombreCliente", nullable = false, length = 200)
    private String nombreCliente;

    @Column(name = "FormaPago", nullable = false, length = 20)
    private String formaPago;

    @Column(name = "Subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "Descuento", nullable = false, precision = 12, scale = 2)
    private BigDecimal descuento;

    @Column(name = "Total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Column(name = "NumeroCredito", length = 20)
    private String numeroCredito;

    @Column(name = "FechaEmision", nullable = false)
    private LocalDateTime fechaEmision;

    // Relación con DetalleFactura
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleFactura> detalles = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (fechaEmision == null) {
            fechaEmision = LocalDateTime.now();
        }
        if (descuento == null) {
            descuento = BigDecimal.ZERO;
        }
    }

    public void addDetalle(DetalleFactura detalle) {
        detalles.add(detalle);
        detalle.setFactura(this);
    }
}
