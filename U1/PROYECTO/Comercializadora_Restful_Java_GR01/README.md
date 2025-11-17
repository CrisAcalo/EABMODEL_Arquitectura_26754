# 🏪 Comercializadora RESTful Server - Java

Sistema RESTful para comercialización de electrodomésticos desarrollado en Java 17 con Jakarta EE 10, MySQL y Payara Server 6.

## 📋 Características

- **CRUD completo de Productos**: Gestión de electrodomésticos
- **Sistema de Facturación**: Creación y consulta de facturas
- **Soporte de Crédito**: Integración con sistema bancario BanQuito
- **API RESTful**: Endpoints JSON para todas las operaciones
- **Base de datos MySQL**: Almacenamiento persistente

## 🛠️ Tecnologías

- **Java 17**
- **Jakarta EE 10** (JAX-RS, JPA, CDI, EJB)
- **Payara Server 6**
- **MySQL 8.0+**
- **Maven 3.8+**
- **EclipseLink** (JPA Provider)
- **Lombok** (Reducción de boilerplate)

## 📁 Estructura del Proyecto

```
src/main/java/ec/edu/monster/
├── model/              # Entidades JPA
│   ├── Producto.java
│   ├── Factura.java
│   └── DetalleFactura.java
├── dao/                # Data Access Objects
│   ├── GenericDAO.java
│   ├── ProductoDAO.java
│   ├── FacturaDAO.java
│   └── DetalleFacturaDAO.java
├── dto/                # Data Transfer Objects
│   ├── ProductoDTO.java
│   ├── FacturaDTO.java
│   ├── DetalleFacturaDTO.java
│   └── RespuestaDTO.java
├── service/            # Lógica de negocio
│   ├── ProductoService.java
│   └── FacturacionService.java
├── rest/               # REST Resources
│   ├── RestApplication.java
│   ├── ProductoResource.java
│   └── FacturacionResource.java
└── util/               # Utilidades
    └── CorsFilter.java
```

## 🚀 Instalación y Configuración

### 1. Requisitos Previos

- JDK 17 instalado
- Maven 3.8+ instalado
- Payara Server 6 instalado
- MySQL 8.0+ instalado y ejecutándose

### 2. Configurar Base de Datos

Ejecutar el script SQL:
```bash
mysql -u root -p < database/01_crear_base_datos.sql
```

### 3. Configurar Data Source en Payara

#### Opción A: Usando Admin Console (Recomendado)

1. Acceder a Admin Console: `http://localhost:4848`
2. Ir a: **Resources → JDBC → JDBC Connection Pools**
3. Crear nuevo pool:
   - **Pool Name**: `ComercializadoraPool`
   - **Resource Type**: `javax.sql.DataSource`
   - **Database Driver Vendor**: `MySQL`
4. Configurar propiedades:
   ```
   serverName: localhost
   portNumber: 3306
   databaseName: comercializadora_db
   user: root
   password: tu_password
   ```
5. Ping para verificar conexión
6. Ir a: **Resources → JDBC → JDBC Resources**
7. Crear nuevo resource:
   - **JNDI Name**: `jdbc/ComercializadoraDB`
   - **Pool Name**: `ComercializadoraPool`

#### Opción B: Usando CLI de Payara

```bash
# Crear Connection Pool
asadmin create-jdbc-connection-pool \
  --datasourceclassname com.mysql.cj.jdbc.MysqlDataSource \
  --restype javax.sql.DataSource \
  --property user=root:password=tu_password:serverName=localhost:portNumber=3306:databaseName=comercializadora_db \
  ComercializadoraPool

# Crear JDBC Resource
asadmin create-jdbc-resource \
  --connectionpoolid ComercializadoraPool \
  jdbc/ComercializadoraDB

# Verificar conexión
asadmin ping-connection-pool ComercializadoraPool
```

### 4. Compilar el Proyecto

```bash
mvn clean package
```

El WAR generado estará en: `target/comercializadora-server.war`

### 5. Desplegar en Payara

#### Opción A: Admin Console
1. Ir a: **Applications**
2. Click en **Deploy**
3. Seleccionar el archivo `comercializadora-server.war`
4. Click en **OK**

#### Opción B: CLI
```bash
asadmin deploy target/comercializadora-server.war
```

#### Opción C: Autodeploy (Desarrollo)
```bash
cp target/comercializadora-server.war $PAYARA_HOME/glassfish/domains/domain1/autodeploy/
```

### 6. Verificar Despliegue

Acceder a: `http://localhost:8080/comercializadora-server/`

## 📡 Endpoints Disponibles

### Productos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos` | Obtener todos los productos |
| GET | `/api/productos/activos` | Obtener productos activos |
| GET | `/api/productos/{id}` | Obtener producto por ID |
| GET | `/api/productos/codigo/{codigo}` | Obtener producto por código |
| GET | `/api/productos/buscar?nombre=xxx` | Buscar productos por nombre |
| GET | `/api/productos/categoria/{cat}` | Obtener productos por categoría |
| POST | `/api/productos` | Crear nuevo producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto (inactivar) |
| PATCH | `/api/productos/{id}/stock` | Actualizar stock |

