/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.validators;

import ec.edu.monster.models.ConversionError;

/**
 *
 * Clase auxiliar para retornar resultados de validación con valor
 * @author Kewo
 */
public class ValidationResult {
    private ConversionError error;
    private double valor;
    
    public ValidationResult(ConversionError error, double valor) {
        this.error = error;
        this.valor = valor;
    }
    
    public ConversionError getError() {
        return error;
    }
    
    public double getValor() {
        return valor;
    }
    
    public boolean isValido() {
        return error == null;
    }
}
