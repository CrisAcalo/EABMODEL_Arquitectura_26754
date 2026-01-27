package ec.edu.monster.Repositories;

import ec.edu.monster.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para operaciones de Cliente
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Buscar cliente por cédula
     */
    Optional<Cliente> findByCedula(String cedula);
}
