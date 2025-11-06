using System;
using System.ServiceModel;
using System.Text;
using System.Web.UI;
using ClienteWebConversion.Helpers;
using LongitudSvc = ClienteWebConversion.LongitudServiceReference;
using MasaSvc = ClienteWebConversion.MasaServiceReference;
using TempSvc = ClienteWebConversion.TemperaturaServiceReference;

namespace ClienteWebConversion
{
    public partial class Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            // Verificar autenticación
            if (Session["UsuarioAutenticado"] == null)
            {
                Response.Redirect("~/Login.aspx");
                return;
            }

            if (!IsPostBack)
            {
                lblUsuario.Text = Session["UsuarioAutenticado"].ToString();
            }
        }

        protected void btnCerrarSesion_Click(object sender, EventArgs e)
        {
            Session.Clear();
            Session.Abandon();
            Response.Redirect("~/Login.aspx");
        }

        #region Conversiones de Longitud
        protected void btnLongitud_Click(object sender, EventArgs e)
        {
            OcultarResultados();

            string tipoConversion = ddlLongitud.SelectedValue;
            string valor = txtLongitud.Text.Trim();

            try
            {
                string endpointUrl = ConfigHelper.GetServiceURL("longitud");
                var binding = new BasicHttpBinding();
                var endpoint = new EndpointAddress(endpointUrl);

                using (var client = new LongitudSvc.LongitudServiceClient(binding, endpoint))
                {
                    LongitudSvc.ConversionResult resultado = null;

                    switch (tipoConversion)
                    {
                        case "millaMetro":
                            resultado = client.MillaAMetro(valor);
                            break;
                        case "metroMilla":
                            resultado = client.MetroAMilla(valor);
                            break;
                        case "millaPulgada":
                            resultado = client.MillaAPulgada(valor);
                            break;
                        case "pulgadaMilla":
                            resultado = client.PulgadaAMilla(valor);
                            break;
                        case "metroPulgada":
                            resultado = client.MetroAPulgada(valor);
                            break;
                        case "pulgadaMetro":
                            resultado = client.PulgadaAMetro(valor);
                            break;
                    }

                    if (resultado != null && resultado.Exitoso)
                    {
                        var conv = resultado.Resultado;
                        litResultadoLongitud.Text = FormatearResultado(
                            conv.ValorOriginal,
                            conv.UnidadOrigen,
                            conv.ValorConvertidoRedondeado,
                            conv.UnidadDestino,
                            conv.ValorConvertidoExacto,
                            conv.FactorConversion
                        );
                        pnlResultadoLongitud.Visible = true;
                    }
                    else if (resultado != null && resultado.Error != null)
                    {
                        var error = resultado.Error;
                        litErrorLongitud.Text = FormatearError(
                            error.CodigoError,
                            error.TipoError,
                            error.Mensaje,
                            error.Detalles
                        );
                        pnlErrorLongitud.Visible = true;
                    }
                }
            }
            catch (Exception ex)
            {
                litErrorLongitud.Text = FormatearExcepcion(ex);
                pnlErrorLongitud.Visible = true;
            }
        }
        #endregion

        #region Conversiones de Masa
        protected void btnMasa_Click(object sender, EventArgs e)
        {
            OcultarResultados();

            string tipoConversion = ddlMasa.SelectedValue;
            string valor = txtMasa.Text.Trim();

            try
            {
                string endpointUrl = ConfigHelper.GetServiceURL("masa");
                var binding = new BasicHttpBinding();
                var endpoint = new EndpointAddress(endpointUrl);

                using (var client = new MasaSvc.MasaServiceClient(binding, endpoint))
                {
                    MasaSvc.ConversionResult resultado = null;

                    switch (tipoConversion)
                    {
                        case "kilogramoQuintal":
                            resultado = client.KilogramoAQuintal(valor);
                            break;
                        case "quintalKilogramo":
                            resultado = client.QuintalAKilogramo(valor);
                            break;
                        case "kilogramoLibra":
                            resultado = client.KilogramoALibra(valor);
                            break;
                        case "libraKilogramo":
                            resultado = client.LibraAKilogramo(valor);
                            break;
                        case "quintalLibra":
                            resultado = client.QuintalALibra(valor);
                            break;
                        case "libraQuintal":
                            resultado = client.LibraAQuintal(valor);
                            break;
                    }

                    if (resultado != null && resultado.Exitoso)
                    {
                        var conv = resultado.Resultado;
                        litResultadoMasa.Text = FormatearResultado(
                            conv.ValorOriginal,
                            conv.UnidadOrigen,
                            conv.ValorConvertidoRedondeado,
                            conv.UnidadDestino,
                            conv.ValorConvertidoExacto,
                            conv.FactorConversion
                        );
                        pnlResultadoMasa.Visible = true;
                    }
                    else if (resultado != null && resultado.Error != null)
                    {
                        var error = resultado.Error;
                        litErrorMasa.Text = FormatearError(
                            error.CodigoError,
                            error.TipoError,
                            error.Mensaje,
                            error.Detalles
                        );
                        pnlErrorMasa.Visible = true;
                    }
                }
            }
            catch (Exception ex)
            {
                litErrorMasa.Text = FormatearExcepcion(ex);
                pnlErrorMasa.Visible = true;
            }
        }
        #endregion

        #region Conversiones de Temperatura
        protected void btnTemperatura_Click(object sender, EventArgs e)
        {
            OcultarResultados();

            string tipoConversion = ddlTemperatura.SelectedValue;
            string valor = txtTemperatura.Text.Trim();

            try
            {
                string endpointUrl = ConfigHelper.GetServiceURL("temperatura");
                var binding = new BasicHttpBinding();
                var endpoint = new EndpointAddress(endpointUrl);

                using (var client = new TempSvc.TemperaturaServiceClient(binding, endpoint))
                {
                    TempSvc.ConversionResult resultado = null;

                    switch (tipoConversion)
                    {
                        case "celsiusFahrenheit":
                            resultado = client.CelsiusAFahrenheit(valor);
                            break;
                        case "celsiusKelvin":
                            resultado = client.CelsiusAKelvin(valor);
                            break;
                        case "fahrenheitCelsius":
                            resultado = client.FahrenheitACelsius(valor);
                            break;
                        case "fahrenheitKelvin":
                            resultado = client.FahrenheitAKelvin(valor);
                            break;
                        case "kelvinCelsius":
                            resultado = client.KelvinACelsius(valor);
                            break;
                        case "kelvinFahrenheit":
                            resultado = client.KelvinAFahrenheit(valor);
                            break;
                    }

                    if (resultado != null && resultado.Exitoso)
                    {
                        var conv = resultado.Resultado;
                        litResultadoTemperatura.Text = FormatearResultado(
                            conv.ValorOriginal,
                            conv.UnidadOrigen,
                            conv.ValorConvertidoRedondeado,
                            conv.UnidadDestino,
                            conv.ValorConvertidoExacto,
                            conv.FactorConversion
                        );
                        pnlResultadoTemperatura.Visible = true;
                    }
                    else if (resultado != null && resultado.Error != null)
                    {
                        var error = resultado.Error;
                        litErrorTemperatura.Text = FormatearError(
                            error.CodigoError,
                            error.TipoError,
                            error.Mensaje,
                            error.Detalles
                        );
                        pnlErrorTemperatura.Visible = true;
                    }
                }
            }
            catch (Exception ex)
            {
                litErrorTemperatura.Text = FormatearExcepcion(ex);
                pnlErrorTemperatura.Visible = true;
            }
        }
        #endregion

        #region Helpers de Formateo
        private void OcultarResultados()
        {
            pnlResultadoLongitud.Visible = false;
            pnlErrorLongitud.Visible = false;
            pnlResultadoMasa.Visible = false;
            pnlErrorMasa.Visible = false;
            pnlResultadoTemperatura.Visible = false;
            pnlErrorTemperatura.Visible = false;
        }

        // MÉTODO CORREGIDO: Acepta object para manejar tanto string como double
        private string FormatearResultado(object valorOriginal, object unidadOrigen,
                                         object valorConvertido, object unidadDestino,
                                         object valorExacto, object factor)
        {
            var sb = new StringBuilder();

            sb.Append("<div class='result-item'>");
            sb.Append("<span class='result-label'>Valor Original:</span>");
            sb.AppendFormat("<span class='result-value'>{0} {1}</span>", valorOriginal, unidadOrigen);
            sb.Append("</div>");

            sb.Append("<div class='result-item'>");
            sb.Append("<span class='result-label'>Valor Convertido:</span>");
            sb.AppendFormat("<span class='result-value'>{0} {1}</span>", valorConvertido, unidadDestino);
            sb.Append("</div>");

            sb.Append("<div class='result-item'>");
            sb.Append("<span class='result-label'>Valor Exacto:</span>");
            sb.AppendFormat("<span class='result-value'>{0}</span>", valorExacto);
            sb.Append("</div>");

            sb.Append("<div class='result-item'>");
            sb.Append("<span class='result-label'>Factor de Conversión:</span>");
            sb.AppendFormat("<span class='result-value'>{0}</span>", factor);
            sb.Append("</div>");

            return sb.ToString();
        }

        // MÉTODO CORREGIDO: Acepta object para manejar tanto string como int
        private string FormatearError(object codigo, object tipo, object mensaje, object detalles)
        {
            var sb = new StringBuilder();

            sb.Append("<div class='result-item'>");
            sb.Append("<span class='result-label'>Código:</span>");
            sb.AppendFormat("<span class='result-value'>{0}</span>", codigo);
            sb.Append("</div>");

            sb.Append("<div class='result-item'>");
            sb.Append("<span class='result-label'>Tipo:</span>");
            sb.AppendFormat("<span class='result-value'>{0}</span>", tipo);
            sb.Append("</div>");

            sb.Append("<div class='result-item'>");
            sb.Append("<span class='result-label'>Mensaje:</span>");
            sb.AppendFormat("<span class='result-value'>{0}</span>", mensaje);
            sb.Append("</div>");

            if (detalles != null && !string.IsNullOrEmpty(detalles.ToString()))
            {
                sb.Append("<div class='result-item'>");
                sb.Append("<span class='result-label'>Detalles:</span>");
                sb.AppendFormat("<span class='result-value'>{0}</span>", detalles);
                sb.Append("</div>");
            }

            return sb.ToString();
        }

        private string FormatearExcepcion(Exception ex)
        {
            var sb = new StringBuilder();

            sb.Append("<div class='result-item'>");
            sb.Append("<span class='result-label'>Error:</span>");
            sb.AppendFormat("<span class='result-value'>{0}</span>", ex.Message);
            sb.Append("</div>");

            sb.Append("<div class='result-item'>");
            sb.Append("<span class='result-label'>Servidor:</span>");
            sb.AppendFormat("<span class='result-value'>{0}</span>", ConfigHelper.ServidorURL);
            sb.Append("</div>");

            return sb.ToString();
        }
        #endregion
    }
}