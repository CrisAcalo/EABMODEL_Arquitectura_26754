package ec.edu.monster.db;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para gestionar conexiones a la base de datos MySQL
 * usando DataSource JNDI configurado en Payara Server
 */
public class ConexionDB {

    private static final Logger LOGGER = Logger.getLogger(ConexionDB.class.getName());
    private static final String JNDI_NAME = "jdbc/eurekabank";
    private static DataSource dataSource;

    // Bloque estático para inicializar el DataSource
    static {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup(JNDI_NAME);
            LOGGER.info("DataSource JNDI inicializado correctamente: " + JNDI_NAME);
        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el DataSource JNDI: " + JNDI_NAME, e);
            throw new RuntimeException("No se pudo inicializar el DataSource. "
                    + "Verifique que el recurso JNDI esté configurado en Payara.", e);
        }
    }

    /**
     * Obtiene una conexión desde el pool de conexiones
     *
     * @return Conexión a la base de datos
     * @throws SQLException si hay error al obtener la conexión
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource no inicializado");
        }

        Connection conn = dataSource.getConnection();
        LOGGER.fine("Conexión obtenida del pool");
        return conn;
    }

    /**
     * Cierra una conexión de forma segura
     *
     * @param conn Conexión a cerrar
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                LOGGER.fine("Conexión cerrada");
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar la conexión", e);
            }
        }
    }

    /**
     * Prueba la conectividad con la base de datos
     *
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            boolean isValid = conn.isValid(5);
            if (isValid) {
                LOGGER.info("Prueba de conexión exitosa");
            } else {
                LOGGER.warning("La conexión no es válida");
            }
            return isValid;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al probar la conexión", e);
            return false;
        }
    }

    /**
     * Obtiene información sobre la base de datos conectada
     *
     * @return String con información de la BD
     */
    public static String getDatabaseInfo() {
        try (Connection conn = getConnection()) {
            String dbName = conn.getMetaData().getDatabaseProductName();
            String dbVersion = conn.getMetaData().getDatabaseProductVersion();
            String url = conn.getMetaData().getURL();
            return String.format("BD: %s %s - URL: %s", dbName, dbVersion, url);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener información de la BD", e);
            return "No disponible";
        }
    }

    // Constructor privado para evitar instanciación
    private ConexionDB() {
        throw new AssertionError("No se puede instanciar esta clase");
    }
}
