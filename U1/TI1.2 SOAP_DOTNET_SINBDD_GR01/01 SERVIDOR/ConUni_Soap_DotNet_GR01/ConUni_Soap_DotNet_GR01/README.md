# Sistema de Conversión de Unidades - Arquitectura Limpia

## ?? Estructura del Proyecto

```
ConUni_Soap_DotNet_GR01/
??? ec/edu/monster/
?   ??? constants/          # ?? Constantes y factores de conversión
?   ?   ??? ConversionConstants.cs
?   ??? models/             # ?? Modelos de datos
?   ?   ??? UnidadConversion.cs
?   ??? services/           # ?? Servicios de negocio
?   ?   ??? LongitudBusinessService.cs
?   ?   ??? MasaBusinessService.cs
?   ?   ??? TemperaturaBusinessService.cs
?   ??? validators/         # ? Validaciones
?   ?   ??? ValidationHelper.cs
?   ??? ws/                 # ?? Servicios WCF (Capa de presentación)
?       ??? ILongitudService.cs
?       ??? LongitudService.svc.cs
?       ??? IMasaService.cs
?       ??? MasaService.svc.cs
?       ??? ITemperaturaService.cs
?       ??? TemperaturaService.svc.cs
```

## ??? Arquitectura de Capas

### 1. **Capa de Presentación** (`ws/`)
- **Responsabilidad**: Exponer servicios WCF y manejar la comunicación
- **Componentes**: Interfaces y servicios WCF
- **Patrón**: Service Contract + Implementation

### 2. **Capa de Negocio** (`services/`)
- **Responsabilidad**: Lógica de negocio y reglas de conversión
- **Componentes**: BusinessServices para cada tipo de unidad
- **Funcionalidades**: 
  - Ejecutar conversiones
  - Crear objetos de respuesta detallados
  - Coordinar validaciones

### 3. **Capa de Validación** (`validators/`)
- **Responsabilidad**: Validar datos de entrada
- **Componentes**: Validadores especializados
- **Validaciones**:
  - Números válidos (no NaN, no infinito)
  - Valores positivos para longitud y masa
  - Límites físicos para temperatura (cero absoluto)

### 4. **Capa de Modelos** (`models/`)
- **Responsabilidad**: Representar datos y estructuras
- **Componentes**: POCOs (Plain Old CLR Objects)
- **Funcionalidades**: Encapsular información de conversiones

### 5. **Capa de Constantes** (`constants/`)
- **Responsabilidad**: Centralizar valores constantes
- **Componentes**: Clases estáticas con factores de conversión
- **Beneficios**: Fácil mantenimiento y modificación

## ?? Principios de Diseño Aplicados

### SOLID Principles
- ? **Single Responsibility**: Cada clase tiene una responsabilidad específica
- ? **Open/Closed**: Extensible para nuevas conversiones sin modificar código existente
- ? **Liskov Substitution**: Las implementaciones pueden sustituirse
- ? **Interface Segregation**: Interfaces específicas por tipo de conversión
- ? **Dependency Inversion**: Dependencias hacia abstracciones

### Clean Architecture
- ? **Separación de responsabilidades**
- ? **Independencia de frameworks**
- ? **Testabilidad**
- ? **Mantenibilidad**

### DRY (Don't Repeat Yourself)
- ? **Constantes centralizadas**
- ? **Validaciones reutilizables**
- ? **Patrones consistentes**

## ?? Flujo de Ejecución

```
Cliente ? WCF Service ? Business Service ? Validator ? Constants ? Response
   ?                                                                   ?
   ???????????????????? Resultado ?????????????????????????????????????
```

1. **Cliente** llama al servicio WCF
2. **Servicio WCF** recibe la petición y delega al servicio de negocio
3. **Servicio de Negocio** ejecuta validaciones
4. **Validador** verifica los datos usando constantes
5. **Servicio de Negocio** realiza la conversión
6. **Modelo** encapsula el resultado
7. **Servicio WCF** retorna el valor convertido al cliente

## ?? Servicios Implementados

### LongitudService
- Km ? Milla
- Metro ? Pie  
- Cm ? Pulgada

### MasaService
- Gramo ? Onza
- Gramo ? Libra
- Kg ? Quintal

### TemperaturaService
- Celsius ? Fahrenheit
- Fahrenheit ? Kelvin
- Kelvin ? Celsius

## ??? Manejo de Errores

- **Validaciones de entrada**: ArgumentException con mensajes descriptivos
- **Manejo de excepciones**: Try-catch en servicios WCF
- **Límites físicos**: Validación del cero absoluto en temperaturas
- **Logging implícito**: Estructura preparada para logging

## ?? Beneficios de la Arquitectura

1. **Mantenibilidad**: Código organizado y fácil de modificar
2. **Escalabilidad**: Fácil agregar nuevos tipos de conversiones
3. **Testabilidad**: Cada capa se puede probar independientemente
4. **Reutilización**: Servicios de negocio reutilizables
5. **Separación de responsabilidades**: Cada clase tiene un propósito específico
6. **Configurabilidad**: Constantes centralizadas
7. **Robustez**: Validaciones completas y manejo de errores

## ?? Tecnologías Utilizadas

- **.NET Framework 4.7.2**
- **WCF (Windows Communication Foundation)**
- **C# 7.3**
- **Clean Architecture Pattern**
- **SOLID Principles**

---
*Arquitectura diseñada para ser escalable, mantenible y robusta.*