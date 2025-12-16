package ec.edu.monster.controllers;

import ec.edu.monster.dtos.ActualizarSucursalDTO;
import ec.edu.monster.dtos.CoordenadasDTO;
import ec.edu.monster.dtos.CrearSucursalDTO;
import ec.edu.monster.dtos.RespuestaDTO;
import ec.edu.monster.services.SucursalService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Controlador para gestionar operaciones relacionadas con sucursales
 * 
 * @author EurekaBank
 */
@Path("/api/sucursal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SucursalResource {

    private final SucursalService sucursalService = new SucursalService();

    @GET
    public Response obtenerTodas() {
        RespuestaDTO respuesta = sucursalService.obtenerTodasLasSucursales();
        if (respuesta.isExitoso()) {
            return Response.ok(respuesta).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
        }
    }

    @GET
    @Path("/{codigo}")
    public Response obtenerPorCodigo(@PathParam("codigo") int codigo) {
        RespuestaDTO respuesta = sucursalService.obtenerSucursalPorCodigo(codigo);
        if (respuesta.isExitoso()) {
            return Response.ok(respuesta).build();
        } else if ("VAL001".equals(respuesta.getCodigoError())) {
            return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
        } else if ("SUC001".equals(respuesta.getCodigoError())) {
            return Response.status(Response.Status.NOT_FOUND).entity(respuesta).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
        }
    }

    @POST
    public Response crearSucursal(CrearSucursalDTO sucursalDTO) {
        RespuestaDTO respuesta = sucursalService.crearSucursal(sucursalDTO);
        if (respuesta.isExitoso()) {
            return Response.status(Response.Status.CREATED).entity(respuesta).build();
        } else { // Manejo simplificado de errores, en prod se afinaría mas
            return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
        }
    }

    @PATCH
    @Path("/{codigo}")
    public Response actualizarSucursal(@PathParam("codigo") int codigo, ActualizarSucursalDTO sucursalDTO) {
        RespuestaDTO respuesta = sucursalService.actualizarSucursal(codigo, sucursalDTO);
        if (respuesta.isExitoso()) {
            return Response.ok(respuesta).build();
        } else if ("SUC001".equals(respuesta.getCodigoError())) {
            return Response.status(Response.Status.NOT_FOUND).entity(respuesta).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
        }
    }

    @DELETE
    @Path("/{codigo}")
    public Response eliminarSucursal(@PathParam("codigo") int codigo) {
        RespuestaDTO respuesta = sucursalService.eliminarSucursal(codigo);
        if (respuesta.isExitoso()) {
            return Response.ok(respuesta).build();
        } else if ("SUC001".equals(respuesta.getCodigoError())) {
            return Response.status(Response.Status.NOT_FOUND).entity(respuesta).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
        }
    }

    @PATCH
    @Path("/{codigo}/coordenadas")
    public Response actualizarCoordenadas(@PathParam("codigo") int codigo, CoordenadasDTO coordenadas) {
        RespuestaDTO respuesta = sucursalService.actualizarCoordenadas(codigo, coordenadas);
        if (respuesta.isExitoso()) {
            return Response.ok(respuesta).build();
        } else if ("SUC001".equals(respuesta.getCodigoError())) {
            return Response.status(Response.Status.NOT_FOUND).entity(respuesta).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
        }
    }
}
