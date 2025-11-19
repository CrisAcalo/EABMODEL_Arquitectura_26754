package ec.edu.monster.rest;

import ec.edu.monster.dto.FacturaDTO;
import ec.edu.monster.dto.RespuestaDTO;
import ec.edu.monster.dto.SolicitudCalculoDTO;
import ec.edu.monster.dto.CalculoFacturaDTO;
import ec.edu.monster.service.FacturacionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

/**
 * Recurso REST para operaciones de Facturación
 *
 * Endpoints disponibles:
 * - GET  /api/facturas                    : Obtener todas las facturas
 * - GET  /api/facturas/{id}               : Obtener factura por ID
 * - GET  /api/facturas/numero/{numero}    : Obtener factura por número
 * - GET  /api/facturas/cliente/{cedula}   : Obtener facturas por cliente
 * - GET  /api/facturas/credito            : Obtener facturas a crédito
 * - GET  /api/facturas/credito/{numero}   : Obtener factura por número de crédito
 * - POST /api/facturas                    : Crear nueva factura
 * - POST /api/facturas/calcular           : Calcular total de factura sin crearla
 */
@Path("/facturas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FacturacionResource {
    
    private static final Logger LOGGER = Logger.getLogger(FacturacionResource.class.getName());

    @Inject
    private FacturacionService facturacionService;
    
    /**
     * Obtener todas las facturas
     * GET /api/facturas
     */
    @GET
    public Response obtenerTodas() {
        try {
            List<FacturaDTO> facturas = facturacionService.obtenerTodas();
            return Response.ok(facturas).build();
        } catch (Exception e) {
            LOGGER.severe("Error al obtener facturas: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Obtener factura por ID
     * GET /api/facturas/{id}
     */
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Integer id) {
        try {
            FacturaDTO factura = facturacionService.obtenerPorId(id);
            if (factura == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new RespuestaDTO(false, "Factura no encontrada"))
                        .build();
            }
            return Response.ok(factura).build();
        } catch (Exception e) {
            LOGGER.severe("Error al obtener factura: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Obtener factura por número
     * GET /api/facturas/numero/{numero}
     */
    @GET
    @Path("/numero/{numero}")
    public Response obtenerPorNumero(@PathParam("numero") String numero) {
        try {
            FacturaDTO factura = facturacionService.obtenerPorNumero(numero);
            if (factura == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new RespuestaDTO(false, "Factura no encontrada"))
                        .build();
            }
            return Response.ok(factura).build();
        } catch (Exception e) {
            LOGGER.severe("Error al obtener factura por número: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Obtener facturas por cédula de cliente
     * GET /api/facturas/cliente/{cedula}
     */
    @GET
    @Path("/cliente/{cedula}")
    public Response obtenerPorCliente(@PathParam("cedula") String cedula) {
        try {
            List<FacturaDTO> facturas = facturacionService.obtenerPorCliente(cedula);
            return Response.ok(facturas).build();
        } catch (Exception e) {
            LOGGER.severe("Error al obtener facturas por cliente: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Obtener todas las facturas a crédito
     * GET /api/facturas/credito
     */
    @GET
    @Path("/credito")
    public Response obtenerFacturasCredito() {
        try {
            List<FacturaDTO> facturas = facturacionService.obtenerFacturasCredito();
            return Response.ok(facturas).build();
        } catch (Exception e) {
            LOGGER.severe("Error al obtener facturas a crédito: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Obtener factura por número de crédito
     * GET /api/facturas/credito/{numeroCredito}
     */
    @GET
    @Path("/credito/{numeroCredito}")
    public Response obtenerPorNumeroCredito(@PathParam("numeroCredito") String numeroCredito) {
        try {
            FacturaDTO factura = facturacionService.obtenerPorNumeroCredito(numeroCredito);
            if (factura == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new RespuestaDTO(false, "Factura no encontrada con ese número de crédito"))
                        .build();
            }
            return Response.ok(factura).build();
        } catch (Exception e) {
            LOGGER.severe("Error al obtener factura por número de crédito: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Crear nueva factura
     * POST /api/facturas
     *
     * Body ejemplo para EFECTIVO (descuento se calcula automáticamente - 33%):
     * {
     *   "cedulaCliente": "1234567890",
     *   "nombreCliente": "Juan Pérez",
     *   "formaPago": "EFECTIVO",
     *   "detalles": [
     *     {
     *       "productoId": 1,
     *       "cantidad": 2
     *     }
     *   ]
     * }
     *
     * Body ejemplo para CREDITO (debe incluir numeroCredito obtenido de BanQuito):
     * {
     *   "cedulaCliente": "1234567890",
     *   "nombreCliente": "Juan Pérez",
     *   "formaPago": "CREDITO",
     *   "numeroCredito": "CRE-000001",
     *   "detalles": [
     *     {
     *       "productoId": 1,
     *       "cantidad": 2
     *     }
     *   ]
     * }
     */
    @POST
    public Response crearFactura(FacturaDTO facturaDTO) {
        try {
            LOGGER.info("Creando factura para cliente: " + facturaDTO.getCedulaCliente());
            FacturaDTO creada = facturacionService.crearFactura(facturaDTO);
            return Response.status(Response.Status.CREATED)
                    .entity(new RespuestaDTO(true, "Factura creada exitosamente", creada))
                    .build();
        } catch (Exception e) {
            LOGGER.severe("Error al crear factura: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new RespuestaDTO(false, e.getMessage()))
                    .build();
        }
    }

    /**
     * Calcular total de factura SIN generarla
     * POST /api/facturas/calcular
     *
     * Este endpoint permite calcular el total de una factura ANTES de crearla.
     * Útil para conocer el monto exacto antes de solicitar crédito en BanQuito.
     *
     * NO crea la factura en la base de datos
     * NO actualiza el stock de productos
     *
     * Body ejemplo:
     * {
     *   "items": [
     *     {
     *       "productoId": 1,
     *       "cantidad": 2
     *     },
     *     {
     *       "productoId": 3,
     *       "cantidad": 1
     *     }
     *   ]
     * }
     *
     * Respuesta ejemplo exitosa:
     * {
     *   "exitoso": true,
     *   "mensaje": "Cálculo realizado exitosamente",
     *   "total": 150.00,
     *   "detalles": [
     *     {
     *       "productoId": 1,
     *       "nombreProducto": "Laptop Dell",
     *       "cantidad": 2,
     *       "precioUnitario": 50.00,
     *       "subtotal": 100.00
     *     },
     *     {
     *       "productoId": 3,
     *       "nombreProducto": "Mouse",
     *       "cantidad": 1,
     *       "precioUnitario": 50.00,
     *       "subtotal": 50.00
     *     }
     *   ]
     * }
     *
     * Respuesta ejemplo con error:
     * {
     *   "exitoso": false,
     *   "mensaje": "Stock insuficiente para el producto 'Laptop Dell'. Stock disponible: 1, solicitado: 2",
     *   "total": 0,
     *   "detalles": []
     * }
     */
    @POST
    @Path("/calcular")
    public Response calcularTotalFactura(SolicitudCalculoDTO solicitud) {
        try {
            LOGGER.info("Calculando total de factura...");
            CalculoFacturaDTO resultado = facturacionService.calcularTotalFactura(solicitud);

            // El servicio ya retorna el DTO con exitoso true/false
            // Si es exitoso retornamos 200 OK, si no 400 BAD REQUEST
            if (resultado.getExitoso()) {
                return Response.ok(resultado).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(resultado)
                        .build();
            }
        } catch (Exception e) {
            LOGGER.severe("Error inesperado al calcular factura: " + e.getMessage());
            e.printStackTrace();

            // Retornar DTO de error
            CalculoFacturaDTO error = new CalculoFacturaDTO();
            error.setExitoso(false);
            error.setMensaje("Error interno del servidor: " + e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error)
                    .build();
        }
    }

    /**
     * Endpoint de prueba
     * GET /api/facturas/ping
     */
    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping() {
        return Response.ok("Servicio de Facturación está activo").build();
    }
}
