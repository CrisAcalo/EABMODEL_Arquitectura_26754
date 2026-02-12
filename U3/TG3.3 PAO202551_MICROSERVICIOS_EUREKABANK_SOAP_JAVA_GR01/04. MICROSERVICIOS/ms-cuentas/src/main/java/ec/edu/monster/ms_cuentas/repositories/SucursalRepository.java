package ec.edu.monster.ms_cuentas.repositories;

import ec.edu.monster.ms_cuentas.models.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, String> {

    @Modifying
    @Query("UPDATE Sucursal s SET s.contadorCuentas = s.contadorCuentas + 1 WHERE s.codigo = :codigo")
    void incrementarContadorCuentas(@Param("codigo") String codigo);
}
