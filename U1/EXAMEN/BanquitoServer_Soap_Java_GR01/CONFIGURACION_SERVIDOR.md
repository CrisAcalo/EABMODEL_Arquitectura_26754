# CONFIGURACIÓN DEL SERVIDOR PAYARA 6

## 1. CONFIGURAR IP DEL SERVIDOR

**IMPORTANTE:** Antes de compilar y desplegar, debe configurar la IP del servidor.

### Archivo a modificar:
```
src/main/resources/application.properties
```

### Cambiar el parámetro:
```properties
# Cambie esta IP por la dirección IP de su computadora
server.ip=192.168.1.100
```

### ¿Cómo obtener mi IP?
- **Windows:** Ejecute `ipconfig` en CMD y busque "Dirección IPv4"
- **Linux/Mac:** Ejecute `ifconfig` o `ip addr`

**Ejemplo de IP local:** 192.168.1.100, 192.168.0.105, 10.0.0.15, etc.

---

## 2. INSTALAR Y CONFIGURAR POSTGRESQL

### 2.1. Crear la Base de Datos
```sql
-- Ejecutar el script:
database/01_crear_base_datos.sql
```

### 2.2. Cargar Datos de Prueba
```sql
-- Ejecutar el script:
database/02_datos_prueba.sql
```

---

## 3. CONFIGURAR DATA SOURCE EN PAYARA

### 3.1. Copiar el Driver de PostgreSQL

1. Descargar el driver JDBC de PostgreSQL (postgresql-42.7.1.jar)
2. Copiarlo a: `PAYARA_HOME/glassfish/domains/domain1/lib/`
3. Reiniciar Payara Server

### 3.2. Crear Connection Pool

#### Opción A: Mediante Consola Admin (Recomendado)

1. Acceder a: http://localhost:4848
2. Ir a: **Resources → JDBC → JDBC Connection Pools**
3. Clic en **New**
4. Configurar:
   - **Pool Name:** BanquitoPool
   - **Resource Type:** javax.sql.DataSource
   - **Database Driver Vendor:** PostgreSQL
5. Clic en **Next**
6. En "Additional Properties", configurar:
   ```
   ServerName: localhost
   PortNumber: 5432
   DatabaseName: banquito_db
   User: postgres
   Password: postgres
   ```
7. Clic en **Finish**
8. **Ping** para probar la conexión

#### Opción B: Mediante asadmin (Línea de comandos)

```bash
cd PAYARA_HOME/bin

# Crear Connection Pool
./asadmin create-jdbc-connection-pool \
  --datasourceclassname org.postgresql.ds.PGSimpleDataSource \
  --restype javax.sql.DataSource \
  --property ServerName=localhost:PortNumber=5432:DatabaseName=banquito_db:User=postgres:Password=postgres \
  BanquitoPool

# Probar el pool
./asadmin ping-connection-pool BanquitoPool
```

### 3.3. Crear JDBC Resource

#### Opción A: Mediante Consola Admin

1. Ir a: **Resources → JDBC → JDBC Resources**
2. Clic en **New**
3. Configurar:
   - **JNDI Name:** java:app/jdbc/BanquitoDS
   - **Pool Name:** BanquitoPool
4. Clic en **OK**

#### Opción B: Mediante asadmin

```bash
./asadmin create-jdbc-resource \
  --connectionpoolid BanquitoPool \
  java:app/jdbc/BanquitoDS
```

---

## 4. COMPILAR EL PROYECTO

### Con Maven:
```bash
cd BanquitoServer
mvn clean package
```

Esto generará el archivo: `target/banquito-server.war`

---

## 5. DESPLEGAR EN PAYARA

### Opción A: Mediante Consola Admin

1. Acceder a: http://localhost:4848
2. Ir a: **Applications**
3. Clic en **Deploy**
4. Seleccionar el archivo `banquito-server.war`
5. Context Root: `/banquito-server`
6. Clic en **OK**

### Opción B: Mediante asadmin

```bash
cd PAYARA_HOME/bin
./asadmin deploy --contextroot /banquito-server --name banquito-server path/to/banquito-server.war
```

### Opción C: Auto-deploy (Desarrollo)

Copiar el archivo WAR a:
```
PAYARA_HOME/glassfish/domains/domain1/autodeploy/
```

---

## 6. VERIFICAR EL DESPLIEGUE

### 6.1. Acceder a la Aplicación
```
http://localhost:8080/banquito-server
```

Debería ver la página de bienvenida con la lista de servicios disponibles.

### 6.2. Probar el Servicio Ping
```
http://localhost:8080/banquito-server/api/credito/ping
```

Debería retornar: "Servicio de Crédito BanQuito - Activo"

### 6.3. Probar Validación de Crédito
```
http://localhost:8080/banquito-server/api/credito/validar/1234567890
```

Debería retornar un JSON con la validación del cliente.

---

## 7. ACCESO DESDE OTRAS COMPUTADORAS

Una vez desplegado el servidor, otros clientes pueden acceder usando:

```
http://TU_IP:8080/banquito-server/api/...
```

**Ejemplo:**
```
http://192.168.1.100:8080/banquito-server/api/credito/validar/1234567890
```

### Configuración de Firewall

Si no puede acceder desde otras computadoras, debe:

1. **Windows:** Permitir el puerto 8080 en el Firewall
2. **Linux:** 
   ```bash
   sudo ufw allow 8080/tcp
   ```

---

## 8. SOLUCIÓN DE PROBLEMAS

### Problema: No se puede conectar a la base de datos
- Verificar que PostgreSQL esté ejecutándose
- Verificar usuario y contraseña en el Connection Pool
- Hacer ping al pool desde la consola admin

### Problema: Error al desplegar
- Verificar logs en: `PAYARA_HOME/glassfish/domains/domain1/logs/server.log`
- Asegurar que el driver PostgreSQL esté en `lib/`

### Problema: 404 Not Found
- Verificar que el context root sea correcto
- Verificar que la aplicación esté desplegada en la consola admin

### Problema: No se puede acceder desde otra computadora
- Verificar que cambió la IP en `application.properties`
- Verificar configuración del Firewall
- Asegurar que ambas computadoras estén en la misma red

---

## 9. COMANDOS ÚTILES

### Iniciar Payara
```bash
cd PAYARA_HOME/bin
./asadmin start-domain
```

### Detener Payara
```bash
./asadmin stop-domain
```

### Ver aplicaciones desplegadas
```bash
./asadmin list-applications
```

### Ver logs en tiempo real
```bash
./asadmin start-domain --verbose
```

### Redesplegar aplicación
```bash
./asadmin redeploy banquito-server.war
```

---

## RESUMEN DE URLs

- **Consola Admin Payara:** http://localhost:4848
- **Aplicación Principal:** http://localhost:8080/banquito-server
- **API REST Base:** http://localhost:8080/banquito-server/api
- **Validar Crédito:** http://localhost:8080/banquito-server/api/credito/validar/{cedula}
- **Monto Máximo:** http://localhost:8080/banquito-server/api/credito/monto-maximo/{cedula}
- **Otorgar Crédito:** http://localhost:8080/banquito-server/api/credito/otorgar (POST)
- **Tabla Amortización:** http://localhost:8080/banquito-server/api/credito/tabla-amortizacion/{numero}

---

¡Configuración completada! El servidor está listo para ser usado por los clientes.
