package ec.edu.monster.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Factura
 * Representa una factura de venta en el sistema
 */
@Entity
@Table(name = "Factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factura_id")
    private Integer facturaId;
    
    @Column(name = "numero_factura", length = 20, nullable = false, unique = true)
    private String numeroFactura;
    
    @Column(name = "cedula_cliente", length = 10, nullable = false)
    private String cedulaCliente;
    
    @Column(name = "nombre_cliente", length = 200, nullable = false)
    private String nombreCliente;
    
    @Column(name = "forma_pago", length = 20, nullable = false)
    private String formaPago; // EFECTIVO o CREDITO
    
    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;
    
    @Column(name = "descuento", precision = 12, scale = 2)
    private BigDecimal descuento;
    
    @Column(name = "total", precision = 12, scale = 2, nullable = false)
    private BigDecimal total;
    
    @Column(name = "numero_credito", length = 20)
    private String numeroCredito; // Solo si es crédito
    
    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision;
    
    // Relación con DetalleFactura
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
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
}
