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
 * Entidad Producto
 * Representa un producto/electrodoméstico en el sistema de comercialización
 */
@Entity
@Table(name = "Producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Integer productoId;
    
    @Column(name = "codigo", length = 20, nullable = false, unique = true)
    private String codigo;
    
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "precio", precision = 12, scale = 2, nullable = false)
    private BigDecimal precio;
    
    @Column(name = "stock", nullable = false)
    private Integer stock;
    
    @Column(name = "categoria", length = 50)
    private String categoria;
    
    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;
    
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
    
    @Column(name = "estado", length = 20, nullable = false)
    private String estado;
    
    // Relación con DetalleFactura
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleFactura> detallesFactura = new ArrayList<>();
    
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
