# Manejo de Errores Mejorado para Clientes SOAP

## ?? Problema Anterior
Los errores se manejaban con excepciones de .NET que no se serializaban correctamente en SOAP, causando:
- Mensajes de error inconsistentes
- Falta de información estructurada
- Dificultad para manejar errores en clientes SOAP/REST
- No compatibilidad con SoapUI y otros clientes

## ? Solución Implementada

### Modelo de Error Estructurado
```xml
<!-- Respuesta SOAP con error -->
<ConversionResult>
    <Exitoso>false</Exitoso>
    <Resultado>null</Resultado>
    <Error>
        <CodigoError>VAL_001</CodigoError>
        <Mensaje>El valor no puede ser negativo en Kilómetro</Mensaje>
        <TipoError>Validacion</TipoError>
        <ValorProblematico>-10</ValorProblematico>
        <Unidad>Kilómetro</Unidad>
        <FechaError>2024-01-15T10:30:00</FechaError>
        <Detalles>Las medidas de kilómetro deben ser valores positivos o cero.</Detalles>
    </Error>
</ConversionResult>
```

### Respuesta SOAP Exitosa
```xml
<!-- Respuesta SOAP exitosa -->
<ConversionResult>
    <Exitoso>true</Exitoso>
    <Resultado>
        <ValorOriginal>10</ValorOriginal>
        <ValorConvertidoExacto>6.21371</ValorConvertidoExacto>
        <ValorConvertidoRedondeado>6.21</ValorConvertidoRedondeado>
        <UnidadOrigen>Kilómetro</UnidadOrigen>
        <UnidadDestino>Milla</UnidadDestino>
        <FactorConversion>0.621371</FactorConversion>
        <FechaConversion>2024-01-15T10:30:00</FechaConversion>
    </Resultado>
    <Error>null</Error>
</ConversionResult>
```

## ??? Códigos de Error Estandarizados

### Validación (VAL_xxx)
- **VAL_001**: Valor negativo
- **VAL_002**: Valor NaN (no es número)
- **VAL_003**: Valor infinito
- **VAL_004**: Temperatura bajo cero absoluto

### Conversión (CONV_xxx)
- **CONV_001**: Error en conversión de longitud
- **CONV_002**: Error en conversión de masa
- **CONV_003**: Error en conversión de temperatura

### Sistema (SYS_xxx)
- **SYS_001**: Error interno del sistema
- **SYS_002**: Servicio no disponible

## ?? Pruebas en SoapUI

### Caso 1: Valor Negativo
```xml
<!-- Petición -->
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.monster.edu.ec/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:KilometroAMilla>
         <ws:kilometros>-10</ws:kilometros>
      </ws:KilometroAMilla>
   </soapenv:Body>
</soapenv:Envelope>
```

```xml
<!-- Respuesta estructurada -->
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <KilometroAMillaResponse>
         <ConversionResult>
            <Exitoso>false</Exitoso>
            <Error>
               <CodigoError>VAL_001</CodigoError>
               <Mensaje>El valor no puede ser negativo en Kilómetro</Mensaje>
               <TipoError>Validacion</TipoError>
               <ValorProblematico>-10</ValorProblematico>
               <Unidad>Kilómetro</Unidad>
               <Detalles>Las medidas de kilómetro deben ser valores positivos o cero.</Detalles>
            </Error>
         </ConversionResult>
      </KilometroAMillaResponse>
   </soap:Body>
</soap:Envelope>
```

### Caso 2: Temperatura Bajo Cero Absoluto
```xml
<!-- Petición -->
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.monster.edu.ec/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:CelsiusAFahrenheit>
         <ws:celsius>-300</ws:celsius>
      </ws:CelsiusAFahrenheit>
   </soapenv:Body>
</soapenv:Envelope>
```

```xml
<!-- Respuesta con error específico -->
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <CelsiusAFahrenheitResponse>
         <ConversionResult>
            <Exitoso>false</Exitoso>
            <Error>
               <CodigoError>VAL_004</CodigoError>
               <Mensaje>La temperatura está por debajo del cero absoluto para Celsius</Mensaje>
               <TipoError>Validacion</TipoError>
               <ValorProblematico>-300</ValorProblematico>
               <Unidad>Celsius</Unidad>
               <Detalles>La temperatura mínima posible es -273.15°C (cero absoluto).</Detalles>
            </Error>
         </ConversionResult>
      </CelsiusAFahrenheitResponse>
   </soap:Body>
</soap:Envelope>
```

## ?? Manejo en Código Cliente

### Cliente .NET
```csharp
var resultado = cliente.KilometroAMilla(-10);

if (resultado.Exitoso)
{
    Console.WriteLine($"Conversión: {resultado.Resultado.ValorConvertidoRedondeado} millas");
}
else
{
    Console.WriteLine($"Error {resultado.Error.CodigoError}: {resultado.Error.Mensaje}");
    Console.WriteLine($"Detalles: {resultado.Error.Detalles}");
}
```

### Cliente Java
```java
ConversionResult resultado = cliente.kilometroAMilla(-10);

if (resultado.isExitoso()) {
    System.out.println("Conversión: " + resultado.getResultado().getValorConvertidoRedondeado() + " millas");
} else {
    System.out.println("Error " + resultado.getError().getCodigoError() + ": " + resultado.getError().getMensaje());
}
```

### Cliente JavaScript/Node.js
```javascript
const resultado = await cliente.KilometroAMilla(-10);

if (resultado.Exitoso) {
    console.log(`Conversión: ${resultado.Resultado.ValorConvertidoRedondeado} millas`);
} else {
    console.log(`Error ${resultado.Error.CodigoError}: ${resultado.Error.Mensaje}`);
    console.log(`Detalles: ${resultado.Error.Detalles}`);
}
```

## ?? Beneficios del Nuevo Sistema

### ? Para Desarrolladores
- **Errores predecibles**: Códigos de error estandarizados
- **Información completa**: Valor problemático, unidad, detalles
- **Facilidad de debugging**: Timestamps y mensajes descriptivos
- **Compatibilidad universal**: Funciona con cualquier cliente SOAP

### ? Para Clientes SOAP
- **SoapUI**: Respuestas parseables y comprensibles
- **Postman**: Formato JSON limpio cuando se usa REST
- **Herramientas de testing**: Assertions basadas en códigos de error
- **Documentación automática**: Contratos claros

### ? Para Operaciones
- **Monitoreo**: Códigos de error para métricas
- **Logging**: Información estructurada
- **Alertas**: Filtrado por tipo de error
- **Análisis**: Patrones de errores identificables

## ?? Migración para Clientes Existentes

### Antes (problemático)
```csharp
try {
    double resultado = cliente.KilometroAMilla(-10);
} catch (FaultException ex) {
    // Mensaje inconsistente, difícil de parsear
    Console.WriteLine("Error: " + ex.Message);
}
```

### Ahora (estructurado)
```csharp
ConversionResult resultado = cliente.KilometroAMilla(-10);

if (!resultado.Exitoso) {
    switch (resultado.Error.CodigoError) {
        case "VAL_001":
            // Manejar valor negativo
            break;
        case "VAL_002":
            // Manejar valor NaN
            break;
        // etc.
    }
}
```

---
*Sistema de errores diseñado para máxima compatibilidad y usabilidad con clientes SOAP.*