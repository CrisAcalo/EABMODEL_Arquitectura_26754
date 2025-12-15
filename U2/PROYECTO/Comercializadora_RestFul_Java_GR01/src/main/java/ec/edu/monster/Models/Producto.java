package ec.edu.monster.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad Producto
 */
@Entity
@Table(name = "Producto")
@Data
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductoId")
    private Integer productoId;

    @Column(name = "Codigo", nullable = false, length = 20, unique = true)
    private String codigo;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Descripcion", length = 500)
    private String descripcion;

    @Column(name = "Precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @Column(name = "Stock", nullable = false)
    private Integer stock;

    @Column(name = "Categoria", length = 50)
    private String categoria;

    @Column(name = "ImagenUrl", length = 500)
    private String imagenUrl;

    @Column(name = "FechaRegistro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "Estado", nullable = false, length = 20)
    private String estado;

    // Relación con DetalleFactura
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private Set<DetalleFactura> detallesFactura = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "ACTIVO";
        }
        if (stock == null) {
            stock = 0;
        }
    }
}
