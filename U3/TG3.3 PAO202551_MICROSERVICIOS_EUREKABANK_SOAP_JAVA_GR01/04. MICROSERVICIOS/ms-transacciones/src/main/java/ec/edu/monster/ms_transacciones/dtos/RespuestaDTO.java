package ec.edu.monster.ms_transacciones.dtos;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class RespuestaDTO {
    private boolean exitoso;
    private String mensaje;
    private String codigoError;

    public static RespuestaDTO exito(String mensaje) {
        RespuestaDTO r = new RespuestaDTO();
        r.setExitoso(true);
        r.setMensaje(mensaje);
        return r;
    }

    public static RespuestaDTO error(String mensaje) {
        RespuestaDTO r = new RespuestaDTO();
        r.setExitoso(false);
        r.setMensaje(mensaje);
        r.setCodigoError("TRX001");
        return r;
    }
}
