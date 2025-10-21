# Ejemplos de Uso - API WCF con Objetos UnidadConversion

## ?? API Unificada para Clientes

Todos los servicios WCF ahora retornan objetos `UnidadConversion` completos, proporcionando m�xima flexibilidad a los clientes.

## ?? Consumo de Servicios WCF

### Longitud
```csharp
// Cliente WCF consume el servicio
LongitudServiceClient cliente = new LongitudServiceClient();

// Conversi�n completa con toda la informaci�n
UnidadConversion resultado = cliente.KilometroAMilla(10.0);

// El cliente puede acceder a ambos valores
Console.WriteLine($"Valor exacto: {resultado.ValorConvertidoExacto}");      // 6.21371
Console.WriteLine($"Valor redondeado: {resultado.ValorConvertidoRedondeado}"); // 6.21
Console.WriteLine($"Factor usado: {resultado.FactorConversion}");           // 0.621371
Console.WriteLine($"Conversi�n: {resultado}"); // "10 Kil�metro = 6.21 Milla (Exacto: 6.21371)"
```

### Masa
```csharp
// Cliente WCF consume el servicio
MasaServiceClient cliente = new MasaServiceClient();

// Conversi�n completa
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

// Conversi�n con validaciones de l�mites f�sicos
UnidadConversion resultado = cliente.CelsiusAFahrenheit(25.0);

Console.WriteLine($"Conversi�n: {resultado.ValorOriginal}�{resultado.UnidadOrigen} " +
                 $"= {resultado.ValorConvertidoRedondeado}�{resultado.UnidadDestino}");
// "Conversi�n: 25�Celsius = 77.00�Fahrenheit"
```

## ?? Flexibilidad para Diferentes Tipos de Clientes

### Cliente Simple (Solo necesita el valor)
```csharp
UnidadConversion resultado = cliente.MetroAPie(1.5);
double pies = resultado.ValorConvertidoRedondeado; // 4.92
```

### Cliente Cient�fico (Necesita precisi�n m�xima)
```csharp
UnidadConversion resultado = cliente.MetroAPie(1.5);
double piesExactos = resultado.ValorConvertidoExacto; // 4.92126
```

### Cliente de Auditor�a (Necesita metadatos completos)
```csharp
UnidadConversion resultado = cliente.KilogramoAQuintal(250.0);
string log = $"Conversi�n: {resultado.ValorOriginal} {resultado.UnidadOrigen} " +
             $"a {resultado.ValorConvertidoExacto} {resultado.UnidadDestino} " +
             $"(Factor: {resultado.FactorConversion}) " +
             $"el {resultado.FechaConversion:yyyy-MM-dd HH:mm:ss}";
```

### Cliente de UI (Formato amigable)
```csharp
UnidadConversion resultado = cliente.CentimetroAPulgada(25.4);
string display = resultado.ToString(); 
// "25.4 Cent�metro = 10.00 Pulgada (Exacto: 10.00394)"
```

## ? Casos de Uso Avanzados

### Encadenamiento de Conversiones
```csharp
// Convertir metros ? pies ? pulgadas manteniendo precisi�n
UnidadConversion metrosAPies = longitudClient.MetroAPie(1.0);
// Usar el valor exacto para la siguiente conversi�n
UnidadConversion piesAPulgadas = longitudClient.PieAPulgada(metrosAPies.ValorConvertidoExacto);
```

### Validaci�n de Resultados
```csharp
try 
{
    UnidadConversion resultado = temperaturaClient.CelsiusAKelvin(-300.0);
}
catch (ArgumentException ex)
{
    Console.WriteLine($"Error de validaci�n: {ex.Message}");
    // "La temperatura en Celsius no puede ser menor al cero absoluto (-273.15�C)"
}
```

### Comparaci�n de M�todos
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
- **Metadatos**: Informaci�n completa de cada conversi�n
- **Debugging**: ToString() descriptivo para logs

### ? Para Arquitectos
- **Escalabilidad**: F�cil agregar nuevas conversiones
- **Mantenibilidad**: L�gica centralizada en business services
- **Testabilidad**: Objetos ricos para assertions
- **Documentaci�n**: Auto-documentado con metadatos

### ? Para Usuarios Finales
- **Precisi�n**: Valores exactos disponibles cuando se necesiten
- **Simplicidad**: Valores redondeados para presentaci�n
- **Confiabilidad**: Validaciones robustas
- **Transparencia**: Factor de conversi�n visible

## ?? Especificaciones T�cnicas

### Tipo de Retorno
```csharp
public class UnidadConversion
{
    public double ValorOriginal { get; set; }
    public double ValorConvertidoExacto { get; set; }      // Precisi�n completa
    public double ValorConvertidoRedondeado { get; set; }  // 2 decimales
    public string UnidadOrigen { get; set; }
    public string UnidadDestino { get; set; }
    public double FactorConversion { get; set; }
    public DateTime FechaConversion { get; set; }
}
```

### Serializaci�n WCF
- ? Compatible con DataContract
- ? Serializable para m�ltiples formatos (SOAP, JSON)
- ? Versionado para compatibilidad futura
- ? Optimizado para transferencia de red

---
*API dise�ada para m�xima flexibilidad y usabilidad por diferentes tipos de clientes.*