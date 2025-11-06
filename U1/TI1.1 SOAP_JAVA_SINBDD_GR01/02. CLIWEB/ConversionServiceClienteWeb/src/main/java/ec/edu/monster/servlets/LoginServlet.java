package ec.edu.monster.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet de autenticación
 * @author Kewo
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final String USUARIO_VALIDO = "MONSTER";
    private static final String CONTRASENA_VALIDA = "MONSTER9";
    private static final int MAX_INTENTOS = 3;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(true);
        
        Integer intentos = (Integer) session.getAttribute("intentos");
        if (intentos == null) {
            intentos = 0;
        }
        
        if (intentos >= MAX_INTENTOS) {
            request.setAttribute("error", "Has superado el número máximo de intentos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");
        
        if (usuario == null || contrasena == null || usuario.trim().isEmpty() || contrasena.trim().isEmpty()) {
            request.setAttribute("error", "Debe ingresar usuario y contraseña.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        if (USUARIO_VALIDO.equals(usuario.trim()) && CONTRASENA_VALIDA.equals(contrasena)) {
            // Login exitoso
            session.setAttribute("usuarioAutenticado", usuario);
            session.setAttribute("intentos", 0);
            session.setMaxInactiveInterval(1800); // 30 minutos
            
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            // Login fallido
            intentos++;
            session.setAttribute("intentos", intentos);
            
            int intentosRestantes = MAX_INTENTOS - intentos;
            
            if (intentosRestantes > 0) {
                request.setAttribute("error", "Usuario o contraseña incorrectos.");
            } else {
                request.setAttribute("error", "Has superado el número máximo de intentos.");
            }
            
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}