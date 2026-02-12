package ec.edu.monster.ms_cuentas.repositories;

import ec.edu.monster.ms_cuentas.models.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonedaRepository extends JpaRepository<Moneda, String> {
}
