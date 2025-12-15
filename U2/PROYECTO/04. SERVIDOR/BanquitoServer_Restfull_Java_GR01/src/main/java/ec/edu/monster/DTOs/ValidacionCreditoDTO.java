package ec.edu.monster.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuesta de validación de sujeto de crédito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidacionCreditoDTO {
    private Boolean esValido;
    private String mensaje;
    private String cedula;
    private String nombreCompleto;
}
