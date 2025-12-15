package ec.edu.monster.Services;

import ec.edu.monster.Constants.AppConfig;
import ec.edu.monster.Constants.ErrorMessages;
import ec.edu.monster.DTOs.*;
import ec.edu.monster.Models.Producto;
import ec.edu.monster.Repositories.ProductoRepository;
import ec.edu.monster.Validators.ProductoValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de productos
 */
@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Crear un nuevo producto
     */
    public RespuestaDTO crearProducto(CrearProductoDTO dto) {
        // Validar usando ProductoValidator
        Optional<String> error = ProductoValidator.validarCrear(dto);
        if (error.isPresent()) {
            return RespuestaDTO.error(error.get());
        }

        // Validar código único
        if (productoRepository.existsByCodigo(dto.getCodigo())) {
            return RespuestaDTO.error(String.format(ErrorMessages.CODIGO_DUPLICADO, dto.getCodigo()));
        }

        // Crear producto
        Producto producto = new Producto();
        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock() != null ? dto.getStock() : 0);
        producto.setCategoria(dto.getCategoria());
        producto.setImagenUrl(dto.getImagenUrl());
        producto.setEstado(AppConfig.ESTADO_ACTIVO);

        Producto productoCreado = productoRepository.save(producto);

        return RespuestaDTO.exitoso(
                String.format(ErrorMessages.PRODUCTO_CREADO, productoCreado.getProductoId()),
                mapearADTO(productoCreado));
    }

    /**
     * Listar todos los productos activos
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerProductos() {
        return productoRepository.findByEstadoOrderByNombreAsc(AppConfig.ESTADO_ACTIVO)
                .stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener producto por ID
     */
    @Transactional(readOnly = true)
    public ProductoDTO obtenerProductoPorId(Integer id) {
        return productoRepository.findById(id)
                .map(this::mapearADTO)
                .orElse(null);
    }

    /**
     * Obtener producto por código
     */
    @Transactional(readOnly = true)
    public ProductoDTO obtenerProductoPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo)
                .map(this::mapearADTO)
                .orElse(null);
    }

    /**
     * Obtener productos por categoría
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoriaAndEstado(categoria, AppConfig.ESTADO_ACTIVO)
                .stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener productos por rango de precio
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerProductosPorPrecio(BigDecimal precioMin, BigDecimal precioMax) {
        return productoRepository.findByPrecioBetween(precioMin, precioMax)
                .stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualizar producto (PATCH - solo campos enviados)
     */
    public RespuestaDTO actualizarProducto(Integer id, ActualizarProductoDTO dto) {
        // Validar usando ProductoValidator
        Optional<String> error = ProductoValidator.validarActualizar(dto);
        if (error.isPresent()) {
            return RespuestaDTO.error(error.get());
        }

        // Buscar producto existente
        var productoOpt = productoRepository.findById(id);
        if (productoOpt.isEmpty()) {
            return RespuestaDTO.error(ErrorMessages.PRODUCTO_NO_ENCONTRADO);
        }

        Producto producto = productoOpt.get();

        // Validar código único si se está cambiando
        if (dto.getCodigo() != null && !dto.getCodigo().isBlank()) {
            if (productoRepository.existsByCodigoAndIdNot(dto.getCodigo(), id)) {
                return RespuestaDTO.error(String.format(ErrorMessages.CODIGO_DUPLICADO, dto.getCodigo()));
            }
            producto.setCodigo(dto.getCodigo());
        }

        // Actualizar solo campos enviados (PATCH)
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            producto.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            producto.setDescripcion(dto.getDescripcion());
        }
        if (dto.getPrecio() != null) {
            producto.setPrecio(dto.getPrecio());
        }
        if (dto.getStock() != null) {
            producto.setStock(dto.getStock());
        }
        if (dto.getCategoria() != null) {
            producto.setCategoria(dto.getCategoria());
        }
        if (dto.getImagenUrl() != null) {
            producto.setImagenUrl(dto.getImagenUrl());
        }
        if (dto.getEstado() != null && !dto.getEstado().isBlank()) {
            producto.setEstado(dto.getEstado());
        }

        Producto productoActualizado = productoRepository.save(producto);

        return RespuestaDTO.exitoso(
                ErrorMessages.PRODUCTO_ACTUALIZADO,
                mapearADTO(productoActualizado));
    }

    /**
     * Eliminar producto
     */
    public RespuestaDTO eliminarProducto(Integer id) {
        var productoOpt = productoRepository.findById(id);
        if (productoOpt.isEmpty()) {
            return RespuestaDTO.error(ErrorMessages.PRODUCTO_NO_ENCONTRADO);
        }

        productoRepository.deleteById(id);
        return RespuestaDTO.exitoso(ErrorMessages.PRODUCTO_ELIMINADO);
    }

    // Mapear entidad a DTO
    private ProductoDTO mapearADTO(Producto producto) {
        return new ProductoDTO(
                producto.getProductoId(),
                producto.getCodigo(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria(),
                producto.getImagenUrl(),
                producto.getFechaRegistro(),
                producto.getEstado());
    }
}
