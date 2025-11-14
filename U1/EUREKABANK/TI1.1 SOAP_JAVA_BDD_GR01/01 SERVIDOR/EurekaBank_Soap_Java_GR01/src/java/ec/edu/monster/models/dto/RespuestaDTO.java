package ec.edu.monster.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;

/**
 * DTO estándar para respuestas de servicios SOAP
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({DepositoResultDTO.class, RetiroResultDTO.class, TransferenciaResultDTO.class})
public class RespuestaDTO implements Serializable {

    private boolean exitoso;
    private String mensaje;
    private String codigoError;
    private Object datos;

    // Constructor vacío
    public RespuestaDTO() {
    }

    // Constructor para respuesta exitosa
    public RespuestaDTO(boolean exitoso, String mensaje) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
    }

    // Constructor completo
    public RespuestaDTO(boolean exitoso, String mensaje, String codigoError, Object datos) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.codigoError = codigoError;
        this.datos = datos;
    }

    // Métodos estáticos de utilidad
    public static RespuestaDTO exito(String mensaje, Object datos) {
        return new RespuestaDTO(true, mensaje, null, datos);
    }

    public static RespuestaDTO exito(String mensaje) {
        return new RespuestaDTO(true, mensaje, null, null);
    }

    public static RespuestaDTO error(String mensaje, String codigoError) {
        return new RespuestaDTO(false, mensaje, codigoError, null);
    }

    public static RespuestaDTO error(String mensaje) {
        return new RespuestaDTO(false, mensaje, "ERROR", null);
    }

    // Getters y Setters
    public boolean isExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        this.datos = datos;
    }
}
