package ec.edu.monster.Constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Configuración centralizada de la aplicación
 */
@Component
public class AppConfig {

    @Value("${comercializadora.descuento-efectivo:0.33}")
    private BigDecimal descuentoEfectivo;

    // Formas de pago válidas
    public static final String FORMA_PAGO_EFECTIVO = "EFECTIVO";
    public static final String FORMA_PAGO_CREDITO = "CREDITO";

    // Estados de producto
    public static final String ESTADO_ACTIVO = "ACTIVO";
    public static final String ESTADO_INACTIVO = "INACTIVO";

    // Prefijos para generación de códigos
    public static final String PREFIJO_FACTURA = "FAC";
    public static final String FORMATO_FECHA = "yyyyMMdd";

    /**
     * Obtener porcentaje de descuento para pago en efectivo
     */
    public BigDecimal getDescuentoEfectivo() {
        return descuentoEfectivo;
    }

    /**
     * Verificar si una forma de pago es válida
     */
    public static boolean esFormaPagoValida(String formaPago) {
        if (formaPago == null)
            return false;
        String fp = formaPago.toUpperCase();
        return fp.equals(FORMA_PAGO_EFECTIVO) || fp.equals(FORMA_PAGO_CREDITO);
    }

    /**
     * Verificar si es pago en efectivo
     */
    public static boolean esEfectivo(String formaPago) {
        return formaPago != null && formaPago.equalsIgnoreCase(FORMA_PAGO_EFECTIVO);
    }

    /**
     * Verificar si es pago a crédito
     */
    public static boolean esCredito(String formaPago) {
        return formaPago != null && formaPago.equalsIgnoreCase(FORMA_PAGO_CREDITO);
    }
}
