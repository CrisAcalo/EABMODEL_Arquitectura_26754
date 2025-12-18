package ec.edu.monster.Validators;

import ec.edu.monster.Constants.ErrorMessages;
import ec.edu.monster.DTOs.CrearProductoDTO;
import ec.edu.monster.DTOs.ActualizarProductoDTO;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Validador para operaciones de Producto
 */
public final class ProductoValidator {

    private ProductoValidator() {
    }

    /**
     * Validar DTO para crear producto
     * 
     * @return Optional vacío si es válido, o mensaje de error
     */
    public static Optional<String> validarCrear(CrearProductoDTO dto) {
        if (dto == null) {
            return Optional.of(ErrorMessages.SOLICITUD_NULA);
        }

        if (dto.getCodigo() == null || dto.getCodigo().isBlank()) {
            return Optional.of(ErrorMessages.CODIGO_REQUERIDO);
        }

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            return Optional.of(ErrorMessages.NOMBRE_REQUERIDO);
        }

        if (dto.getPrecio() == null || dto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            return Optional.of(ErrorMessages.PRECIO_INVALIDO);
        }

        return Optional.empty();
    }

    /**
     * Validar DTO para actualizar producto (PATCH)
     * Solo valida campos que están presentes
     * 
     * @return Optional vacío si es válido, o mensaje de error
     */
    public static Optional<String> validarActualizar(ActualizarProductoDTO dto) {
        if (dto == null) {
            return Optional.of(ErrorMessages.SOLICITUD_NULA);
        }

        // Validar precio solo si está presente
        if (dto.getPrecio() != null && dto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            return Optional.of(ErrorMessages.PRECIO_INVALIDO);
        }

        return Optional.empty();
    }

    /**
     * Validar que el precio sea un valor numérico válido
     */
    public static boolean esPrecioValido(String precioStr) {
        if (precioStr == null || precioStr.isBlank()) {
            return false;
        }
        try {
            BigDecimal precio = new BigDecimal(precioStr);
            return precio.compareTo(BigDecimal.ZERO) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
