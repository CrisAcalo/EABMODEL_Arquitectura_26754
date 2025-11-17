package ec.edu.monster.dao;

import ec.edu.monster.model.DetalleFactura;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * DAO para la entidad DetalleFactura
 * Maneja todas las operaciones de base de datos relacionadas con detalles de factura
 */
@Stateless
public class DetalleFacturaDAO extends GenericDAO<DetalleFactura> {
    
    public DetalleFacturaDAO() {
        super(DetalleFactura.class);
    }
    
    /**
     * Buscar detalles por factura ID
     */
    public List<DetalleFactura> findByFacturaId(Integer facturaId) {
        TypedQuery<DetalleFactura> query = em.createQuery(
            "SELECT d FROM DetalleFactura d WHERE d.factura.facturaId = :facturaId", 
            DetalleFactura.class);
        query.setParameter("facturaId", facturaId);
        return query.getResultList();
    }
    
    /**
     * Buscar detalles por producto ID
     */
    public List<DetalleFactura> findByProductoId(Integer productoId) {
        TypedQuery<DetalleFactura> query = em.createQuery(
            "SELECT d FROM DetalleFactura d WHERE d.producto.productoId = :productoId", 
            DetalleFactura.class);
        query.setParameter("productoId", productoId);
        return query.getResultList();
    }
    
    /**
     * Eliminar todos los detalles de una factura
     */
    public void deleteByFacturaId(Integer facturaId) {
        em.createQuery("DELETE FROM DetalleFactura d WHERE d.factura.facturaId = :facturaId")
          .setParameter("facturaId", facturaId)
          .executeUpdate();
    }
}
