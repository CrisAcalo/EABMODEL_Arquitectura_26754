package ec.edu.monster.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO para respuesta de monto máximo de crédito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MontoMaximoCreditoDTO {
    private String cedula;
    private BigDecimal montoMaximo;
    private BigDecimal promedioDepositos;
    private BigDecimal promedioRetiros;
    private String mensaje;
}
