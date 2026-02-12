package ec.edu.monster.ms_websocket.repositories;

import ec.edu.monster.ms_websocket.models.BloqueoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BloqueoCuentaRepository extends JpaRepository<BloqueoCuenta, Long> {

    @Query("SELECT b FROM BloqueoCuenta b WHERE b.codigoCuenta = :cuenta " +
            "AND b.estado = 'ACTIVO' AND b.fechaExpiracion > CURRENT_TIMESTAMP")
    Optional<BloqueoCuenta> findBloqueoActivo(@Param("cuenta") String codigoCuenta);

    @Query("SELECT b FROM BloqueoCuenta b WHERE b.estado = 'ACTIVO' " +
            "AND b.fechaExpiracion > CURRENT_TIMESTAMP")
    List<BloqueoCuenta> findBloqueosActivos();

    @Modifying
    @Transactional
    @Query("DELETE FROM BloqueoCuenta b WHERE b.codigoCuenta = :cuenta AND b.codigoVentanilla = :ventanilla")
    void liberarCuenta(@Param("cuenta") String codigoCuenta, @Param("ventanilla") String codigoVentanilla);

    @Modifying
    @Transactional
    @Query("DELETE FROM BloqueoCuenta b WHERE b.codigoVentanilla = :ventanilla")
    void liberarTodosDeVentanilla(@Param("ventanilla") String codigoVentanilla);

    @Modifying
    @Transactional
    @Query("DELETE FROM BloqueoCuenta b WHERE b.fechaExpiracion < CURRENT_TIMESTAMP")
    void limpiarBloqueosExpirados();

    @Query("SELECT COUNT(b) > 0 FROM BloqueoCuenta b WHERE b.codigoCuenta = :cuenta " +
            "AND b.estado = 'ACTIVO' AND b.fechaExpiracion > CURRENT_TIMESTAMP")
    boolean estaBloqueada(@Param("cuenta") String codigoCuenta);
}
