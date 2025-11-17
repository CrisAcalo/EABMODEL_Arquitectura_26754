package ec.edu.monster.dao;

import ec.edu.monster.model.Factura;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * DAO para la entidad Factura
 * Maneja todas las operaciones de base de datos relacionadas con facturas
 */
@Stateless
public class FacturaDAO extends GenericDAO<Factura> {
    
    public FacturaDAO() {
        super(Factura.class);
    }
    
    /**
     * Buscar factura por número
     */
    public Factura findByNumeroFactura(String numeroFactura) {
        try {
            TypedQuery<Factura> query = em.createQuery(
                "SELECT f FROM Factura f WHERE f.numeroFactura = :numeroFactura", Factura.class);
            query.setParameter("numeroFactura", numeroFactura);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Buscar facturas por cédula de cliente
     */
    public List<Factura> findByCedulaCliente(String cedula) {
        TypedQuery<Factura> query = em.createQuery(
            "SELECT f FROM Factura f WHERE f.cedulaCliente = :cedula ORDER BY f.fechaEmision DESC", 
            Factura.class);
        query.setParameter("cedula", cedula);
        return query.getResultList();
    }
    
    /**
     * Buscar facturas por forma de pago
     */
    public List<Factura> findByFormaPago(String formaPago) {
        TypedQuery<Factura> query = em.createQuery(
            "SELECT f FROM Factura f WHERE f.formaPago = :formaPago ORDER BY f.fechaEmision DESC", 
            Factura.class);
        query.setParameter("formaPago", formaPago);
        return query.getResultList();
    }
    
    /**
     * Buscar facturas a crédito
     */
    public List<Factura> findFacturasCredito() {
        return findByFormaPago("CREDITO");
    }
    
    /**
     * Buscar facturas por número de crédito
     */
    public Factura findByNumeroCredito(String numeroCredito) {
        try {
            TypedQuery<Factura> query = em.createQuery(
                "SELECT f FROM Factura f WHERE f.numeroCredito = :numeroCredito", Factura.class);
            query.setParameter("numeroCredito", numeroCredito);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Generar número de factura único
     */
    public String generarNumeroFactura() {
        Long count = count() + 1;
        return String.format("FAC-%06d", count);
    }
    
    /**
     * Verificar si existe una factura con el número especificado
     */
    public boolean existsByNumeroFactura(String numeroFactura) {
        Long count = em.createQuery(
            "SELECT COUNT(f) FROM Factura f WHERE f.numeroFactura = :numeroFactura", Long.class)
            .setParameter("numeroFactura", numeroFactura)
            .getSingleResult();
        return count > 0;
    }
    
    /**
     * Obtener facturas con detalles (eager loading)
     */
    public Factura findByIdWithDetalles(Integer facturaId) {
        try {
            TypedQuery<Factura> query = em.createQuery(
                "SELECT DISTINCT f FROM Factura f LEFT JOIN FETCH f.detalles WHERE f.facturaId = :id", 
                Factura.class);
            query.setParameter("id", facturaId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
