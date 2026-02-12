package ec.edu.monster.ms_auth.services.impl;

import ec.edu.monster.ms_auth.dtos.RespuestaDTO;
import ec.edu.monster.ms_auth.models.Sucursal;
import ec.edu.monster.ms_auth.repositories.SucursalRepository;
import ec.edu.monster.ms_auth.services.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SucursalServiceImpl implements SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ec.edu.monster.ms_auth.services.ContadorService contadorService;

    @Override
    public RespuestaDTO registrar(Sucursal sucursal) {
        if (sucursal.getNombre() == null) {
            return RespuestaDTO.error("El nombre es obligatorio", "SUC004");
        }

        if (sucursal.getCodigo() == null || sucursal.getCodigo().isEmpty()) {
            try {
                sucursal.setCodigo(contadorService.generarCodigo("Sucursal"));
            } catch (Exception e) {
                return RespuestaDTO.error("Error al generar código de sucursal: " + e.getMessage(), "ERR005");
            }
        }

        if (sucursalRepository.existsById(sucursal.getCodigo())) {
            return RespuestaDTO.error("El código de sucursal ya existe", "ERR001");
        }
        try {
            Sucursal saved = sucursalRepository.save(sucursal);
            return RespuestaDTO.exito("Sucursal registrada correctamente", saved);
        } catch (Exception e) {
            return RespuestaDTO.error("Error al registrar sucursal: " + e.getMessage(), "ERR500");
        }
    }

    @Override
    public RespuestaDTO actualizar(Sucursal sucursal) {
        if (!sucursalRepository.existsById(sucursal.getCodigo())) {
            return RespuestaDTO.error("La sucursal no existe", "ERR004");
        }
        try {
            Sucursal updated = sucursalRepository.save(sucursal);
            return RespuestaDTO.exito("Sucursal actualizada correctamente", updated);
        } catch (Exception e) {
            return RespuestaDTO.error("Error al actualizar sucursal: " + e.getMessage(), "ERR500");
        }
    }

    @Override
    public RespuestaDTO eliminar(String codigo) {
        if (!sucursalRepository.existsById(codigo)) {
            return RespuestaDTO.error("La sucursal no existe", "ERR004");
        }
        try {
            sucursalRepository.deleteById(codigo);
            return RespuestaDTO.exito("Sucursal eliminada correctamente", null);
        } catch (Exception e) {
            return RespuestaDTO.error("Error al eliminar sucursal: " + e.getMessage(), "ERR500");
        }
    }

    @Override
    public RespuestaDTO obtener(String codigo) {
        Optional<Sucursal> sucursal = sucursalRepository.findById(codigo);
        if (sucursal.isPresent()) {
            return RespuestaDTO.exito("Sucursal encontrada", sucursal.get());
        } else {
            return RespuestaDTO.error("La sucursal no existe", "ERR004");
        }
    }

    @Override
    public List<Sucursal> listar() {
        return sucursalRepository.findAll();
    }
}
