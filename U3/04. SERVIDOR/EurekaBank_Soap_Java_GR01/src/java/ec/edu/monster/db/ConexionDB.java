package ec.edu.monster.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para gestionar la conexión a la base de datos MySQL
 * Adaptada desde RESTful .NET a SOAP Java (JDK 17 + Payara)
 * 
 * Las credenciales se cargan desde el archivo database.properties
 * ubicado en el classpath (src/java/database.properties)
 *
 * @author EurekaBank
 */
public class ConexionDB {

    private static final Logger LOGGER = Logger.getLogger(ConexionDB.class.getName());
    
    // Nombre del archivo de configuración
    private static final String CONFIG_FILE = "database.properties";
    
    // Propiedades de conexión (cargadas desde archivo)
    private static String driver;
    private static String url;
    private static String usuario;
    private static String clave;
    
    // Flag para indicar si la configuración fue cargada
    private static boolean configuracionCargada = false;

    /**
     * Bloque estático que carga la configuración y el driver JDBC
     */
    static {
        cargarConfiguracion();
        cargarDriver();
    }
    
    /**
     * Carga la configuración desde el archivo database.properties
     */
    private static void cargarConfiguracion() {
        Properties props = new Properties();
        
        try (InputStream input = ConexionDB.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                LOGGER.severe("No se encontró el archivo de configuración: " + CONFIG_FILE);
                throw new RuntimeException("No se encontró el archivo de configuración: " + CONFIG_FILE + 
                    ". Asegúrese de que el archivo existe en el classpath.");
            }
            
            props.load(input);
            
            driver = props.getProperty("db.driver");
            url = props.getProperty("db.url");
            usuario = props.getProperty("db.user");
            clave = props.getProperty("db.password");
            
            // Validar que todas las propiedades existan
            if (driver == null || url == null || usuario == null || clave == null) {
                throw new RuntimeException("Faltan propiedades en " + CONFIG_FILE + 
                    ". Se requieren: db.driver, db.url, db.user, db.password");
            }
            
            configuracionCargada = true;
            LOGGER.info("Configuración de base de datos cargada correctamente desde " + CONFIG_FILE);
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al leer el archivo de configuración", e);
            throw new RuntimeException("Error al cargar la configuración de base de datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Carga el driver JDBC de MySQL
     */
    private static void cargarDriver() {
        try {
            Class.forName(driver);
            LOGGER.info("Driver JDBC cargado: " + driver);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar el driver de MySQL", e);
            throw new RuntimeException("Error al cargar el driver de MySQL: " + e.getMessage(), e);
        }
    }

    /**
     * Crea y retorna una nueva conexión a la base de datos MySQL
     *
     * @return Connection configurada
     * @throws SQLException si hay error al conectar
     */
    public static Connection getConnection() throws SQLException {
        if (!configuracionCargada) {
            throw new SQLException("La configuración de base de datos no ha sido cargada correctamente");
        }
        
        try {
            Connection conn = DriverManager.getConnection(url, usuario, clave);
            return conn;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al conectar con la base de datos", e);
            throw new SQLException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una conexión a la base de datos (alias para compatibilidad)
     *
     * @return Connection configurada
     * @throws SQLException si hay error al conectar
     */
    public static Connection obtenerConexion() throws SQLException {
        return getConnection();
    }

    /**
     * Prueba la conexión a la base de datos
     *
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public static boolean probarConexion() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            LOGGER.warning("Error al probar conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cierra la conexión de forma segura
     *
     * @param conn Conexión a cerrar
     */
    public static void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOGGER.warning("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    /**
     * Verifica si la configuración fue cargada correctamente
     * 
     * @return true si la configuración está lista
     */
    public static boolean isConfiguracionCargada() {
        return configuracionCargada;
    }
}
