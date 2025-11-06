package ec.edu.monster.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Cliente REST para servicios de conversión de unidades
 * @author YourName
 */
public class RestClient {
    
    private String baseURL;
    private Gson gson;
    
    public RestClient(String baseURL) {
        this.baseURL = baseURL;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    /**
     * Conversión de masa (Kilogramo, Libra, Onza, Tonelada)
     */
    public ConversionResult convertirMasa(String valor, String unidadOrigen, String unidadDestino) {
        return convertir("/masa", valor, unidadOrigen, unidadDestino);
    }
    
    /**
     * Conversión de longitud (Metro, Milla, Pulgada)
     */
    public ConversionResult convertirLongitud(String valor, String unidadOrigen, String unidadDestino) {
        return convertir("/longitud", valor, unidadOrigen, unidadDestino);
    }
    
    /**
     * Conversión de temperatura (Celsius, Fahrenheit, Kelvin)
     */
    public ConversionResult convertirTemperatura(String valor, String unidadOrigen, String unidadDestino) {
        return convertir("/temperatura", valor, unidadOrigen, unidadDestino);
    }
    
    /**
     * Conversión genérica
     */
    private ConversionResult convertir(String endpoint, String valor, String unidadOrigen, String unidadDestino) {
        try {
            // Crear JSON del request
            JsonObject requestJson = new JsonObject();
            requestJson.addProperty("valor", valor);
            requestJson.addProperty("unidadOrigen", unidadOrigen);
            requestJson.addProperty("unidadDestino", unidadDestino);
            
            String jsonBody = gson.toJson(requestJson);
            
            // Hacer POST request
            String responseJson = hacerRequest(baseURL + endpoint, "POST", jsonBody);
            
            // Parsear respuesta
            JsonObject response = JsonParser.parseString(responseJson).getAsJsonObject();
            
            ConversionResult result = new ConversionResult();
            
            // Verificar si fue exitoso
            if (response.has("exitoso") && response.get("exitoso").getAsBoolean()) {
                result.setExitoso(true);
                result.setResultado(gson.fromJson(response.get("resultado"), ResultadoConversion.class));
            } else if (response.has("error")) {
                result.setExitoso(false);
                result.setMensajeError(response.get("error").getAsString());
            } else {
                result.setExitoso(false);
                result.setMensajeError("Respuesta desconocida del servidor");
            }
            
            return result;
            
        } catch (Exception e) {
            ConversionResult result = new ConversionResult();
            result.setExitoso(false);
            result.setMensajeError("Error: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * Hace un request HTTP
     */
    private String hacerRequest(String urlString, String metodo, String jsonBody) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod(metodo);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        
        if (jsonBody != null && !jsonBody.isEmpty()) {
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
        }
        
        int responseCode = conn.getResponseCode();
        
        // Leer la respuesta
        InputStream is;
        if (responseCode >= 200 && responseCode < 300) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }
        
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }
        
        return response.toString();
    }
    
    // ============= CLASES INTERNAS =============
    
    public static class ConversionResult {
        private boolean exitoso;
        private ResultadoConversion resultado;
        private String mensajeError;
        
        public boolean isExitoso() { return exitoso; }
        public void setExitoso(boolean exitoso) { this.exitoso = exitoso; }
        
        public ResultadoConversion getResultado() { return resultado; }
        public void setResultado(ResultadoConversion resultado) { this.resultado = resultado; }
        
        public String getMensajeError() { return mensajeError; }
        public void setMensajeError(String mensajeError) { this.mensajeError = mensajeError; }
    }
    
    public static class ResultadoConversion {
        private double valorOriginal;
        private double valorConvertidoExacto;
        private double valorConvertidoRedondeado;
        private String unidadOrigen;
        private String unidadDestino;
        private String tipoConversion;
        private double factorConversion;
        private String fechaConversion;
        
        // Getters
        public double getValorOriginal() { return valorOriginal; }
        public double getValorConvertidoExacto() { return valorConvertidoExacto; }
        public double getValorConvertidoRedondeado() { return valorConvertidoRedondeado; }
        public String getUnidadOrigen() { return unidadOrigen; }
        public String getUnidadDestino() { return unidadDestino; }
        public String getTipoConversion() { return tipoConversion; }
        public double getFactorConversion() { return factorConversion; }
        public String getFechaConversion() { return fechaConversion; }
    }
}