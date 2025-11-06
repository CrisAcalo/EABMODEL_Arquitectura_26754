using System;
using System.Web.UI;

namespace ClienteWebConversion
{
    // IMPORTANTE: Este archivo debe estar en la raíz del proyecto
    // junto con Login.aspx
    public partial class Login : System.Web.UI.Page
    {
        private const string USUARIO_VALIDO = "MONSTER";
        private const string CONTRASENA_VALIDA = "MONSTER9";
        private const int MAX_INTENTOS = 3;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Si ya está autenticado, redirigir a la página principal
                if (Session["UsuarioAutenticado"] != null)
                {
                    Response.Redirect("~/Default.aspx");
                }

                // Verificar intentos
                int intentos = Session["Intentos"] != null ? (int)Session["Intentos"] : 0;

                if (intentos >= MAX_INTENTOS)
                {
                    pnlBloqueado.Visible = true;
                    pnlFormulario.Visible = false;
                }
                else if (intentos > 0)
                {
                    int intentosRestantes = MAX_INTENTOS - intentos;
                    lblIntentos.Text = $"Te quedan {intentosRestantes} intento(s)";
                    pnlIntentos.Visible = true;
                }
            }
        }

        protected void btnLogin_Click(object sender, EventArgs e)
        {
            // Validar que los campos no estén vacíos
            if (string.IsNullOrWhiteSpace(txtUsuario.Text) || string.IsNullOrWhiteSpace(txtContrasena.Text))
            {
                MostrarError("Debe ingresar usuario y contraseña.");
                return;
            }

            // Obtener intentos actuales
            int intentos = Session["Intentos"] != null ? (int)Session["Intentos"] : 0;

            // Verificar si ya superó los intentos
            if (intentos >= MAX_INTENTOS)
            {
                MostrarError("Has superado el número máximo de intentos.");
                pnlFormulario.Visible = false;
                pnlBloqueado.Visible = true;
                return;
            }

            // Validar credenciales
            string usuario = txtUsuario.Text.Trim();
            string contrasena = txtContrasena.Text;

            if (USUARIO_VALIDO.Equals(usuario) && CONTRASENA_VALIDA.Equals(contrasena))
            {
                // Login exitoso
                Session["UsuarioAutenticado"] = usuario;
                Session["Intentos"] = 0;
                Session.Timeout = 30; // 30 minutos

                // Redirigir a la página principal
                Response.Redirect("~/Default.aspx");
            }
            else
            {
                // Login fallido
                intentos++;
                Session["Intentos"] = intentos;

                int intentosRestantes = MAX_INTENTOS - intentos;

                if (intentosRestantes > 0)
                {
                    MostrarError("Usuario o contraseña incorrectos.");
                    lblIntentos.Text = $"Te quedan {intentosRestantes} intento(s)";
                    pnlIntentos.Visible = true;
                }
                else
                {
                    MostrarError("Has superado el número máximo de intentos.");
                    pnlFormulario.Visible = false;
                    pnlBloqueado.Visible = true;
                }

                // Limpiar contraseña
                txtContrasena.Text = string.Empty;
            }
        }

        private void MostrarError(string mensaje)
        {
            lblError.Text = "❌ " + mensaje;
            pnlError.Visible = true;
        }
    }
}