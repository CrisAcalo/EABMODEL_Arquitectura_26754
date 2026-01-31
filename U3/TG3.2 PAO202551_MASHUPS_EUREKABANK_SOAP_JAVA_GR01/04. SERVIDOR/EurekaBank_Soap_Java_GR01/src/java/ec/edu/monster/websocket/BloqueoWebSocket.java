package ec.edu.monster.websocket;

import ec.edu.monster.dal.BloqueoDAO;
import ec.edu.monster.models.Bloqueo;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.StringReader;

/**
 * WebSocket para gestión de bloqueos en tiempo real
 */
@ServerEndpoint("/ws/bloqueo")
public class BloqueoWebSocket {

    private static final Logger LOGGER = Logger.getLogger(BloqueoWebSocket.class.getName());
    private static final Set<Session> sesiones = Collections.synchronizedSet(new HashSet<>());
    private final BloqueoDAO bloqueoDAO = new BloqueoDAO();

    @OnOpen
    public void onOpen(Session session) {
        sesiones.add(session);
        LOGGER.info("Nueva sesión conectada: " + session.getId());

        // Enviar estado actual de todos los bloqueos al nuevo cliente
        enviarEstadoInicial(session);
    }

    @OnClose
    public void onClose(Session session) {
        sesiones.remove(session);
        LOGGER.info("Sesión desconectada: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.log(Level.SEVERE, "Error en sesión: " + session.getId(), throwable);
    }

    @OnMessage
    public void onMessage(String mensaje, Session session) {
        LOGGER.info("Mensaje recibido: " + mensaje);

        try (JsonReader jsonReader = Json.createReader(new StringReader(mensaje))) {
            JsonObject json = jsonReader.readObject();
            String accion = json.getString("accion", "");

            if ("BLOQUEAR".equals(accion)) {
                procesarBloqueo(json);
            } else if ("LIBERAR".equals(accion)) {
                procesarLiberacion(json);
            } else if ("VERIFICAR".equals(accion)) {
                // Solo reenvía estado (heartbeat o consulta)
                // Implementar si es necesario
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error procesando mensaje: " + mensaje, e);
        }
    }

    private void enviarEstadoInicial(Session session) {
        try {
            List<Bloqueo> bloqueos = bloqueoDAO.obtenerBloqueosActivos();
            for (Bloqueo bloqueo : bloqueos) {
                String jsonMsg = String.format(
                        "{\"tipo\":\"ESTADO_CUENTA\", \"cuenta\":\"%s\", \"estado\":\"BLOQUEADO\", \"ventanilla\":\"%s\"}",
                        bloqueo.getCodigoCuenta(), bloqueo.getCodigoVentanilla());
                session.getBasicRemote().sendText(jsonMsg);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error enviando estado inicial", e);
        }
    }

    private void procesarBloqueo(JsonObject json) {
        String cuenta = json.getString("cuenta");
        String ventanilla = json.getString("ventanilla");

        try {
            boolean exito = bloqueoDAO.bloquearCuenta(cuenta, ventanilla);
            if (exito) {
                // Broadcast a todos: CUENTA BLOQUEADA
                String msg = String.format(
                        "{\"tipo\":\"ESTADO_CUENTA\", \"cuenta\":\"%s\", \"estado\":\"BLOQUEADO\", \"ventanilla\":\"%s\"}",
                        cuenta, ventanilla);
                broadcast(msg);
            } else {
                // Si falla, significa que ya está bloqueda (o error).
                // Obtener quien la tiene bloqueada y notificar para actualizar la UI del
                // solicitante
                Bloqueo bloqueoActual = bloqueoDAO.obtenerBloqueo(cuenta);
                if (bloqueoActual != null) {
                    String msg = String.format(
                            "{\"tipo\":\"ESTADO_CUENTA\", \"cuenta\":\"%s\", \"estado\":\"BLOQUEADO\", \"ventanilla\":\"%s\"}",
                            cuenta, bloqueoActual.getCodigoVentanilla());

                    // Podríamos enviar solo al remitente, pero un broadcast asegura consistencia
                    // eventual
                    broadcast(msg);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al bloquear cuenta", e);
        }
    }

    private void procesarLiberacion(JsonObject json) {
        String cuenta = json.getString("cuenta");
        String ventanilla = json.getString("ventanilla");

        try {
            boolean exito = bloqueoDAO.liberarCuenta(cuenta, ventanilla);
            if (exito) {
                // Broadcast a todos: CUENTA LIBRE
                String msg = String.format("{\"tipo\":\"ESTADO_CUENTA\", \"cuenta\":\"%s\", \"estado\":\"LIBRE\"}",
                        cuenta);
                broadcast(msg);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al liberar cuenta", e);
        }
    }

    private void broadcast(String mensaje) {
        // Copiar la lista para evitar ConcurrentModificationException y bloquear el set
        // original lo menos posible
        Set<Session> sesionesCopia;
        synchronized (sesiones) {
            sesionesCopia = new HashSet<>(sesiones);
        }

        for (Session s : sesionesCopia) {
            if (s.isOpen()) {
                // Enviar asíncronamente para evitar bloquear el loop si un cliente es lento
                s.getAsyncRemote().sendText(mensaje);
            }
        }
    }
}
