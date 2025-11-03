<%-- 
    Document   : login
    Created on : 3 nov. 2025, 13:13:08
    Author     : Kewo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Sistema de Conversión</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .login-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 40px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        
        .login-header {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .login-header h1 {
            color: #2c3e50;
            margin-bottom: 10px;
        }
        
        .login-header p {
            color: #7f8c8d;
        }
        
        .login-form {
            margin-top: 20px;
        }
        
        .error-message {
            background-color: #fee;
            border-left: 4px solid #e74c3c;
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 4px;
            color: #c0392b;
        }
        
        .login-icon {
            font-size: 60px;
            text-align: center;
            margin-bottom: 20px;
        }
        
        .attempts-info {
            text-align: center;
            color: #e74c3c;
            margin-top: 10px;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-icon">🔐</div>
        
        <div class="login-header">
            <h1>Sistema de Autenticación</h1>
            <p>Servicios de Conversión de Unidades</p>
        </div>
        
        <% 
            String error = (String) request.getAttribute("error");
            Integer intentos = (Integer) session.getAttribute("intentos");
            if (intentos == null) intentos = 0;
            int intentosRestantes = 3 - intentos;
        %>
        
        <% if (error != null) { %>
            <div class="error-message">
                ❌ <%= error %>
            </div>
        <% } %>
        
        <% if (intentos > 0 && intentos < 3) { %>
            <div class="attempts-info">
                Te quedan <%= intentosRestantes %> intento(s)
            </div>
        <% } %>
        
        <% if (intentos >= 3) { %>
            <div class="error-message" style="text-align: center;">
                <strong>Acceso bloqueado</strong><br>
                Has superado el número máximo de intentos.<br>
            </div>
        <% } else { %>
            <form action="LoginServlet" method="POST" class="login-form">
                <div class="form-group">
                    <label for="usuario">Usuario:</label>
                    <input type="text" id="usuario" name="usuario" 
                           placeholder="Ingrese su usuario" required autofocus>
                </div>
                
                <div class="form-group">
                    <label for="contrasena">Contraseña:</label>
                    <input type="password" id="contrasena" name="contrasena" 
                           placeholder="Ingrese su contraseña" required>
                </div>
                
                <button type="submit" class="btn btn-primary btn-block">
                    Iniciar Sesión
                </button>
            </form>
        <% } %>
    </div>
</body>
</html>