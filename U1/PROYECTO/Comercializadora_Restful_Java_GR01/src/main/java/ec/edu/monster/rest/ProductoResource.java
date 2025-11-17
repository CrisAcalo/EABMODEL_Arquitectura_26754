package ec.edu.monster.rest;

import ec.edu.monster.dto.ProductoDTO;
import ec.edu.monster.dto.RespuestaDTO;
import ec.edu.monster.service.ProductoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

/**
 * Recurso REST para operaciones de Producto
 * 
 * Endpoints disponibles:
 * - GET    /api/productos              : Obtener todos los productos
 * - GET    /api/productos/activos      : Obtener productos activos
 * - GET    /api/productos/{id}         : Obtener producto por ID
 * - GET    /api/productos/codigo/{codigo} : Obtener producto por código
 * - GET    /api/productos/buscar       : Buscar productos por nombre
 * - GET    /api/productos/categoria/{categoria} : Obtener productos por categoría
 * - POST   /api/productos              : Crear nuevo producto
 * - PUT    /api/productos/{id}         : Actualizar producto
 * - DELETE /api/productos/{id}         : Eliminar producto (inactivar)
 * - PATCH  /api/productos/{id}/stock   : Actualizar stock
 */
@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoResource {
    
    private static final Logger LOGGER = Logger.getLogger(ProductoResource.class.getName());
    
    @Inject
    private ProductoService productoService;
    
    /**
     * Obtener todos los productos
     * GET /api/productos
     */
    @GET
    public Response obtenerTodos() {
        try {
            List<ProductoDTO> productos = productoService.obtenerTodos();
            return Response.ok(productos).build();
        } catch (Exception e) {
            LOGGER.severe("Error al obtener productos: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Obtener productos activos
     * GET /api/productos/activos
     */
    @GET
    @Path("/activos")
    public Response obtenerActivos() {
        try {
            List<ProductoDTO> productos = productoService.obtenerActivos();
            return Response.ok(productos).build();
        } catch (Exception e) {
            LOGGER.severe("Error al obtener productos activos: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Obtener producto por ID
     * GET /api/productos/{id}
     */
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Integer id) {
        try {
            ProductoDTO producto = productoService.obtenerPorId(id);
            if (producto == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new RespuestaDTO(false, "Producto no encontrado"))
                        .build();
            }
            return Response.ok(producto).build();
        } catch (Exception e) {
            LOGGER.severe("Error al obtener producto: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Obtener producto por código
     * GET /api/productos/codigo/{codigo}
     */
    @GET
    @Path("/codigo/{codigo}")
    public Response obtenerPorCodigo(@PathParam("codigo") String codigo) {
        try {
            ProductoDTO producto = productoService.obtenerPorCodigo(codigo);
            if (producto == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new RespuestaDTO(false, "Producto no encontrado"))
                        .build();
            }
            return Response.ok(producto).build();
        } catch (Exception e) {
            LOGGER.severe("Error al obtener producto por código: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Buscar productos por nombre
     * GET /api/productos/buscar?nombre=xxx
     */
    @GET
    @Path("/buscar")
    public Response buscarPorNombre(@QueryParam("nombre") String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new RespuestaDTO(false, "El parámetro nombre es requerido"))
                        .build();
            }
            List<ProductoDTO> productos = productoService.buscarPorNombre(nombre);
            return Response.ok(productos).build();
        } catch (Exception e) {
            LOGGER.severe("Error al buscar productos: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Obtener productos por categoría
     * GET /api/productos/categoria/{categoria}
     */
    @GET
    @Path("/categoria/{categoria}")
    public Response obtenerPorCategoria(@PathParam("categoria") String categoria) {
        try {
            List<ProductoDTO> productos = productoService.obtenerPorCategoria(categoria);
            return Response.ok(productos).build();
        } catch (Exception e) {
            LOGGER.severe("Error al obtener productos por categoría: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RespuestaDTO(false, "Error interno: " + e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Crear nuevo producto
     * POST /api/productos
     */
    @POST
    public Response crear(ProductoDTO productoDTO) {
        try {
            ProductoDTO creado = productoService.crear(productoDTO);
            return Response.status(Response.Status.CREATED)
                    .entity(new RespuestaDTO(true, "Producto creado exitosamente", creado))
                    .build();
        } catch (Exception e) {
            LOGGER.severe("Error al crear producto: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new RespuestaDTO(false, e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Actualizar producto
     * PUT /api/productos/{id}
     */
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, ProductoDTO productoDTO) {
        try {
            ProductoDTO actualizado = productoService.actualizar(id, productoDTO);
            return Response.ok(new RespuestaDTO(true, "Producto actualizado exitosamente", actualizado))
                    .build();
        } catch (Exception e) {
            LOGGER.severe("Error al actualizar producto: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new RespuestaDTO(false, e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Eliminar producto (inactivar)
     * DELETE /api/productos/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        try {
            productoService.eliminar(id);
            return Response.ok(new RespuestaDTO(true, "Producto eliminado exitosamente"))
                    .build();
        } catch (Exception e) {
            LOGGER.severe("Error al eliminar producto: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new RespuestaDTO(false, e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Actualizar stock
     * PATCH /api/productos/{id}/stock
     * Body: { "stock": 100 }
     */
    @PATCH
    @Path("/{id}/stock")
    public Response actualizarStock(@PathParam("id") Integer id, StockUpdateDTO stockDTO) {
        try {
            productoService.actualizarStock(id, stockDTO.getStock());
            return Response.ok(new RespuestaDTO(true, "Stock actualizado exitosamente"))
                    .build();
        } catch (Exception e) {
            LOGGER.severe("Error al actualizar stock: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new RespuestaDTO(false, e.getMessage()))
                    .build();
        }
    }
    
    /**
     * Endpoint de prueba
     * GET /api/productos/ping
     */
    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping() {
        return Response.ok("Servicio de Productos está activo").build();
    }
    
    /**
     * DTO interno para actualización de stock
     */
    public static class StockUpdateDTO {
        private Integer stock;
        
        public Integer getStock() {
            return stock;
        }
        
        public void setStock(Integer stock) {
            this.stock = stock;
        }
    }
}
