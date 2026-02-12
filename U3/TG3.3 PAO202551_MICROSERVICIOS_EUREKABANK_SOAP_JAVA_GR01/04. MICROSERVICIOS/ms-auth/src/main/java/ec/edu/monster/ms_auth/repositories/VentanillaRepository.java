package ec.edu.monster.ms_auth.repositories;

import ec.edu.monster.ms_auth.models.Ventanilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentanillaRepository extends JpaRepository<Ventanilla, String> {
    List<Ventanilla> findByEstado(String estado);
}
