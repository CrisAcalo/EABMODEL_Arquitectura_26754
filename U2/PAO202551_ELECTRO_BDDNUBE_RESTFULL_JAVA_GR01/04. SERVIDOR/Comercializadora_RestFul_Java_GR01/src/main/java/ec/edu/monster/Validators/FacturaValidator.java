package ec.edu.monster.Validators;

import ec.edu.monster.Constants.AppConfig;
import ec.edu.monster.Constants.ErrorMessages;
import ec.edu.monster.DTOs.ItemFacturaDTO;
import ec.edu.monster.DTOs.SolicitudCalculoDTO;
import ec.edu.monster.DTOs.SolicitudFacturaDTO;

import java.util.Optional;

/**
 * Validador para operaciones de Facturación
 */
public final class FacturaValidator {

    private FacturaValidator() {
    }

    /**
     * Validar solicitud de cálculo de factura
     * 
     * @return Optional vacío si es válido, o mensaje de error
     */
    public static Optional<String> validarSolicitudCalculo(SolicitudCalculoDTO solicitud) {
        if (solicitud == null) {
            return Optional.of(ErrorMessages.SOLICITUD_NULA);
        }

        if (solicitud.getItems() == null || solicitud.getItems().isEmpty()) {
            return Optional.of(ErrorMessages.ITEMS_REQUERIDOS);
        }

        // Validar cada item
        for (ItemFacturaDTO item : solicitud.getItems()) {
            Optional<String> errorItem = validarItem(item);
            if (errorItem.isPresent()) {
                return errorItem;
            }
        }

        return Optional.empty();
    }

    /**
     * Validar solicitud de generación de factura
     * 
     * @return Optional vacío si es válido, o mensaje de error
     */
    public static Optional<String> validarSolicitudFactura(SolicitudFacturaDTO solicitud) {
        if (solicitud == null) {
            return Optional.of(ErrorMessages.SOLICITUD_NULA);
        }

        if (solicitud.getItems() == null || solicitud.getItems().isEmpty()) {
            return Optional.of(ErrorMessages.ITEMS_REQUERIDOS);
        }

        if (solicitud.getCedulaCliente() == null || solicitud.getCedulaCliente().isBlank()) {
            return Optional.of(ErrorMessages.CEDULA_REQUERIDA);
        }

        if (solicitud.getNombreCliente() == null || solicitud.getNombreCliente().isBlank()) {
            return Optional.of(ErrorMessages.NOMBRE_CLIENTE_REQUERIDO);
        }

        if (solicitud.getFormaPago() == null || solicitud.getFormaPago().isBlank()) {
            return Optional.of(ErrorMessages.FORMA_PAGO_REQUERIDA);
        }

        if (!AppConfig.esFormaPagoValida(solicitud.getFormaPago())) {
            return Optional.of(ErrorMessages.FORMA_PAGO_INVALIDA);
        }

        // Validar NumeroCredito si es CREDITO
        if (AppConfig.esCredito(solicitud.getFormaPago())) {
            if (solicitud.getNumeroCredito() == null || solicitud.getNumeroCredito().isBlank()) {
                return Optional.of(ErrorMessages.NUMERO_CREDITO_REQUERIDO);
            }
        }

        // Validar cada item
        for (ItemFacturaDTO item : solicitud.getItems()) {
            Optional<String> errorItem = validarItem(item);
            if (errorItem.isPresent()) {
                return errorItem;
            }
        }

        return Optional.empty();
    }

    /**
     * Validar un item de factura
     */
    public static Optional<String> validarItem(ItemFacturaDTO item) {
        if (item == null) {
            return Optional.of(ErrorMessages.SOLICITUD_NULA);
        }

        if (item.getProductoId() == null || item.getProductoId() <= 0) {
            return Optional.of(ErrorMessages.PRODUCTO_ID_REQUERIDO);
        }

        if (item.getCantidad() == null || item.getCantidad() <= 0) {
            return Optional.of(ErrorMessages.CANTIDAD_INVALIDA);
        }

        return Optional.empty();
    }
}
