<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="ClienteWebConversion.Default" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Cliente Web - Servicios de Conversión</title>
    <link href="css/styles.css" rel="stylesheet" />
</head>
<body>
    <form id="form1" runat="server">
        <div class="container">
            <!-- Header con información de usuario y logout -->
            <div class="header">
                <div style="display: flex; justify-content: space-between; align-items: center;">
                    <div>
                        <h1>Servicios de Conversión de Unidades</h1>
                        <p>Cliente Web para conversiones de unidades</p>
                    </div>
                    <div style="text-align: right;">
                        <p style="margin: 0; color: #27ae60;">
                            👤 Bienvenido, <strong><asp:Label ID="lblUsuario" runat="server"></asp:Label></strong>
                        </p>
                        <asp:Button ID="btnCerrarSesion" runat="server" 
                                   Text="🚪 Cerrar Sesión" 
                                   CssClass="btn btn-danger" 
                                   OnClick="btnCerrarSesion_Click"
                                   CausesValidation="false"
                                   style="font-size: 14px; padding: 8px 16px; margin-top: 10px; display: inline-block;" />
                    </div>
                </div>
            </div>

            <!-- Grid de Servicios -->
            <div class="services-grid">
                
                <!-- ============================================= -->
                <!-- Tarjeta de Longitud -->
                <!-- ============================================= -->
                <div class="service-card">
                    <h2>Longitud</h2>
                    
                    <div class="form-group">
                        <label for="ddlLongitud">Tipo de Conversión:</label>
                        <asp:DropDownList ID="ddlLongitud" runat="server" CssClass="form-control">
                            <asp:ListItem Value="" Text="Seleccione..."></asp:ListItem>
                            <asp:ListItem Value="millaMetro" Text="Milla → Metro"></asp:ListItem>
                            <asp:ListItem Value="metroMilla" Text="Metro → Milla"></asp:ListItem>
                            <asp:ListItem Value="millaPulgada" Text="Milla → Pulgada"></asp:ListItem>
                            <asp:ListItem Value="pulgadaMilla" Text="Pulgada → Milla"></asp:ListItem>
                            <asp:ListItem Value="metroPulgada" Text="Metro → Pulgada"></asp:ListItem>
                            <asp:ListItem Value="pulgadaMetro" Text="Pulgada → Metro"></asp:ListItem>
                        </asp:DropDownList>
                        <asp:RequiredFieldValidator ID="rfvLongitud" runat="server" 
                                                   ControlToValidate="ddlLongitud"
                                                   ErrorMessage="Seleccione un tipo de conversión"
                                                   ForeColor="Red"
                                                   Display="Dynamic"
                                                   ValidationGroup="Longitud"></asp:RequiredFieldValidator>
                    </div>
                    
                    <div class="form-group">
                        <label for="txtLongitud">Valor:</label>
                        <asp:TextBox ID="txtLongitud" runat="server" 
                                    placeholder="Ej: 5.5" 
                                    CssClass="form-control"></asp:TextBox>
                        <asp:RequiredFieldValidator ID="rfvValorLongitud" runat="server" 
                                                   ControlToValidate="txtLongitud"
                                                   ErrorMessage="Ingrese un valor"
                                                   ForeColor="Red"
                                                   Display="Dynamic"
                                                   ValidationGroup="Longitud"></asp:RequiredFieldValidator>
                    </div>
                    
                    <asp:Button ID="btnLongitud" runat="server" 
                               Text="Convertir" 
                               CssClass="btn btn-primary btn-block" 
                               OnClick="btnLongitud_Click"
                               ValidationGroup="Longitud" />
                    
                    <!-- Resultado -->
                    <asp:Panel ID="pnlResultadoLongitud" runat="server" Visible="false" CssClass="result-container result-success">
                        <div class="result-title">✓ Conversión Exitosa</div>
                        <div class="result-data">
                            <asp:Literal ID="litResultadoLongitud" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>
                    
                    <asp:Panel ID="pnlErrorLongitud" runat="server" Visible="false" CssClass="result-container result-error">
                        <div class="result-title">❌ Error en la Conversión</div>
                        <div class="result-data">
                            <asp:Literal ID="litErrorLongitud" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>
                </div>

                <!-- ============================================= -->
                <!-- Tarjeta de Masa -->
                <!-- ============================================= -->
                <div class="service-card">
                    <h2>Masa</h2>
                    
                    <div class="form-group">
                        <label for="ddlMasa">Tipo de Conversión:</label>
                        <asp:DropDownList ID="ddlMasa" runat="server" CssClass="form-control">
                            <asp:ListItem Value="" Text="Seleccione..."></asp:ListItem>
                            <asp:ListItem Value="kilogramoQuintal" Text="Kilogramo → Quintal"></asp:ListItem>
                            <asp:ListItem Value="quintalKilogramo" Text="Quintal → Kilogramo"></asp:ListItem>
                            <asp:ListItem Value="kilogramoLibra" Text="Kilogramo → Libra"></asp:ListItem>
                            <asp:ListItem Value="libraKilogramo" Text="Libra → Kilogramo"></asp:ListItem>
                            <asp:ListItem Value="quintalLibra" Text="Quintal → Libra"></asp:ListItem>
                            <asp:ListItem Value="libraQuintal" Text="Libra → Quintal"></asp:ListItem>
                        </asp:DropDownList>
                        <asp:RequiredFieldValidator ID="rfvMasa" runat="server" 
                                                   ControlToValidate="ddlMasa"
                                                   ErrorMessage="Seleccione un tipo de conversión"
                                                   ForeColor="Red"
                                                   Display="Dynamic"
                                                   ValidationGroup="Masa"></asp:RequiredFieldValidator>
                    </div>
                    
                    <div class="form-group">
                        <label for="txtMasa">Valor:</label>
                        <asp:TextBox ID="txtMasa" runat="server" 
                                    placeholder="Ej: 75.5" 
                                    CssClass="form-control"></asp:TextBox>
                        <asp:RequiredFieldValidator ID="rfvValorMasa" runat="server" 
                                                   ControlToValidate="txtMasa"
                                                   ErrorMessage="Ingrese un valor"
                                                   ForeColor="Red"
                                                   Display="Dynamic"
                                                   ValidationGroup="Masa"></asp:RequiredFieldValidator>
                    </div>
                    
                    <asp:Button ID="btnMasa" runat="server" 
                               Text="Convertir" 
                               CssClass="btn btn-primary btn-block" 
                               OnClick="btnMasa_Click"
                               ValidationGroup="Masa" />
                    
                    <!-- Resultado -->
                    <asp:Panel ID="pnlResultadoMasa" runat="server" Visible="false" CssClass="result-container result-success">
                        <div class="result-title">✓ Conversión Exitosa</div>
                        <div class="result-data">
                            <asp:Literal ID="litResultadoMasa" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>
                    
                    <asp:Panel ID="pnlErrorMasa" runat="server" Visible="false" CssClass="result-container result-error">
                        <div class="result-title">❌ Error en la Conversión</div>
                        <div class="result-data">
                            <asp:Literal ID="litErrorMasa" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>
                </div>

                <!-- ============================================= -->
                <!-- Tarjeta de Temperatura -->
                <!-- ============================================= -->
                <div class="service-card">
                    <h2>Temperatura</h2>
                    
                    <div class="form-group">
                        <label for="ddlTemperatura">Tipo de Conversión:</label>
                        <asp:DropDownList ID="ddlTemperatura" runat="server" CssClass="form-control">
                            <asp:ListItem Value="" Text="Seleccione..."></asp:ListItem>
                            <asp:ListItem Value="celsiusFahrenheit" Text="Celsius → Fahrenheit"></asp:ListItem>
                            <asp:ListItem Value="celsiusKelvin" Text="Celsius → Kelvin"></asp:ListItem>
                            <asp:ListItem Value="fahrenheitCelsius" Text="Fahrenheit → Celsius"></asp:ListItem>
                            <asp:ListItem Value="fahrenheitKelvin" Text="Fahrenheit → Kelvin"></asp:ListItem>
                            <asp:ListItem Value="kelvinCelsius" Text="Kelvin → Celsius"></asp:ListItem>
                            <asp:ListItem Value="kelvinFahrenheit" Text="Kelvin → Fahrenheit"></asp:ListItem>
                        </asp:DropDownList>
                        <asp:RequiredFieldValidator ID="rfvTemperatura" runat="server" 
                                                   ControlToValidate="ddlTemperatura"
                                                   ErrorMessage="Seleccione un tipo de conversión"
                                                   ForeColor="Red"
                                                   Display="Dynamic"
                                                   ValidationGroup="Temperatura"></asp:RequiredFieldValidator>
                    </div>
                    
                    <div class="form-group">
                        <label for="txtTemperatura">Valor:</label>
                        <asp:TextBox ID="txtTemperatura" runat="server" 
                                    placeholder="Ej: 25" 
                                    CssClass="form-control"></asp:TextBox>
                        <asp:RequiredFieldValidator ID="rfvValorTemperatura" runat="server" 
                                                   ControlToValidate="txtTemperatura"
                                                   ErrorMessage="Ingrese un valor"
                                                   ForeColor="Red"
                                                   Display="Dynamic"
                                                   ValidationGroup="Temperatura"></asp:RequiredFieldValidator>
                    </div>
                    
                    <asp:Button ID="btnTemperatura" runat="server" 
                               Text="Convertir" 
                               CssClass="btn btn-primary btn-block" 
                               OnClick="btnTemperatura_Click"
                               ValidationGroup="Temperatura" />
                    
                    <!-- Resultado -->
                    <asp:Panel ID="pnlResultadoTemperatura" runat="server" Visible="false" CssClass="result-container result-success">
                        <div class="result-title">✓ Conversión Exitosa</div>
                        <div class="result-data">
                            <asp:Literal ID="litResultadoTemperatura" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>
                    
                    <asp:Panel ID="pnlErrorTemperatura" runat="server" Visible="false" CssClass="result-container result-error">
                        <div class="result-title">❌ Error en la Conversión</div>
                        <div class="result-data">
                            <asp:Literal ID="litErrorTemperatura" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>
                </div>

            </div>

            <!-- Footer -->
            <div class="footer">
                <p>© 2024 - Sistema de Conversión de Unidades | Cliente Web</p>
            </div>
        </div>
    </form>
</body>
</html>