package ec.edu.monster.ms_transacciones.repositories;

import ec.edu.monster.ms_transacciones.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {

    @Modifying
    @Query("UPDATE Cuenta c SET c.saldo = :nuevoSaldo WHERE c.codigo = :codigo")
    void actualizarSaldo(@Param("codigo") String codigo, @Param("nuevoSaldo") BigDecimal nuevoSaldo);

    @Modifying
    @Query("UPDATE Cuenta c SET c.contadorMovimientos = c.contadorMovimientos + 1 WHERE c.codigo = :codigo")
    void incrementarContadorMovimientos(@Param("codigo") String codigo);
}
