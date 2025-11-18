package ec.edu.monster.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Clase abstracta genérica para operaciones CRUD básicas
 */
public abstract class GenericDAO<T> {
    
    @PersistenceContext(unitName = "BanquitoPU")
    protected EntityManager em;
    
    private final Class<T> entityClass;
    
    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    /**
     * Crear o actualizar una entidad
     */
    public T save(T entity) {
        if (em.contains(entity)) {
            return em.merge(entity);
        } else {
            em.persist(entity);
            return entity;
        }
    }
    
    /**
     * Buscar por ID
     */
    public T findById(Object id) {
        return em.find(entityClass, id);
    }
    
    /**
     * Obtener todas las entidades
     */
    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }
    
    /**
     * Actualizar una entidad
     */
    public T update(T entity) {
        return em.merge(entity);
    }
    
    /**
     * Eliminar una entidad
     */
    public void delete(T entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }
        em.remove(entity);
    }
    
    /**
     * Eliminar por ID
     */
    public void deleteById(Object id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }
    
    /**
     * Contar todas las entidades
     */
    public long count() {
        return em.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class)
                .getSingleResult();
    }
}
