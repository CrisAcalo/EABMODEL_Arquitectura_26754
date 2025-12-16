package ec.edu.monster.validators;

import ec.edu.monster.models.Sucursal;
import java.math.BigDecimal;

/**
 * Validador para la entidad Sucursal
 * 
 * @author EurekaBank
 */
public class SucursalValidator {

    public static ValidacionResultado validarParaCreacion(Sucursal sucursal) {
        if (sucursal.getNombre() == null || sucursal.getNombre().trim().isEmpty()) {
            return new ValidacionResultado(false, "El nombre de la sucursal es requerido");
        }
        if (sucursal.getCiudad() == null || sucursal.getCiudad().trim().isEmpty()) {
            return new ValidacionResultado(false, "La ciudad de la sucursal es requerida");
        }
        if (sucursal.getDireccion() == null || sucursal.getDireccion().trim().isEmpty()) {
            return new ValidacionResultado(false, "La dirección de la sucursal es requerida");
        }
        if (!coordenadasValidas(sucursal.getLatitud(), sucursal.getLongitud())) {
            return new ValidacionResultado(false,
                    "Las coordenadas son inválidas. Debe proporcionar ambas o ninguna, y en rangos válidos.");
        }
        return new ValidacionResultado(true, null);
    }

    public static ValidacionResultado validarParaActualizacion(Sucursal sucursal) {
        if (sucursal.getCodigo() <= 0) {
            return new ValidacionResultado(false, "Código de sucursal inválido");
        }
        // Reutilizamos validacion de creación para campos obligatorios
        return validarParaCreacion(sucursal);
    }

    public static boolean coordenadasValidas(BigDecimal latitud, BigDecimal longitud) {
        // Ambas nulas es válido (no tiene coordenadas)
        if (latitud == null && longitud == null) {
            return true;
        }
        // Una nula y otra no es inválido
        if (latitud == null || longitud == null) {
            return false;
        }

        // Validar rangos
        double lat = latitud.doubleValue();
        double lon = longitud.doubleValue();

        return lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180;
    }

    public static boolean puedeSerEliminada(Sucursal sucursal) {
        return sucursal.getContadorCuentas() == 0;
    }

    public static boolean existe(Sucursal sucursal) {
        return sucursal != null;
    }

    // Clase interna para resultado de validación (similar a Tuple<bool, string> en
    // C#)
    public static class ValidacionResultado {
        public final boolean esValida;
        public final String mensajeError;

        public ValidacionResultado(boolean esValida, String mensajeError) {
            this.esValida = esValida;
            this.mensajeError = mensajeError;
        }
    }
}
