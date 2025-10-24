<%-- 
    Document   : index
    Created on : 23 oct. 2025, 20:01:31
    Author     : Kewo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cliente Web - Servicios de Conversión</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1>Servicios de Conversión de Unidades</h1>
            <p>Cliente Web para conversiones de unidades</p>
        </div>

        <!-- Grid de Servicios -->
        <div class="services-grid">
            
            <!-- Tarjeta de Longitud -->
            <div class="service-card">
                <h2>
                    Longitud
                </h2>
                <form action="LongitudServlet" method="POST">
                    <div class="form-group">
                        <label for="tipoConversionLongitud">Tipo de Conversión:</label>
                        <select id="tipoConversionLongitud" name="tipoConversion" required>
                            <option value="">Seleccione...</option>
                            <option value="millaMetro">Milla → Metro</option>
                            <option value="metroMilla">Metro → Milla</option>
                            <option value="millaPulgada">Milla → Pulgada</option>
                            <option value="pulgadaMilla">Pulgada → Milla</option>
                            <option value="metroPulgada">Metro → Pulgada</option>
                            <option value="pulgadaMetro">Pulgada → Metro</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="valorLongitud">Valor:</label>
                        <input type="text" id="valorLongitud" name="valor" 
                               placeholder="Ej: 5.5" required>
                    </div>
                    
                    <button type="submit" class="btn btn-primary btn-block">
                        Convertir
                    </button>
                </form>
                
                <!-- Resultado -->
                <% if(request.getAttribute("resultadoLongitud") != null) { %>
                    <div class="result-container result-success">
                        <div class="result-title">Conversión Exitosa</div>
                        <div class="result-data">
                            ${resultadoLongitud}
                        </div>
                    </div>
                <% } %>
                
                <% if(request.getAttribute("errorLongitud") != null) { %>
                    <div class="result-container result-error">
                        <div class="result-title">Error en la Conversión</div>
                        <div class="result-data">
                            ${errorLongitud}
                        </div>
                    </div>
                <% } %>
            </div>

            <!-- Tarjeta de Masa -->
            <div class="service-card">
                <h2>
                    Masa
                </h2>
                <form action="MasaServlet" method="POST">
                    <div class="form-group">
                        <label for="tipoConversionMasa">Tipo de Conversión:</label>
                        <select id="tipoConversionMasa" name="tipoConversion" required>
                            <option value="">Seleccione...</option>
                            <option value="kilogramoQuintal">Kilogramo → Quintal</option>
                            <option value="quintalKilogramo">Quintal → Kilogramo</option>
                            <option value="kilogramoLibra">Kilogramo → Libra</option>
                            <option value="libraKilogramo">Libra → Kilogramo</option>
                            <option value="quintalLibra">Quintal → Libra</option>
                            <option value="libraQuintal">Libra → Quintal</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="valorMasa">Valor:</label>
                        <input type="text" id="valorMasa" name="valor" 
                               placeholder="Ej: 75.5" required>
                    </div>
                    
                    <button type="submit" class="btn btn-primary btn-block">
                        Convertir
                    </button>
                </form>
                
                <!-- Resultado -->
                <% if(request.getAttribute("resultadoMasa") != null) { %>
                    <div class="result-container result-success">
                        <div class="result-title">Conversión Exitosa</div>
                        <div class="result-data">
                            ${resultadoMasa}
                        </div>
                    </div>
                <% } %>
                
                <% if(request.getAttribute("errorMasa") != null) { %>
                    <div class="result-container result-error">
                        <div class="result-title">Error en la Conversión</div>
                        <div class="result-data">
                            ${errorMasa}
                        </div>
                    </div>
                <% } %>
            </div>

            <!-- Tarjeta de Temperatura -->
            <div class="service-card">
                <h2>
                    Temperatura
                </h2>
                <form action="TemperaturaServlet" method="POST">
                    <div class="form-group">
                        <label for="tipoConversionTemp">Tipo de Conversión:</label>
                        <select id="tipoConversionTemp" name="tipoConversion" required>
                            <option value="">Seleccione...</option>
                            <option value="celsiusFahrenheit">Celsius → Fahrenheit</option>
                            <option value="celsiusKelvin">Celsius → Kelvin</option>
                            <option value="fahrenheitCelsius">Fahrenheit → Celsius</option>
                            <option value="fahrenheitKelvin">Fahrenheit → Kelvin</option>
                            <option value="kelvinCelsius">Kelvin → Celsius</option>
                            <option value="kelvinFahrenheit">Kelvin → Fahrenheit</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="valorTemp">Valor:</label>
                        <input type="text" id="valorTemp" name="valor" 
                               placeholder="Ej: 25" required>
                    </div>
                    
                    <button type="submit" class="btn btn-primary btn-block">
                        Convertir
                    </button>
                </form>
                
                <!-- Resultado -->
                <% if(request.getAttribute("resultadoTemperatura") != null) { %>
                    <div class="result-container result-success">
                        <div class="result-title">Conversión Exitosa</div>
                        <div class="result-data">
                            ${resultadoTemperatura}
                        </div>
                    </div>
                <% } %>
                
                <% if(request.getAttribute("errorTemperatura") != null) { %>
                    <div class="result-container result-error">
                        <div class="result-title">Error en la Conversión</div>
                        <div class="result-data">
                            ${errorTemperatura}
                        </div>
                    </div>
                <% } %>
            </div>

        </div>

        <!-- Footer -->
        <div class="footer">
            <p>© 2024 - Sistema de Conversión de Unidades | Cliente Web</p>
        </div>
    </div>
</body>
</html>
