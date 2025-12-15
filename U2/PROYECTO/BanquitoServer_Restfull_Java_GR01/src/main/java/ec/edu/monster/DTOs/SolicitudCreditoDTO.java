package ec.edu.monster.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para solicitud de crédito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudCreditoDTO {
    private String cedula;
    private String precioElectrodomestico;
    private String numeroCuotas;
}
