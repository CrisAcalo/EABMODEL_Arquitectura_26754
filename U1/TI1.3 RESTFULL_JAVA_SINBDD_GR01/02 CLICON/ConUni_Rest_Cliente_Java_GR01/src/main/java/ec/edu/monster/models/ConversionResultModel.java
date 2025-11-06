package ec.edu.monster.models;

/**
 * Modelo para recibir resultados de conversión del servidor REST
 * Representa el JSON que se recibe como respuesta
 * 
 * Ejemplo JSON exitoso:
 * {
 *   "exitoso": true,
 *   "resultado": {
 *     "valorOriginal": 100.0,
 *     "valorConvertidoExacto": 0.0621371192,
 *     "valorConvertidoRedondeado": 0.062,
 *     "unidadOrigen": "Metro",
 *     "unidadDestino": "Milla",
 *     "factorConversion": 0.000621371192
 *   },
 *   "error": null
 * }
 * 
 * @author jeffe
 */
public class ConversionResultModel {
    
    private boolean exitoso;
    private UnidadConversion resultado;
    private ConversionError error;
    
    // Constructor vacío
    public ConversionResultModel() {
    }
    
    // Getters y Setters
    public boolean isExitoso() {
        return exitoso;
    }
    
    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }
    
    public UnidadConversion getResultado() {
        return resultado;
    }
    
    public void setResultado(UnidadConversion resultado) {
        this.resultado = resultado;
    }
    
    public ConversionError getError() {
        return error;
    }
    
    public void setError(ConversionError error) {
        this.error = error;
    }
    
    @Override
    public String toString() {
        return "ConversionResultModel{" +
               "exitoso=" + exitoso +
               ", resultado=" + resultado +
               ", error=" + error +
               '}';
    }
}