package ec.edu.monster.ms_websocket.services;

import ec.edu.monster.ms_websocket.models.BloqueoCuenta;
import ec.edu.monster.ms_websocket.repositories.BloqueoCuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BloqueoService {

    private static final Logger LOGGER = Logger.getLogger(BloqueoService.class.getName());
    private static final int TIMEOUT_MINUTOS = 2;

    @Autowired
    private BloqueoCuentaRepository repository;

    /**
     * Intenta bloquear una cuenta para una ventanilla.
     * Retorna true si tuvo éxito, false si ya está bloqueada.
     */
    @Transactional
    public boolean bloquearCuenta(String codigoCuenta, String codigoVentanilla) {
        try {
            // Limpiar expirados primero
            repository.limpiarBloqueosExpirados();

            // Verificar si ya está bloqueada
            Optional<BloqueoCuenta> existente = repository.findBloqueoActivo(codigoCuenta);
            if (existente.isPresent()) {
                // Si ya la tiene la misma ventanilla, es re-entrada
                if (existente.get().getCodigoVentanilla().equals(codigoVentanilla)) {
                    return true;
                }
                return false;
            }

            // Crear nuevo bloqueo
            BloqueoCuenta bloqueo = new BloqueoCuenta();
            bloqueo.setCodigoCuenta(codigoCuenta);
            bloqueo.setCodigoVentanilla(codigoVentanilla);
            bloqueo.setFechaInicio(LocalDateTime.now());
            bloqueo.setFechaExpiracion(LocalDateTime.now().plusMinutes(TIMEOUT_MINUTOS));
            bloqueo.setEstado("ACTIVO");
            repository.save(bloqueo);

            LOGGER.info("Cuenta " + codigoCuenta + " bloqueada por ventanilla " + codigoVentanilla);
            return true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al bloquear cuenta", e);
            return false;
        }
    }

    /**
     * Libera el bloqueo de una cuenta.
     */
    @Transactional
    public void liberarCuenta(String codigoCuenta, String codigoVentanilla) {
        repository.liberarCuenta(codigoCuenta, codigoVentanilla);
        LOGGER.info("Cuenta " + codigoCuenta + " liberada por ventanilla " + codigoVentanilla);
    }

    /**
     * Obtiene el bloqueo activo de una cuenta.
     */
    public Optional<BloqueoCuenta> obtenerBloqueo(String codigoCuenta) {
        return repository.findBloqueoActivo(codigoCuenta);
    }

    /**
     * Obtiene todos los bloqueos activos.
     */
    public List<BloqueoCuenta> obtenerBloqueosActivos() {
        return repository.findBloqueosActivos();
    }

    /**
     * Verifica si una cuenta está bloqueada.
     */
    public boolean estaBloqueada(String codigoCuenta) {
        return repository.estaBloqueada(codigoCuenta);
    }

    /**
     * Libera todos los bloqueos de una ventanilla.
     */
    @Transactional
    public void liberarTodosDeVentanilla(String codigoVentanilla) {
        repository.liberarTodosDeVentanilla(codigoVentanilla);
        LOGGER.info("Liberados todos los bloqueos de ventanilla " + codigoVentanilla);
    }
}
