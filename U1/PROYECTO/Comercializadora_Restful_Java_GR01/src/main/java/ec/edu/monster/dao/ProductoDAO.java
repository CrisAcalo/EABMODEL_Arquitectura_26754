package ec.edu.monster.dao;

import ec.edu.monster.model.Producto;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * DAO para la entidad Producto
 * Maneja todas las operaciones de base de datos relacionadas con productos
 */
@Stateless
public class ProductoDAO extends GenericDAO<Producto> {
    
    public ProductoDAO() {
        super(Producto.class);
    }
    
    /**
     * Buscar producto por código
     */
    public Producto findByCodigo(String codigo) {
        try {
            TypedQuery<Producto> query = em.createQuery(
                "SELECT p FROM Producto p WHERE p.codigo = :codigo", Producto.class);
            query.setParameter("codigo", codigo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Buscar productos por categoría
     */
    public List<Producto> findByCategoria(String categoria) {
        TypedQuery<Producto> query = em.createQuery(
            "SELECT p FROM Producto p WHERE p.categoria = :categoria", Producto.class);
        query.setParameter("categoria", categoria);
        return query.getResultList();
    }
    
    /**
     * Buscar productos por estado
     */
    public List<Producto> findByEstado(String estado) {
        TypedQuery<Producto> query = em.createQuery(
            "SELECT p FROM Producto p WHERE p.estado = :estado", Producto.class);
        query.setParameter("estado", estado);
        return query.getResultList();
    }
    
    /**
     * Buscar productos activos
     */
    public List<Producto> findActivos() {
        return findByEstado("ACTIVO");
    }
    
    /**
     * Buscar productos por nombre (búsqueda parcial)
     */
    public List<Producto> searchByNombre(String nombre) {
        TypedQuery<Producto> query = em.createQuery(
            "SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(:nombre)", Producto.class);
        query.setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }
    
    /**
     * Verificar si existe un producto con el código especificado
     */
    public boolean existsByCodigo(String codigo) {
        Long count = em.createQuery(
            "SELECT COUNT(p) FROM Producto p WHERE p.codigo = :codigo", Long.class)
            .setParameter("codigo", codigo)
            .getSingleResult();
        return count > 0;
    }
    
    /**
     * Actualizar stock de un producto
     */
    public void actualizarStock(Integer productoId, Integer nuevoStock) {
        Producto producto = findById(productoId);
        if (producto != null) {
            producto.setStock(nuevoStock);
            update(producto);
        }
    }
    
    /**
     * Reducir stock de un producto
     */
    public boolean reducirStock(Integer productoId, Integer cantidad) {
        Producto producto = findById(productoId);
        if (producto != null && producto.getStock() >= cantidad) {
            producto.setStock(producto.getStock() - cantidad);
            update(producto);
            return true;
        }
        return false;
    }
}
