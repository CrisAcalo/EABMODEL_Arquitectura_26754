package ec.edu.monster.ms_auth.repositories;

import ec.edu.monster.ms_auth.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

    // Método para validar credenciales (Hashing se manejará en servicio)
    // Por compatibilidad con sistema anterior que guardaba clave en texto plano o
    // hash simple
    Optional<Empleado> findByUsuarioAndClave(String usuario, String clave);

    Optional<Empleado> findByUsuario(String usuario);
}
