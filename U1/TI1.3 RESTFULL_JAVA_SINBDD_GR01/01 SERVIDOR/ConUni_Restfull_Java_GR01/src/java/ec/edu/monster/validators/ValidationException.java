/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.validators;

import ec.edu.monster.models.ConversionErrorModel;

/**
 *
 * @author jeffe
 */

public class ValidationException extends Exception{
     private final ConversionErrorModel errorModel;

    public ValidationException(ConversionErrorModel errorModel) {
        super(errorModel.getMensaje());
        this.errorModel = errorModel;
    }

    public ConversionErrorModel getErrorModel() {
        return errorModel;
    }
    
}
