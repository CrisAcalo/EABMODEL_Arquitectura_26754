package ec.edu.monster.dto;

import java.io.Serializable;

/**
 * DTO para respuesta de validación de sujeto de crédito
 * 
 * @author Javi
 */
public class ValidacionCreditoDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private boolean esSujetoCredito;
    private String mensaje;
    private String cedula;
    private String nombreCliente;
    
    // Constructores
    public ValidacionCreditoDTO() {
    }
    
    public ValidacionCreditoDTO(boolean esSujetoCredito, String mensaje) {
        this.esSujetoCredito = esSujetoCredito;
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    
    public boolean isEsSujetoCredito() {
        return esSujetoCredito;
    }
    
    public void setEsSujetoCredito(boolean esSujetoCredito) {
        this.esSujetoCredito = esSujetoCredito;
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
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
}
