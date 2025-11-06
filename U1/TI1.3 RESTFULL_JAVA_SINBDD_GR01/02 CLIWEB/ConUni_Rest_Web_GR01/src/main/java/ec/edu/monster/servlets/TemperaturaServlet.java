package ec.edu.monster.servlets;

import ec.edu.monster.model.*;
import ec.edu.monster.util.ConfigManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet para conversiones de temperatura via REST
 * @author YourName
 */
@WebServlet(name = "TemperaturaServlet", urlPatterns = {"/TemperaturaServlet"})
public class TemperaturaServlet extends HttpServlet {
    
    private final Gson gson = new Gson();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String tipoConversion = request.getParameter("tipoConversion");
        String valor = request.getParameter("valor");
        
        try {
            // Mapear tipo de conversión a unidades
            String unidadOrigen = "";
            String unidadDestino = "";
            
            switch (tipoConversion) {
                case "celsiusFahrenheit":
                    unidadOrigen = "Celsius";
                    unidadDestino = "Fahrenheit";
                    break;
                case "celsiusKelvin":
                    unidadOrigen = "Celsius";
                    unidadDestino = "Kelvin";
                    break;
                case "fahrenheitCelsius":
                    unidadOrigen = "Fahrenheit";
                    unidadDestino = "Celsius";
                    break;
                case "fahrenheitKelvin":
                    unidadOrigen = "Fahrenheit";
                    unidadDestino = "Kelvin";
                    break;
                case "kelvinCelsius":
                    unidadOrigen = "Kelvin";
                    unidadDestino = "Celsius";
                    break;
                case "kelvinFahrenheit":
                    unidadOrigen = "Kelvin";
                    unidadDestino = "Fahrenheit";
                    break;
            }
            
            // Crear JSON para el request
            JsonObject requestJson = new JsonObject();
            requestJson.addProperty("valor", valor);
            requestJson.addProperty("unidadOrigen", unidadOrigen);
            requestJson.addProperty("unidadDestino", unidadDestino);
            
            // Llamar al servicio REST
            String endpoint = ConfigManager.getEndpointURL("temperatura");
            String responseJson = hacerRequestREST(endpoint, requestJson.toString());
            
            // Parsear respuesta
            ConversionResult resultado = gson.fromJson(responseJson, ConversionResult.class);
            
            if (resultado != null && resultado.isExitoso()) {
                ResultadoConversion conv = resultado.getResultado();
                String htmlResultado = formatearResultado(conv);
                request.setAttribute("resultadoTemperatura", htmlResultado);
            } else if (resultado != null && resultado.getError() != null) {
                ErrorConversion error = resultado.getError();
                String htmlError = formatearError(error);
                request.setAttribute("errorTemperatura", htmlError);
            } else {
                String htmlError = "<div class='result-item'>"
                        + "<span class='result-label'>Respuesta del servidor:</span>"
                        + "<span class='result-value'>" + responseJson + "</span>"
                        + "</div>";
                request.setAttribute("errorTemperatura", htmlError);
            }
            
        } catch (Exception e) {
            String htmlError = "<div class='result-item'>"
                    + "<span class='result-label'>Error:</span>"
                    + "<span class='result-value'>" + e.getMessage() + "</span>"
                    + "</div>"
                    + "<div class='result-item'>"
                    + "<span class='result-label'>Servidor:</span>"
                    + "<span class='result-value'>" + ConfigManager.getBaseURL() + "</span>"
                    + "</div>";
            request.setAttribute("errorTemperatura", htmlError);
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    /**
     * Hace un request HTTP POST al servidor REST
     */
    private String hacerRequestREST(String urlString, String jsonBody) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setDoOutput(true);
        
        // Enviar body
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        // Leer respuesta
        int responseCode = conn.getResponseCode();
        InputStream is = (responseCode >= 200 && responseCode < 300) 
                ? conn.getInputStream() 
                : conn.getErrorStream();
        
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }
        
        return response.toString();
    }
    
    private String formatearResultado(ResultadoConversion conv) {
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
    
    private String formatearError(ErrorConversion error) {
        return "<div class='result-item'>"
                + "<span class='result-label'>Mensaje:</span>"
                + "<span class='result-value'>" + error.getMensaje() + "</span>"
                + "</div>"
                + (error.getCodigo() != null ? 
                    "<div class='result-item'>"
                    + "<span class='result-label'>Código:</span>"
                    + "<span class='result-value'>" + error.getCodigo() + "</span>"
                    + "</div>" : "")
                + (error.getTipo() != null ? 
                    "<div class='result-item'>"
                    + "<span class='result-label'>Tipo:</span>"
                    + "<span class='result-value'>" + error.getTipo() + "</span>"
                    + "</div>" : "");
    }
}