package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 * Respuesta estándar para operaciones
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class RespuestaDTO {

    @JsonbProperty("Exitoso")
    private boolean exitoso;

    @JsonbProperty("Mensaje")
    private String mensaje;

    @JsonbProperty("CodigoError")
    private String codigoError;

    @JsonbProperty("Datos")
    private Object datos;

    // Constructores
    public RespuestaDTO() {
    }

    public RespuestaDTO(boolean exitoso, String mensaje) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
    }

    public RespuestaDTO(boolean exitoso, String mensaje, Object datos) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.datos = datos;
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
