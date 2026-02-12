package ec.edu.monster.ms_websocket.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.monster.ms_websocket.models.BloqueoCuenta;
import ec.edu.monster.ms_websocket.services.BloqueoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BloqueoWebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = Logger.getLogger(BloqueoWebSocketHandler.class.getName());
    private static final Set<WebSocketSession> sesiones = Collections.synchronizedSet(new HashSet<>());
    private static final ConcurrentMap<String, ConcurrentLinkedQueue<String>> colasDeEspera = new ConcurrentHashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private BloqueoService bloqueoService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sesiones.add(session);
        LOGGER.info("Nueva sesión conectada: " + session.getId());
        enviarEstadoInicial(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sesiones.remove(session);
        LOGGER.info("Sesión desconectada: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOGGER.log(Level.SEVERE, "Error en sesión: " + session.getId(), exception);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        LOGGER.info("Mensaje recibido: " + payload);

        try {
            JsonNode json = mapper.readTree(payload);
            String accion = json.has("accion") ? json.get("accion").asText() : "";

            switch (accion) {
                case "BLOQUEAR":
                    procesarBloqueo(json);
                    break;
                case "LIBERAR":
                    procesarLiberacion(json);
                    break;
                case "VERIFICAR":
                    // Heartbeat — no action needed
                    break;
                default:
                    LOGGER.warning("Acción desconocida: " + accion);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error procesando mensaje: " + payload, e);
        }
    }

    private void enviarEstadoInicial(WebSocketSession session) {
        try {
            List<BloqueoCuenta> bloqueos = bloqueoService.obtenerBloqueosActivos();
            for (BloqueoCuenta bloqueo : bloqueos) {
                String jsonMsg = String.format(
                        "{\"tipo\":\"ESTADO_CUENTA\", \"cuenta\":\"%s\", \"estado\":\"BLOQUEADO\", \"ventanilla\":\"%s\"}",
                        bloqueo.getCodigoCuenta(), bloqueo.getCodigoVentanilla());
                session.sendMessage(new TextMessage(jsonMsg));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error enviando estado inicial", e);
        }
    }

    private void procesarBloqueo(JsonNode json) {
        String cuenta = json.get("cuenta").asText();
        String ventanilla = json.get("ventanilla").asText();

        // Obtener o crear la cola para esta cuenta
        ConcurrentLinkedQueue<String> cola = colasDeEspera.computeIfAbsent(cuenta, k -> new ConcurrentLinkedQueue<>());

        // Agregar a la cola si no está ya presente
        if (!cola.contains(ventanilla)) {
            cola.add(ventanilla);
        }

        try {
            String primero = cola.peek();

            if (primero != null && primero.equals(ventanilla)) {
                // Soy el primero, intento bloquear en BD
                boolean exito = bloqueoService.bloquearCuenta(cuenta, ventanilla);

                if (exito) {
                    broadcastBloqueado(cuenta, ventanilla);
                } else {
                    Optional<BloqueoCuenta> actual = bloqueoService.obtenerBloqueo(cuenta);
                    if (actual.isPresent() && actual.get().getCodigoVentanilla().equals(ventanilla)) {
                        broadcastBloqueado(cuenta, ventanilla);
                    } else if (actual.isPresent()) {
                        broadcastBloqueado(cuenta, actual.get().getCodigoVentanilla());
                    }
                }
            } else {
                // No soy el primero, estoy en cola
                Optional<BloqueoCuenta> actual = bloqueoService.obtenerBloqueo(cuenta);
                String ventanillaBloqueadora = actual.isPresent()
                        ? actual.get().getCodigoVentanilla()
                        : (primero != null ? primero : "DESCONOCIDO");
                broadcastBloqueado(cuenta, ventanillaBloqueadora);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al bloquear cuenta", e);
        }
    }

    private void procesarLiberacion(JsonNode json) {
        String cuenta = json.get("cuenta").asText();
        String ventanilla = json.get("ventanilla").asText();

        try {
            // 1. Liberar en BD
            bloqueoService.liberarCuenta(cuenta, ventanilla);

            // 2. Actualizar cola
            ConcurrentLinkedQueue<String> cola = colasDeEspera.get(cuenta);
            if (cola != null) {
                cola.remove(ventanilla);

                String siguiente = cola.peek();
                if (siguiente != null) {
                    // Asignar bloqueo al siguiente en cola
                    boolean exitoNext = bloqueoService.bloquearCuenta(cuenta, siguiente);
                    if (exitoNext) {
                        broadcastBloqueado(cuenta, siguiente);
                    } else {
                        broadcast(String.format(
                                "{\"tipo\":\"ESTADO_CUENTA\", \"cuenta\":\"%s\", \"estado\":\"LIBRE\"}", cuenta));
                    }
                } else {
                    colasDeEspera.remove(cuenta);
                    broadcast(String.format(
                            "{\"tipo\":\"ESTADO_CUENTA\", \"cuenta\":\"%s\", \"estado\":\"LIBRE\"}", cuenta));
                }
            } else {
                broadcast(String.format(
                        "{\"tipo\":\"ESTADO_CUENTA\", \"cuenta\":\"%s\", \"estado\":\"LIBRE\"}", cuenta));
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al liberar cuenta", e);
        }
    }

    private void broadcastBloqueado(String cuenta, String ventanilla) {
        String msg = String.format(
                "{\"tipo\":\"ESTADO_CUENTA\", \"cuenta\":\"%s\", \"estado\":\"BLOQUEADO\", \"ventanilla\":\"%s\"}",
                cuenta, ventanilla);
        broadcast(msg);
    }

    private void broadcast(String mensaje) {
        Set<WebSocketSession> sesionesCopia;
        synchronized (sesiones) {
            sesionesCopia = new HashSet<>(sesiones);
        }
        for (WebSocketSession s : sesionesCopia) {
            if (s.isOpen()) {
                try {
                    s.sendMessage(new TextMessage(mensaje));
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Error enviando mensaje a sesión: " + s.getId(), e);
                }
            }
        }
    }
}
