# 🔧 Guía: Cambiar Puerto en Payara Server

## 📋 Resumen

Para que **BanQuito** corra en el puerto **8080** y **Comercializadora** en el puerto **8081**, necesitas configurar dos dominios diferentes en Payara o cambiar el puerto HTTP del dominio.

---

## ✅ Opción 1: Usar Consola de Administración de Payara (MÁS FÁCIL)

### Paso 1: Iniciar Payara Server

```bash
# Ubicación de Payara
cd C:\payara6\bin

# Iniciar servidor (o usar el servicio de Windows)
asadmin start-domain domain1
```

### Paso 2: Abrir Consola de Administración

1. Abre tu navegador
2. Ve a: `http://localhost:4848`
3. Esta es la consola de administración de Payara

### Paso 3: Cambiar el Puerto HTTP

1. En la consola, ve a:
   ```
   Configurations → server-config → Network Config → Network Listeners → http-listener-1
   ```

2. Busca el campo **"Port"**
   - Por defecto está en: `8080`
   - Cámbialo a: `8081`

3. Click en **"Save"**

### Paso 4: Reiniciar el Servidor

```bash
# Detener
asadmin stop-domain domain1

# Iniciar nuevamente
asadmin start-domain domain1
```

### Paso 5: Verificar

```bash
# Debe responder en puerto 8081
curl http://localhost:8081
```

---

## ✅ Opción 2: Usar Línea de Comandos (asadmin)

### Cambiar el Puerto HTTP

```bash
cd C:\payara6\bin

# Detener el dominio primero
asadmin stop-domain domain1

# Cambiar puerto HTTP de 8080 a 8081
asadmin set server.network-config.network-listeners.network-listener.http-listener-1.port=8081

# Iniciar dominio con el nuevo puerto
asadmin start-domain domain1
```

### Verificar Configuración

```bash
# Ver configuración actual de puertos
asadmin get server.network-config.network-listeners.network-listener.*.port
```

**Salida esperada:**
```
server.network-config.network-listeners.network-listener.admin-listener.port=4848
server.network-config.network-listeners.network-listener.http-listener-1.port=8081
server.network-config.network-listeners.network-listener.http-listener-2.port=8181
```

---

## ✅ Opción 3: Editar Archivo domain.xml (AVANZADO)

### Ubicación del Archivo

```
C:\payara6\glassfish\domains\domain1\config\domain.xml
```

### Paso 1: Detener el Servidor

```bash
asadmin stop-domain domain1
```

### Paso 2: Editar domain.xml

Busca la sección `<network-listeners>` y modifica:

**ANTES:**
```xml
<network-listener port="8080" protocol="http-listener-1" transport="tcp"
                  name="http-listener-1" thread-pool="http-thread-pool">
</network-listener>
```

**DESPUÉS:**
```xml
<network-listener port="8081" protocol="http-listener-1" transport="tcp"
                  name="http-listener-1" thread-pool="http-thread-pool">
</network-listener>
```

### Paso 3: Guardar y Reiniciar

```bash
asadmin start-domain domain1
```

---

## 🎯 Opción 4: Crear un Segundo Dominio (RECOMENDADO PARA MÚLTIPLES APLICACIONES)

Si quieres tener **BanQuito en 8080** y **Comercializadora en 8081** corriendo simultáneamente:

### Paso 1: Crear Nuevo Dominio para Comercializadora

```bash
cd C:\payara6\bin

# Crear dominio "comercializadora" en puerto 8081
asadmin create-domain --portbase 8100 comercializadora
```

**Explicación de `--portbase 8100`:**
- Puerto HTTP: 8100 + 0 = **8100**
- Puerto Admin: 8100 + 48 = **8148**
- Puerto HTTPS: 8100 + 81 = **8181**

Si quieres que el HTTP sea exactamente 8081:

```bash
# Crear dominio sin --portbase
asadmin create-domain comercializadora

# Cambiar el puerto HTTP a 8081
asadmin set --domain comercializadora server.network-config.network-listeners.network-listener.http-listener-1.port=8081

# Iniciar el dominio
asadmin start-domain comercializadora
```

### Paso 2: Configurar Data Source JDBC para Comercializadora

```bash
# Iniciar dominio
asadmin start-domain comercializadora

# Crear connection pool
asadmin create-jdbc-connection-pool \
  --datasourceclassname com.mysql.cj.jdbc.MysqlDataSource \
  --restype javax.sql.DataSource \
  --property user=root:password=root:serverName=localhost:port=3306:databaseName=comercializadora_db \
  ComercializadoraPool

# Crear JDBC Resource
asadmin create-jdbc-resource \
  --connectionpoolid ComercializadoraPool \
  jdbc/ComercializadoraDB
```

### Paso 3: Desplegar Aplicación en el Nuevo Dominio

```bash
# Desplegar en dominio comercializadora
asadmin --port 4849 deploy --force=true \
  C:\Users\Kewo\Desktop\GIT\EABMODEL_Arquitectura_26754\U1\PROYECTO\Comercializadora_Restful_Java_GR01\target\comercializadora-server.war
```

### Paso 4: Administrar Ambos Dominios

```bash
# Dominio 1 (BanQuito - Puerto 8080)
asadmin start-domain domain1
asadmin stop-domain domain1
# Admin: http://localhost:4848

# Dominio 2 (Comercializadora - Puerto 8081)
asadmin start-domain comercializadora
asadmin stop-domain comercializadora
# Admin: http://localhost:4849
```

