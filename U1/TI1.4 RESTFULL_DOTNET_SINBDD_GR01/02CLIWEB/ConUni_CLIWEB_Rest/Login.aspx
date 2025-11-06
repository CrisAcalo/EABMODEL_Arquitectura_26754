<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="ClienteWebConversion.Login" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login - Sistema de Conversión de Unidades</title>
    <link href="css/styles.css" rel="stylesheet" />
</head>
<body>
    <div class="container">

        <div class="services-grid" style="grid-template-columns: 1fr; max-width: 500px; margin: 0 auto;">
            <div class="service-card">
                <!-- Imagen de Monster -->
                <div style="text-align: center; margin-bottom: 20px;">
                    <img src="images/monsterLogin.jpeg" alt="Monster Campaña" style="width: 200px; height: 200px; object-fit: cover; box-shadow: 0 5px 15px rgba(0,0,0,0.2);">
                </div>

                <h2 style="text-align: center;">Iniciar Sesión</h2>
                
                <form id="form1" runat="server">
                    
                    <!-- Mensaje de error -->
                    <asp:Panel ID="pnlError" runat="server" Visible="false" CssClass="result-container result-error">
                        <div class="result-title">
                            <asp:Label ID="lblError" runat="server"></asp:Label>
                        </div>
                    </asp:Panel>

                    <!-- Información de intentos -->
                    <asp:Panel ID="pnlIntentos" runat="server" Visible="false" style="text-align: center; color: #e74c3c; margin: 10px 0; font-weight: bold;">
                        <asp:Label ID="lblIntentos" runat="server"></asp:Label>
                    </asp:Panel>

                    <!-- Mensaje de bloqueo -->
                    <asp:Panel ID="pnlBloqueado" runat="server" Visible="false" CssClass="result-container result-error">
                        <div class="result-title">🔒 Acceso Bloqueado</div>
                        <div class="result-data">
                            <div class="result-item">
                                <span class="result-value">Has superado el número máximo de intentos.</span>
                            </div>
                        </div>
                    </asp:Panel>

                    <!-- Formulario de login -->
                    <asp:Panel ID="pnlFormulario" runat="server">
                        <div class="form-group">
                            <label for="txtUsuario">Usuario</label>
                            <asp:TextBox ID="txtUsuario" runat="server" CssClass="form-control" placeholder="Ingresa tu usuario" />
                            <asp:RequiredFieldValidator ID="rfvUsuario" runat="server" 
                                ControlToValidate="txtUsuario" 
                                ErrorMessage="El usuario es requerido" 
                                CssClass="text-danger" 
                                Display="Dynamic" />
                        </div>

                        <div class="form-group">
                            <label for="txtContrasena">Contraseña</label>
                            <asp:TextBox ID="txtContrasena" runat="server" TextMode="Password" CssClass="form-control" placeholder="Ingresa tu contraseña" />
                            <asp:RequiredFieldValidator ID="rfvContrasena" runat="server" 
                                ControlToValidate="txtContrasena" 
                                ErrorMessage="La contraseña es requerida" 
                                CssClass="text-danger" 
                                Display="Dynamic" />
                        </div>

                        <asp:Button ID="btnLogin" runat="server" Text="INGRESAR" CssClass="btn btn-primary btn-block" OnClick="btnLogin_Click" />
                    </asp:Panel>

                </form>
            </div>
        </div>

        <div class="footer">
            <p>© 2025 - Sistema de Conversión de Unidades | Grupo 01</p>
        </div>
    </div>
</body>
</html>
