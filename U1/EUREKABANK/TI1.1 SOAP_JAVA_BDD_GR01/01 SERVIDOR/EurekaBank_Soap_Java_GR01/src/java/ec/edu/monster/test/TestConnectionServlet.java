package ec.edu.monster.test;

import ec.edu.monster.db.ConexionDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * Servlet para probar la conexión a MySQL desde Payara
 */
@WebServlet(name = "TestConnectionServlet", urlPatterns = {"/test-connection"})
public class TestConnectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Test de Conexión MySQL</title>");
            out.println("<style>");
            out.println("body { font-family: Arial; margin: 40px; }");
            out.println(".success { color: green; font-weight: bold; }");
            out.println(".error { color: red; font-weight: bold; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Prueba de Conexión a Base de Datos MySQL</h1>");
            out.println("<hr>");

            // Probar conexión
            try {
                out.println("<h2>1. Probando conexión...</h2>");

                boolean isConnected = ConexionDB.testConnection();

                if (isConnected) {
                    out.println("<p class='success'>✓ Conexión exitosa!</p>");

                    out.println("<h2>2. Información de la base de datos:</h2>");
                    String dbInfo = ConexionDB.getDatabaseInfo();
                    out.println("<p>" + dbInfo + "</p>");

                    out.println("<hr>");
                    out.println("<h3 class='success'>RESULTADO: TODO FUNCIONA CORRECTAMENTE</h3>");

                } else {
                    out.println("<p class='error'>✗ Error de conexión!</p>");
                    out.println("<h3>Posibles causas:</h3>");
                    out.println("<ul>");
                    out.println("<li>MySQL no está corriendo en Docker</li>");
                    out.println("<li>Credenciales incorrectas en glassfish-resources.xml</li>");
                    out.println("<li>Puerto incorrecto (debe ser 3306)</li>");
                    out.println("</ul>");
                }

            } catch (Exception e) {
                out.println("<p class='error'>ERROR: " + e.getMessage() + "</p>");
                out.println("<h3>Stack Trace:</h3>");
                out.println("<pre>");
                e.printStackTrace(out);
                out.println("</pre>");
            }

            out.println("</body>");
            out.println("</html>");
        }
    }
}
