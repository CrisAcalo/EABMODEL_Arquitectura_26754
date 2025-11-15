# 🏗️ Arquitectura del Sistema BanQuito Server

## Patrón de Arquitectura: MVC (Model-View-Controller)

### Estructura General

```
┌─────────────────────────────────────────────────────────┐
│                      CLIENTES                           │
│  (Web, Móvil, Escritorio, Consola)                     │
└─────────────────────────────────────────────────────────┘
                          ↓ HTTP/REST
┌─────────────────────────────────────────────────────────┐
│              SERVIDOR - BanQuito Server                  │
│                                                          │
│  ┌────────────────────────────────────────────────┐    │
│  │  CAPA REST (Controller/View)                   │    │
│  │  - CreditoResource                             │    │
│  │  - RestConfig                                  │    │
│  │  - DTOs (Data Transfer Objects)                │    │
│  └────────────────────────────────────────────────┘    │
│                          ↓                               │
│  ┌────────────────────────────────────────────────┐    │
│  │  CAPA DE SERVICIO (Business Logic)             │    │
│  │  - CreditoValidacionService                    │    │
│  │  - CreditoService                              │    │
│  └────────────────────────────────────────────────┘    │
│                          ↓                               │
│  ┌────────────────────────────────────────────────┐    │
│  │  CAPA DAO (Data Access Object)                 │    │
│  │  - ClienteDAO                                  │    │
│  │  - CreditoDAO                                  │    │
│  │  - MovimientoDAO                               │    │
│  └────────────────────────────────────────────────┘    │
│                          ↓                               │
│  ┌────────────────────────────────────────────────┐    │
│  │  CAPA MODEL (Entidades JPA)                    │    │
│  │  - Cliente                                     │    │
│  │  - Cuenta                                      │    │
│  │  - Movimiento                                  │    │
│  │  - Credito                                     │    │
│  │  - CuotaAmortizacion                          │    │
│  └────────────────────────────────────────────────┘    │
│                          ↓                               │
│  ┌────────────────────────────────────────────────┐    │
│  │  CONFIGURACIÓN                                 │    │
│  │  - AppConfig (Configuración Centralizada)     │    │
│  │  - persistence.xml (JPA)                       │    │
│  │  - config.properties                           │    │
│  └────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────┘
                          ↓ JDBC
┌─────────────────────────────────────────────────────────┐
│              BASE DE DATOS - MySQL                       │
│  - cliente                                              │
│  - cuenta                                               │
│  - movimiento                                           │
│  - credito                                              │
│  - cuota_amortizacion                                   │
└─────────────────────────────────────────────────────────┘
```

## Capas del Sistema

### 1. CAPA REST (Controller/View)
**Responsabilidad:** Exponer los servicios web RESTful y manejar las peticiones HTTP.

**Componentes:**
- **RestConfig**: Configuración de JAX-RS, define la ruta base `/api`
- **CreditoResource**: Controlador REST que expone los endpoints
- **DTOs**: Objetos de transferencia de datos para las respuestas JSON

**Endpoints Expuestos:**
```
GET  /api/credito/validar/{cedula}
GET  /api/credito/monto-maximo/{cedula}
POST /api/credito/otorgar
GET  /api/credito/tabla-amortizacion/{numeroCredito}
```

**Tecnologías:**
- JAX-RS (Jakarta REST)
- JSON-B (JSON Binding)

### 2. CAPA DE SERVICIO (Business Logic)
**Responsabilidad:** Implementar la lógica de negocio y las reglas del dominio.

**Componentes:**

#### CreditoValidacionService
- Validar si una persona es sujeto de crédito (4 reglas)
- Calcular el monto máximo de crédito
- Aplicar fórmulas de negocio

#### CreditoService
- Otorgar créditos
- Generar tabla de amortización
- Calcular cuota fija mensual
- Gestionar el ciclo de vida de los créditos

**Anotaciones:** `@Stateless`, `@Inject`

### 3. CAPA DAO (Data Access Object)
**Responsabilidad:** Abstraer el acceso a la base de datos.

**Componentes:**
- **GenericDAO**: Clase abstracta con operaciones CRUD genéricas
- **ClienteDAO**: Operaciones específicas de Cliente
- **CreditoDAO**: Operaciones específicas de Crédito
- **MovimientoDAO**: Operaciones específicas de Movimiento

**Operaciones:**
- CRUD básico (Create, Read, Update, Delete)
- Consultas personalizadas con Named Queries
- Búsquedas por criterios específicos

**Anotaciones:** `@Stateless`, `@PersistenceContext`

### 4. CAPA MODEL (Entidades)
**Responsabilidad:** Representar el modelo de datos del dominio.

