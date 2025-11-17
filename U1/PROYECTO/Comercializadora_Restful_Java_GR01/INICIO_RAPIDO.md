# 🚀 Guía de Inicio Rápido

## Configuración Rápida (5 minutos)

### 1. Crear Base de Datos
```bash
mysql -u root -p < database/01_crear_base_datos.sql
```

### 2. Configurar Payara (CLI - Rápido)

```bash
# Variables (ajustar según tu configuración)
MYSQL_USER="root"
MYSQL_PASSWORD="tu_password"
MYSQL_HOST="localhost"
MYSQL_PORT="3306"
MYSQL_DB="comercializadora_db"

# Crear Connection Pool
asadmin create-jdbc-connection-pool \
  --datasourceclassname com.mysql.cj.jdbc.MysqlDataSource \
  --restype javax.sql.DataSource \
  --property user=${MYSQL_USER}:password=${MYSQL_PASSWORD}:serverName=${MYSQL_HOST}:portNumber=${MYSQL_PORT}:databaseName=${MYSQL_DB} \
  ComercializadoraPool

# Crear JDBC Resource
asadmin create-jdbc-resource \
  --connectionpoolid ComercializadoraPool \
  jdbc/ComercializadoraDB

# Verificar
asadmin ping-connection-pool ComercializadoraPool
```

### 3. Compilar y Desplegar
```bash
mvn clean package
asadmin deploy target/comercializadora-server.war
```

### 4. Verificar
```bash
curl http://localhost:8080/comercializadora-server/api/productos/ping
```

## Comandos Útiles

### Payara

```bash
# Iniciar Payara
asadmin start-domain

# Detener Payara
asadmin stop-domain

# Ver aplicaciones desplegadas
asadmin list-applications

# Redesplegar
asadmin redeploy target/comercializadora-server.war

# Ver logs
asadmin view-log

# Verificar recursos JDBC
asadmin list-jdbc-resources
asadmin list-jdbc-connection-pools
```

### Maven

```bash
# Limpiar
mvn clean

# Compilar
mvn compile

# Empaquetar
mvn package

# Todo junto
mvn clean package
```

### MySQL

```bash
# Conectarse
mysql -u root -p

# Ver bases de datos
SHOW DATABASES;

# Usar base de datos
USE comercializadora_db;

# Ver tablas
SHOW TABLES;

# Ver datos
SELECT * FROM Producto;
SELECT * FROM Factura;
SELECT * FROM DetalleFactura;
```

## URLs Importantes

- **Aplicación**: http://localhost:8080/comercializadora-server/
- **API Base**: http://localhost:8080/comercializadora-server/api/
- **Payara Admin**: http://localhost:4848
- **MySQL**: localhost:3306

## Pruebas Rápidas con curl

```bash
# Base URL
BASE_URL="http://localhost:8080/comercializadora-server/api"

# Ping
curl ${BASE_URL}/productos/ping
curl ${BASE_URL}/facturas/ping

# Obtener productos
curl ${BASE_URL}/productos

# Obtener facturas
curl ${BASE_URL}/facturas

# Crear producto
curl -X POST ${BASE_URL}/productos \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "TEST-001",
    "nombre": "Producto de Prueba",
    "precio": 99.99,
    "stock": 10,
    "categoria": "TEST",
    "estado": "ACTIVO"
  }'
```

## Solución Rápida de Problemas

### Problema: No puede conectar a MySQL
```bash
# Verificar que MySQL está corriendo
sudo systemctl status mysql
# o
sudo service mysql status

# Iniciar MySQL si está detenido
sudo systemctl start mysql
```

### Problema: Payara no inicia
```bash
# Ver logs
asadmin view-log

# Verificar puerto 8080
netstat -an | grep 8080

# Matar proceso si está ocupado
kill -9 $(lsof -t -i:8080)
```

### Problema: Error al desplegar
```bash
# Undeployar primero
asadmin undeploy comercializadora-server

# Limpiar y volver a compilar
mvn clean package

# Redesplegar
asadmin deploy target/comercializadora-server.war
```

### Problema: Connection Pool falla
```bash
# Eliminar y recrear
asadmin delete-jdbc-resource jdbc/ComercializadoraDB
asadmin delete-jdbc-connection-pool ComercializadoraPool

# Crear de nuevo (ver paso 2 arriba)
```

## Flujo de Trabajo Típico

1. **Hacer cambios en el código**
2. **Compilar**: `mvn clean package`
3. **Redesplegar**: `asadmin redeploy target/comercializadora-server.war`
4. **Probar**: `curl http://localhost:8080/comercializadora-server/api/productos/ping`

## Auto-redeploy para Desarrollo

```bash
# Copiar WAR a autodeploy
cp target/comercializadora-server.war $PAYARA_HOME/glassfish/domains/domain1/autodeploy/
```

Payara automáticamente detectará y desplegará el WAR.

---

¿Problemas? Revisa el README.md completo para más detalles.
