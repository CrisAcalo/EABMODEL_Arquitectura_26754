package ec.edu.monster.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO para detalle en cálculo de factura
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCalculoDTO {
    private Integer productoId;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