**Entidades:**

```
Cliente (1) ──────< (N) Cuenta
                      │
                      │ (1)
                      │
                      ▼
                    (N) Movimiento

Cliente (1) ──────< (N) Credito (1) ──────< (N) CuotaAmortizacion
```

**Anotaciones JPA:** `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`, `@OneToMany`, `@ManyToOne`

### 5. CAPA DE CONFIGURACIÓN
**Responsabilidad:** Gestionar la configuración de la aplicación.

**Componentes:**
- **AppConfig**: Singleton que lee `config.properties`
- **persistence.xml**: Configuración de JPA y DataSource
- **config.properties**: Parámetros configurables

**Configuración Centralizada:**
```properties
server.host=192.168.1.100     # ← Solo cambiar aquí la IP
db.host=localhost
credito.tasa.anual=0.16
```

## Flujo de una Petición

### Ejemplo: Otorgar un Crédito

```
1. Cliente HTTP POST
   ↓
2. CreditoResource.otorgarCredito()
   - Recibe SolicitudCreditoDTO
   - Valida datos de entrada
   ↓
3. CreditoService.otorgarCredito()
   - Aplica lógica de negocio
   - Valida sujeto de crédito
   - Calcula monto máximo
   - Genera tabla de amortización
   ↓
4. CreditoDAO.save()
   - Persiste el Credito
   ↓
5. JPA/EntityManager
   - Ejecuta INSERT en BD
   ↓
6. MySQL
   - Almacena los datos
   ↓
7. Respuesta JSON
   - RespuestaCreditoDTO
   ↓
8. Cliente recibe respuesta
```

## Tecnologías Utilizadas

### Backend
- **Java 17**: Lenguaje de programación
- **Jakarta EE 10**: Plataforma empresarial
  - JAX-RS: REST API
  - JPA: Persistencia
  - CDI: Inyección de dependencias
  - EJB: Enterprise JavaBeans

### Servidor de Aplicaciones
- **Payara Server 6**: Implementación de Jakarta EE

### Base de Datos
- **MySQL 8.0**: Sistema de gestión de base de datos
- **JDBC**: Conectividad con BD

### Herramientas
- **Maven**: Gestión de dependencias y build
- **Git**: Control de versiones

## Características de la Arquitectura

### 1. Separación de Responsabilidades
Cada capa tiene una responsabilidad clara y bien definida.

### 2. Desacoplamiento
Las capas se comunican a través de interfaces, facilitando cambios y pruebas.

### 3. Inyección de Dependencias (CDI)
```java
@Inject
private ClienteDAO clienteDAO;
```

### 4. Transacciones (JTA)
```java
@Transactional
public Map<String, Object> otorgarCredito(...) {
    // Operaciones transaccionales
}
```

### 5. Configuración Centralizada
Un solo archivo para toda la configuración del servidor:
```
src/main/resources/config.properties
```

### 6. RESTful Design
- Uso correcto de métodos HTTP (GET, POST)
- Respuestas con códigos de estado apropiados
- Formato JSON para intercambio de datos

## Escalabilidad

El sistema está diseñado para ser escalable:

1. **Horizontal**: Múltiples instancias de Payara con balanceo de carga
2. **Vertical**: Aumento de recursos del servidor
3. **Base de Datos**: Replicación y sharding de MySQL

## Seguridad

Consideraciones de seguridad implementadas:

1. **Validación de Datos**: En la capa de servicio
2. **Transacciones ACID**: Garantizadas por JTA
3. **Inyección SQL**: Prevenida con JPA/Named Queries
4. **Configuración Externa**: Credenciales en properties (no en código)

## Mantenibilidad

El código es fácil de mantener gracias a:

1. **Código Limpio**: Nombres descriptivos y comentarios
2. **Patrones de Diseño**: DAO, DTO, Singleton
3. **Modularidad**: Cada clase tiene una responsabilidad única
4. **Documentación**: README y comentarios Javadoc

## Extensibilidad

Fácil de extender para nuevas funcionalidades:

1. Agregar nuevos endpoints: Crear método en `CreditoResource`
2. Nueva lógica de negocio: Crear nuevo servicio
3. Nueva entidad: Crear clase con anotaciones JPA
4. Nuevas validaciones: Agregar en `CreditoValidacionService`

## Próximos Pasos (Cliente)

Este servidor expone los Web Services que serán consumidos por:

1. **Cliente Web** (HTML/CSS/JavaScript o JSP)
2. **Cliente Móvil** (Android/iOS)
3. **Cliente Escritorio** (Java Swing/JavaFX)
4. **Cliente Consola** (Java Console Application)

Todos consumirán los mismos endpoints RESTful.
