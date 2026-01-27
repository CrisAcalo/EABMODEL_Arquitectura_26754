package ec.edu.monster.ws;

import ec.edu.monster.dal.ClienteDAO;
import ec.edu.monster.models.Cliente;
import ec.edu.monster.models.dto.RespuestaDTO;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio Web SOAP para gestión de Clientes
 */
@WebService(serviceName = "ServicioCliente")
public class ServicioCliente {

    private static final Logger LOGGER = Logger.getLogger(ServicioCliente.class.getName());
    private final ClienteDAO clienteDAO = new ClienteDAO();

    /**
     * Obtiene todos los clientes
     */
    @WebMethod(operationName = "listarClientes")
    public List<Cliente> listarClientes() {
        try {
            return clienteDAO.obtenerTodos();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar clientes", e);
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Obtiene un cliente por su código
     */
    @WebMethod(operationName = "obtenerCliente")
    public RespuestaDTO obtenerCliente(@WebParam(name = "codigo") String codigo) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Cliente cliente = clienteDAO.obtenerPorCodigo(codigo);
            if (cliente != null) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Cliente encontrado");
                respuesta.setDatos(cliente);
            } else {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cliente no encontrado");
                respuesta.setCodigoError("CLI002");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener cliente", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener cliente: " + e.getMessage());
            respuesta.setCodigoError("CLI003");
        }
        return respuesta;
    }

    /**
     * Obtiene un cliente por su DNI
     */
    @WebMethod(operationName = "obtenerClientePorDNI")
    public RespuestaDTO obtenerClientePorDNI(@WebParam(name = "dni") String dni) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Cliente cliente = clienteDAO.obtenerPorDNI(dni);
            if (cliente != null) {
                respuesta.setExitoso(true);
                respuesta.setMensaje("Cliente encontrado");
                respuesta.setDatos(cliente);
            } else {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cliente no encontrado con DNI: " + dni);
                respuesta.setCodigoError("CLI002");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener cliente por DNI", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al obtener cliente: " + e.getMessage());
            respuesta.setCodigoError("CLI003");
        }
        return respuesta;
    }

    /**
     * Registra un nuevo cliente
     */
    @WebMethod(operationName = "registrarCliente")
    public RespuestaDTO registrarCliente(@WebParam(name = "cliente") Cliente cliente) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            // Validar datos básicos
            if (cliente.getDni() == null || cliente.getDni().trim().isEmpty()) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("El DNI es obligatorio");
                respuesta.setCodigoError("CLI004");
                return respuesta;
            }

            // Verificar si ya existe
            Cliente existente = clienteDAO.obtenerPorDNI(cliente.getDni());
            if (existente != null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Ya existe un cliente con el DNI: " + cliente.getDni());
                respuesta.setCodigoError("CLI005");
                return respuesta;
            }

            // Generar código automático si no tiene
            if (cliente.getCodigo() == null || cliente.getCodigo().isEmpty()) {
                cliente.setCodigo(clienteDAO.generarCodigoCliente());
            }

            clienteDAO.insertar(cliente);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Cliente registrado correctamente");
            respuesta.setDatos(cliente);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar cliente", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al registrar cliente: " + e.getMessage());
            respuesta.setCodigoError("CLI006");
        }
        return respuesta;
    }

    /**
     * Actualiza un cliente existente
     */
    @WebMethod(operationName = "actualizarCliente")
    public RespuestaDTO actualizarCliente(@WebParam(name = "cliente") Cliente cliente) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Cliente existente = clienteDAO.obtenerPorCodigo(cliente.getCodigo());
            if (existente == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cliente no encontrado");
                respuesta.setCodigoError("CLI002");
                return respuesta;
            }

            clienteDAO.actualizar(cliente);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Cliente actualizado correctamente");
            respuesta.setDatos(cliente);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar cliente", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al actualizar cliente: " + e.getMessage());
            respuesta.setCodigoError("CLI007");
        }
        return respuesta;
    }

    /**
     * Elimina un cliente
     */
    @WebMethod(operationName = "eliminarCliente")
    public RespuestaDTO eliminarCliente(@WebParam(name = "codigo") String codigo) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            Cliente existente = clienteDAO.obtenerPorCodigo(codigo);
            if (existente == null) {
                respuesta.setExitoso(false);
                respuesta.setMensaje("Cliente no encontrado");
                respuesta.setCodigoError("CLI002");
                return respuesta;
            }

            clienteDAO.eliminar(codigo);
            respuesta.setExitoso(true);
            respuesta.setMensaje("Cliente eliminado correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar cliente", e);
            respuesta.setExitoso(false);
            respuesta.setMensaje("Error al eliminar cliente: " + e.getMessage());
            respuesta.setCodigoError("CLI008");
        }
        return respuesta;
    }
}
