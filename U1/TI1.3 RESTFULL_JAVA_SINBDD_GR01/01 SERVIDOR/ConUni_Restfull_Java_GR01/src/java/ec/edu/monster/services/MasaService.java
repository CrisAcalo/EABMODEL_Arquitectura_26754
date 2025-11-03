/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.services;

import ec.edu.monster.constants.ConversionConstants.Masa;
import ec.edu.monster.models.ConversionResultModel;
import ec.edu.monster.models.UnidadConversionModel;
import ec.edu.monster.validators.BaseValidator;
import ec.edu.monster.validators.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Servicio para conversiones de unidades de masa
 * Soporta conversiones entre Kilogramo, Quintal y Libra
 *
 * @author jeffe
 */
@ApplicationScoped
public class MasaService {

    /**
     * Convierte kilogramos a quintales
     * @param kilogramos Valor en kilogramos a convertir
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirKilogramoAQuintal(double kilogramos) {
        try {
            BaseValidator.validarValorPositivo(kilogramos, Masa.KILOGRAMO);
            double quintales = kilogramos * Masa.KILOGRAMO_A_QUINTAL;
            var resultado = new UnidadConversionModel(kilogramos, quintales, Masa.KILOGRAMO, Masa.QUINTAL, "Masa", Masa.KILOGRAMO_A_QUINTAL);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    /**
     * Convierte quintales a kilogramos
     * @param quintales Valor en quintales a convertir
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirQuintalAKilogramo(double quintales) {
        try {
            BaseValidator.validarValorPositivo(quintales, Masa.QUINTAL);
            double kilogramos = quintales * Masa.QUINTAL_A_KILOGRAMO;
            var resultado = new UnidadConversionModel(quintales, kilogramos, Masa.QUINTAL, Masa.KILOGRAMO, "Masa", Masa.QUINTAL_A_KILOGRAMO);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    /**
     * Convierte kilogramos a libras
     * @param kilogramos Valor en kilogramos a convertir
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirKilogramoALibra(double kilogramos) {
        try {
            BaseValidator.validarValorPositivo(kilogramos, Masa.KILOGRAMO);
            double libras = kilogramos * Masa.KILOGRAMO_A_LIBRA;
            var resultado = new UnidadConversionModel(kilogramos, libras, Masa.KILOGRAMO, Masa.LIBRA, "Masa", Masa.KILOGRAMO_A_LIBRA);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    /**
     * Convierte libras a kilogramos
     * @param libras Valor en libras a convertir
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirLibraAKilogramo(double libras) {
        try {
            BaseValidator.validarValorPositivo(libras, Masa.LIBRA);
            double kilogramos = libras * Masa.LIBRA_A_KILOGRAMO;
            var resultado = new UnidadConversionModel(libras, kilogramos, Masa.LIBRA, Masa.KILOGRAMO, "Masa", Masa.LIBRA_A_KILOGRAMO);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    /**
     * Convierte quintales a libras
     * @param quintales Valor en quintales a convertir
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirQuintalALibra(double quintales) {
        try {
            BaseValidator.validarValorPositivo(quintales, Masa.QUINTAL);
            double libras = quintales * Masa.QUINTAL_A_LIBRA;
            var resultado = new UnidadConversionModel(quintales, libras, Masa.QUINTAL, Masa.LIBRA, "Masa", Masa.QUINTAL_A_LIBRA);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }

    /**
     * Convierte libras a quintales
     * @param libras Valor en libras a convertir
     * @return ConversionResultModel con el resultado o error
     */
    public ConversionResultModel convertirLibraAQuintal(double libras) {
        try {
            BaseValidator.validarValorPositivo(libras, Masa.LIBRA);
            double quintales = libras * Masa.LIBRA_A_QUINTAL;
            var resultado = new UnidadConversionModel(libras, quintales, Masa.LIBRA, Masa.QUINTAL, "Masa", Masa.LIBRA_A_QUINTAL);
            return ConversionResultModel.exito(resultado);
        } catch (ValidationException e) {
            return ConversionResultModel.fallo(e.getErrorModel());
        }
    }
}
