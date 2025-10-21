# Sistema de Conversi�n de Unidades - Arquitectura Limpia

## ?? Estructura del Proyecto

```
ConUni_Soap_DotNet_GR01/
??? ec/edu/monster/
?   ??? constants/          # ?? Constantes y factores de conversi�n
?   ?   ??? ConversionConstants.cs
?   ??? models/             # ?? Modelos de datos
?   ?   ??? UnidadConversion.cs
?   ??? services/           # ?? Servicios de negocio
?   ?   ??? LongitudBusinessService.cs
?   ?   ??? MasaBusinessService.cs
?   ?   ??? TemperaturaBusinessService.cs
?   ??? validators/         # ? Validaciones
?   ?   ??? ValidationHelper.cs
?   ??? ws/                 # ?? Servicios WCF (Capa de presentaci�n)
?       ??? ILongitudService.cs
?       ??? LongitudService.svc.cs
?       ??? IMasaService.cs
?       ??? MasaService.svc.cs
?       ??? ITemperaturaService.cs
?       ??? TemperaturaService.svc.cs
```

## ??? Arquitectura de Capas

### 1. **Capa de Presentaci�n** (`ws/`)
- **Responsabilidad**: Exponer servicios WCF y manejar la comunicaci�n
- **Componentes**: Interfaces y servicios WCF
- **Patr�n**: Service Contract + Implementation

### 2. **Capa de Negocio** (`services/`)
- **Responsabilidad**: L�gica de negocio y reglas de conversi�n
- **Componentes**: BusinessServices para cada tipo de unidad
- **Funcionalidades**: 
  - Ejecutar conversiones
  - Crear objetos de respuesta detallados
  - Coordinar validaciones

### 3. **Capa de Validaci�n** (`validators/`)
- **Responsabilidad**: Validar datos de entrada
- **Componentes**: Validadores especializados
- **Validaciones**:
  - N�meros v�lidos (no NaN, no infinito)
  - Valores positivos para longitud y masa
  - L�mites f�sicos para temperatura (cero absoluto)

### 4. **Capa de Modelos** (`models/`)
- **Responsabilidad**: Representar datos y estructuras
- **Componentes**: POCOs (Plain Old CLR Objects)
- **Funcionalidades**: Encapsular informaci�n de conversiones

### 5. **Capa de Constantes** (`constants/`)
- **Responsabilidad**: Centralizar valores constantes
- **Componentes**: Clases est�ticas con factores de conversi�n
- **Beneficios**: F�cil mantenimiento y modificaci�n

## ?? Principios de Dise�o Aplicados

### SOLID Principles
- ? **Single Responsibility**: Cada clase tiene una responsabilidad espec�fica
- ? **Open/Closed**: Extensible para nuevas conversiones sin modificar c�digo existente
- ? **Liskov Substitution**: Las implementaciones pueden sustituirse
- ? **Interface Segregation**: Interfaces espec�ficas por tipo de conversi�n
- ? **Dependency Inversion**: Dependencias hacia abstracciones

### Clean Architecture
- ? **Separaci�n de responsabilidades**
- ? **Independencia de frameworks**
- ? **Testabilidad**
- ? **Mantenibilidad**

### DRY (Don't Repeat Yourself)
- ? **Constantes centralizadas**
- ? **Validaciones reutilizables**
- ? **Patrones consistentes**

## ?? Flujo de Ejecuci�n

```
Cliente ? WCF Service ? Business Service ? Validator ? Constants ? Response
   ?                                                                   ?
   ???????????????????? Resultado ?????????????????????????????????????
```

1. **Cliente** llama al servicio WCF
2. **Servicio WCF** recibe la petici�n y delega al servicio de negocio
3. **Servicio de Negocio** ejecuta validaciones
4. **Validador** verifica los datos usando constantes
5. **Servicio de Negocio** realiza la conversi�n
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
- **L�mites f�sicos**: Validaci�n del cero absoluto en temperaturas
- **Logging impl�cito**: Estructura preparada para logging

## ?? Beneficios de la Arquitectura

1. **Mantenibilidad**: C�digo organizado y f�cil de modificar
2. **Escalabilidad**: F�cil agregar nuevos tipos de conversiones
3. **Testabilidad**: Cada capa se puede probar independientemente
4. **Reutilizaci�n**: Servicios de negocio reutilizables
5. **Separaci�n de responsabilidades**: Cada clase tiene un prop�sito espec�fico
6. **Configurabilidad**: Constantes centralizadas
7. **Robustez**: Validaciones completas y manejo de errores

## ?? Tecnolog�as Utilizadas

- **.NET Framework 4.7.2**
- **WCF (Windows Communication Foundation)**
- **C# 7.3**
- **Clean Architecture Pattern**
- **SOLID Principles**

---
*Arquitectura dise�ada para ser escalable, mantenible y robusta.*