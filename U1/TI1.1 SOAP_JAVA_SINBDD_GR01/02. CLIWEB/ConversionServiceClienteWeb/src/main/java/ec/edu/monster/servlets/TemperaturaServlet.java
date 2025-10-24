/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ec.edu.monster.servlets;

import ec.edu.monster.temperatura.*;
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
@WebServlet(name = "TemperaturaServlet", urlPatterns = {"/TemperaturaServlet"})
public class TemperaturaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String tipoConversion = request.getParameter("tipoConversion");
        String valor = request.getParameter("valor");
        
        try {
            // Obtener URL dinámica desde config.properties
            String serviceName = ConfigManager.getProperty("servicio.temperatura");
            URL wsdlURL = new URL(ConfigManager.getWSDLURL(serviceName));
            QName qname = new QName("http://ws.monster.edu.ec/", serviceName);
            
            // Crear servicio con URL dinámica
            TemperaturaService_Service service = new TemperaturaService_Service(wsdlURL, qname);
            TemperaturaService port = service.getTemperaturaServicePort();
            
            ConversionResult resultado = null;
            
            switch (tipoConversion) {
                case "celsiusFahrenheit":
                    resultado = port.celsiusAFahrenheit(valor);
                    break;
                case "celsiusKelvin":
                    resultado = port.celsiusAKelvin(valor);
                    break;
                case "fahrenheitCelsius":
                    resultado = port.fahrenheitACelsius(valor);
                    break;
                case "fahrenheitKelvin":
                    resultado = port.fahrenheitAKelvin(valor);
                    break;
                case "kelvinCelsius":
                    resultado = port.kelvinACelsius(valor);
                    break;
                case "kelvinFahrenheit":
                    resultado = port.kelvinAFahrenheit(valor);
                    break;
            }
            
            if (resultado != null && resultado.isExitoso()) {
                UnidadConversion conv = resultado.getResultado();
                String htmlResultado = formatearResultado(conv);
                request.setAttribute("resultadoTemperatura", htmlResultado);
            } else if (resultado != null && resultado.getError() != null) {
                ConversionError error = resultado.getError();
                String htmlError = formatearError(error);
                request.setAttribute("errorTemperatura", htmlError);
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
            request.setAttribute("errorTemperatura", htmlError);
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
