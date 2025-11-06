package ec.edu.monster.model;

/**
 * Modelo para el resultado de una conversión REST
 * @author YourName
 */
public class ConversionResult {
    private boolean exitoso;
    private ResultadoConversion resultado;
    private ErrorConversion error;
    
    // Getters y Setters
    public boolean isExitoso() {
        return exitoso;
    }
    
    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }
    
    public ResultadoConversion getResultado() {
        return resultado;
    }
    
    public void setResultado(ResultadoConversion resultado) {
        this.resultado = resultado;
    }
    
    public ErrorConversion getError() {
        return error;
    }
    
    public void setError(ErrorConversion error) {
        this.error = error;
    }
}