package ec.edu.monster.service;

import ec.edu.monster.dao.ProductoDAO;
import ec.edu.monster.dto.ProductoDTO;
import ec.edu.monster.model.Producto;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la lógica de negocio de Producto
 */
@Stateless
public class ProductoService {
    
    @Inject
    private ProductoDAO productoDAO;
    
    /**
     * Convertir entidad a DTO
     */
    private ProductoDTO convertirADTO(Producto producto) {
        if (producto == null) return null;
        
        ProductoDTO dto = new ProductoDTO();
        dto.setProductoId(producto.getProductoId());
        dto.setCodigo(producto.getCodigo());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setCategoria(producto.getCategoria());
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setFechaRegistro(producto.getFechaRegistro());
        dto.setEstado(producto.getEstado());
        
        return dto;
    }
    
    /**
     * Convertir DTO a entidad
     */
    private Producto convertirAEntidad(ProductoDTO dto) {
        if (dto == null) return null;
        
        Producto producto = new Producto();
        producto.setProductoId(dto.getProductoId());
        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        producto.setImagenUrl(dto.getImagenUrl());
        producto.setFechaRegistro(dto.getFechaRegistro());
        producto.setEstado(dto.getEstado());
        
        return producto;
    }
    
    /**
     * Obtener todos los productos
     */
    public List<ProductoDTO> obtenerTodos() {
        return productoDAO.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener productos activos
     */
    public List<ProductoDTO> obtenerActivos() {
        return productoDAO.findActivos().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener producto por ID
     */
    public ProductoDTO obtenerPorId(Integer id) {
        Producto producto = productoDAO.findById(id);
        return convertirADTO(producto);
    }
    
    /**
     * Obtener producto por código
     */
    public ProductoDTO obtenerPorCodigo(String codigo) {
        Producto producto = productoDAO.findByCodigo(codigo);
        return convertirADTO(producto);
    }
    
    /**
     * Buscar productos por nombre
     */
    public List<ProductoDTO> buscarPorNombre(String nombre) {
        return productoDAO.searchByNombre(nombre).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener productos por categoría
     */
    public List<ProductoDTO> obtenerPorCategoria(String categoria) {
        return productoDAO.findByCategoria(categoria).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Crear nuevo producto
     */
    @Transactional
    public ProductoDTO crear(ProductoDTO productoDTO) {
        // Validar que no exista el código
        if (productoDAO.existsByCodigo(productoDTO.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con el código: " + productoDTO.getCodigo());
        }
        
        Producto producto = convertirAEntidad(productoDTO);
        Producto guardado = productoDAO.save(producto);
        return convertirADTO(guardado);
    }
    
    /**
     * Actualizar producto
     */
    @Transactional
    public ProductoDTO actualizar(Integer id, ProductoDTO productoDTO) {
        Producto existente = productoDAO.findById(id);
        if (existente == null) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        
        // Validar código único si se está cambiando
        if (!existente.getCodigo().equals(productoDTO.getCodigo())) {
            if (productoDAO.existsByCodigo(productoDTO.getCodigo())) {
                throw new RuntimeException("Ya existe un producto con el código: " + productoDTO.getCodigo());
            }
        }
        
        existente.setCodigo(productoDTO.getCodigo());
        existente.setNombre(productoDTO.getNombre());
        existente.setDescripcion(productoDTO.getDescripcion());
        existente.setPrecio(productoDTO.getPrecio());
        existente.setStock(productoDTO.getStock());
        existente.setCategoria(productoDTO.getCategoria());
        existente.setImagenUrl(productoDTO.getImagenUrl());
        existente.setEstado(productoDTO.getEstado());
        
        Producto actualizado = productoDAO.update(existente);
        return convertirADTO(actualizado);
    }
    
    /**
     * Eliminar producto (cambiar estado a INACTIVO)
     */
    @Transactional
    public void eliminar(Integer id) {
        Producto producto = productoDAO.findById(id);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        
        producto.setEstado("INACTIVO");
        productoDAO.update(producto);
    }
    
    /**
     * Actualizar stock
     */
    @Transactional
    public void actualizarStock(Integer id, Integer nuevoStock) {
        productoDAO.actualizarStock(id, nuevoStock);
    }
}
