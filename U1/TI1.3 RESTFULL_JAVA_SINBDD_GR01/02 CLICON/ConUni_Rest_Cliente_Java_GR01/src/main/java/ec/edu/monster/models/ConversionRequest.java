package ec.edu.monster.models;

/**
 * Modelo para enviar solicitudes de conversión al servidor REST
 * Representa el JSON que se envía en el body del POST
 * 
 * Ejemplo JSON:
 * {
 *   "valor": "100",
 *   "unidadOrigen": "Metro",
 *   "unidadDestino": "Milla"
 * }
 * 
 * @author jeffe
 */
public class ConversionRequest {
    
    private String valor;
    private String unidadOrigen;
    private String unidadDestino;
    
    // Constructor vacío (requerido para Gson)
    public ConversionRequest() {
    }
    
    // Constructor con parámetros
    public ConversionRequest(String valor, String unidadOrigen, String unidadDestino) {
        this.valor = valor;
        this.unidadOrigen = unidadOrigen;
        this.unidadDestino = unidadDestino;
    }
    
    // Getters y Setters
    public String getValor() {
        return valor;
    }
    
    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public String getUnidadOrigen() {
        return unidadOrigen;
    }
    
    public void setUnidadOrigen(String unidadOrigen) {
        this.unidadOrigen = unidadOrigen;
    }
    
    public String getUnidadDestino() {
        return unidadDestino;
    }
    
    public void setUnidadDestino(String unidadDestino) {
        this.unidadDestino = unidadDestino;
    }
    
    @Override
    public String toString() {
        return "ConversionRequest{" +
               "valor='" + valor + '\'' +
               ", unidadOrigen='" + unidadOrigen + '\'' +
               ", unidadDestino='" + unidadDestino + '\'' +
               '}';
    }
}