### Facturación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/facturas` | Obtener todas las facturas |
| GET | `/api/facturas/{id}` | Obtener factura por ID |
| GET | `/api/facturas/numero/{num}` | Obtener factura por número |
| GET | `/api/facturas/cliente/{cedula}` | Obtener facturas por cliente |
| GET | `/api/facturas/credito` | Obtener facturas a crédito |
| GET | `/api/facturas/credito/{num}` | Obtener factura por número de crédito |
| POST | `/api/facturas` | Crear nueva factura |

## 📝 Ejemplos de Uso

### Crear Producto

```bash
curl -X POST http://localhost:8080/comercializadora-server/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "TV-002",
    "nombre": "Smart TV LG 65\"",
    "descripcion": "Televisor 4K con WebOS",
    "precio": 1299.99,
    "stock": 8,
    "categoria": "ELECTRONICA",
    "estado": "ACTIVO"
  }'
```

### Crear Factura (Efectivo)

```bash
curl -X POST http://localhost:8080/comercializadora-server/api/facturas \
  -H "Content-Type: application/json" \
  -d '{
    "cedulaCliente": "1234567890",
    "nombreCliente": "Juan Pérez",
    "formaPago": "EFECTIVO",
    "descuento": 50.00,
    "detalles": [
      {
        "productoId": 1,
        "cantidad": 1
      },
      {
        "productoId": 4,
        "cantidad": 2
      }
    ]
  }'
```

### Crear Factura (Crédito)

```bash
curl -X POST http://localhost:8080/comercializadora-server/api/facturas \
  -H "Content-Type: application/json" \
  -d '{
    "cedulaCliente": "0987654321",
    "nombreCliente": "María González",
    "formaPago": "CREDITO",
    "numeroCredito": "CRE-000001",
    "descuento": 0,
    "detalles": [
      {
        "productoId": 2,
        "cantidad": 1
      }
    ]
  }'
```

### Obtener Todos los Productos

```bash
curl http://localhost:8080/comercializadora-server/api/productos
```

### Buscar Productos por Nombre

```bash
curl "http://localhost:8080/comercializadora-server/api/productos/buscar?nombre=Samsung"
```

## 🔧 Solución de Problemas

### Error: Cannot find Data Source

**Problema**: `javax.naming.NameNotFoundException: jdbc/ComercializadoraDB`

**Solución**:
1. Verificar que el Data Source está creado en Payara
2. Verificar el JNDI name en `persistence.xml`
3. Reiniciar Payara Server

### Error: Connection refused to MySQL

**Problema**: No puede conectarse a MySQL

**Solución**:
1. Verificar que MySQL está ejecutándose
2. Verificar usuario y contraseña
3. Verificar que la base de datos existe
4. Ping al connection pool desde Payara Admin

### Error: ClassNotFoundException: com.mysql.cj.jdbc.Driver

**Problema**: Driver de MySQL no encontrado

**Solución**:
Copiar el driver JDBC de MySQL a Payara:
```bash
cp mysql-connector-j-8.0.33.jar $PAYARA_HOME/glassfish/lib/
```
Reiniciar Payara.

## 📊 Base de Datos

### Tablas

- **Producto**: Almacena información de productos/electrodomésticos
- **Factura**: Almacena facturas de venta
- **DetalleFactura**: Almacena líneas de detalle de cada factura

### Relaciones

- Una Factura tiene muchos DetalleFactura (1:N)
- Un Producto tiene muchos DetalleFactura (1:N)
- Un DetalleFactura pertenece a una Factura y un Producto (N:1)

## 🧪 Pruebas

### Test de Conectividad

```bash
# Test Productos
curl http://localhost:8080/comercializadora-server/api/productos/ping

# Test Facturación
curl http://localhost:8080/comercializadora-server/api/facturas/ping
```

## 📦 Compilación y Empaquetado

```bash
# Compilar
mvn clean compile

# Ejecutar tests (si los hay)
mvn test

# Empaquetar WAR
mvn package

# Limpiar y empaquetar
mvn clean package
```

## 🔐 Configuración de Seguridad

Por defecto, CORS está habilitado para permitir peticiones desde cualquier origen. Para ambientes de producción, modificar `CorsFilter.java` para restringir orígenes.

## 📚 Documentación Adicional

- [Jakarta EE 10 Documentation](https://jakarta.ee/specifications/platform/10/)
- [Payara Server Documentation](https://docs.payara.fish/)
- [MySQL Documentation](https://dev.mysql.com/doc/)

## 👥 Autores

- **Grupo**: GR01
- **Universidad**: ESPE - Universidad de las Fuerzas Armadas

## 📄 Licencia

Este proyecto es parte de un trabajo académico.

---

**Versión**: 1.0-SNAPSHOT  
**Fecha**: 2025
