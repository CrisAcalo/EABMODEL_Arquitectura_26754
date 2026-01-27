package ec.edu.monster.Controllers;

import ec.edu.monster.DTOs.*;
import ec.edu.monster.Services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller REST para productos
 */
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "CRUD de productos de electrodomésticos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Crear un nuevo producto
     */
    @PostMapping
    @Operation(summary = "Crear producto", description = "Registra un nuevo producto en el catálogo")
    public ResponseEntity<RespuestaDTO> crearProducto(@RequestBody CrearProductoDTO dto) {
        RespuestaDTO respuesta = productoService.crearProducto(dto);
        if (respuesta.getExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
        return ResponseEntity.badRequest().body(respuesta);
    }

    /**
     * Listar todos los productos activos
     */
    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene todos los productos activos")
    public ResponseEntity<List<ProductoDTO>> obtenerProductos() {
        return ResponseEntity.ok(productoService.obtenerProductos());
    }

    /**
     * Obtener producto por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID", description = "Busca un producto por su ID")
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(
            @Parameter(description = "ID del producto") @PathVariable Integer id) {
        ProductoDTO producto = productoService.obtenerProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Obtener producto por código
     */
    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Obtener por código", description = "Busca un producto por su código único")
    public ResponseEntity<ProductoDTO> obtenerProductoPorCodigo(
            @Parameter(description = "Código del producto") @PathVariable String codigo) {
        ProductoDTO producto = productoService.obtenerProductoPorCodigo(codigo);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Obtener productos por categoría
     */
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Filtrar por categoría", description = "Lista productos de una categoría específica")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorCategoria(
            @Parameter(description = "Nombre de la categoría") @PathVariable String categoria) {
        return ResponseEntity.ok(productoService.obtenerProductosPorCategoria(categoria));
    }

    /**
     * Obtener productos por rango de precio
     */
    @GetMapping("/precio")
    @Operation(summary = "Filtrar por precio", description = "Lista productos en un rango de precios")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorPrecio(
            @Parameter(description = "Precio mínimo") @RequestParam BigDecimal precioMin,
            @Parameter(description = "Precio máximo") @RequestParam BigDecimal precioMax) {
        return ResponseEntity.ok(productoService.obtenerProductosPorPrecio(precioMin, precioMax));
    }

    /**
     * Actualizar producto (PATCH - actualización parcial)
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza parcialmente un producto existente")
    public ResponseEntity<RespuestaDTO> actualizarProducto(
            @Parameter(description = "ID del producto") @PathVariable Integer id,
            @RequestBody ActualizarProductoDTO dto) {
        RespuestaDTO respuesta = productoService.actualizarProducto(id, dto);
        if (respuesta.getExito()) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.badRequest().body(respuesta);
    }

    /**
     * Eliminar producto
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del catálogo")
    public ResponseEntity<RespuestaDTO> eliminarProducto(
            @Parameter(description = "ID del producto") @PathVariable Integer id) {
        RespuestaDTO respuesta = productoService.eliminarProducto(id);
        if (respuesta.getExito()) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }
}
