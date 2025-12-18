using Comercializadora.Core.Managers;
using Comercializadora.Core.Services.Abstractions;
using Comercializadora.Core.Services.Implementations.Rest;
using Comercializadora.Shared.Services;
using Comercializadora.Web.Components;
using Comercializadora.Web.Services;
using Comercializadora.Web.Services;
using System.Reflection;

var builder = WebApplication.CreateBuilder(args);

// Configurar logging mejorado
builder.Logging.ClearProviders();
builder.Logging.AddConsole();
builder.Logging.AddDebug();

// Configurar niveles de log más detallados en desarrollo
if (builder.Environment.IsDevelopment())
{
    builder.Logging.SetMinimumLevel(LogLevel.Debug);
    builder.Services.Configure<LoggerFilterOptions>(options =>
    {
        options.AddFilter("Comercializadora", LogLevel.Debug);
        options.AddFilter("System.Net.Http.HttpClient", LogLevel.Information);
    });
}

// ⭐ CARGAR CONFIGURACIÓN COMPARTIDA COMO EL PROYECTO MAUI ⭐
try
{
    // Cargar configuración desde el proyecto Comercializadora (MAUI)
    var sharedConfigPath = Path.Combine(builder.Environment.ContentRootPath, "..", "Comercializadora", "appsettings.json");
    
    if (File.Exists(sharedConfigPath))
    {
        builder.Configuration.AddJsonFile(sharedConfigPath, optional: false, reloadOnChange: true);
        var configLogger = LoggerFactory.Create(b => b.AddConsole()).CreateLogger("ConfigLoader");
        configLogger.LogInformation("✅ Configuración compartida cargada desde: {Path}", sharedConfigPath);
    }
    else
    {
        var configLogger = LoggerFactory.Create(b => b.AddConsole()).CreateLogger("ConfigLoader");
        configLogger.LogWarning("⚠️ No se encontró configuración compartida en: {Path}", sharedConfigPath);
    }
}
catch (Exception ex)
{
    var configLogger = LoggerFactory.Create(b => b.AddConsole()).CreateLogger("ConfigLoader");
    configLogger.LogError(ex, "❌ Error al cargar configuración compartida");
}

// Add services to the container.
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

// --- REGISTRO DE SERVICIOS CORE ---
builder.Services.AddSingleton<ApiServiceManager>();

// --- SERVICIO DE AUTENTICACIÓN ---
builder.Services.AddSingleton<IAuthenticationService, AuthenticationService>();

// Configurar HttpClient con timeout y headers
builder.Services.AddHttpClient("ComercializadoraClient", client =>
{
    client.Timeout = TimeSpan.FromMinutes(5);
    client.DefaultRequestHeaders.Add("User-Agent", "ComercializadoraApp/1.0");
}).ConfigurePrimaryHttpMessageHandler(() => new HttpClientHandler()
{
    ServerCertificateCustomValidationCallback = (message, cert, chain, errors) => true // Solo para desarrollo
});

builder.Services.AddHttpClient();

// --- REGISTRAR SERVICIOS DE PRODUCTOS ---
// --- REGISTRAR SERVICIOS DE PRODUCTOS (REST ONLY) ---
builder.Services.AddSingleton<IProductService, RestProductService>();

// Servicios de Facturación (REST ONLY)
builder.Services.AddSingleton<IFacturacionService, RestFacturacionService>();

// Servicios de Crédito (REST ONLY)
builder.Services.AddSingleton<ICreditoService, RestCreditoService>();

// Add device-specific services used by the Comercializadora.Shared project
builder.Services.AddSingleton<IFormFactor, FormFactor>();

var app = builder.Build();

// Log de configuración al iniciar
var logger = app.Services.GetRequiredService<ILogger<Program>>();
logger.LogInformation("🚀 Aplicación Comercializadora Web iniciando...");
logger.LogInformation("🌍 Ambiente: {Environment}", app.Environment.EnvironmentName);

// Verificar configuraciones críticas con logging mejorado
var configuration = app.Services.GetRequiredService<IConfiguration>();

// Verificar URLs REST  
var restComercializadoraUrl = configuration["Hosts:Comercializadora:Rest:Java"];
var restBanQuitoUrl = configuration["Hosts:BanQuito:Rest:Java"];

logger.LogInformation("📊 CONFIGURACIÓN CARGADA:");
logger.LogInformation("  ⚡ REST Comercializadora: {RestComercializadoraUrl}", restComercializadoraUrl ?? "❌ NO CONFIGURADA");
logger.LogInformation("  🏦 REST BanQuito: {RestBanQuitoUrl}", restBanQuitoUrl ?? "❌ NO CONFIGURADA");

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    app.UseHsts();
}
else
{
    logger.LogInformation("🔧 Modo desarrollo activado - Certificados SSL ignorados");
}

app.UseStatusCodePagesWithReExecute("/not-found", createScopeForStatusCodePages: true);
app.UseHttpsRedirection();
app.UseAntiforgery();
app.MapStaticAssets();

app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode()
    .AddAdditionalAssemblies(
        typeof(Comercializadora.Shared._Imports).Assembly);

logger.LogInformation("✅ Aplicación Comercializadora Web iniciada correctamente");

app.Run();
