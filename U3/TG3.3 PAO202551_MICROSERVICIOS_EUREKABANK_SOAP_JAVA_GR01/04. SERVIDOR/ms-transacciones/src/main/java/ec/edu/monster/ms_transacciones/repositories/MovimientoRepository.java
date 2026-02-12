package ec.edu.monster.ms_transacciones.repositories;

import ec.edu.monster.ms_transacciones.models.Movimiento;
import ec.edu.monster.ms_transacciones.models.MovimientoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, MovimientoId> {

    @Query("SELECT COALESCE(MAX(m.numero), 0) FROM Movimiento m WHERE m.codigoCuenta = :codigoCuenta")
    int obtenerUltimoNumero(@Param("codigoCuenta") String codigoCuenta);

    List<Movimiento> findByCodigoCuentaOrderByNumeroDesc(String codigoCuenta);

    @Query("SELECT m FROM Movimiento m WHERE m.codigoCuenta = :codigoCuenta " +
            "AND m.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY m.numero")
    List<Movimiento> findByCodigoCuentaAndFechaBetween(
            @Param("codigoCuenta") String codigoCuenta,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
}
