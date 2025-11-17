# 🏦 Sistema BanQuito - Servidor Web Services RESTful

Sistema CORE del Banco BanQuito con Módulo de Crédito desarrollado en Java 17, Payara 6, RESTful (JAX-RS), JPA y MySQL.

## 📋 Requisitos Previos

- Java JDK 17
- Maven 3.8+
- Payara Server 6.x
- MySQL 8.0+
- IDE (NetBeans, IntelliJ IDEA, Eclipse)

## 🏗️ Arquitectura del Proyecto

```
BanquitoServer/
├── src/
│   ├── main/
│   │   ├── java/ec/edu/epn/banquito/
│   │   │   ├── config/          # Configuración centralizada
│   │   │   ├── model/           # Entidades JPA
│   │   │   ├── dao/             # Capa de acceso a datos
│   │   │   ├── service/         # Lógica de negocio
│   │   │   └── rest/            # Endpoints RESTful
│   │   │       └── dto/         # Data Transfer Objects
│   │   ├── resources/
│   │   │   ├── config.properties    # Configuración centralizada
│   │   │   └── META-INF/
│   │   │       └── persistence.xml  # Configuración JPA
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── beans.xml        # Configuración CDI
│   └── test/
├── database/
│   └── 01_crear_base_datos.sql      # Script de BD
├── pom.xml                           # Configuración Maven
└── README.md
```

## 🚀 Pasos de Instalación y Configuración

### 1. Clonar o Descargar el Proyecto

```bash
# Si está en un repositorio
git clone <url-del-repositorio>
cd BanquitoServer
```

### 2. Configurar la Base de Datos MySQL

#### 2.1 Ejecutar el script SQL

```bash
# Desde la línea de comandos de MySQL
mysql -u root -p < database/01_crear_base_datos.sql
```

O desde MySQL Workbench:
- Abrir MySQL Workbench
- Conectarse al servidor MySQL
- Abrir el archivo `database/01_crear_base_datos.sql`
- Ejecutar el script completo

#### 2.2 Verificar que se crearon los datos

```sql
USE banquito_db;

-- Verificar clientes
SELECT * FROM cliente;

-- Verificar cuentas
SELECT * FROM cuenta;

-- Verificar movimientos (debe haber 50)
SELECT COUNT(*) FROM movimiento;
```

### 3. Configurar la IP del Servidor

**⚠️ IMPORTANTE:** Este es el paso clave para que los clientes se conecten desde otras computadoras.

Editar el archivo `src/main/resources/config.properties`:

```properties
# CAMBIAR ESTA IP A LA IP DE TU LAPTOP/SERVIDOR
server.host=192.168.1.100   # <- Cambiar a tu IP local (usa ipconfig o ifconfig)
server.port=8080
server.context=/banquito-server

# Configuración de Base de Datos
db.host=localhost
db.port=3306
db.name=banquito_db
db.username=root
db.password=root            # <- Cambiar si tienes otra contraseña
```

#### ¿Cómo obtener tu IP local?

**Windows:**
```cmd
ipconfig
# Buscar "Dirección IPv4" en tu adaptador de red activo
```

**Linux/Mac:**
```bash
ifconfig
# o
ip addr show
```

### 4. Configurar Payara Server

#### 4.1 Descargar e instalar Payara Server 6

- Descargar desde: https://www.payara.fish/downloads/
- Extraer en una ubicación (ej: `C:\payara6` o `/opt/payara6`)

#### 4.2 Configurar el DataSource JDBC en Payara

**Opción 1: Usando la Consola Web de Payara**

1. Iniciar Payara:
   ```bash
   # Windows
   payara6\bin\asadmin start-domain

   # Linux/Mac
   payara6/bin/asadmin start-domain
   ```

2. Abrir el navegador en: `http://localhost:4848`

3. Navegar a: **Resources → JDBC → JDBC Connection Pools**

4. Crear un nuevo Connection Pool:
   - **Pool Name:** `BanquitoPool`
   - **Resource Type:** `javax.sql.DataSource`
   - **Database Driver Vendor:** `MySQL`

5. En la pestaña "Additional Properties", configurar:
   - **ServerName:** `localhost`
   - **Port:** `3306`
   - **DatabaseName:** `banquito_db`
   - **User:** `root`
   - **Password:** `tu_contraseña`
   - **URL:** `jdbc:mysql://localhost:3306/banquito_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`

