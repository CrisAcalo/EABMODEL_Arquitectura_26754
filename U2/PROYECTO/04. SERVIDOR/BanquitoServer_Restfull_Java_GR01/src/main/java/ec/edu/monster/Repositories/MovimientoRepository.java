package ec.edu.monster.Repositories;

import ec.edu.monster.Models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para operaciones de Movimiento
 */
@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    /**
     * Verificar si el cliente tiene al menos un depósito en el último mes
     */
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Movimiento m " +
            "JOIN m.cuenta c " +
            "JOIN c.cliente cl " +
            "WHERE cl.cedula = :cedula " +
            "AND m.tipoMovimiento = 'DEPOSITO' " +
            "AND m.fechaMovimiento >= :fechaInicio")
    boolean tieneDepositoEnUltimoMes(@Param("cedula") String cedula,
            @Param("fechaInicio") LocalDateTime fechaInicio);

    /**
     * Obtener depósitos de un cliente en un período
     */
    @Query("SELECT m FROM Movimiento m " +
            "JOIN m.cuenta c " +
            "JOIN c.cliente cl " +
            "WHERE cl.cedula = :cedula " +
            "AND m.tipoMovimiento = 'DEPOSITO' " +
            "AND m.fechaMovimiento >= :fechaInicio " +
            "AND m.fechaMovimiento <= :fechaFin")
    List<Movimiento> findDepositosByClienteAndPeriodo(@Param("cedula") String cedula,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    /**
     * Obtener retiros de un cliente en un período
     */
    @Query("SELECT m FROM Movimiento m " +
            "JOIN m.cuenta c " +
            "JOIN c.cliente cl " +
            "WHERE cl.cedula = :cedula " +
            "AND m.tipoMovimiento = 'RETIRO' " +
            "AND m.fechaMovimiento >= :fechaInicio " +
            "AND m.fechaMovimiento <= :fechaFin")
    List<Movimiento> findRetirosByClienteAndPeriodo(@Param("cedula") String cedula,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    /**
     * Calcular promedio de depósitos en un período
     */
    @Query("SELECT COALESCE(AVG(m.monto), 0) FROM Movimiento m " +
            "JOIN m.cuenta c " +
            "JOIN c.cliente cl " +
            "WHERE cl.cedula = :cedula " +
            "AND m.tipoMovimiento = 'DEPOSITO' " +
            "AND m.fechaMovimiento >= :fechaInicio " +
            "AND m.fechaMovimiento <= :fechaFin")
    BigDecimal promedioDepositosPorPeriodo(@Param("cedula") String cedula,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    /**
     * Calcular promedio de retiros en un período
     */
    @Query("SELECT COALESCE(AVG(m.monto), 0) FROM Movimiento m " +
            "JOIN m.cuenta c " +
            "JOIN c.cliente cl " +
            "WHERE cl.cedula = :cedula " +
            "AND m.tipoMovimiento = 'RETIRO' " +
            "AND m.fechaMovimiento >= :fechaInicio " +
            "AND m.fechaMovimiento <= :fechaFin")
    BigDecimal promedioRetirosPorPeriodo(@Param("cedula") String cedula,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);
}
