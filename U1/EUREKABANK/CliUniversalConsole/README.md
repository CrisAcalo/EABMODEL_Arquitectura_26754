# Cliente Universal EurekaBank - Consola

Cliente de consola universal que se conecta a servicios bancarios mediante SOAP y REST, con soporte para implementaciones Java y .NET.

## Características

- **Múltiples tecnologías de servicios:**
  - REST (RESTful Web Services)
  - SOAP (Simple Object Access Protocol)

- **Soporte para múltiples implementaciones:**
  - Java
  - .NET

- **Funcionalidades completas:**
  - Autenticación de empleados
  - Depósitos bancarios
  - Retiros bancarios (con cálculo de ITF y cargos)
  - Transferencias entre cuentas

- **Interfaz de usuario amigable:**
  - Menú interactivo en consola
  - Entrada de contraseña oculta
  - Mensajes de resultado con colores
  - Validación de opciones
  - Gestión de sesión de empleado

## Estructura del Proyecto

```
CliUniversalConsole/
├── Config/
│   └── ServiceConfig.cs                    # Configuración de URLs de servicios
├── Models/
│   ├── Empleado.cs                         # Modelo de datos del empleado
│   ├── LoginRequest.cs                     # Modelo de solicitud de login
│   ├── LoginResult.cs                      # Modelo de respuesta de login
│   ├── TransaccionRequest.cs               # Modelo de solicitud de transacción
│   ├── TransferenciaRequest.cs             # Modelo de solicitud de transferencia
│   ├── TransaccionResult.cs                # Modelo genérico de respuesta
│   ├── DepositoResult.cs                   # Modelo de resultado de depósito
│   ├── RetiroResult.cs                     # Modelo de resultado de retiro
│   └── TransferenciaResult.cs              # Modelo de resultado de transferencia
├── Services/
│   ├── IAutenticacionService.cs            # Interfaz para servicios de autenticación
│   ├── ITransaccionService.cs              # Interfaz para servicios de transacciones
│   ├── Rest/
│   │   ├── RestJavaAutenticacionService.cs
│   │   ├── RestDotNetAutenticacionService.cs
│   │   ├── RestJavaTransaccionService.cs
│   │   └── RestDotNetTransaccionService.cs
│   └── Soap/
│       ├── SoapJavaAutenticacionService.cs
│       ├── SoapDotNetAutenticacionService.cs
│       ├── SoapJavaTransaccionService.cs
│       └── SoapDotNetTransaccionService.cs
└── Program.cs                              # Punto de entrada con menú interactivo
```

## Configuración

Las URLs de los servicios se configuran en `Config/ServiceConfig.cs`:

```csharp
public static class ServiceConfig
{
    // URLs para servicios REST - Autenticación
    public static string RestJavaBaseUrl = "http://localhost:8081/EurekaBank_RestFull_Java_GR01/api/Autenticacion";
    public static string RestDotNetBaseUrl = "http://localhost:8005/api/Autenticacion";

    // URLs para servicios SOAP - Autenticación
    public static string SoapJavaBaseUrl = "http://localhost:8080/EurekaBank_Soap_Java_GR01/ServicioAutenticacion";
    public static string SoapDotNetBaseUrl = "http://localhost:8004/ws/ServicioAutenticacion.svc";

    // URLs para servicios REST - Transacciones
    public static string RestJavaTransaccionUrl = "http://localhost:8081/EurekaBank_RestFull_Java_GR01/api/Transaccion";
    public static string RestDotNetTransaccionUrl = "http://localhost:8005/api/Transaccion";

    // URLs para servicios SOAP - Transacciones
    public static string SoapJavaTransaccionUrl = "http://localhost:8080/EurekaBank_Soap_Java_GR01/ServicioTransaccion";
    public static string SoapDotNetTransaccionUrl = "http://localhost:8004/ws/ServicioTransaccion.svc";
}
```

## Uso

### Ejecutar el proyecto

```bash
cd CliUniversalConsole
dotnet run
```

### Flujo de uso

```
┌─────────────────────────────────────────┐
│ 1. Seleccionar Protocolo (REST/SOAP)   │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│ 2. Seleccionar Tecnología (Java/.NET)  │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│ 3. Login de Empleado                   │
│    - Ingresa Usuario y Clave           │
│    - Se guarda código de empleado      │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│ 4. Menú de Transacciones               │
│    ┌──────────────────────────┐        │
│    │ • Depósito               │        │
│    │ • Retiro                 │        │
│    │ • Transferencia          │◄───────┼─┐
│    │ • Cerrar Sesión          │        │ │
│    └──────────────────────────┘        │ │
└────────────────┬────────────────────────┘ │
                 │                          │
                 └──────────────────────────┘
               (Loop continuo hasta
                cerrar sesión)
```

1. **Seleccionar tipo de servicio (Protocolo):**
   - Opción 1: REST (RESTful Web Services)
   - Opción 2: SOAP (Simple Object Access Protocol)
   - Opción 0: Salir

2. **Seleccionar tecnología (Implementación):**
   - Opción 1: Java
   - Opción 2: .NET
   - Opción 0: Volver

3. **Login de Empleado:**
   - Ingresar usuario y clave
   - Ver resultado y datos del empleado
   - El código del empleado se guarda en sesión
   - Si el login falla, vuelve al menú principal

4. **Menú de Transacciones (loop continuo):**
   - El sistema muestra el protocolo y tecnología seleccionados
   - Muestra el empleado activo en sesión
   - Opciones disponibles:
     - **Depósito**: Aumenta saldo de cuenta
     - **Retiro**: Disminuye saldo (calcula ITF 0.005% y cargos)
     - **Transferencia**: Mueve fondos entre cuentas
     - **Cerrar Sesión**: Vuelve al menú principal

5. **Realizar Transacción:**
   - Ingresar datos de la transacción
   - El código de empleado se usa automáticamente de la sesión
   - Ver resultado detallado con saldos y movimientos
   - Volver automáticamente al menú de transacciones

### Credenciales de prueba

Según las imágenes proporcionadas:
- Usuario: `MONSTER`
- Clave: `MONSTER9` (Java) o `MONSTER` (.NET)

## Información técnica

### Servicios REST

#### Java
- **Endpoint:** `/api/Autentacion/login`
- **Método:** POST
- **Body:** `{"usuario": "xxx", "clave": "xxx"}`
- **Respuesta:** JSON con `Exitoso`, `Mensaje` y `Datos`

#### .NET
- **Endpoint:** `/api/Autentacion/login`
- **Método:** POST
- **Body:** `{"usuario": "xxx", "clave": "xxx"}`
- **Respuesta:** JSON con `exitoso`, `mensaje` y `datos`

### Servicios SOAP

#### Java
- **Namespace:** `http://ws.monster.edu.ec/`
- **Operación:** `login`
- **Parámetros:** `usuario`, `clave`

#### .NET
- **Namespace:** `http://tempuri.org/`
- **Operación:** `Login`
- **Parámetros:** `tem:usuario`, `tem:clave`

## Compilación

```bash
dotnet build
```

## Requisitos

- .NET 8.0 SDK o superior
- Conexión a los servicios backend (REST/SOAP)

## Notas importantes

1. El código del empleado se captura y muestra después del login exitoso
2. La contraseña se oculta mientras se escribe usando asteriscos (*)
3. Los servicios tienen parsing flexible para manejar variaciones en las respuestas
4. El cliente maneja errores de conexión y respuestas inválidas