6. Hacer clic en "Ping" para verificar la conexión

7. Navegar a: **Resources → JDBC → JDBC Resources**

8. Crear un nuevo JDBC Resource:
   - **JNDI Name:** `jdbc/BanquitoDB`
   - **Pool Name:** `BanquitoPool` (seleccionar el que creamos)

**Opción 2: Usando comandos asadmin**

```bash
# Copiar el driver MySQL a Payara
cp mysql-connector-j-8.0.33.jar payara6/glassfish/domains/domain1/lib/

# Crear el Connection Pool
asadmin create-jdbc-connection-pool \
  --datasourceclassname com.mysql.cj.jdbc.MysqlDataSource \
  --restype javax.sql.DataSource \
  --property "ServerName=localhost:Port=3306:DatabaseName=banquito_db:User=root:Password=root:URL=jdbc\:mysql\://localhost\:3306/banquito_db?useSSL\=false&serverTimezone\=UTC&allowPublicKeyRetrieval\=true" \
  BanquitoPool

# Crear el JDBC Resource
asadmin create-jdbc-resource --connectionpoolid BanquitoPool jdbc/BanquitoDB

# Probar la conexión
asadmin ping-connection-pool BanquitoPool
```

### 5. Compilar el Proyecto

```bash
mvn clean package
```

Esto generará el archivo `banquito-server.war` en la carpeta `target/`

### 6. Desplegar en Payara Server

**Opción 1: Desde la Consola Web**

1. Ir a: `http://localhost:4848`
2. Navegar a: **Applications**
3. Click en "Deploy"
4. Seleccionar el archivo `target/banquito-server.war`
5. Click en "OK"

**Opción 2: Usando asadmin**

```bash
asadmin deploy target/banquito-server.war
```

**Opción 3: Copiar manualmente**

```bash
# Copiar el WAR a la carpeta de autodeploy
cp target/banquito-server.war payara6/glassfish/domains/domain1/autodeploy/
```

### 7. Verificar el Despliegue

1. Verificar que no haya errores en los logs de Payara:
   ```bash
   tail -f payara6/glassfish/domains/domain1/logs/server.log
   ```

2. Probar el endpoint de prueba:
   ```
   http://localhost:8080/banquito-server/api/credito/ping
   ```

   Debe responder: "Servicio de Crédito BanQuito está activo"

## 🌐 Endpoints Disponibles

Base URL: `http://<TU_IP>:8080/banquito-server/api`

### 1. Validar Sujeto de Crédito

```
GET /credito/validar/{cedula}

Ejemplo:
http://192.168.1.100:8080/banquito-server/api/credito/validar/1234567890

Respuesta:
{
  "esValido": true,
  "mensaje": "El cliente es sujeto de crédito",
  "cedula": "1234567890",
  "nombreCompleto": "Juan Carlos Pérez González"
}
```

### 2. Obtener Monto Máximo de Crédito

```
GET /credito/monto-maximo/{cedula}

Ejemplo:
http://192.168.1.100:8080/banquito-server/api/credito/monto-maximo/1234567890

Respuesta:
{
  "cedula": "1234567890",
  "montoMaximo": 5832.00,
  "promedioDepositos": 1900.00,
  "promedioRetiros": 1180.00,
  "mensaje": "Monto máximo calculado exitosamente"
}
```

### 3. Otorgar Crédito

```
POST /credito/otorgar
Content-Type: application/json

Body:
{
  "cedula": "1234567890",
  "precioElectrodomestico": 3000.00,
  "numeroCuotas": 12
}

Respuesta:
{
  "exito": true,
  "mensaje": "Crédito otorgado exitosamente",
  "numeroCredito": "CRE1731523456789",
  "cedula": "1234567890",
  "montoCredito": 3000.00,
  "numeroCuotas": 12,
  "cuotaMensual": 268.85,
  "tasaInteres": 0.1600,
  "tablaAmortizacion": [
    {
      "numeroCuota": 1,
      "valorCuota": 268.85,
      "interes": 40.00,
      "capitalPagado": 228.85,
      "saldo": 2771.15
    },
    ...
  ]
}
```

### 4. Obtener Tabla de Amortización

```
GET /credito/tabla-amortizacion/{numeroCredito}

Ejemplo:
http://192.168.1.100:8080/banquito-server/api/credito/tabla-amortizacion/CRE1731523456789

Respuesta:
[
  {
    "numeroCuota": 1,
    "valorCuota": 268.85,
    "interes": 40.00,
    "capitalPagado": 228.85,
    "saldo": 2771.15
  },
  ...
]
```