---

## 📊 Comparación de Opciones

| Opción | Complejidad | Uso | Ventajas | Desventajas |
|--------|-------------|-----|----------|-------------|
| **1. Consola Web** | ⭐ Fácil | Un dominio | Interfaz gráfica | Requiere reiniciar servidor |
| **2. asadmin CLI** | ⭐⭐ Media | Un dominio | Rápido, scripteable | Requiere reiniciar servidor |
| **3. Editar XML** | ⭐⭐⭐ Difícil | Un dominio | Control total | Fácil cometer errores |
| **4. Dos Dominios** | ⭐⭐⭐⭐ Complejo | Múltiples apps | Aislamiento completo | Más memoria RAM |

---

## 🎯 Recomendación para Tu Caso

### Si solo vas a correr UNA aplicación a la vez:
**Usa la Opción 1 o 2** (cambiar puerto del dominio)

### Si vas a correr AMBAS aplicaciones simultáneamente:
**Usa la Opción 4** (crear dos dominios separados)

```
Dominio 1: domain1 (Puerto 8080) → BanQuito
Dominio 2: comercializadora (Puerto 8081) → Comercializadora
```

---

## 🔍 Verificar Puertos en Uso

### Windows

```bash
# Ver qué aplicación usa el puerto 8080
netstat -ano | findstr :8080

# Ver qué aplicación usa el puerto 8081
netstat -ano | findstr :8081
```

### Ver todos los puertos de Payara

```bash
asadmin list-system-properties | findstr port
```

---

## ⚠️ Problemas Comunes

### Problema 1: Puerto 8080 ya está en uso

**Error:**
```
Port 8080 is already in use by another process
```

**Solución:**
```bash
# Windows: Encontrar el proceso
netstat -ano | findstr :8080

# Matar el proceso (reemplaza PID con el número que te dio netstat)
taskkill /PID <PID> /F

# O cambiar a otro puerto
asadmin set server.network-config.network-listeners.network-listener.http-listener-1.port=8081
```

### Problema 2: No puedo acceder después de cambiar el puerto

**Causa:** No reiniciaste el servidor

**Solución:**
```bash
asadmin stop-domain domain1
asadmin start-domain domain1
```

### Problema 3: Error al desplegar después de cambiar puerto

**Causa:** La configuración del datasource JDBC está en el dominio, no en la aplicación

**Solución:** No necesitas cambiar nada en tu proyecto. El datasource está configurado en Payara, no en el código.

---

## 📝 Configuración Actual de Tu Proyecto

### ✅ NO necesitas cambiar NADA en el código

Los archivos de configuración de tu proyecto (`persistence.xml`, `web.xml`, etc.) **NO contienen el puerto**. El puerto es configuración del servidor, no de la aplicación.

**Tu proyecto está listo para correr en cualquier puerto que configures en Payara.**

### Archivos Verificados:
- ✅ `persistence.xml` - Solo tiene `jdbc/ComercializadoraDB` (JNDI name)
- ✅ `web.xml` - No tiene configuración de puerto
- ✅ `config.properties` - Ya no tiene configuración de BanQuito

---

## 🚀 Pasos Rápidos para Configuración Final

### Para correr Comercializadora en puerto 8081:

```bash
# 1. Detener Payara
asadmin stop-domain domain1

# 2. Cambiar puerto
asadmin set server.network-config.network-listeners.network-listener.http-listener-1.port=8081

# 3. Iniciar Payara
asadmin start-domain domain1

# 4. Compilar proyecto
cd C:\Users\Kewo\Desktop\GIT\EABMODEL_Arquitectura_26754\U1\PROYECTO\Comercializadora_Restful_Java_GR01
mvn clean package

# 5. Desplegar
asadmin deploy --force=true target/comercializadora-server.war

# 6. Verificar
curl http://localhost:8081/comercializadora-server/api/productos/ping
```

**Resultado esperado:**
```
Servicio de Productos está activo
```

---

## 📞 URLs Finales

Después de configurar el puerto 8081:

### Aplicación Comercializadora
- **Base URL:** `http://localhost:8081/comercializadora-server`
- **API Base:** `http://localhost:8081/comercializadora-server/api`
- **Productos:** `http://localhost:8081/comercializadora-server/api/productos`
- **Facturas:** `http://localhost:8081/comercializadora-server/api/facturas`

### Consola de Administración Payara
- **Puerto Admin:** `http://localhost:4848` (no cambia)

### BanQuito (si está en el mismo servidor)
- **Base URL:** `http://localhost:8080/banquito-server`
- **API Base:** `http://localhost:8080/banquito-server/api`

---

## ✅ Checklist Final

- [ ] Cambié el puerto HTTP en Payara a 8081
- [ ] Reinicié el servidor Payara
- [ ] Compilé el proyecto: `mvn clean package`
- [ ] Desplegué el WAR: `asadmin deploy --force=true target/comercializadora-server.war`
- [ ] Verifiqué que responde en puerto 8081: `curl http://localhost:8081/comercializadora-server/api/productos/ping`
- [ ] Importé la colección de Postman: `Comercializadora_Postman_Collection.json`
- [ ] Probé crear una factura en EFECTIVO (descuento automático 33%)
- [ ] Probé crear una factura a CREDITO (con numeroCredito)

---

¡Listo! Tu aplicación Comercializadora ahora corre en el puerto 8081. 🎉
