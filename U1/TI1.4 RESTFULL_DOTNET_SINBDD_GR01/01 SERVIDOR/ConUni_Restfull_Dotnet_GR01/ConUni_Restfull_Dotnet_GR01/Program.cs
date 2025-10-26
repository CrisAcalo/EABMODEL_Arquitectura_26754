using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllers();

// Registrar servicios de conversión
builder.Services.AddScoped<LongitudService>();
builder.Services.AddScoped<MasaService>();
builder.Services.AddScoped<TemperaturaService>();

// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(options =>
{
    options.SwaggerDoc("v1", new Microsoft.OpenApi.Models.OpenApiInfo
    {
        Title = "API de Conversión de Unidades",
        Version = "v1",
        Description = "API RESTful para convertir unidades de Longitud, Masa y Temperatura",
        Contact = new Microsoft.OpenApi.Models.OpenApiContact
        {
            Name = "GR01",
            Email = "info@monster.edu.ec"
        }
    });
});

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI(options =>
    {
        options.SwaggerEndpoint("/swagger/v1/swagger.json", "API Conversión Unidades v1");
    });
}

app.UseAuthorization();

app.MapControllers();

app.Run();
