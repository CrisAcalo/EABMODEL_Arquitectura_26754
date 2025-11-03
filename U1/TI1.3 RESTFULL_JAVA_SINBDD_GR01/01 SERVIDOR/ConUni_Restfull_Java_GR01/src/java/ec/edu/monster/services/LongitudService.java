/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.services;

import ec.edu.monster.constants.ConversionConstants.Longitud;
import ec.edu.monster.models.ConversionResultModel;
import ec.edu.monster.models.UnidadConversionModel;
import ec.edu.monster.validators.BaseValidator;
import ec.edu.monster.validators.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
/**
 *
 * @author jeffe
 */

@ApplicationScoped
public class LongitudService {
     public ConversionResultModel convertirMillaAMetro(double millas) {
        try {
            BaseValidator.validarValorPositivo(millas, Longitud.MILLA);
            double metros = millas * Longitud.MILLA_A_METRO;
            var resultado = new UnidadConversionModel(millas, metros, Longitud.MILLA, Longitud.METRO, "Longitud", Longitud.MILLA_A_METRO);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    public ConversionResultModel convertirMetroAMilla(double metros) {
        try {
            BaseValidator.validarValorPositivo(metros, Longitud.METRO);
            double millas = metros * Longitud.METRO_A_MILLA;
            var resultado = new UnidadConversionModel(metros, millas, Longitud.METRO, Longitud.MILLA, "Longitud", Longitud.METRO_A_MILLA);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    public ConversionResultModel convertirMillaAPulgada(double millas) {
        try {
            BaseValidator.validarValorPositivo(millas, Longitud.MILLA);
            double pulgadas = millas * Longitud.MILLA_A_PULGADA;
            var resultado = new UnidadConversionModel(millas, pulgadas, Longitud.MILLA, Longitud.PULGADA, "Longitud", Longitud.MILLA_A_PULGADA);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    public ConversionResultModel convertirPulgadaAMilla(double pulgadas) {
        try {
            BaseValidator.validarValorPositivo(pulgadas, Longitud.PULGADA);
            double millas = pulgadas * Longitud.PULGADA_A_MILLA;
            var resultado = new UnidadConversionModel(pulgadas, millas, Longitud.PULGADA, Longitud.MILLA, "Longitud", Longitud.PULGADA_A_MILLA);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    public ConversionResultModel convertirMetroAPulgada(double metros) {
        try {
            BaseValidator.validarValorPositivo(metros, Longitud.METRO);
            double pulgadas = metros * Longitud.METRO_A_PULGADA;
            var resultado = new UnidadConversionModel(metros, pulgadas, Longitud.METRO, Longitud.PULGADA, "Longitud", Longitud.METRO_A_PULGADA);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    public ConversionResultModel convertirPulgadaAMetro(double pulgadas) {
        try {
            BaseValidator.validarValorPositivo(pulgadas, Longitud.PULGADA);
            double metros = pulgadas * Longitud.PULGADA_A_METRO;
            var resultado = new UnidadConversionModel(pulgadas, metros, Longitud.PULGADA, Longitud.METRO, "Longitud", Longitud.PULGADA_A_METRO);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }
}
