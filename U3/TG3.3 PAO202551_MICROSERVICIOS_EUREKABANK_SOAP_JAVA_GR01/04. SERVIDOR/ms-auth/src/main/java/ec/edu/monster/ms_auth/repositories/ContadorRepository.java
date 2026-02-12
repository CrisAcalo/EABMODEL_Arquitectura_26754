package ec.edu.monster.ms_auth.repositories;

import ec.edu.monster.ms_auth.models.Contador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContadorRepository extends JpaRepository<Contador, String> {
}
