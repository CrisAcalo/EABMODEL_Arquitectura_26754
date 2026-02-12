package ec.edu.monster.ms_auth.repositories;

import ec.edu.monster.ms_auth.models.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, String> {
}
