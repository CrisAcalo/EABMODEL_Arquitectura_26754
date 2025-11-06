package ec.edu.monster.model;

/**
 * Modelo para los datos de resultado de conversión
 * @author YourName
 */
public class ResultadoConversion {
    private double valorOriginal;
    private double valorConvertidoExacto;
    private double valorConvertidoRedondeado;
    private String unidadOrigen;
    private String unidadDestino;
    private String tipoConversion;
    private double factorConversion;
    private String fechaConversion;
    
    // Getters y Setters
    public double getValorOriginal() {
        return valorOriginal;
    }
    
    public void setValorOriginal(double valorOriginal) {
        this.valorOriginal = valorOriginal;
    }
    
    public double getValorConvertidoExacto() {
        return valorConvertidoExacto;
    }
    
    public void setValorConvertidoExacto(double valorConvertidoExacto) {
        this.valorConvertidoExacto = valorConvertidoExacto;
    }
    
    public double getValorConvertidoRedondeado() {
        return valorConvertidoRedondeado;
    }
    
    public void setValorConvertidoRedondeado(double valorConvertidoRedondeado) {
        this.valorConvertidoRedondeado = valorConvertidoRedondeado;
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
    
    public String getTipoConversion() {
        return tipoConversion;
    }
    
    public void setTipoConversion(String tipoConversion) {
        this.tipoConversion = tipoConversion;
    }
    
    public double getFactorConversion() {
        return factorConversion;
    }
    
    public void setFactorConversion(double factorConversion) {
        this.factorConversion = factorConversion;
    }
    
    public String getFechaConversion() {
        return fechaConversion;
    }
    
    public void setFechaConversion(String fechaConversion) {
        this.fechaConversion = fechaConversion;
    }
}