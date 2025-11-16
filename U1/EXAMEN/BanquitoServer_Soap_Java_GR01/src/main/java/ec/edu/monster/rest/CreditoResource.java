package ec.edu.monster.rest;

import ec.edu.monster.model.Cliente;
import ec.edu.monster.model.Credito;
import ec.edu.monster.model.CuotaAmortizacion;
import ec.edu.monster.rest.dto.*;
import ec.edu.monster.service.CreditoService;
import ec.edu.monster.service.CreditoValidacionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.logging.Logger;

/**
 * Recurso REST para operaciones de crédito
 * 
 * Endpoints disponibles:
 * - GET  /api/credito/validar/{cedula}                 : Validar si es sujeto de crédito
 * - GET  /api/credito/monto-maximo/{cedula}            : Obtener monto máximo de crédito
 * - POST /api/credito/otorgar                          : Otorgar un crédito
 * - GET  /api/credito/tabla-amortizacion/{numeroCredito} : Obtener tabla de amortización
 */
@Path("/credito")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CreditoResource {
    
    private static final Logger LOGGER = Logger.getLogger(CreditoResource.class.getName());
    
    @Inject
    private CreditoValidacionService validacionService;
    
    @Inject
    private CreditoService creditoService;
    
    /**
     * WS 1: Validar si una persona es sujeto de crédito
     * 
     * GET /api/credito/validar/{cedula}
     */
    @GET
    @Path("/validar/{cedula}")
    public Response validarSujetoCredito(@PathParam("cedula") String cedula) {
        try {
            LOGGER.info("Validando sujeto de crédito para cédula: " + cedula);
            
            Map<String, Object> resultado = validacionService.validarSujetoCredito(cedula);
            
            ValidacionCreditoDTO response = new ValidacionCreditoDTO();
            response.setEsValido((Boolean) resultado.get("esValido"));
            response.setMensaje((String) resultado.get("mensaje"));
            response.setCedula(cedula);
            
            if (resultado.containsKey("cliente")) {
                Cliente cliente = (Cliente) resultado.get("cliente");
                response.setNombreCompleto(cliente.getNombreCompleto());
            }
            
            return Response.ok(response).build();
            
        } catch (Exception e) {
            LOGGER.severe("Error al validar sujeto de crédito: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ValidacionCreditoDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * WS 2: Obtener el monto máximo de crédito
     * 
     * GET /api/credito/monto-maximo/{cedula}
     */
    @GET
    @Path("/monto-maximo/{cedula}")
    public Response obtenerMontoMaximoCredito(@PathParam("cedula") String cedula) {
        try {
            LOGGER.info("Calculando monto máximo de crédito para cédula: " + cedula);
            
            Map<String, Object> resultado = validacionService.calcularMontoMaximoCredito(cedula);
            
            MontoMaximoCreditoDTO response = new MontoMaximoCreditoDTO();
            response.setCedula(cedula);
            response.setMontoMaximo((BigDecimal) resultado.get("montoMaximo"));
            response.setPromedioDepositos((BigDecimal) resultado.get("promedioDepositos"));
            response.setPromedioRetiros((BigDecimal) resultado.get("promedioRetiros"));
            response.setMensaje((String) resultado.get("mensaje"));
            
            return Response.ok(response).build();
            
        } catch (Exception e) {
            LOGGER.severe("Error al calcular monto máximo: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MontoMaximoCreditoDTO(cedula, BigDecimal.ZERO, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * WS 3: Otorgar un crédito y crear tabla de amortización
     * 
     * POST /api/credito/otorgar
     * Body: { "cedula": "1234567890", "precioElectrodomestico": 1500.00, "numeroCuotas": 12 }
     */
    @POST
    @Path("/otorgar")
    public Response otorgarCredito(SolicitudCreditoDTO solicitud) {
        try {
            LOGGER.info("Otorgando crédito para cédula: " + solicitud.getCedula());
            
            Map<String, Object> resultado = creditoService.otorgarCredito(
                solicitud.getCedula(),
                solicitud.getPrecioElectrodomestico(),
                solicitud.getNumeroCuotas()
            );
            
            RespuestaCreditoDTO response = new RespuestaCreditoDTO();
            response.setExito((Boolean) resultado.get("exito"));
            response.setMensaje((String) resultado.get("mensaje"));
            response.setCedula(solicitud.getCedula());
            
            if ((Boolean) resultado.get("exito")) {
                Credito credito = (Credito) resultado.get("credito");
                response.setNumeroCredito(credito.getNumeroCredito());
                response.setMontoCredito(credito.getMontoCredito());
                response.setNumeroCuotas(credito.getNumeroCuotas());
                response.setCuotaMensual(credito.getCuotaMensual());
                response.setTasaInteres(credito.getTasaInteres());
                
                // Convertir tabla de amortización a DTO
                @SuppressWarnings("unchecked")
                List<CuotaAmortizacion> tablaAmortizacion = (List<CuotaAmortizacion>) resultado.get("tablaAmortizacion");
                List<CuotaAmortizacionDTO> tablaDTO = tablaAmortizacion.stream()
                    .map(cuota -> new CuotaAmortizacionDTO(
                        cuota.getNumeroCuota(),
                        cuota.getValorCuota(),
                        cuota.getInteres(),
                        cuota.getCapitalPagado(),
                        cuota.getSaldo()
                    ))
                    .collect(Collectors.toList());
                
                response.setTablaAmortizacion(tablaDTO);
                
                return Response.status(Response.Status.CREATED).entity(response).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
            }
            
        } catch (Exception e) {
            LOGGER.severe("Error al otorgar crédito: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaCreditoDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * WS 4: Obtener la tabla de amortización de un crédito
     * 
     * GET /api/credito/tabla-amortizacion/{numeroCredito}
     */
    @GET
    @Path("/tabla-amortizacion/{numeroCredito}")
    public Response obtenerTablaAmortizacion(@PathParam("numeroCredito") String numeroCredito) {
        try {
            LOGGER.info("Obteniendo tabla de amortización para crédito: " + numeroCredito);
            
            List<CuotaAmortizacion> tablaAmortizacion = creditoService.obtenerTablaAmortizacion(numeroCredito);
            
            if (tablaAmortizacion.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new RespuestaCreditoDTO(false, "Crédito no encontrado"))
                        .build();
            }
            
            List<CuotaAmortizacionDTO> tablaDTO = tablaAmortizacion.stream()
                .map(cuota -> new CuotaAmortizacionDTO(
                    cuota.getNumeroCuota(),
                    cuota.getValorCuota(),
                    cuota.getInteres(),
                    cuota.getCapitalPagado(),
                    cuota.getSaldo()
                ))
                .collect(Collectors.toList());
            
            return Response.ok(tablaDTO).build();
            
        } catch (Exception e) {
            LOGGER.severe("Error al obtener tabla de amortización: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaCreditoDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Endpoint de prueba para verificar que el servicio está funcionando
     * 
     * GET /api/credito/ping
     */
    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping() {
        return Response.ok("Servicio de Crédito BanQuito está activo").build();
    }
}
