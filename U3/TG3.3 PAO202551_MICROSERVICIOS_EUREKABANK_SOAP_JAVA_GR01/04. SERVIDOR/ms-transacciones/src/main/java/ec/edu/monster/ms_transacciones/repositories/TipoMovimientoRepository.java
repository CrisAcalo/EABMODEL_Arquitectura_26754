package ec.edu.monster.ms_transacciones.repositories;

import ec.edu.monster.ms_transacciones.models.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, String> {
}
