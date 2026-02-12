package ec.edu.monster.ms_auth.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ec.edu.monster.ms_auth.models.Empleado;
import ec.edu.monster.ms_auth.models.Sucursal;
import ec.edu.monster.ms_auth.models.Ventanilla;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ Empleado.class, Sucursal.class, Ventanilla.class })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaDTO {

    private boolean exitoso;
    private String mensaje;
    private String codigoError;
    private Object datos;

    public static RespuestaDTO exito(String mensaje, Object datos) {
        return new RespuestaDTO(true, mensaje, null, datos);
    }

    public static RespuestaDTO exito(String mensaje) {
        return new RespuestaDTO(true, mensaje, null, null);
    }

    public static RespuestaDTO error(String mensaje, String codigoError) {
        return new RespuestaDTO(false, mensaje, codigoError, null);
    }
}
