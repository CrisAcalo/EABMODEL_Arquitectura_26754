/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ec.edu.monster.servlets;

import ec.edu.monster.masa.*;
import ec.edu.monster.util.ConfigManager;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.net.URL;

/**
 *
 * @author Kewo
 */
@WebServlet(name = "MasaServlet", urlPatterns = {"/MasaServlet"})
public class MasaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String tipoConversion = request.getParameter("tipoConversion");
        String valor = request.getParameter("valor");
        
        try {
            // Obtener URL dinámica desde config.properties
            String serviceName = ConfigManager.getProperty("servicio.masa");
            URL wsdlURL = new URL(ConfigManager.getWSDLURL(serviceName));
            QName qname = new QName("http://ws.monster.edu.ec/", serviceName);
            
            // Crear servicio con URL dinámica
            MasaService_Service service = new MasaService_Service(wsdlURL, qname);
            MasaService port = service.getMasaServicePort();
            
            ConversionResult resultado = null;
            
            switch (tipoConversion) {
                case "kilogramoQuintal":
                    resultado = port.kilogramoAQuintal(valor);
                    break;
                case "quintalKilogramo":
                    resultado = port.quintalAKilogramo(valor);
                    break;
                case "kilogramoLibra":
                    resultado = port.kilogramoALibra(valor);
                    break;
                case "libraKilogramo":
                    resultado = port.libraAKilogramo(valor);
                    break;
                case "quintalLibra":
                    resultado = port.quintalALibra(valor);
                    break;
                case "libraQuintal":
                    resultado = port.libraAQuintal(valor);
                    break;
            }
            
            if (resultado != null && resultado.isExitoso()) {
                UnidadConversion conv = resultado.getResultado();
                String htmlResultado = formatearResultado(conv);
                request.setAttribute("resultadoMasa", htmlResultado);
            } else if (resultado != null && resultado.getError() != null) {
                ConversionError error = resultado.getError();
                String htmlError = formatearError(error);
                request.setAttribute("errorMasa", htmlError);
            }
            
        } catch (Exception e) {
            String htmlError = "<div class='result-item'>"
                    + "<span class='result-label'>Error:</span>"
                    + "<span class='result-value'>" + e.getMessage() + "</span>"
                    + "</div>"
                    + "<div class='result-item'>"
                    + "<span class='result-label'>Servidor:</span>"
                    + "<span class='result-value'>" + ConfigManager.getServidorURL() + "</span>"
                    + "</div>";
            request.setAttribute("errorMasa", htmlError);
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    private String formatearResultado(UnidadConversion conv) {
        return "<div class='result-item'>"
                + "<span class='result-label'>Valor Original:</span>"
                + "<span class='result-value'>" + conv.getValorOriginal() + " " + conv.getUnidadOrigen() + "</span>"
                + "</div>"
                + "<div class='result-item'>"
                + "<span class='result-label'>Valor Convertido:</span>"
                + "<span class='result-value'>" + conv.getValorConvertidoRedondeado() + " " + conv.getUnidadDestino() + "</span>"
                + "</div>"
                + "<div class='result-item'>"
                + "<span class='result-label'>Valor Exacto:</span>"
                + "<span class='result-value'>" + conv.getValorConvertidoExacto() + "</span>"
                + "</div>"
                + "<div class='result-item'>"
                + "<span class='result-label'>Factor de Conversión:</span>"
                + "<span class='result-value'>" + conv.getFactorConversion() + "</span>"
                + "</div>";
    }
    
    private String formatearError(ConversionError error) {
        return "<div class='result-item'>"
                + "<span class='result-label'>Código:</span>"
                + "<span class='result-value'>" + error.getCodigoError() + "</span>"
                + "</div>"
                + "<div class='result-item'>"
                + "<span class='result-label'>Tipo:</span>"
                + "<span class='result-value'>" + error.getTipoError() + "</span>"
                + "</div>"
                + "<div class='result-item'>"
                + "<span class='result-label'>Mensaje:</span>"
                + "<span class='result-value'>" + error.getMensaje() + "</span>"
                + "</div>"
                + (error.getDetalles() != null ? 
                    "<div class='result-item'>"
                    + "<span class='result-label'>Detalles:</span>"
                    + "<span class='result-value'>" + error.getDetalles() + "</span>"
                    + "</div>" : "");
    }
}
