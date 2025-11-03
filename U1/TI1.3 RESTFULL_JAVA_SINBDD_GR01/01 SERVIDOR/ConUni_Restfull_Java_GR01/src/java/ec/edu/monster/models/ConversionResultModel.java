/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.models;

/**
 *
 * @author jeffe
 */
public class ConversionResultModel {
    private boolean exitoso;
    private UnidadConversionModel resultado;
    private ConversionErrorModel error;

    // Métodos estáticos de fábrica (como en C#)
    public static ConversionResultModel exito(UnidadConversionModel conversion) {
        ConversionResultModel result = new ConversionResultModel();
        result.setExitoso(true);
        result.setResultado(conversion);
        result.setError(null);
        return result;
    }

    public static ConversionResultModel fallo(ConversionErrorModel error) {
        ConversionResultModel result = new ConversionResultModel();
        result.setExitoso(false);
        result.setResultado(null);
        result.setError(error);
        return result;
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    public UnidadConversionModel getResultado() {
        return resultado;
    }

    public void setResultado(UnidadConversionModel resultado) {
        this.resultado = resultado;
    }

    public ConversionErrorModel getError() {
        return error;
    }

    public void setError(ConversionErrorModel error) {
        this.error = error;
    }
    
    
}
