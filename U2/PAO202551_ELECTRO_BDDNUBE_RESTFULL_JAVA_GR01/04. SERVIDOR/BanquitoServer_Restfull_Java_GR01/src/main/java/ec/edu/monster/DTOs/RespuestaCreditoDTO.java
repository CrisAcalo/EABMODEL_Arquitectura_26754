package ec.edu.monster.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para respuesta de otorgar crédito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaCreditoDTO {
    private Boolean exito;
    private String mensaje;
    private String cedula;
    private String numeroCredito;
    private BigDecimal montoCredito;
    private Integer numeroCuotas;
    private BigDecimal cuotaMensual;
    private BigDecimal tasaInteres;
    private List<CuotaAmortizacionDTO> tablaAmortizacion;
}
