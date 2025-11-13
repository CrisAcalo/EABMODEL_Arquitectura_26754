package ec.edu.monster.constants;

/**
 * Constantes para los estados de las entidades
 */
public class EstadosConstants {

    // Estados generales
    public static final String ACTIVO = "ACTIVO";
    public static final String ANULADO = "ANULADO";
    public static final String CANCELADO = "CANCELADO";

    // Constructor privado para evitar instanciación
    private EstadosConstants() {
        throw new AssertionError("No se puede instanciar esta clase");
    }
}
