using System.Windows;
using System.Windows.Controls;
using ConUni_CliEsc_GR01.ec.edu.monster.config;
using ConUni_CliEsc_GR01.ec.edu.monster.models;

namespace ConUni_CliEsc_GR01;

public partial class TemperaturaView : UserControl
{
    public TemperaturaView()
    {
        InitializeComponent();
    }

    private void CmbTipoConversion_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        // Ocultar paneles de resultado/error cuando se cambia la selección
        // Verificar que los elementos estén inicializados (puede dispararse antes de InitializeComponent)
        if (pnlResultado != null && pnlError != null)
        {
            pnlResultado.Visibility = Visibility.Collapsed;
            pnlError.Visibility = Visibility.Collapsed;
        }
    }

    private async void BtnConvertir_Click(object sender, RoutedEventArgs e)
    {
        // Ocultar paneles previos
        pnlResultado.Visibility = Visibility.Collapsed;
        pnlError.Visibility = Visibility.Collapsed;

        // Validar selección
        if (cmbTipoConversion.SelectedIndex == 0)
        {
            MostrarError("Por favor seleccione un tipo de conversión");
            return;
        }

        // Validar valor numérico
        if (!double.TryParse(txtValor.Text, out double valor))
        {
            MostrarError("Por favor ingrese un valor numérico válido");
            return;
        }

        // Obtener unidades según la selección
        var (unidadOrigen, unidadDestino) = ObtenerUnidades(cmbTipoConversion.SelectedIndex);

        if (string.IsNullOrEmpty(unidadOrigen))
        {
            MostrarError("Tipo de conversión no válido");
            return;
        }

        // Crear solicitud
        var request = new ConversionRequest
        {
            Valor = valor,
            UnidadOrigen = unidadOrigen,
            UnidadDestino = unidadDestino
        };

        try
        {
            // Deshabilitar botón durante la petición
            btnConvertir.IsEnabled = false;
            btnConvertir.Content = "CONVIRTIENDO...";

            // Llamar al servicio
            var service = ConfigurationManager.GetConversionService();
            var resultado = await service.ConvertirTemperaturaAsync(request);

            // Mostrar resultado
            if (resultado.Exitoso && resultado.Resultado != null)
            {
                MostrarResultado(resultado.Resultado);
            }
            else if (resultado.Error != null)
            {
                MostrarError(resultado.Error.Mensaje);
            }
        }
        catch (Exception ex)
        {
            MostrarError($"Error al realizar la conversión: {ex.Message}");
        }
        finally
        {
            // Rehabilitar botón
            btnConvertir.IsEnabled = true;
            btnConvertir.Content = "CONVERTIR";
        }
    }

    private (string origen, string destino) ObtenerUnidades(int selectedIndex)
    {
        return selectedIndex switch
        {
            1 => ("Celsius", "Fahrenheit"),
            2 => ("Fahrenheit", "Celsius"),
            3 => ("Celsius", "Kelvin"),
            4 => ("Kelvin", "Celsius"),
            5 => ("Fahrenheit", "Kelvin"),
            6 => ("Kelvin", "Fahrenheit"),
            _ => (string.Empty, string.Empty)
        };
    }

    private void MostrarResultado(UnidadConversionModel resultado)
    {
        txtValorOriginal.Text = $"{resultado.ValorOriginal} {resultado.UnidadOrigen}";
        txtValorConvertido.Text = $"{resultado.ValorConvertidoRedondeado} {resultado.UnidadDestino}";
        txtValorExacto.Text = resultado.ValorConvertidoExacto.ToString("F10");
        txtFactorConversion.Text = resultado.FactorConversion.ToString("F10");

        pnlResultado.Visibility = Visibility.Visible;
    }

    private void MostrarError(string mensaje)
    {
        txtMensajeError.Text = mensaje;
        pnlError.Visibility = Visibility.Visible;
    }
}
