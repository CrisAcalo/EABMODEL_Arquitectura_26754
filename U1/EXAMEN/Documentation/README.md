# 🏦 BanQuito Server SOAP - .NET Framework

Sistema CORE del Banco BanQuito con Módulo de Crédito desarrollado en **.NET Framework 4.7.2**, **WCF (SOAP)**, **Entity Framework 6** y **SQL Server**.

---

## 📋 Requisitos Previos

- Visual Studio 2019 o superior
- .NET Framework 4.7.2
- SQL Server 2019 o SQL Server Express
- SQL Server Management Studio (SSMS)

---

## 🏗️ Arquitectura del Proyecto

```
BanquitoServer_Soap_DotNet_GR01/
├── Models/                     # Entidades (Cliente, Cuenta, Movimiento, Credito, CuotaAmortizacion)
├── DataAccess/                 # DbContext y Repositorios
│   └── Repositories/
├── BusinessLogic/              # Servicios de negocio (Validación, Crédito)
├── WS/                         # Web Services SOAP
│   ├── ICreditoSoapService.cs
│   ├── CreditoSoapService.svc
│   └── CreditoSoapService.svc.cs
├── DTOs/                       # Data Transfer Objects
├── Utilities/                  # Calculadora Financiera
├── Database/                   # Scripts SQL
└── Documentation/              # Documentación
```

---

## 🚀 Pasos de Instalación

### **1. Instalar Paquetes NuGet**

Abrir **Package Manager Console** en Visual Studio y ejecutar:

```powershell
Install-Package EntityFramework -Version 6.4.4
Install-Package Microsoft.Data.SqlClient -Version 5.1.1
```

O usando **NuGet Package Manager UI**:
1. Click derecho en el proyecto → Manage NuGet Packages
2. Buscar e instalar:
   - `EntityFramework` (versión 6.4.4)
   - `Microsoft.Data.SqlClient`

### **2. Configurar SQL Server**

#### Opción 1: Crear la base de datos con el script

1. Abrir **SQL Server Management Studio (SSMS)**
2. Conectarse a tu servidor SQL Server
3. Abrir el archivo: `Database/01_crear_base_datos_sqlserver.sql`
4. Ejecutar el script completo (F5)

Esto creará:
- ✅ Base de datos `BanquitoDB`
- ✅ 5 tablas (Cliente, Cuenta, Movimiento, Credito, CuotaAmortizacion)
- ✅ 5 clientes de prueba
- ✅ 5 cuentas
- ✅ 50 movimientos

#### Verificar los datos:

```sql
USE BanquitoDB;

SELECT * FROM Cliente;
SELECT * FROM Cuenta;
SELECT COUNT(*) FROM Movimiento;
```

### **3. Configurar Cadena de Conexión**

Editar `Web.config` y actualizar la cadena de conexión según tu configuración:

```xml
<connectionStrings>
  <!-- Opción 1: Autenticación de Windows -->
  <add name="BanquitoDb"
       connectionString="Server=localhost;Database=BanquitoDB;Integrated Security=true;TrustServerCertificate=True;"
       providerName="System.Data.SqlClient" />

  <!-- Opción 2: Usuario SQL Server -->
  <add name="BanquitoDb"
       connectionString="Server=localhost;Database=BanquitoDB;User Id=sa;Password=TuPassword123;TrustServerCertificate=True;"
       providerName="System.Data.SqlClient" />

  <!-- Opción 3: SQL Server Express -->
  <add name="BanquitoDb"
       connectionString="Server=localhost\SQLEXPRESS;Database=BanquitoDB;Integrated Security=true;TrustServerCertificate=True;"
       providerName="System.Data.SqlClient" />
</connectionStrings>
```

### **4. Compilar el Proyecto**

1. En Visual Studio: **Build → Rebuild Solution** (Ctrl+Shift+B)
2. Verificar que no haya errores de compilación

### **5. Ejecutar el Proyecto**

1. Presionar **F5** o click en "Start"
2. Se abrirá IIS Express con el proyecto
3. Navegar a: `http://localhost:PUERTO/WS/CreditoSoapService.svc`

Deberías ver la página del servicio WCF.

---

## 🌐 Web Services SOAP Disponibles

Base URL: `http://localhost:PUERTO/WS/CreditoSoapService.svc`

### **WS 1: Validar Sujeto de Crédito**

**Método:** `ValidarSujetoCredito`
**Parámetro:** `string cedula`
**Retorna:** `ValidacionCreditoDTO`

**Ejemplo SOAP Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:tem="http://tempuri.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:ValidarSujetoCredito>
         <tem:cedula>1234567890</tem:cedula>
      </tem:ValidarSujetoCredito>
   </soapenv:Body>
