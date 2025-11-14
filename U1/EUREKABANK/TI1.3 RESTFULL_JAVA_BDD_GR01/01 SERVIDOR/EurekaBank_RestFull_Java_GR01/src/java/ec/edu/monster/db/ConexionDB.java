package ec.edu.monster.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL
 * Adaptada desde RESTful .NET a RESTful Java (JDK 17 + Payara)
 *
 * @author EurekaBank
 */
public class ConexionDB {

    // Parámetros de conexión MySQL en Docker
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/eurekabank?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "eureka";
    private static final String CLAVE = "admin";

    /**
     * Carga el driver JDBC de MySQL
     */
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver de MySQL: " + e.getMessage(), e);
        }
    }

    /**
     * Crea y retorna una nueva conexión a la base de datos MySQL
     *
     * @return Connection configurada
     * @throws SQLException si hay error al conectar
     */
    public static Connection obtenerConexion() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USUARIO, CLAVE);
            return conn;
        } catch (SQLException e) {
            throw new SQLException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Prueba la conexión a la base de datos
     *
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public static boolean probarConexion() {
        try (Connection conn = obtenerConexion()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error al probar conexión: " + e.getMessage());
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
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
