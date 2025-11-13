package ec.edu.monster.db;

/**
 * Clase de prueba para verificar la conexión a la base de datos
 * Usar solo para pruebas de desarrollo
 */
public class TestConexion {

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("PRUEBA DE CONEXIÓN A BASE DE DATOS MYSQL");
        System.out.println("===========================================\n");

        // Probar conexión
        System.out.println("1. Probando conexión...");
        boolean conectado = ConexionDB.testConnection();

        if (conectado) {
            System.out.println("   ✓ Conexión exitosa!\n");

            // Obtener información de la BD
            System.out.println("2. Información de la base de datos:");
            String info = ConexionDB.getDatabaseInfo();
            System.out.println("   " + info + "\n");

            System.out.println("===========================================");
            System.out.println("RESULTADO: TODO FUNCIONA CORRECTAMENTE");
            System.out.println("===========================================");

        } else {
            System.out.println("   ✗ Error de conexión!\n");

            System.out.println("===========================================");
            System.out.println("POSIBLES CAUSAS DEL ERROR:");
            System.out.println("===========================================");
            System.out.println("1. El driver MySQL no está en Payara");
            System.out.println("   Solución: Copiar mysql-connector-j-X.jar a:");
            System.out.println("   PAYARA_HOME/glassfish/domains/domain1/lib/");
            System.out.println();
            System.out.println("2. El DataSource JNDI no está configurado");
            System.out.println("   Solución: Verificar que glassfish-resources.xml");
            System.out.println("   esté en web/WEB-INF/ y hacer deploy nuevamente");
            System.out.println();
            System.out.println("3. MySQL no está corriendo");
            System.out.println("   Solución: Iniciar MySQL con Docker:");
            System.out.println("   docker ps");
            System.out.println();
            System.out.println("4. Credenciales incorrectas");
            System.out.println("   Solución: Verificar glassfish-resources.xml:");
            System.out.println("   - serverName: localhost");
            System.out.println("   - portNumber: 3306");
            System.out.println("   - databaseName: eurekabank");
            System.out.println("   - user: eureka");
            System.out.println("   - password: admin");
            System.out.println("===========================================");
        }
    }
}
