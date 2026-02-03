using CLIEurekabank.Shared.Services;
using CLIEurekabank.Web.Components;
using CLIEurekabank.Web.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

// Add device-specific services used by the CLIEurekabank.Shared project
builder.Services.AddSingleton<IFormFactor, FormFactor>();

// Configuration
builder.Services.AddSingleton<CLIEurekabank.Shared.Config.AppConfig>();

// AppState scoped for session management (Isolated per user circuit outside of singleton)
builder.Services.AddScoped<CLIEurekabank.Shared.Services.AppState>();

// EurekaBank Services
builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.AuthService>();
builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.BloqueoWebSocketService>();
builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.CuentaService>();
builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.TransaccionService>();
builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.ClienteService>();
builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.ReporteService>();
builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.MonedaService>();
builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.SucursalService>();
builder.Services.AddScoped<CLIEurekabank.Shared.Services.Wrappers.VentanillaService>();
builder.Services.AddScoped<BloqueoService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
}
app.UseStatusCodePagesWithReExecute("/not-found", createScopeForStatusCodePages: true);
app.UseAntiforgery();

app.MapStaticAssets();

app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode()
    .AddAdditionalAssemblies(
        typeof(CLIEurekabank.Shared._Imports).Assembly);

app.Run();
