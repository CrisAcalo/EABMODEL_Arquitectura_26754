package ec.edu.monster.services;

import ec.edu.monster.dal.SucursalDAO;
import ec.edu.monster.dtos.ActualizarSucursalDTO;
import ec.edu.monster.dtos.CoordenadasDTO;
import ec.edu.monster.dtos.CrearSucursalDTO;
import ec.edu.monster.dtos.RespuestaDTO;
import ec.edu.monster.dtos.SucursalDetalleDTO;
import ec.edu.monster.dtos.SucursalResumenDTO;
import ec.edu.monster.models.Sucursal;
import ec.edu.monster.validators.SucursalValidator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de lógica de negocio para sucursales
 * 
 * @author EurekaBank
 */
public class SucursalService {

    private final SucursalDAO sucursalDAO;

    public SucursalService() {
        this.sucursalDAO = new SucursalDAO();
    }

    public RespuestaDTO obtenerTodasLasSucursales() {
        try {
            List<Sucursal> sucursales = sucursalDAO.listarTodas();
            List<SucursalResumenDTO> resumenes = new ArrayList<>();

            for (Sucursal s : sucursales) {
                SucursalResumenDTO dto = new SucursalResumenDTO();
                dto.setCodigo(s.getCodigo());
                dto.setNombre(s.getNombre());
                dto.setCiudad(s.getCiudad());
                dto.setContadorCuentas(s.getContadorCuentas());
                dto.setLatitud(s.getLatitud());
                dto.setLongitud(s.getLongitud());
                dto.setDireccion(s.getDireccion());
                dto.setTieneCoordenadas(s.getLatitud() != null && s.getLongitud() != null);

                String dir = s.getDireccion() != null ? s.getDireccion() : "";
                String ciu = s.getCiudad() != null ? s.getCiudad() : "";
                String completa = dir;
                if (!dir.isEmpty() && !ciu.isEmpty()) {
                    completa += ", " + ciu;
                } else if (dir.isEmpty()) {
                    completa = ciu;
                }
                dto.setDireccionCompleta(completa);

                resumenes.add(dto);
            }

            return new RespuestaDTO(true, "Sucursales obtenidas correctamente", resumenes);
        } catch (SQLException ex) {
            return new RespuestaDTO(false, "Error al obtener sucursales: " + ex.getMessage(), "SRV001");
        }
    }

    public RespuestaDTO obtenerSucursalPorCodigo(int codigo) {
        if (codigo <= 0) {
            return new RespuestaDTO(false, "El código de sucursal debe ser mayor a cero", "VAL001");
        }

        try {
            Sucursal sucursal = sucursalDAO.obtenerPorCodigo(codigo);
            if (!SucursalValidator.existe(sucursal)) {
                return new RespuestaDTO(false, "Sucursal no encontrada", "SUC001");
            }

            SucursalDetalleDTO dto = mappingSucursalDetalleDTO(sucursal);

            return new RespuestaDTO(true, "Sucursal encontrada", dto);
        } catch (SQLException ex) {
            return new RespuestaDTO(false, "Error al obtener sucursal: " + ex.getMessage(), "SRV001");
        }
    }

    public RespuestaDTO crearSucursal(CrearSucursalDTO dto) {
        if (dto == null) {
            return new RespuestaDTO(false, "Los datos de la sucursal son requeridos", "VAL002");
        }

        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre() != null ? dto.getNombre().trim() : null);
        sucursal.setCiudad(dto.getCiudad() != null ? dto.getCiudad().trim() : null);
        sucursal.setDireccion(dto.getDireccion() != null ? dto.getDireccion().trim() : null);
        sucursal.setLatitud(dto.getLatitud());
        sucursal.setLongitud(dto.getLongitud());
        sucursal.setContadorCuentas(0);

        SucursalValidator.ValidacionResultado validacion = SucursalValidator.validarParaCreacion(sucursal);
        if (!validacion.esValida) {
            return new RespuestaDTO(false, validacion.mensajeError, "VAL003");
        }

