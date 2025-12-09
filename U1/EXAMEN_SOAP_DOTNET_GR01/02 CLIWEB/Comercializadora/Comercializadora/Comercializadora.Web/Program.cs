using Comercializadora.Core.Managers;
using Comercializadora.Core.Services.Abstractions;
using Comercializadora.Core.Services.Implementations.Dispatchers;
using Comercializadora.Core.Services.Implementations.Rest;
using Comercializadora.Core.Services.Implementations.Soap;
using Comercializadora.Shared.Services;
using Comercializadora.Web.Components;
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
builder.Services.AddSingleton<SoapProductService>();
builder.Services.AddSingleton<RestProductService>();
builder.Services.AddSingleton<IProductService, ProductServiceDispatcher>();

// Servicios de Facturación
builder.Services.AddSingleton<SoapFacturacionService>();
builder.Services.AddSingleton<RestFacturacionService>(); // Registramos la clase vacía
builder.Services.AddSingleton<IFacturacionService, FacturacionServiceDispatcher>();

// Servicios de Crédito
builder.Services.AddSingleton<SoapCreditoService>();
builder.Services.AddSingleton<RestCreditoService>(); // Registramos la clase vacía
builder.Services.AddSingleton<ICreditoService, CreditoServiceDispatcher>();

// Add device-specific services used by the Comercializadora.Shared project
builder.Services.AddSingleton<IFormFactor, FormFactor>();

var app = builder.Build();

// Log de configuración al iniciar
var logger = app.Services.GetRequiredService<ILogger<Program>>();
logger.LogInformation("🚀 Aplicación Comercializadora Web iniciando...");
logger.LogInformation("🌍 Ambiente: {Environment}", app.Environment.EnvironmentName);

// Verificar configuraciones críticas con logging mejorado
var configuration = app.Services.GetRequiredService<IConfiguration>();

// Verificar URLs SOAP
var soapProductsUrl = configuration["Hosts:Comercializadora:Soap:DotNet:Products"];
var soapBillingUrl = configuration["Hosts:Comercializadora:Soap:DotNet:Billing"];
var soapCreditoUrl = configuration["Hosts:BanQuito:Soap:DotNet:Credito"];

// Verificar URLs REST  
var restComercializadoraUrl = configuration["Hosts:Comercializadora:Rest:Java"];
var restBanQuitoUrl = configuration["Hosts:BanQuito:Rest:Java"];

logger.LogInformation("📊 CONFIGURACIÓN CARGADA:");
logger.LogInformation("  🛍️ SOAP Products: {SoapProductsUrl}", soapProductsUrl ?? "❌ NO CONFIGURADA");
logger.LogInformation("  🧾 SOAP Billing: {SoapBillingUrl}", soapBillingUrl ?? "❌ NO CONFIGURADA");
logger.LogInformation("  💳 SOAP Crédito: {SoapCreditoUrl}", soapCreditoUrl ?? "❌ NO CONFIGURADA");
logger.LogInformation("  ⚡ REST Comercializadora: {RestComercializadoraUrl}", restComercializadoraUrl ?? "❌ NO CONFIGURADA");
logger.LogInformation("  🏦 REST BanQuito: {RestBanQuitoUrl}", restBanQuitoUrl ?? "❌ NO CONFIGURADA");

// Warnings para configuraciones faltantes
if (string.IsNullOrWhiteSpace(soapProductsUrl))
    logger.LogWarning("⚠️ SOAP Products URL no está configurada. Funcionalidad limitada.");
if (string.IsNullOrWhiteSpace(soapBillingUrl))
    logger.LogWarning("⚠️ SOAP Billing URL no está configurada. Funcionalidad limitada.");
if (string.IsNullOrWhiteSpace(soapCreditoUrl))
    logger.LogWarning("⚠️ SOAP Crédito URL no está configurada. Funcionalidad limitada.");

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
