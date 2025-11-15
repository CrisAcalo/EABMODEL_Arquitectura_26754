package ec.edu.monster.dao;

import ec.edu.monster.model.Movimiento;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DAO para la entidad Movimiento
 */
@Stateless
public class MovimientoDAO extends GenericDAO<Movimiento> {
    
    public MovimientoDAO() {
        super(Movimiento.class);
    }
    
    /**
     * Obtener movimientos de un cliente en un período
     */
    public List<Movimiento> findByClienteAndPeriodo(String cedula, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        TypedQuery<Movimiento> query = em.createNamedQuery("Movimiento.findByClienteAndPeriodo", Movimiento.class);
        query.setParameter("cedula", cedula);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }
    
    /**
     * Obtener depósitos de un cliente en un período
     */
    public List<Movimiento> findDepositosByClienteAndPeriodo(String cedula, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        TypedQuery<Movimiento> query = em.createNamedQuery("Movimiento.findDepositosByClienteAndPeriodo", Movimiento.class);
        query.setParameter("cedula", cedula);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }
    
    /**
     * Obtener retiros de un cliente en un período
     */
    public List<Movimiento> findRetirosByClienteAndPeriodo(String cedula, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        TypedQuery<Movimiento> query = em.createNamedQuery("Movimiento.findRetirosByClienteAndPeriodo", Movimiento.class);
        query.setParameter("cedula", cedula);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }
    
    /**
     * Verificar si hay al menos un depósito en el último mes
     */
    public boolean hasDepositoEnUltimoMes(String cedula) {
        LocalDateTime fechaInicio = LocalDateTime.now().minusMonths(1);
        LocalDateTime fechaFin = LocalDateTime.now();
        
        List<Movimiento> depositos = findDepositosByClienteAndPeriodo(cedula, fechaInicio, fechaFin);
        return !depositos.isEmpty();
    }
}
