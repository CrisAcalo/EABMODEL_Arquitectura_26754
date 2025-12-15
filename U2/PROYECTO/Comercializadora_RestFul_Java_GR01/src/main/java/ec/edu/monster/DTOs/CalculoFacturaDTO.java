package ec.edu.monster.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para respuesta de cálculo de factura
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculoFacturaDTO {
    private Boolean exitoso;
    private String mensaje;
    private BigDecimal total;
    private List<DetalleCalculoDTO> detalles;
}
