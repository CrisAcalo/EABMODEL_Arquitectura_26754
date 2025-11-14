package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 * DTO para request de cambiar clave
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class CambiarClaveRequest {

    @JsonbProperty("Codigo")
    private String codigo;

    @JsonbProperty("ClaveActual")
    private String claveActual;

    @JsonbProperty("ClaveNueva")
    private String claveNueva;

    // Constructores
    public CambiarClaveRequest() {
    }

    public CambiarClaveRequest(String codigo, String claveActual, String claveNueva) {
        this.codigo = codigo;
        this.claveActual = claveActual;
        this.claveNueva = claveNueva;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }
}