        try {
            int nuevoCodigo = sucursalDAO.insertar(sucursal);
            if (nuevoCodigo <= 0) {
                return new RespuestaDTO(false, "Error al crear la sucursal", "SUC004");
            }

            Sucursal sucursalCreada = sucursalDAO.obtenerPorCodigo(nuevoCodigo);
            SucursalDetalleDTO detalleDTO = mappingSucursalDetalleDTO(sucursalCreada);

            return new RespuestaDTO(true, "Sucursal creada correctamente", detalleDTO);
        } catch (SQLException ex) {
            return new RespuestaDTO(false, "Error al crear sucursal: " + ex.getMessage(), "SRV001");
        }
    }

    public RespuestaDTO actualizarSucursal(int codigo, ActualizarSucursalDTO dto) {
        if (codigo <= 0 || dto == null) {
            return new RespuestaDTO(false, "Datos inválidos para actualización", "VAL001");
        }

        try {
            Sucursal sucursalExistente = sucursalDAO.obtenerPorCodigo(codigo);
            if (!SucursalValidator.existe(sucursalExistente)) {
                return new RespuestaDTO(false, "Sucursal no encontrada", "SUC001");
            }

            Sucursal sucursalActualizada = new Sucursal();
            sucursalActualizada.setCodigo(codigo);
            sucursalActualizada
                    .setNombre(dto.getNombre() != null ? dto.getNombre().trim() : sucursalExistente.getNombre());
            sucursalActualizada
                    .setCiudad(dto.getCiudad() != null ? dto.getCiudad().trim() : sucursalExistente.getCiudad());
            sucursalActualizada.setDireccion(
                    dto.getDireccion() != null ? dto.getDireccion().trim() : sucursalExistente.getDireccion());
            sucursalActualizada.setContadorCuentas(sucursalExistente.getContadorCuentas());
            // Lógica de actualización parcial de coordenadas: si viene null, mantenemos el
            // existente.
            sucursalActualizada
                    .setLatitud(dto.getLatitud() != null ? dto.getLatitud() : sucursalExistente.getLatitud());
            sucursalActualizada
                    .setLongitud(dto.getLongitud() != null ? dto.getLongitud() : sucursalExistente.getLongitud());

            SucursalValidator.ValidacionResultado validacion = SucursalValidator
                    .validarParaActualizacion(sucursalActualizada);
            if (!validacion.esValida) {
                return new RespuestaDTO(false, validacion.mensajeError, "VAL003");
            }

            boolean actualizado = sucursalDAO.actualizar(sucursalActualizada);
            if (!actualizado) {
                return new RespuestaDTO(false, "Error al actualizar la sucursal", "SUC005");
            }

            // Refetch para asegurar datos persistidos
            Sucursal sucursalFinal = sucursalDAO.obtenerPorCodigo(codigo);
            SucursalDetalleDTO detalleDTO = mappingSucursalDetalleDTO(sucursalFinal);

            return new RespuestaDTO(true, "Sucursal actualizada correctamente", detalleDTO);

        } catch (SQLException ex) {
            return new RespuestaDTO(false, "Error al actualizar sucursal: " + ex.getMessage(), "SRV001");
        }
    }

    public RespuestaDTO eliminarSucursal(int codigo) {
        if (codigo <= 0) {
            return new RespuestaDTO(false, "El código de sucursal debe ser mayor a cero", "VAL001");
        }

        try {
            Sucursal sucursalExistente = sucursalDAO.obtenerPorCodigo(codigo);
            if (!SucursalValidator.existe(sucursalExistente)) {
                return new RespuestaDTO(false, "Sucursal no encontrada", "SUC001");
            }

            if (!SucursalValidator.puedeSerEliminada(sucursalExistente)) {
                return new RespuestaDTO(false, "No se puede eliminar la sucursal porque tiene "
                        + sucursalExistente.getContadorCuentas() + " cuentas asociadas", "SUC007");
            }

            boolean eliminado = sucursalDAO.eliminar(codigo);
            if (!eliminado) {
                return new RespuestaDTO(false, "Error al eliminar la sucursal", "SUC006");
            }

            return new RespuestaDTO(true, "Sucursal eliminada correctamente", null);
        } catch (SQLException ex) {
            return new RespuestaDTO(false, "Error al eliminar sucursal: " + ex.getMessage(), "SRV001");
        }
    }

    public RespuestaDTO actualizarCoordenadas(int codigo, CoordenadasDTO coordenadas) {
        if (codigo <= 0 || coordenadas == null) {
            return new RespuestaDTO(false, "Código o coordenadas inválidas", "VAL001");
        }

        if (!SucursalValidator.coordenadasValidas(coordenadas.getLatitud(), coordenadas.getLongitud())) {
            return new RespuestaDTO(false, "Coordenadas inválidas", "VAL003");
        }

        try {
            Sucursal sucursalExistente = sucursalDAO.obtenerPorCodigo(codigo);
            if (!SucursalValidator.existe(sucursalExistente)) {
                return new RespuestaDTO(false, "Sucursal no encontrada", "SUC001");
            }

            boolean actualizado = sucursalDAO.actualizarCoordenadas(codigo, coordenadas.getLatitud(),
                    coordenadas.getLongitud());
            if (!actualizado) {
                return new RespuestaDTO(false, "Error al actualizar coordenadas", "SUC002");
            }

            return new RespuestaDTO(true, "Coordenadas actualizadas correctamente", null);
        } catch (SQLException ex) {
            return new RespuestaDTO(false, "Error interno: " + ex.getMessage(), "SRV001");
        }
    }

    private SucursalDetalleDTO mappingSucursalDetalleDTO(Sucursal s) {
        SucursalDetalleDTO dto = new SucursalDetalleDTO();
        dto.setCodigo(s.getCodigo());
        dto.setNombre(s.getNombre());
        dto.setCiudad(s.getCiudad());
        dto.setDireccion(s.getDireccion());
        dto.setContadorCuentas(s.getContadorCuentas());
        dto.setLatitud(s.getLatitud());
        dto.setLongitud(s.getLongitud());
        dto.setTieneCoordenadas(s.getLatitud() != null && s.getLongitud() != null);

        String dir = s.getDireccion() != null ? s.getDireccion() : "";
        String ciu = s.getCiudad() != null ? s.getCiudad() : "";
        String completa = dir;
        if (!dir.isEmpty() && !ciu.isEmpty()) {
            completa += ", " + ciu;
        } else if (dir.isEmpty()) {
            completa = ciu;
        }
        dto.setDireccionCompleta(completa);

        return dto;
    }
}
