using UniversalConverter.Client.ViewModels;

namespace UniversalConverter.Client.Views;

public partial class LoginPage : ContentPage
{
	public LoginPage(LoginViewModel viewModel)
	{
		InitializeComponent();
		BindingContext = viewModel;
	}
} 