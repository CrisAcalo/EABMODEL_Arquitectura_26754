<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="ConUni_CLIWEB_Rest.Default" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Conversiones - Sistema de Conversión de Unidades</title>
    <link href="css/styles.css" rel="stylesheet" />
</head>
<body>
    <form id="form1" runat="server">
        <div class="container">
            <div class="header">
                <h1>🔄 Sistema de Conversión de Unidades</h1>
                <p>Convierte fácilmente entre diferentes unidades de medida</p>
                <div style="margin-top: 15px;">
                    <span style="opacity: 0.9;">👤 Bienvenido, <strong><asp:Label ID="lblUsuario" runat="server"></asp:Label></strong></span>
                    <asp:Button ID="btnCerrarSesion" runat="server" Text="Cerrar Sesión" CssClass="btn btn-danger" OnClick="btnCerrarSesion_Click" style="margin-left: 20px;" CausesValidation="false" />
                </div>
            </div>
            <div class="services-grid">
                <!-- ========== LONGITUD ========== -->
                <div class="service-card">
                    <h2> Longitud</h2>

                    <div class="form-group">
                        <label>Tipo de Conversión</label>
                        <asp:DropDownList ID="ddlLongitud" runat="server" CssClass="form-control">
                            <asp:ListItem Value="">Seleccione una conversión</asp:ListItem>
                            <asp:ListItem Value="millaMetro">Milla → Metro</asp:ListItem>
                            <asp:ListItem Value="metroMilla">Metro → Milla</asp:ListItem>
                            <asp:ListItem Value="millaPulgada">Milla → Pulgada</asp:ListItem>
                            <asp:ListItem Value="pulgadaMilla">Pulgada → Milla</asp:ListItem>
                            <asp:ListItem Value="metroPulgada">Metro → Pulgada</asp:ListItem>
                            <asp:ListItem Value="pulgadaMetro">Pulgada → Metro</asp:ListItem>
                        </asp:DropDownList>
                        <asp:RequiredFieldValidator ID="rfvLongitud" runat="server" 
                            ControlToValidate="ddlLongitud" 
                            InitialValue=""
                            ErrorMessage="Seleccione un tipo de conversión" 
                            CssClass="text-danger" 
                            ValidationGroup="Longitud"
                            Display="Dynamic" />
                    </div>

                    <div class="form-group">
                        <label>Valor</label>
                        <asp:TextBox ID="txtValorLongitud" runat="server" CssClass="form-control" placeholder="Ejemplo: 10" />
                        <asp:RequiredFieldValidator ID="rfvValorLongitud" runat="server" 
                            ControlToValidate="txtValorLongitud" 
                            ErrorMessage="El valor es requerido" 
                            CssClass="text-danger" 
                            ValidationGroup="Longitud"
                            Display="Dynamic" />
                    </div>

                    <asp:Button ID="btnConvertirLongitud" runat="server" Text="CONVERTIR" CssClass="btn btn-primary btn-block" OnClick="btnConvertirLongitud_Click" ValidationGroup="Longitud" />

                    <!-- Resultado Longitud -->
                    <asp:Panel ID="pnlResultadoLongitud" runat="server" Visible="false" CssClass="result-container result-success">
                        <div class="result-title">Conversión Exitosa</div>
                        <div class="result-data">
                            <asp:Literal ID="litResultadoLongitud" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>

                    <!-- Error Longitud -->
                    <asp:Panel ID="pnlErrorLongitud" runat="server" Visible="false" CssClass="result-container result-error">
                        <div class="result-title">Error en la Conversión</div>
                        <div class="result-data">
                            <asp:Literal ID="litErrorLongitud" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>
                </div>

                <!-- ========== MASA ========== -->
                <div class="service-card">
                    <h2>Masa</h2>

                    <div class="form-group">
                        <label>Tipo de Conversión</label>
                        <asp:DropDownList ID="ddlMasa" runat="server" CssClass="form-control">
                            <asp:ListItem Value="">Seleccione una conversión</asp:ListItem>
                            <asp:ListItem Value="kilogramoQuintal">Kilogramo → Quintal</asp:ListItem>
                            <asp:ListItem Value="quintalKilogramo">Quintal → Kilogramo</asp:ListItem>
                            <asp:ListItem Value="kilogramoLibra">Kilogramo → Libra</asp:ListItem>
                            <asp:ListItem Value="libraKilogramo">Libra → Kilogramo</asp:ListItem>
                            <asp:ListItem Value="quintalLibra">Quintal → Libra</asp:ListItem>
                            <asp:ListItem Value="libraQuintal">Libra → Quintal</asp:ListItem>
                        </asp:DropDownList>
                        <asp:RequiredFieldValidator ID="rfvMasa" runat="server" 
                            ControlToValidate="ddlMasa" 
                            InitialValue=""
                            ErrorMessage="Seleccione un tipo de conversión" 
                            CssClass="text-danger" 
                            ValidationGroup="Masa"
                            Display="Dynamic" />
                    </div>

                    <div class="form-group">
                        <label>Valor</label>
                        <asp:TextBox ID="txtValorMasa" runat="server" CssClass="form-control" placeholder="Ejemplo: 100" />
                        <asp:RequiredFieldValidator ID="rfvValorMasa" runat="server" 
                            ControlToValidate="txtValorMasa" 
                            ErrorMessage="El valor es requerido" 
                            CssClass="text-danger" 
                            ValidationGroup="Masa"
                            Display="Dynamic" />
                    </div>

                    <asp:Button ID="btnConvertirMasa" runat="server" Text="CONVERTIR" CssClass="btn btn-primary btn-block" OnClick="btnConvertirMasa_Click" ValidationGroup="Masa" />

                    <!-- Resultado Masa -->
                    <asp:Panel ID="pnlResultadoMasa" runat="server" Visible="false" CssClass="result-container result-success">
                        <div class="result-title">Conversión Exitosa</div>
                        <div class="result-data">
                            <asp:Literal ID="litResultadoMasa" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>

                    <!-- Error Masa -->
                    <asp:Panel ID="pnlErrorMasa" runat="server" Visible="false" CssClass="result-container result-error">
                        <div class="result-title">Error en la Conversión</div>
                        <div class="result-data">
                            <asp:Literal ID="litErrorMasa" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>
                </div>

                <!-- ========== TEMPERATURA ========== -->
                <div class="service-card">
                    <h2> Temperatura</h2>

                    <div class="form-group">
                        <label>Tipo de Conversión</label>
                        <asp:DropDownList ID="ddlTemperatura" runat="server" CssClass="form-control">
                            <asp:ListItem Value="">Seleccione una conversión</asp:ListItem>
                            <asp:ListItem Value="celsiusFahrenheit">Celsius → Fahrenheit</asp:ListItem>
                            <asp:ListItem Value="celsiusKelvin">Celsius → Kelvin</asp:ListItem>
                            <asp:ListItem Value="fahrenheitCelsius">Fahrenheit → Celsius</asp:ListItem>
                            <asp:ListItem Value="fahrenheitKelvin">Fahrenheit → Kelvin</asp:ListItem>
                            <asp:ListItem Value="kelvinCelsius">Kelvin → Celsius</asp:ListItem>
                            <asp:ListItem Value="kelvinFahrenheit">Kelvin → Fahrenheit</asp:ListItem>
                        </asp:DropDownList>
                        <asp:RequiredFieldValidator ID="rfvTemperatura" runat="server" 
                            ControlToValidate="ddlTemperatura" 
                            InitialValue=""
                            ErrorMessage="Seleccione un tipo de conversión" 
                            CssClass="text-danger" 
                            ValidationGroup="Temperatura"
                            Display="Dynamic" />
                    </div>

                    <div class="form-group">
                        <label>Valor</label>
                        <asp:TextBox ID="txtValorTemperatura" runat="server" CssClass="form-control" placeholder="Ejemplo: 25" />
                        <asp:RequiredFieldValidator ID="rfvValorTemperatura" runat="server" 
                            ControlToValidate="txtValorTemperatura" 
                            ErrorMessage="El valor es requerido" 
                            CssClass="text-danger" 
                            ValidationGroup="Temperatura"
                            Display="Dynamic" />
                    </div>

                    <asp:Button ID="btnConvertirTemperatura" runat="server" Text="CONVERTIR" CssClass="btn btn-primary btn-block" OnClick="btnConvertirTemperatura_Click" ValidationGroup="Temperatura" />

                    <!-- Resultado Temperatura -->
                    <asp:Panel ID="pnlResultadoTemperatura" runat="server" Visible="false" CssClass="result-container result-success">
                        <div class="result-title">Conversión Exitosa</div>
                        <div class="result-data">
                            <asp:Literal ID="litResultadoTemperatura" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>

                    <!-- Error Temperatura -->
                    <asp:Panel ID="pnlErrorTemperatura" runat="server" Visible="false" CssClass="result-container result-error">
                        <div class="result-title">Error en la Conversión</div>
                        <div class="result-data">
                            <asp:Literal ID="litErrorTemperatura" runat="server"></asp:Literal>
                        </div>
                    </asp:Panel>
                </div>
            </div>

            <div class="footer">
                <p>© 2025 - Sistema de Conversión de Unidades | Grupo 01</p>
            </div>
        </div>
    </form>
</body>
</html>