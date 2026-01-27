package ec.edu.monster.Repositories;

import ec.edu.monster.Models.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para operaciones de Crédito
 */
@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {

    /**
     * Verificar si el cliente tiene un crédito activo
     */
    @Query("SELECT CASE WHEN COUNT(cr) > 0 THEN true ELSE false END FROM Credito cr " +
            "JOIN cr.cliente cl " +
            "WHERE cl.cedula = :cedula " +
            "AND cr.estado = 'ACTIVO'")
    boolean tieneCreditoActivo(@Param("cedula") String cedula);

    /**
     * Buscar crédito por número de crédito
     */
    @Query("SELECT cr FROM Credito cr " +
            "LEFT JOIN FETCH cr.cuotasAmortizacion " +
            "WHERE cr.numeroCredito = :numeroCredito")
    Optional<Credito> findByNumeroCredito(@Param("numeroCredito") String numeroCredito);
}
