package ec.edu.monster.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO para cuota de amortización
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuotaAmortizacionDTO {
    private Integer numeroCuota;
    private BigDecimal valorCuota;
    private BigDecimal interes;
    private BigDecimal capitalPagado;
    private BigDecimal saldo;
}
