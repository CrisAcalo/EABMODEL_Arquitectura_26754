package ec.edu.monster.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para respuesta de Producto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Integer productoId;
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String categoria;
    private String imagenUrl;
    private LocalDateTime fechaRegistro;
    private String estado;
}
