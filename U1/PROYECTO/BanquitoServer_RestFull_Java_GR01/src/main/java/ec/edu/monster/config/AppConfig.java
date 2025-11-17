package ec.edu.monster.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de configuración centralizada para el servidor.
 * Lee el archivo config.properties y proporciona acceso a las propiedades.
 */
@ApplicationScoped
public class AppConfig {
    
    private static final Logger LOGGER = Logger.getLogger(AppConfig.class.getName());
    private Properties properties;
    
    @PostConstruct
    public void init() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                LOGGER.severe("No se pudo encontrar config.properties");
                return;
            }
            properties.load(input);
            LOGGER.info("Configuración cargada exitosamente");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error al cargar configuración", ex);
        }
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    // Métodos específicos para configuración del servidor
    public String getServerHost() {
        return getProperty("server.host", "localhost");
    }
    
    public int getServerPort() {
        return Integer.parseInt(getProperty("server.port", "8080"));
    }
    
    public String getServerContext() {
        return getProperty("server.context", "/banquito-server");
    }
    
    public String getServerUrl() {
        return "http://" + getServerHost() + ":" + getServerPort() + getServerContext();
    }
    
    // Métodos específicos para configuración de BD
    public String getDbHost() {
        return getProperty("db.host", "localhost");
    }
    
    public int getDbPort() {
        return Integer.parseInt(getProperty("db.port", "3306"));
    }
    
    public String getDbName() {
        return getProperty("db.name", "banquito_db");
    }
    
    public String getDbUsername() {
        return getProperty("db.username", "root");
    }
    
    public String getDbPassword() {
        return getProperty("db.password", "");
    }
    
    public String getDbUrl() {
        return "jdbc:mysql://" + getDbHost() + ":" + getDbPort() + "/" + getDbName() 
                + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    }
    
    // Métodos específicos para configuración de créditos
    public double getTasaAnual() {
        return Double.parseDouble(getProperty("credito.tasa.anual", "0.16"));
    }
    
    public int getPlazoMinimo() {
        return Integer.parseInt(getProperty("credito.plazo.minimo", "3"));
    }
    
    public int getPlazoMaximo() {
        return Integer.parseInt(getProperty("credito.plazo.maximo", "24"));
    }
    
    public double getPorcentajeCapacidad() {
        return Double.parseDouble(getProperty("credito.porcentaje.capacidad", "0.60"));
    }
    
    public int getMultiplicador() {
        return Integer.parseInt(getProperty("credito.multiplicador", "9"));
    }
}
