package ec.edu.monster.dao;

import ec.edu.monster.model.Factura;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
     * Generar número de factura único con formato FAC-YYYYMMDD-XXX
     * Ejemplo: FAC-20251117-001, FAC-20251117-002, etc.
     */
    public String generarNumeroFactura() {
        // Obtener fecha actual en formato YYYYMMDD
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Contar cuántas facturas existen con esta fecha
        Long countHoy = em.createQuery(
            "SELECT COUNT(f) FROM Factura f WHERE f.numeroFactura LIKE :patron", Long.class)
            .setParameter("patron", "FAC-" + fecha + "-%")
            .getSingleResult();

        // Generar consecutivo del día (001, 002, 003, ...)
        Long consecutivo = countHoy + 1;

        return String.format("FAC-%s-%03d", fecha, consecutivo);
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
