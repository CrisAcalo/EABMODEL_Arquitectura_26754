using UniversalConverter.Client.ViewModels;

namespace UniversalConverter.Client;

public partial class MainPage : ContentPage
{
    public MainPage(MainViewModel viewModel)
    {
        InitializeComponent();

        BindingContext = viewModel;
    }
}