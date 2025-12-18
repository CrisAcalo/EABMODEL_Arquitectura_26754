package ec.edu.monster.Repositories;

import ec.edu.monster.Models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Factura
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    // Buscar por número de factura
    Optional<Factura> findByNumeroFactura(String numeroFactura);

    // Buscar por cédula del cliente
    List<Factura> findByCedulaClienteOrderByFechaEmisionDesc(String cedulaCliente);

    // Obtener el último número de factura para generar el siguiente
    @Query("SELECT MAX(f.facturaId) FROM Factura f")
    Integer findMaxFacturaId();
}
