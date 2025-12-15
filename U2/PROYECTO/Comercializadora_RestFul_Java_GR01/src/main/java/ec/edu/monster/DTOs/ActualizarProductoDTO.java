package ec.edu.monster.DTOs;

import lombok.Data;
import java.math.BigDecimal;

/**
 * DTO para actualizar un producto (PATCH)
 */
@Data
public class ActualizarProductoDTO {
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String categoria;
    private String imagenUrl;
    private String estado;
}
