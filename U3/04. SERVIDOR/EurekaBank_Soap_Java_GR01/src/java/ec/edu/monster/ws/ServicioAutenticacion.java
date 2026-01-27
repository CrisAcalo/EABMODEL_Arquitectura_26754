package ec.edu.monster.ws;

import ec.edu.monster.models.Empleado;
import ec.edu.monster.models.dto.RespuestaDTO;
import ec.edu.monster.services.AutenticacionService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * Web Service SOAP para autenticación y gestión de empleados
 */
@WebService(serviceName = "ServicioAutenticacion")
public class ServicioAutenticacion {

    private final AutenticacionService service;

    public ServicioAutenticacion() {
        this.service = new AutenticacionService();
    }

    /**
     * Autentica un empleado en el sistema
     *
     * @param usuario Usuario del empleado
     * @param clave   Clave del empleado
     * @return RespuestaDTO con el resultado de la autenticación
     */
    @WebMethod(operationName = "login")
    public RespuestaDTO login(
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "clave") String clave) {
        return service.login(usuario, clave);
    }

    /**
     * Registra un nuevo empleado en el sistema
     *
     * @param empleado Datos del empleado a registrar
     * @return RespuestaDTO con el resultado del registro
     */
    @WebMethod(operationName = "registrarEmpleado")
    public RespuestaDTO registrarEmpleado(
            @WebParam(name = "empleado") Empleado empleado) {
        return service.registrarEmpleado(empleado);
    }

    /**
     * Cambia la clave de un empleado
     *
     * @param codigo       Código del empleado
     * @param claveActual  Clave actual
     * @param claveNueva   Nueva clave
     * @return RespuestaDTO con el resultado del cambio de clave
     */
    @WebMethod(operationName = "cambiarClave")
    public RespuestaDTO cambiarClave(
            @WebParam(name = "codigo") String codigo,
            @WebParam(name = "claveActual") String claveActual,
            @WebParam(name = "claveNueva") String claveNueva) {
        return service.cambiarClave(codigo, claveActual, claveNueva);
    }
}
