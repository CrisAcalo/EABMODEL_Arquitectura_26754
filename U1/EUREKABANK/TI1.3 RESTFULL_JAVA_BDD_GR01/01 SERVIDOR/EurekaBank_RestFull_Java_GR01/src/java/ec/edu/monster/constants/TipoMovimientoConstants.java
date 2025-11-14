package ec.edu.monster.constants;

/**
 * Constantes para los tipos de movimientos bancarios
 *
 * @author EurekaBank
 */
public class TipoMovimientoConstants {

    public static final String APERTURA = "001";
    public static final String CANCELAR = "002";
    public static final String DEPOSITO = "003";
    public static final String RETIRO = "004";
    public static final String INTERES = "005";
    public static final String MANTENIMIENTO = "006";
    public static final String ITF = "007";
    public static final String TRANSFERENCIA_INGRESO = "008";
    public static final String TRANSFERENCIA_SALIDA = "009";
    public static final String CARGO_MOVIMIENTO = "010";

    // Constructor privado para evitar instanciación
    private TipoMovimientoConstants() {
    }
}
