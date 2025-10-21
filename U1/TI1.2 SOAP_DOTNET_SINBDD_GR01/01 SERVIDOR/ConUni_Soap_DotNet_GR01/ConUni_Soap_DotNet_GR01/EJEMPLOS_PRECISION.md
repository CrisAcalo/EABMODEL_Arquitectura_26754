# Ejemplos de Uso - API WCF con Objetos UnidadConversion

## ?? API Unificada para Clientes

Todos los servicios WCF ahora retornan objetos `UnidadConversion` completos, proporcionando máxima flexibilidad a los clientes.

## ?? Consumo de Servicios WCF

### Longitud
```csharp
// Cliente WCF consume el servicio
LongitudServiceClient cliente = new LongitudServiceClient();

// Conversión completa con toda la información
UnidadConversion resultado = cliente.KilometroAMilla(10.0);

// El cliente puede acceder a ambos valores
Console.WriteLine($"Valor exacto: {resultado.ValorConvertidoExacto}");      // 6.21371
Console.WriteLine($"Valor redondeado: {resultado.ValorConvertidoRedondeado}"); // 6.21
Console.WriteLine($"Factor usado: {resultado.FactorConversion}");           // 0.621371
Console.WriteLine($"Conversión: {resultado}"); // "10 Kilómetro = 6.21 Milla (Exacto: 6.21371)"
```

### Masa
```csharp
// Cliente WCF consume el servicio
MasaServiceClient cliente = new MasaServiceClient();

// Conversión completa
UnidadConversion resultado = cliente.GramoAOnza(1000.0);

Console.WriteLine($"Original: {resultado.ValorOriginal} {resultado.UnidadOrigen}");
Console.WriteLine($"Exacto: {resultado.ValorConvertidoExacto} {resultado.UnidadDestino}");     // 35.274
Console.WriteLine($"Redondeado: {resultado.ValorConvertidoRedondeado} {resultado.UnidadDestino}"); // 35.27
Console.WriteLine($"Fecha: {resultado.FechaConversion}");
```

### Temperatura
```csharp
// Cliente WCF consume el servicio
TemperaturaServiceClient cliente = new TemperaturaServiceClient();

// Conversión con validaciones de límites físicos
UnidadConversion resultado = cliente.CelsiusAFahrenheit(25.0);

Console.WriteLine($"Conversión: {resultado.ValorOriginal}°{resultado.UnidadOrigen} " +
                 $"= {resultado.ValorConvertidoRedondeado}°{resultado.UnidadDestino}");
// "Conversión: 25°Celsius = 77.00°Fahrenheit"
```

## ?? Flexibilidad para Diferentes Tipos de Clientes

### Cliente Simple (Solo necesita el valor)
```csharp
UnidadConversion resultado = cliente.MetroAPie(1.5);
double pies = resultado.ValorConvertidoRedondeado; // 4.92
```

### Cliente Científico (Necesita precisión máxima)
```csharp
UnidadConversion resultado = cliente.MetroAPie(1.5);
double piesExactos = resultado.ValorConvertidoExacto; // 4.92126
```

### Cliente de Auditoría (Necesita metadatos completos)
```csharp
UnidadConversion resultado = cliente.KilogramoAQuintal(250.0);
string log = $"Conversión: {resultado.ValorOriginal} {resultado.UnidadOrigen} " +
             $"a {resultado.ValorConvertidoExacto} {resultado.UnidadDestino} " +
             $"(Factor: {resultado.FactorConversion}) " +
             $"el {resultado.FechaConversion:yyyy-MM-dd HH:mm:ss}";
```

### Cliente de UI (Formato amigable)
```csharp
UnidadConversion resultado = cliente.CentimetroAPulgada(25.4);
string display = resultado.ToString(); 
// "25.4 Centímetro = 10.00 Pulgada (Exacto: 10.00394)"
```

## ? Casos de Uso Avanzados

### Encadenamiento de Conversiones
```csharp
// Convertir metros ? pies ? pulgadas manteniendo precisión
UnidadConversion metrosAPies = longitudClient.MetroAPie(1.0);
// Usar el valor exacto para la siguiente conversión
UnidadConversion piesAPulgadas = longitudClient.PieAPulgada(metrosAPies.ValorConvertidoExacto);
```

### Validación de Resultados
```csharp
try 
{
    UnidadConversion resultado = temperaturaClient.CelsiusAKelvin(-300.0);
}
catch (ArgumentException ex)
{
    Console.WriteLine($"Error de validación: {ex.Message}");
    // "La temperatura en Celsius no puede ser menor al cero absoluto (-273.15°C)"
}
```

### Comparación de Métodos
```csharp
UnidadConversion metodo1 = masaClient.GramoALibra(500.0);
UnidadConversion metodo2 = masaClient.GramoAOnza(500.0);

Console.WriteLine($"500g = {metodo1.ValorConvertidoRedondeado} libras");
Console.WriteLine($"500g = {metodo2.ValorConvertidoRedondeado} onzas");
```

## ?? Ventajas de la API Unificada

### ? Para Desarrolladores
- **Consistencia**: Todos los servicios funcionan igual
- **Flexibilidad**: Acceso a valor exacto y redondeado
- **Metadatos**: Información completa de cada conversión
- **Debugging**: ToString() descriptivo para logs

### ? Para Arquitectos
- **Escalabilidad**: Fácil agregar nuevas conversiones
- **Mantenibilidad**: Lógica centralizada en business services
- **Testabilidad**: Objetos ricos para assertions
- **Documentación**: Auto-documentado con metadatos

### ? Para Usuarios Finales
- **Precisión**: Valores exactos disponibles cuando se necesiten
- **Simplicidad**: Valores redondeados para presentación
- **Confiabilidad**: Validaciones robustas
- **Transparencia**: Factor de conversión visible

## ?? Especificaciones Técnicas

### Tipo de Retorno
```csharp
public class UnidadConversion
{
    public double ValorOriginal { get; set; }
    public double ValorConvertidoExacto { get; set; }      // Precisión completa
    public double ValorConvertidoRedondeado { get; set; }  // 2 decimales
    public string UnidadOrigen { get; set; }
    public string UnidadDestino { get; set; }
    public double FactorConversion { get; set; }
    public DateTime FechaConversion { get; set; }
}
```

### Serialización WCF
- ? Compatible con DataContract
- ? Serializable para múltiples formatos (SOAP, JSON)
- ? Versionado para compatibilidad futura
- ? Optimizado para transferencia de red

---
*API diseñada para máxima flexibilidad y usabilidad por diferentes tipos de clientes.*