package ec.edu.monster.dto.banquito;

/**
 * DTO para respuesta de validación de crédito desde BanQuito
 */
public class ValidacionCreditoDTO {
    
    private Boolean esValido;
    private String mensaje;
    private String cedula;
    private String nombreCompleto;
    
    public ValidacionCreditoDTO() {
    }
    
    public ValidacionCreditoDTO(Boolean esValido, String mensaje, String cedula, String nombreCompleto) {
        this.esValido = esValido;
        this.mensaje = mensaje;
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
    }
    
    public Boolean getEsValido() {
        return esValido;
    }
    
    public void setEsValido(Boolean esValido) {
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
