package ec.edu.monster.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de configuración centralizada del servidor
 * Lee el archivo application.properties y proporciona acceso a los parámetros
 * 
 * @author Javi
 */
@ApplicationScoped
public class ServerConfig {
    
    private static final Logger LOGGER = Logger.getLogger(ServerConfig.class.getName());
    private Properties properties;
    
    @PostConstruct
    public void init() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                LOGGER.log(Level.SEVERE, "No se pudo encontrar application.properties");
                return;
            }
            properties.load(input);
            LOGGER.log(Level.INFO, "Configuración cargada exitosamente");
            LOGGER.log(Level.INFO, "IP del Servidor: {0}", getServerIp());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error al cargar configuración", ex);
        }
    }
    
    // ========== CONFIGURACIÓN DEL SERVIDOR ==========
    
    public String getServerIp() {
        return properties.getProperty("server.ip", "localhost");
    }
    
    public int getServerPort() {
        return Integer.parseInt(properties.getProperty("server.port", "8080"));
    }
    
    public String getContextPath() {
        return properties.getProperty("server.context", "/banquito-server");
    }
    
    /**
     * Obtiene la URL base completa del servidor
     * Ejemplo: http://192.168.1.100:8080/banquito-server
     */
    public String getServerBaseUrl() {
        return String.format("http://%s:%d%s", 
                getServerIp(), 
                getServerPort(), 
                getContextPath());
    }
    
    /**
     * Obtiene la URL completa para los servicios REST
     * Ejemplo: http://192.168.1.100:8080/banquito-server/api
     */
    public String getRestApiUrl() {
        return getServerBaseUrl() + "/api";
    }
    
    // ========== CONFIGURACIÓN DE BASE DE DATOS ==========
    
    public String getDbHost() {
        return properties.getProperty("db.host", "localhost");
    }
    
    public int getDbPort() {
        return Integer.parseInt(properties.getProperty("db.port", "5432"));
    }
    
    public String getDbName() {
        return properties.getProperty("db.name", "banquito_db");
    }
    
    public String getDbUsername() {
        return properties.getProperty("db.username", "postgres");
    }
    
    public String getDbPassword() {
        return properties.getProperty("db.password", "postgres");
    }
    
    public String getJdbcUrl() {
        return String.format("jdbc:postgresql://%s:%d/%s", 
                getDbHost(), 
                getDbPort(), 
                getDbName());
    }
    
    public int getDbPoolMin() {
        return Integer.parseInt(properties.getProperty("db.pool.min", "5"));
    }
    
    public int getDbPoolMax() {
        return Integer.parseInt(properties.getProperty("db.pool.max", "20"));
    }
    
    // ========== CONFIGURACIÓN DE CRÉDITO ==========
    
    public double getAnnualInterestRate() {
        return Double.parseDouble(properties.getProperty("credit.interest.rate.annual", "0.16"));
    }
    
    public double getMonthlyInterestRate() {
        return getAnnualInterestRate() / 12;
    }
    
    public int getCreditMinMonths() {
        return Integer.parseInt(properties.getProperty("credit.min.months", "3"));
    }
    
    public int getCreditMaxMonths() {
        return Integer.parseInt(properties.getProperty("credit.max.months", "24"));
    }
    
    public double getCreditPercentage() {
        return Double.parseDouble(properties.getProperty("credit.percentage", "0.60"));
    }
    
    public int getCreditMultiplier() {
        return Integer.parseInt(properties.getProperty("credit.multiplier", "9"));
    }
    
    /**
     * Obtiene una propiedad personalizada
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Obtiene una propiedad con valor por defecto
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Imprime todas las configuraciones (útil para debug)
     */
    public void printConfiguration() {
        LOGGER.info("========== CONFIGURACIÓN DEL SERVIDOR ==========");
        LOGGER.info("URL Base: " + getServerBaseUrl());
        LOGGER.info("REST API: " + getRestApiUrl());
        LOGGER.info("Base de Datos: " + getJdbcUrl());
        LOGGER.info("Tasa de Interés Anual: " + (getAnnualInterestRate() * 100) + "%");
        LOGGER.info("Tasa de Interés Mensual: " + (getMonthlyInterestRate() * 100) + "%");
        LOGGER.info("===============================================");
    }
}
