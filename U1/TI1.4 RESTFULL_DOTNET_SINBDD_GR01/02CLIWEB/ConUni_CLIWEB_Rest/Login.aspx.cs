using System;
using System.Configuration;
using System.Data.SqlClient;
using System.Web.UI;

namespace ConUni_CLIWEB_Rest
{
    public partial class Login : Page
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
                    Response.Redirect("Default.aspx");
                }
            }
        }

        protected void btnIngresar_Click(object sender, EventArgs e)
        {
            // Obtener intentos de la sesión
            int intentos = Session["IntentosLogin"] != null ? (int)Session["IntentosLogin"] : 0;

            if (intentos >= MAX_INTENTOS)
            {
                MostrarError("Has superado el número máximo de intentos.");
                return;
            }

            string usuario = txtUsuario.Text.Trim();
            string contrasena = txtContrasena.Text;

            // Validar credenciales
            if (usuario == USUARIO_VALIDO && contrasena == CONTRASENA_VALIDA)
            {
                // Login exitoso
                Session["UsuarioAutenticado"] = usuario;
                Session["IntentosLogin"] = 0;
                Session.Timeout = 30; // 30 minutos

                Response.Redirect("Default.aspx");
            }
            else
            {
                // Login fallido
                intentos++;
                Session["IntentosLogin"] = intentos;

                int intentosRestantes = MAX_INTENTOS - intentos;

                if (intentosRestantes > 0)
                {
                    MostrarError($"Usuario o contraseña incorrectos. Te quedan {intentosRestantes} intento(s).");
                }
                else
                {
                    MostrarError("Has superado el número máximo de intentos.");
                }
            }
        }

        private void MostrarError(string mensaje)
        {
            pnlError.Visible = true;
            lblError.Text = mensaje;
        }
    }
}