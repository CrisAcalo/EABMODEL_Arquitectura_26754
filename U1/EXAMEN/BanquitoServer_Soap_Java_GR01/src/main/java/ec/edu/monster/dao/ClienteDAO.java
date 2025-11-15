package ec.edu.monster.dao;

import ec.edu.monster.model.Cliente;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

/**
 * DAO para la entidad Cliente
 */
@Stateless
public class ClienteDAO extends GenericDAO<Cliente> {
    
    public ClienteDAO() {
        super(Cliente.class);
    }
    
    /**
     * Buscar cliente por cédula
     */
    public Cliente findByCedula(String cedula) {
        try {
            TypedQuery<Cliente> query = em.createQuery(
                "SELECT c FROM Cliente c WHERE c.cedula = :cedula", Cliente.class);
            query.setParameter("cedula", cedula);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Verificar si existe un cliente con la cédula dada
     */
    public boolean existsByCedula(String cedula) {
        Long count = em.createQuery(
            "SELECT COUNT(c) FROM Cliente c WHERE c.cedula = :cedula", Long.class)
            .setParameter("cedula", cedula)
            .getSingleResult();
        return count > 0;
    }
}
