package ec.edu.monster.model;

/**
 * Modelo para errores de conversión
 * @author YourName
 */
public class ErrorConversion {
    private String mensaje;
    private String codigo;
    private String tipo;
    
    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}