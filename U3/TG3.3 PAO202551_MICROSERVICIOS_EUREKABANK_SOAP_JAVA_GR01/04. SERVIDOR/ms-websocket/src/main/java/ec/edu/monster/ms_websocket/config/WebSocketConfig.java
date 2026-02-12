package ec.edu.monster.ms_websocket.config;

import ec.edu.monster.ms_websocket.ws.BloqueoWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final BloqueoWebSocketHandler bloqueoHandler;

    public WebSocketConfig(BloqueoWebSocketHandler bloqueoHandler) {
        this.bloqueoHandler = bloqueoHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(bloqueoHandler, "/ws/bloqueo")
                .setAllowedOrigins("*");
    }
}
