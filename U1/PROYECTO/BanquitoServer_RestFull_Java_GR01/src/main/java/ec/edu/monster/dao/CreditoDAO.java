package ec.edu.monster.dao;

import ec.edu.monster.model.Credito;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * DAO para la entidad Credito
 */
@Stateless
public class CreditoDAO extends GenericDAO<Credito> {
    
    public CreditoDAO() {
        super(Credito.class);
    }
    
    /**
     * Buscar créditos activos de un cliente por cédula
     */
    public List<Credito> findActivosByCedula(String cedula) {
        TypedQuery<Credito> query = em.createNamedQuery("Credito.findActivosByCedula", Credito.class);
        query.setParameter("cedula", cedula);
        return query.getResultList();
    }
    
    /**
     * Verificar si un cliente tiene créditos activos
     */
    public boolean tieneCreditoActivo(String cedula) {
        List<Credito> creditosActivos = findActivosByCedula(cedula);
        return !creditosActivos.isEmpty();
    }
    
    /**
     * Buscar todos los créditos de un cliente por cédula
     */
    public List<Credito> findByCedula(String cedula) {
        TypedQuery<Credito> query = em.createNamedQuery("Credito.findByCedula", Credito.class);
        query.setParameter("cedula", cedula);
        return query.getResultList();
    }
    
    /**
     * Buscar crédito por número de crédito
     */
    public Credito findByNumeroCredito(String numeroCredito) {
        try {
            TypedQuery<Credito> query = em.createQuery(
                "SELECT c FROM Credito c WHERE c.numeroCredito = :numeroCredito", Credito.class);
            query.setParameter("numeroCredito", numeroCredito);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
