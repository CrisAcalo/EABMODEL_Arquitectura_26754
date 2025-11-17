package ec.edu.monster.service;

import ec.edu.monster.config.BanquitoConfig;
import ec.edu.monster.dto.banquito.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Cliente REST para consumir servicios de BanQuito
 * Maneja la comunicación con el servidor BanQuito para operaciones de crédito
 */
@Stateless
public class BanquitoClientService {
    
    private static final Logger LOGGER = Logger.getLogger(BanquitoClientService.class.getName());
    
    @Inject
    private BanquitoConfig config;
    
    /**
     * Crear cliente REST configurado
     */
    private Client createClient() {
        return ClientBuilder.newBuilder()
                .connectTimeout(config.getConnectionTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS)
                .build();
    }
    
    /**
     * WS 1: Validar si un cliente es sujeto de crédito
     * 
     * @param cedula Cédula del cliente
     * @return ValidacionCreditoDTO con resultado de validación
     */
    public ValidacionCreditoDTO validarSujetoCredito(String cedula) {
        Client client = createClient();
        
        try {
            String url = config.buildUrl("/credito/validar/" + cedula);
            LOGGER.info("Validando sujeto de crédito en: " + url);
            
            Response response = client.target(url)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            
            if (response.getStatus() == 200) {
                ValidacionCreditoDTO resultado = response.readEntity(ValidacionCreditoDTO.class);
                LOGGER.info("Validación exitosa: " + resultado.getMensaje());
                return resultado;
            } else {
                LOGGER.warning("Error en validación: Status " + response.getStatus());
                return new ValidacionCreditoDTO(
                    false, 
                    "Error al validar cliente. Status: " + response.getStatus(),
                    cedula,
                    null
                );
            }
            
        } catch (Exception e) {
            LOGGER.severe("Error al conectar con BanQuito: " + e.getMessage());
            return new ValidacionCreditoDTO(
                false, 
                "Error de conexión con BanQuito: " + e.getMessage(),
                cedula,
                null
            );
        } finally {
            client.close();
        }
    }
    
    /**
     * WS 2: Obtener monto máximo de crédito para un cliente
     * 
     * @param cedula Cédula del cliente
     * @return MontoMaximoCreditoDTO con el monto máximo aprobado
     */
    public MontoMaximoCreditoDTO obtenerMontoMaximoCredito(String cedula) {
        Client client = createClient();
        
        try {
            String url = config.buildUrl("/credito/monto-maximo/" + cedula);
            LOGGER.info("Obteniendo monto máximo en: " + url);
            
            Response response = client.target(url)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            
            if (response.getStatus() == 200) {
                MontoMaximoCreditoDTO resultado = response.readEntity(MontoMaximoCreditoDTO.class);
                LOGGER.info("Monto máximo obtenido: $" + resultado.getMontoMaximo());
                return resultado;
            } else {
                LOGGER.warning("Error al obtener monto: Status " + response.getStatus());
                return new MontoMaximoCreditoDTO(
                    cedula,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    "Error al obtener monto máximo. Status: " + response.getStatus()
                );
            }
            
        } catch (Exception e) {
            LOGGER.severe("Error al conectar con BanQuito: " + e.getMessage());
            return new MontoMaximoCreditoDTO(
                cedula,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                "Error de conexión con BanQuito: " + e.getMessage()
            );
        } finally {
            client.close();
        }
    }
    
    /**
     * WS 3: Otorgar crédito en BanQuito
     * 
     * @param cedula Cédula del cliente
     * @param monto Monto total del crédito
     * @param numeroCuotas Número de cuotas
     * @return RespuestaCreditoDTO con información del crédito otorgado
     */
    public RespuestaCreditoDTO otorgarCredito(String cedula, BigDecimal monto, Integer numeroCuotas) {
        Client client = createClient();
        
        try {
            String url = config.buildUrl("/credito/otorgar");
            LOGGER.info("Otorgando crédito en: " + url);
            
            // Crear solicitud
            SolicitudCreditoDTO solicitud = new SolicitudCreditoDTO(cedula, monto, numeroCuotas);
            
            Response response = client.target(url)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(solicitud));
            
            if (response.getStatus() == 201 || response.getStatus() == 200) {
                RespuestaCreditoDTO resultado = response.readEntity(RespuestaCreditoDTO.class);
                LOGGER.info("Crédito otorgado: " + resultado.getNumeroCredito());
                return resultado;
            } else {
                LOGGER.warning("Error al otorgar crédito: Status " + response.getStatus());
                RespuestaCreditoDTO error = new RespuestaCreditoDTO();
                error.setExito(false);
                error.setMensaje("Error al otorgar crédito. Status: " + response.getStatus());
                error.setCedula(cedula);
                return error;
            }
            
        } catch (Exception e) {
            LOGGER.severe("Error al conectar con BanQuito: " + e.getMessage());
            e.printStackTrace();
            RespuestaCreditoDTO error = new RespuestaCreditoDTO();
            error.setExito(false);
            error.setMensaje("Error de conexión con BanQuito: " + e.getMessage());
            error.setCedula(cedula);
            return error;
        } finally {
            client.close();
        }
    }
    
    /**
     * Verificar conectividad con BanQuito
     * 
     * @return true si BanQuito está disponible
     */
    public boolean verificarConectividad() {
        Client client = createClient();
        
        try {
            String url = config.buildUrl("/credito/ping");
            LOGGER.info("Verificando conectividad con BanQuito: " + url);
            
            Response response = client.target(url)
                    .request(MediaType.TEXT_PLAIN)
                    .get();
            
            boolean disponible = response.getStatus() == 200;
            LOGGER.info("BanQuito " + (disponible ? "disponible" : "no disponible"));
            return disponible;
            
        } catch (Exception e) {
            LOGGER.warning("BanQuito no disponible: " + e.getMessage());
            return false;
        } finally {
            client.close();
        }
    }
}
