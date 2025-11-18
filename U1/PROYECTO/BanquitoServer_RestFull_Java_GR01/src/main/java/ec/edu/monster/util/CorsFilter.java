package ec.edu.monster.util;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro CORS (Cross-Origin Resource Sharing)
 * Permite que los clientes en diferentes computadoras accedan a los Web Services
 * 
 * @author Javi
 */
public class CorsFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No se requiere inicialización
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, 
                        FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        // Permitir acceso desde cualquier origen
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        // Permitir métodos HTTP
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        
        // Permitir headers
        response.setHeader("Access-Control-Allow-Headers", 
                "Content-Type, Authorization, X-Requested-With");
        
        // Tiempo de cache para preflight requests
        response.setHeader("Access-Control-Max-Age", "3600");
        
        // Permitir credenciales
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        // Para peticiones OPTIONS (preflight), responder directamente
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }
    
    @Override
    public void destroy() {
        // No se requiere limpieza
    }
}
