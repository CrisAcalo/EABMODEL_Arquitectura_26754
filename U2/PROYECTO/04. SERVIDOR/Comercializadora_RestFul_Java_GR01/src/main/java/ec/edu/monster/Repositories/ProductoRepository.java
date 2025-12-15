package ec.edu.monster.Repositories;

import ec.edu.monster.Models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Producto
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Buscar por código
    Optional<Producto> findByCodigo(String codigo);

    // Verificar si existe un código
    boolean existsByCodigo(String codigo);

    // Verificar si existe un código excluyendo un ID
    @Query("SELECT COUNT(p) > 0 FROM Producto p WHERE p.codigo = :codigo AND p.productoId != :productoId")
    boolean existsByCodigoAndIdNot(@Param("codigo") String codigo, @Param("productoId") Integer productoId);

    // Buscar por categoría
    List<Producto> findByCategoriaAndEstado(String categoria, String estado);

    // Buscar por rango de precio
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioMin AND :precioMax AND p.estado = 'ACTIVO' ORDER BY p.precio")
    List<Producto> findByPrecioBetween(@Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax);

    // Listar todos los activos
    List<Producto> findByEstadoOrderByNombreAsc(String estado);

    // Verificar stock
    @Query("SELECT p.stock >= :cantidad FROM Producto p WHERE p.productoId = :productoId")
    boolean tieneStock(@Param("productoId") Integer productoId, @Param("cantidad") Integer cantidad);

    // Actualizar stock
    @Modifying
    @Query("UPDATE Producto p SET p.stock = p.stock - :cantidad WHERE p.productoId = :productoId")
    void reducirStock(@Param("productoId") Integer productoId, @Param("cantidad") Integer cantidad);
}
