package ec.edu.monster.ms_cuentas.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import ec.edu.monster.ms_cuentas.models.Cliente;
import ec.edu.monster.ms_cuentas.models.Cuenta;
import ec.edu.monster.ms_cuentas.models.Moneda;
import ec.edu.monster.ms_cuentas.models.Sucursal;
import lombok.Data;

@XmlRootElement(name = "respuesta")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ Cliente.class, Cuenta.class, Moneda.class, Sucursal.class })
@Data
public class RespuestaDTO {
    private boolean exitoso;
    private String mensaje;
    private String codigoError;

    @XmlElement(nillable = true)
    private Object datos;

    public static RespuestaDTO exito(String mensaje, Object datos) {
        RespuestaDTO r = new RespuestaDTO();
        r.setExitoso(true);
        r.setMensaje(mensaje);
        r.setDatos(datos);
        return r;
    }

    public static RespuestaDTO error(String mensaje, String codigoError) {
        RespuestaDTO r = new RespuestaDTO();
        r.setExitoso(false);
        r.setMensaje(mensaje);
        r.setCodigoError(codigoError);
        return r;
    }
}