## 🔧 Configuración para Clientes Externos

### Para que otros dispositivos se conecten al servidor:

1. **Configurar el Firewall de Windows:**
   - Abrir "Firewall de Windows Defender"
   - Click en "Configuración avanzada"
   - Click en "Reglas de entrada"
   - Click en "Nueva regla..."
   - Tipo de regla: Puerto
   - TCP, Puerto específico: 8080
   - Permitir la conexión
   - Aplicar a todos los perfiles
   - Nombre: "Payara Server"

2. **Asegurarse de estar en la misma red:**
   - Tanto el servidor como los clientes deben estar en la misma red WiFi/LAN

3. **En los clientes, usar la IP configurada:**
   ```
   http://192.168.1.100:8080/banquito-server/api/...
   ```

## 🧪 Probar con Postman o curl

### Usando curl:

```bash
# Validar sujeto de crédito
curl http://localhost:8080/banquito-server/api/credito/validar/1234567890

# Obtener monto máximo
curl http://localhost:8080/banquito-server/api/credito/monto-maximo/1234567890

# Otorgar crédito
curl -X POST http://localhost:8080/banquito-server/api/credito/otorgar \
  -H "Content-Type: application/json" \
  -d '{
    "cedula": "1234567890",
    "precioElectrodomestico": 3000.00,
    "numeroCuotas": 12
  }'

# Obtener tabla de amortización
curl http://localhost:8080/banquito-server/api/credito/tabla-amortizacion/CRE1731523456789
```

### Usando Postman:

1. Importar la colección de Postman (crear una con los endpoints anteriores)
2. Cambiar la variable `{{baseUrl}}` a tu IP: `http://192.168.1.100:8080/banquito-server/api`

## 📊 Datos de Prueba

El sistema incluye 5 clientes con sus respectivas cuentas y 50 movimientos:

| Cédula      | Nombre                  | Estado Civil | Número Cuenta | Saldo    |
|-------------|-------------------------|--------------|---------------|----------|
| 1234567890  | Juan Carlos Pérez       | Casado       | 2001234567    | 5000.00  |
| 0987654321  | María Fernanda López    | Soltera      | 2001234568    | 8500.00  |
| 1122334455  | Pedro Antonio Ramírez   | Casado       | 2001234569    | 3200.00  |
| 5566778899  | Ana Lucía Torres        | Soltera      | 2001234570    | 6750.00  |
| 9988776655  | Luis Fernando Morales   | Divorciado   | 2001234571    | 4300.00  |

**Todos los clientes son sujetos de crédito válidos** (cumplen con todas las reglas de validación).

## 🐛 Solución de Problemas

### Error: "No se puede conectar a MySQL"
- Verificar que MySQL esté corriendo
- Verificar usuario/contraseña en `config.properties`
- Verificar que el DataSource esté configurado correctamente en Payara

### Error: "PersistenceUnit 'BanquitoPU' not found"
- Verificar que `persistence.xml` esté en `src/main/resources/META-INF/`
- Verificar que el JNDI name sea `jdbc/BanquitoDB`

### Error: "Connection refused" desde otro dispositivo
- Verificar que el firewall permita conexiones al puerto 8080
- Verificar que la IP en `config.properties` sea la correcta
- Verificar que ambos dispositivos estén en la misma red

### Los cambios en config.properties no se reflejan
- Recompilar el proyecto: `mvn clean package`
- Redesplegar la aplicación en Payara

## 📝 Notas Importantes

- La **configuración centralizada** está en `src/main/resources/config.properties`
- Solo necesitas cambiar la IP en UN solo lugar para que funcione en toda la red
- El puerto por defecto de Payara es 8080
- La tasa de interés es 16% anual (configurable en `config.properties`)
- El plazo mínimo es 3 meses y máximo 24 meses

## 📚 Documentación Adicional

- [Payara Server Documentation](https://docs.payara.fish/)
- [Jakarta EE 10 Specification](https://jakarta.ee/specifications/)
- [MySQL Connector/J Documentation](https://dev.mysql.com/doc/connector-j/en/)

## 👨‍💻 Autor

Javier - Escuela Politécnica Nacional

## 📄 Licencia

Este proyecto es parte de un examen práctico universitario.
