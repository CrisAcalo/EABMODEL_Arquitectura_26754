package ec.edu.monster.config;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Configuración del servidor BanQuito
 * Lee las propiedades del archivo config.properties
 */
@Singleton
@Startup
public class BanquitoConfig {
    
    private static final Logger LOGGER = Logger.getLogger(BanquitoConfig.class.getName());
    private static final String CONFIG_FILE = "/config.properties";
    
    private String banquitoServerUrl;
    private int connectionTimeout;
    private int readTimeout;
    
    @PostConstruct
    public void init() {
        Properties props = new Properties();
        
        try (InputStream input = getClass().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                LOGGER.warning("No se encontró el archivo config.properties, usando valores por defecto");
                setDefaultValues();
                return;
            }
            
            props.load(input);
            
            banquitoServerUrl = props.getProperty("banquito.server.url", "http://localhost:8080/banquito-server/api");
            connectionTimeout = Integer.parseInt(props.getProperty("banquito.connection.timeout", "5000"));
            readTimeout = Integer.parseInt(props.getProperty("banquito.read.timeout", "10000"));
            
            LOGGER.info("==============================================");
            LOGGER.info("Configuración de BanQuito cargada:");
            LOGGER.info("URL: " + banquitoServerUrl);
            LOGGER.info("Connection Timeout: " + connectionTimeout + "ms");
            LOGGER.info("Read Timeout: " + readTimeout + "ms");
            LOGGER.info("==============================================");
            
        } catch (IOException e) {
            LOGGER.severe("Error al cargar configuración: " + e.getMessage());
            setDefaultValues();
        }
    }
    
    private void setDefaultValues() {
        banquitoServerUrl = "http://localhost:8080/banquito-server/api";
        connectionTimeout = 5000;
        readTimeout = 10000;
        
        LOGGER.info("Usando configuración por defecto para BanQuito");
    }
    
    public String getBanquitoServerUrl() {
        return banquitoServerUrl;
    }
    
    public int getConnectionTimeout() {
        return connectionTimeout;
    }
    
    public int getReadTimeout() {
        return readTimeout;
    }
    
    /**
     * Construir URL completa para un endpoint
     */
    public String buildUrl(String endpoint) {
        return banquitoServerUrl + endpoint;
    }
}
