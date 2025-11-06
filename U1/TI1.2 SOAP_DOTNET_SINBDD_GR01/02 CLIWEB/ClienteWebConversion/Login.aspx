<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="ClienteWebConversion.Login" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Login - Sistema de Conversión</title>
    <link href="css/styles.css" rel="stylesheet" />
</head>
<body>
    <form id="form1" runat="server">
        <div class="login-container">
            <div class="login-icon">🔐</div>
            
            <div class="login-header">
                <h1>Sistema de Autenticación</h1>
                <p>Servicios de Conversión de Unidades</p>
            </div>
            
            <!-- Mensaje de error -->
            <asp:Panel ID="pnlError" runat="server" CssClass="error-message" Visible="false">
                <asp:Label ID="lblError" runat="server"></asp:Label>
            </asp:Panel>
            
            <!-- Información de intentos -->
            <asp:Panel ID="pnlIntentos" runat="server" CssClass="attempts-info" Visible="false">
                <asp:Label ID="lblIntentos" runat="server"></asp:Label>
            </asp:Panel>
            
            <!-- Mensaje de bloqueo -->
            <asp:Panel ID="pnlBloqueado" runat="server" CssClass="error-message" Visible="false" style="text-align: center;">
                <strong>Acceso bloqueado</strong><br />
                Has superado el número máximo de intentos.<br />
            </asp:Panel>
            
            <!-- Formulario de login -->
            <asp:Panel ID="pnlFormulario" runat="server" CssClass="login-form">
                <div class="form-group">
                    <label for="txtUsuario">Usuario:</label>
                    <asp:TextBox ID="txtUsuario" runat="server" 
                                 placeholder="Ingrese su usuario" 
                                 CssClass="form-control" 
                                 AutoFocus="true"></asp:TextBox>
                    <asp:RequiredFieldValidator ID="rfvUsuario" runat="server" 
                                               ControlToValidate="txtUsuario"
                                               ErrorMessage="El usuario es requerido"
                                               ForeColor="Red"
                                               Display="Dynamic"></asp:RequiredFieldValidator>
                </div>
                
                <div class="form-group">
                    <label for="txtContrasena">Contraseña:</label>
                    <asp:TextBox ID="txtContrasena" runat="server" 
                                 TextMode="Password" 
                                 placeholder="Ingrese su contraseña"
                                 CssClass="form-control"></asp:TextBox>
                    <asp:RequiredFieldValidator ID="rfvContrasena" runat="server" 
                                               ControlToValidate="txtContrasena"
                                               ErrorMessage="La contraseña es requerida"
                                               ForeColor="Red"
                                               Display="Dynamic"></asp:RequiredFieldValidator>
                </div>
                
                <asp:Button ID="btnLogin" runat="server" 
                           Text="Iniciar Sesión" 
                           CssClass="btn btn-primary btn-block" 
                           OnClick="btnLogin_Click" />
            </asp:Panel>
        </div>
    </form>
</body>
</html>