</soapenv:Envelope>
```

### **WS 2: Obtener Monto Máximo**

**Método:** `ObtenerMontoMaximo`
**Parámetro:** `string cedula`
**Retorna:** `MontoMaximoCreditoDTO`

### **WS 3: Otorgar Crédito**

**Método:** `OtorgarCredito`
**Parámetro:** `SolicitudCreditoDTO`
**Retorna:** `RespuestaCreditoDTO`

**Ejemplo SOAP Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:tem="http://tempuri.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:OtorgarCredito>
         <tem:solicitud>
            <tem:Cedula>1234567890</tem:Cedula>
            <tem:PrecioElectrodomestico>3000.00</tem:PrecioElectrodomestico>
            <tem:NumeroCuotas>12</tem:NumeroCuotas>
         </tem:solicitud>
      </tem:OtorgarCredito>
   </soapenv:Body>
</soapenv:Envelope>
```

### **WS 4: Obtener Tabla de Amortización**

**Método:** `ObtenerTablaAmortizacion`
**Parámetro:** `string numeroCredito`
**Retorna:** `List<CuotaAmortizacionDTO>`

---

## 🧪 Probar con SoapUI o Postman

### **Usando SoapUI:**

1. Crear nuevo proyecto SOAP
2. Ingresar WSDL URL: `http://localhost:PUERTO/WS/CreditoSoapService.svc?wsdl`
3. SoapUI generará automáticamente todas las operaciones
4. Ejecutar las pruebas

### **Usando Postman:**

1. Crear nueva request tipo POST
2. URL: `http://localhost:PUERTO/WS/CreditoSoapService.svc`
3. Headers:
   - `Content-Type: text/xml`
   - `SOAPAction: "http://tempuri.org/ICreditoSoapService/ValidarSujetoCredito"`
4. Body: Raw XML (ver ejemplos arriba)

---

## 📊 Datos de Prueba

| Cédula     | Nombre              | Estado Civil | Válido para Crédito |
|------------|---------------------|--------------|---------------------|
| 1234567890 | Juan Carlos Pérez   | Casado       | ✅ SÍ               |
| 0987654321 | María López         | Soltera      | ✅ SÍ               |
| 1122334455 | Pedro Ramírez       | Casado       | ✅ SÍ               |
| 5566778899 | Ana Torres          | Soltera      | ✅ SÍ               |
| 9988776655 | Luis Morales        | Divorciado   | ✅ SÍ               |

---

## ⚙️ Configuración de Crédito

En `Web.config` → `<appSettings>`:

```xml
<add key="CreditoTasaAnual" value="0.16" />          <!-- 16% anual -->
<add key="CreditoPlazoMinimo" value="3" />            <!-- 3 meses mínimo -->
<add key="CreditoPlazoMaximo" value="24" />           <!-- 24 meses máximo -->
<add key="CreditoPorcentajeCapacidad" value="0.60" /> <!-- 60% -->
<add key="CreditoMultiplicador" value="9" />          <!-- Factor 9 -->
```

---

## 🔧 Solución de Problemas

### Error: "No se puede conectar a SQL Server"
- Verificar que SQL Server esté corriendo
- Verificar la cadena de conexión en `Web.config`
- Verificar usuario/contraseña

### Error: "Could not load file or assembly EntityFramework"
- Instalar Entity Framework 6.4.4 via NuGet
- Rebuild Solution

### Error 404 al acceder al servicio
- Verificar que el proyecto esté corriendo
- Verificar la ruta: `/WS/CreditoSoapService.svc`
- Revisar que el archivo `.svc` exista en la carpeta WS

### El servicio no retorna datos
- Verificar que la base de datos tenga datos de prueba
- Revisar los logs en Output de Visual Studio
- Verificar `includeExceptionDetailInFaults="true"` en Web.config

---

## 📚 Tecnologías Utilizadas

- **.NET Framework 4.7.2**
- **WCF (Windows Communication Foundation)** - SOAP
- **Entity Framework 6** - ORM
- **SQL Server** - Base de datos
- **IIS Express** - Servidor de desarrollo

---

## ✅ Validaciones Implementadas

Las 4 reglas de validación de crédito:

1. ✅ Cliente es del banco
2. ✅ Tiene depósitos en el último mes
3. ✅ Si es casado, tiene >= 25 años
4. ✅ No tiene crédito activo

**Fórmula monto máximo:**
```
((Promedio Depósitos - Promedio Retiros) × 60%) × 9
```

**Tabla de Amortización:**
- Cuota fija mensual
- Tasa: 16% anual
- Plazo: 3-24 meses

---

## 📞 Soporte

Para preguntas o problemas, revisar:
1. Logs de Visual Studio (Output window)
2. Event Viewer de Windows
3. SQL Server error logs

---

**Proyecto completado ✅**
Compatible con servidor Java RESTful del examen
