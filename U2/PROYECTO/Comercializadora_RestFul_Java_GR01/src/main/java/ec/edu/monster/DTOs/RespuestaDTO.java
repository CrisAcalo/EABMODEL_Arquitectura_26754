package ec.edu.monster.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO genérico para respuestas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaDTO {
    private Boolean exito;
    private String mensaje;
    private Object datos;

    public static RespuestaDTO exitoso(String mensaje) {
        return new RespuestaDTO(true, mensaje, null);
    }

    public static RespuestaDTO exitoso(String mensaje, Object datos) {
        return new RespuestaDTO(true, mensaje, datos);
    }

    public static RespuestaDTO error(String mensaje) {
        return new RespuestaDTO(false, mensaje, null);
    }
}
