package ec.edu.monster.ms_cuentas.repositories;

import ec.edu.monster.ms_cuentas.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, String> {
    List<Cuenta> findByCodigoCliente(String codigoCliente);

    List<Cuenta> findByEstado(String estado);
}
