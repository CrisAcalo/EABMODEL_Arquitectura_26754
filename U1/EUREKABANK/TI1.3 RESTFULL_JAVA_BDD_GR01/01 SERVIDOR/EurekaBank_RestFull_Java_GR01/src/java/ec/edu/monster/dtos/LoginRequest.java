package ec.edu.monster.dtos;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 * DTO para request de login
 * Propiedades en PascalCase para compatibilidad con API .NET
 *
 * @author EurekaBank
 */
public class LoginRequest {

    @JsonbProperty("Usuario")
    private String usuario;

    @JsonbProperty("Clave")
    private String clave;

    // Constructores
    public LoginRequest() {
    }

    public LoginRequest(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
