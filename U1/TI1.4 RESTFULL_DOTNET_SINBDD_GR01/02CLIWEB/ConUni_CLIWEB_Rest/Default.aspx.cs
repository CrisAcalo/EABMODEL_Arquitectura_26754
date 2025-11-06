using System;
using System.Web.UI;
using ConUni_CLIWEB_Rest.ec.edu.monster.services;
using ConUni_CLIWEB_Rest.ec.edu.monster.models;

namespace ConUni_CLIWEB_Rest
{
    public partial class Default : Page
    {
        private RestConversionService _conversionService;

        protected void Page_Load(object sender, EventArgs e)
        {
            // Verificar autenticación
            if (Session["UsuarioAutenticado"] == null)
            {
                Response.Redirect("Login.aspx");
            }

            if (!IsPostBack)
            {
                lblUsuario.Text = Session["UsuarioAutenticado"].ToString();
            }

            // Inicializar servicio
            _conversionService = new RestConversionService();
        }

        protected void btnCerrarSesion_Click(object sender, EventArgs e)
        {
            Session.Clear();
            Session.Abandon();
            Response.Redirect("Login.aspx");
        }

        // ========== CONVERSIONES DE LONGITUD ==========
        protected void btnConvertirLongitud_Click(object sender, EventArgs e)
        {
            OcultarResultados();

            string tipoConversion = ddlLongitud.SelectedValue;
            string valor = txtValorLongitud.Text.Trim();

            var resultado = _conversionService.ConvertirLongitud(tipoConversion, valor);

            if (resultado.Exitoso)
            {
                pnlResultadoLongitud.Visible = true;
                litResultadoLongitud.Text = FormatearResultado(resultado.Resultado);
            }
            else
            {
                pnlErrorLongitud.Visible = true;
                litErrorLongitud.Text = FormatearError(resultado.Error);
            }
        }

        // ========== CONVERSIONES DE MASA ==========
        protected void btnConvertirMasa_Click(object sender, EventArgs e)
        {
            OcultarResultados();

            string tipoConversion = ddlMasa.SelectedValue;
            string valor = txtValorMasa.Text.Trim();

            var resultado = _conversionService.ConvertirMasa(tipoConversion, valor);

            if (resultado.Exitoso)
            {
                pnlResultadoMasa.Visible = true;
                litResultadoMasa.Text = FormatearResultado(resultado.Resultado);
            }
            else
            {
                pnlErrorMasa.Visible = true;
                litErrorMasa.Text = FormatearError(resultado.Error);
            }
        }

        // ========== CONVERSIONES DE TEMPERATURA ==========
        protected void btnConvertirTemperatura_Click(object sender, EventArgs e)
        {
            OcultarResultados();

            string tipoConversion = ddlTemperatura.SelectedValue;
            string valor = txtValorTemperatura.Text.Trim();

            var resultado = _conversionService.ConvertirTemperatura(tipoConversion, valor);

            if (resultado.Exitoso)
            {
                pnlResultadoTemperatura.Visible = true;
                litResultadoTemperatura.Text = FormatearResultado(resultado.Resultado);
            }
            else
            {
                pnlErrorTemperatura.Visible = true;
                litErrorTemperatura.Text = FormatearError(resultado.Error);
            }
        }

        // ========== MÉTODOS AUXILIARES ==========
        private void OcultarResultados()
        {
            // Ocultar todos los resultados
            pnlResultadoLongitud.Visible = false;
            pnlErrorLongitud.Visible = false;
            pnlResultadoMasa.Visible = false;
            pnlErrorMasa.Visible = false;
            pnlResultadoTemperatura.Visible = false;
            pnlErrorTemperatura.Visible = false;
        }

        private string FormatearResultado(ResultadoConversion conv)
        {
            return $@"
                <div class='result-item'>
                    <span class='result-label'>Valor Original:</span>
                    <span class='result-value'>{conv.ValorOriginal} {conv.UnidadOrigen}</span>
                </div>
                <div class='result-item'>
                    <span class='result-label'>Valor Convertido:</span>
                    <span class='result-value'>{conv.ValorConvertidoRedondeado} {conv.UnidadDestino}</span>
                </div>
                <div class='result-item'>
                    <span class='result-label'>Valor Exacto:</span>
                    <span class='result-value'>{conv.ValorConvertidoExacto}</span>
                </div>
                <div class='result-item'>
                    <span class='result-label'>Factor de Conversión:</span>
                    <span class='result-value'>{conv.FactorConversion}</span>
                </div>";
        }

        private string FormatearError(ErrorConversion error)
        {
            string html = $@"
                <div class='result-item'>
                    <span class='result-label'>Mensaje:</span>
                    <span class='result-value'>{Server.HtmlEncode(error.Mensaje)}</span>
                </div>";

            if (!string.IsNullOrEmpty(error.CodigoError))
            {
                html += $@"
                <div class='result-item'>
                    <span class='result-label'>Código:</span>
                    <span class='result-value'>{error.CodigoError}</span>
                </div>";
            }

            if (!string.IsNullOrEmpty(error.TipoError))
            {
                html += $@"
                <div class='result-item'>
                    <span class='result-label'>Tipo:</span>
                    <span class='result-value'>{error.TipoError}</span>
                </div>";
            }

            if (!string.IsNullOrEmpty(error.Detalles))
            {
                html += $@"
                <div class='result-item'>
                    <span class='result-label'>Detalles:</span>
                    <span class='result-value'>{Server.HtmlEncode(error.Detalles)}</span>
                </div>";
            }

            return html;
        }
    }
}