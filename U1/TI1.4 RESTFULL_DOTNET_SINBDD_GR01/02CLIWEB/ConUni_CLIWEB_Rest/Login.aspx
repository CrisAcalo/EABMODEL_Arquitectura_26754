<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="ConUni_CLIWEB_Rest.Login" %>

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
        <div class="header">
            <h1>🔐 Sistema de Conversión de Unidades</h1>
            <p>Ingresa tus credenciales para acceder</p>
        </div>

        <div class="services-grid" style="grid-template-columns: 1fr; max-width: 500px; margin: 0 auto;">
            <div class="service-card">
                <h2>Iniciar Sesión</h2>

                <form id="form1" runat="server">
                    
                    <!-- Mensaje de error -->
                    <asp:Panel ID="pnlError" runat="server" Visible="false" CssClass="result-container result-error">
                        <div class="result-title">⚠️ Error</div>
                        <div class="result-data">
                            <div class="result-item">
                                <span class="result-value"><asp:Label ID="lblError" runat="server"></asp:Label></span>
                            </div>
                        </div>
                    </asp:Panel>

                    <!-- Formulario de login -->
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

                    <asp:Button ID="btnIngresar" runat="server" Text="INGRESAR" CssClass="btn btn-primary btn-block" OnClick="btnIngresar_Click" />

                    <div style="margin-top: 20px; padding: 15px; background-color: #e0f4f5; border-radius: 8px;">
                        <p style="margin: 0; font-size: 0.9em; color: #033540;">
                            <strong>Credenciales de prueba:</strong><br />
                            Usuario: <code>MONSTER</code><br />
                            Contraseña: <code>MONSTER9</code>
                        </p>
                    </div>
                </form>
            </div>
        </div>

        <div class="footer">
            <p>© 2025 - Sistema de Conversión de Unidades | Grupo 01</p>
        </div>
    </div>
</body>
</html>
