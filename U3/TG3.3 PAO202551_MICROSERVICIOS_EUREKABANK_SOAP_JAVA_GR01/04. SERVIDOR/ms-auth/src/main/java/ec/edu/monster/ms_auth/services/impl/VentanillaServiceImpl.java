package ec.edu.monster.ms_auth.services.impl;

import ec.edu.monster.ms_auth.dtos.RespuestaDTO;
import ec.edu.monster.ms_auth.models.Ventanilla;
import ec.edu.monster.ms_auth.repositories.EmpleadoRepository;
import ec.edu.monster.ms_auth.repositories.VentanillaRepository;
import ec.edu.monster.ms_auth.services.VentanillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class VentanillaServiceImpl implements VentanillaService {

    @Autowired
    private VentanillaRepository ventanillaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public RespuestaDTO registrar(Ventanilla ventanilla) {
        if (ventanilla.getCodigo() == null || ventanilla.getNombre() == null) {
            return RespuestaDTO.error("Código y nombre son obligatorios", "VEN004");
        }

        if (ventanillaRepository.existsById(ventanilla.getCodigo())) {
            return RespuestaDTO.error("Ya existe una ventanilla con ese código", "VEN005");
        }
        try {
            if (ventanilla.getEstado() == null || ventanilla.getEstado().trim().isEmpty()) {
                ventanilla.setEstado("ACTIVO");
            }
            Ventanilla saved = ventanillaRepository.save(ventanilla);
            return RespuestaDTO.exito("Ventanilla registrada correctamente", saved);
        } catch (Exception e) {
            return RespuestaDTO.error("Error al registrar ventanilla: " + e.getMessage(), "VEN006");
        }
    }

    @Override
    public RespuestaDTO actualizar(Ventanilla ventanilla) {
        if (!ventanillaRepository.existsById(ventanilla.getCodigo())) {
            return RespuestaDTO.error("Ventanilla no encontrada", "VEN002");
        }
        try {
            Ventanilla updated = ventanillaRepository.save(ventanilla);
            return RespuestaDTO.exito("Ventanilla actualizada correctamente", updated);
        } catch (Exception e) {
            return RespuestaDTO.error("Error al actualizar ventanilla: " + e.getMessage(), "VEN007");
        }
    }

    @Override
    public RespuestaDTO eliminar(String codigo) {
        if (!ventanillaRepository.existsById(codigo)) {
            return RespuestaDTO.error("Ventanilla no encontrada", "VEN002");
        }
        try {
            ventanillaRepository.deleteById(codigo);
            return RespuestaDTO.exito("Ventanilla eliminada correctamente", null);
        } catch (Exception e) {
            return RespuestaDTO.error("Error al eliminar ventanilla: " + e.getMessage(), "VEN009");
        }
    }

    @Override
    public RespuestaDTO obtener(String codigo) {
        Optional<Ventanilla> ventanilla = ventanillaRepository.findById(codigo);
        if (ventanilla.isPresent()) {
            return RespuestaDTO.exito("Ventanilla encontrada", ventanilla.get());
        } else {
            return RespuestaDTO.error("Ventanilla no encontrada", "VEN002");
        }
    }

    @Override
    public List<Ventanilla> listar() {
        return ventanillaRepository.findAll();
    }

    @Override
    public List<Ventanilla> listarActivas() {
        return ventanillaRepository.findByEstado("ACTIVO");
    }

    @Override
    public RespuestaDTO asignarEmpleado(String codigoVentanilla, String codigoEmpleado) {
        Optional<Ventanilla> ventanillaOpt = ventanillaRepository.findById(codigoVentanilla);
        if (ventanillaOpt.isEmpty()) {
            return RespuestaDTO.error("La ventanilla no existe", "VEN002");
        }

        // Permite desasignar si codigoEmpleado es null, similar al DAO legacy que
        // maneja null
        if (codigoEmpleado != null && !empleadoRepository.existsById(codigoEmpleado)) {
            return RespuestaDTO.error("Empleado no encontrado", "VEN002"); // Código de error reutilizado según legacy
        }

        try {
            Ventanilla ventanilla = ventanillaOpt.get();
            ventanilla.setCodigoEmpleado(codigoEmpleado);
            ventanillaRepository.save(ventanilla);
            // NOTA: El sistema legacy NO guarda histórico en tabla 'Asignado' en este
            // punto.
            return RespuestaDTO.exito("Empleado asignado correctamente", ventanilla);
        } catch (Exception e) {
            return RespuestaDTO.error("Error al asignar empleado: " + e.getMessage(), "VEN008");
        }
    }
}
