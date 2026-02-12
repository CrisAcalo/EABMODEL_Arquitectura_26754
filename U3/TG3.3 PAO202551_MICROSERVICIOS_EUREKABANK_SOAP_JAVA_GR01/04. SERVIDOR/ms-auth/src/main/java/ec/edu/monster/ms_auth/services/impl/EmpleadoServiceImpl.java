package ec.edu.monster.ms_auth.services.impl;

import ec.edu.monster.ms_auth.dtos.RespuestaDTO;
import ec.edu.monster.ms_auth.models.Empleado;
import ec.edu.monster.ms_auth.repositories.EmpleadoRepository;
import ec.edu.monster.ms_auth.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ec.edu.monster.ms_auth.services.ContadorService contadorService;

    @Override
    public RespuestaDTO registrar(Empleado empleado) {
        // Generar código si no viene en el request (Lógica Legacy)
        if (empleado.getCodigo() == null || empleado.getCodigo().isEmpty()) {
            try {
                empleado.setCodigo(contadorService.generarCodigo("Empleado"));
            } catch (Exception e) {
                return RespuestaDTO.error("Error al generar código de empleado: " + e.getMessage(), "ERR005");
            }
        }

        if (empleadoRepository.findById(empleado.getCodigo()).isPresent()) {
            return RespuestaDTO.error("El código de empleado ya existe", "ERR001");
        }
        if (empleadoRepository.findByUsuario(empleado.getUsuario()).isPresent()) {
            return RespuestaDTO.error("El usuario ya existe", "ERR002");
        }
        try {
            // Hashear password antes de guardar
            String hashedPassword = ec.edu.monster.ms_auth.utils.PasswordUtils.hashPassword(empleado.getClave());
            empleado.setClave(hashedPassword);

            Empleado saved = empleadoRepository.save(empleado);
            return RespuestaDTO.exito("Empleado registrado correctamente", saved);
        } catch (Exception e) {
            return RespuestaDTO.error("Error al registrar empleado: " + e.getMessage(), "ERR500");
        }
    }

    @Override
    public RespuestaDTO actualizar(Empleado empleado) {
        if (!empleadoRepository.existsById(empleado.getCodigo())) {
            return RespuestaDTO.error("El empleado no existe", "ERR004");
        }
        // Verificar si se cambia el usuario y si ya existe otro con ese usuario
        Optional<Empleado> existingUser = empleadoRepository.findByUsuario(empleado.getUsuario());
        if (existingUser.isPresent() && !existingUser.get().getCodigo().equals(empleado.getCodigo())) {
            return RespuestaDTO.error("El nombre de usuario ya está en uso por otro empleado", "ERR002");
        }

        try {
            // Lógica Legacy: NO se actualiza usuario ni clave en este método
            Empleado actual = empleadoRepository.findById(empleado.getCodigo()).get();
            empleado.setUsuario(actual.getUsuario());
            empleado.setClave(actual.getClave());

            Empleado updated = empleadoRepository.save(empleado);
            return RespuestaDTO.exito("Empleado actualizado correctamente", updated);
        } catch (Exception e) {
            return RespuestaDTO.error("Error al actualizar empleado: " + e.getMessage(), "ERR500");
        }
    }

    @Override
    public RespuestaDTO eliminar(String codigo) {
        if (!empleadoRepository.existsById(codigo)) {
            return RespuestaDTO.error("El empleado no existe", "ERR004");
        }
        try {
            empleadoRepository.deleteById(codigo);
            return RespuestaDTO.exito("Empleado eliminado correctamente", null);
        } catch (Exception e) {
            return RespuestaDTO.error("Error al eliminar empleado (posible violación de integridad): " + e.getMessage(),
                    "ERR500");
        }
    }

    @Override
    public RespuestaDTO obtener(String codigo) {
        Optional<Empleado> empleado = empleadoRepository.findById(codigo);
        if (empleado.isPresent()) {
            return RespuestaDTO.exito("Empleado encontrado", empleado.get());
        } else {
            return RespuestaDTO.error("El empleado no existe", "ERR004");
        }
    }

    @Override
    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }

    @Override
    public RespuestaDTO cambiarClave(String codigo, String claveActual, String claveNueva) {
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(codigo);
        if (empleadoOpt.isEmpty()) {
            return RespuestaDTO.error("El empleado no existe", "ERR004");
        }
        Empleado empleado = empleadoOpt.get();

        // Verificar clave actual con hash
        if (!ec.edu.monster.ms_auth.utils.PasswordUtils.verificarPassword(claveActual, empleado.getClave())) {
            return RespuestaDTO.error("La clave actual es incorrecta", "ERR003");
        }

        // Hashear nueva clave
        String hashedPassword = ec.edu.monster.ms_auth.utils.PasswordUtils.hashPassword(claveNueva);
        empleado.setClave(hashedPassword);

        empleadoRepository.save(empleado);
        return RespuestaDTO.exito("Clave actualizada correctamente", null);
    }
}
