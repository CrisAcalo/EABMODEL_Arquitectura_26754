package ec.edu.monster.rest.dto;

/**
 * DTO para la respuesta de validación de sujeto de crédito
 */
public class ValidacionCreditoDTO {
    private boolean esValido;
    private String mensaje;
    private String cedula;
    private String nombreCompleto;
    
    public ValidacionCreditoDTO() {
    }
    
    public ValidacionCreditoDTO(boolean esValido, String mensaje) {
        this.esValido = esValido;
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    public boolean isEsValido() {
        return esValido;
    }
    
    public void setEsValido(boolean esValido) {
        this.esValido = esValido;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
