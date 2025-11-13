
## Configuración en Payara Admin Console

### Paso 1: Acceder a la Consola de Administración

#### Desde NetBeans:
1. Ve a la pestaña **Services**
2. Expande **Servers → Payara Server**
3. Click derecho en **Payara Server**
4. Selecciona **View Domain Admin Console**

#### Desde el Navegador:
Abre directamente: `http://localhost:4848`

### Paso 2: Crear el JDBC Connection Pool

1. En el menú izquierdo: **Resources → JDBC → JDBC Connection Pools**
2. Click en **New**
3. Configura la página 1:
   - **Pool Name:** `EurekaBankPool`
   - **Resource Type:** `javax.sql.DataSource`
   - **Database Driver Vendor:** `MySQL` (o selecciona manualmente)
4. Click **Next**

5. En la página 2 - **General Settings:**
   - **Datasource Classname:** `com.mysql.cj.jdbc.MysqlDataSource`

     **¡MUY IMPORTANTE!** Debe ser `com.mysql.cj.jdbc.MysqlDataSource` (nueva versión)
     NO usar `com.mysql.jdbc.jdbc2.optional.MysqlDataSource` (versión antigua)

6. En **Additional Properties**, busca y configura:

   | Property | Value |
   |----------|-------|
   | `serverName` | `localhost` |
   | `portNumber` | `3306` |
   | `databaseName` | `eurekabank` |
   | `user` | `eureka` |
   | `password` | `admin` |

7. Agregar propiedades adicionales (Click en "Add Property"):
   - `useSSL`: `false`
   - `allowPublicKeyRetrieval`: `true`
   - `serverTimezone`: `UTC`
   - `characterEncoding`: `UTF-8`

8. Click **Finish**

### Paso 3: Probar el Connection Pool

1. En la lista de Connection Pools, click en **EurekaBankPool**
2. Click en el botón **Ping** (arriba)
3. Deberías ver: **"Ping Succeeded"** ✓

**Si el Ping falla:**
- Verifica que MySQL esté corriendo: `docker ps`
- Verifica el **Datasource Classname** (debe ser `com.mysql.cj.jdbc.MysqlDataSource`)
- Verifica las credenciales en Additional Properties
- Verifica que el driver MySQL esté en `domains/domain1/lib/`

### Paso 4: Crear el JDBC Resource (JNDI)

1. En el menú izquierdo: **Resources → JDBC → JDBC Resources**
2. Click en **New**
3. Configura:
   - **JNDI Name:** `jdbc/eurekabank`
   - **Pool Name:** `EurekaBankPool` (selecciona del dropdown)
   - **Description:** `DataSource para EurekaBank`
4. Click **OK**

### Paso 5: Verificar la Configuración

#### En la aplicación:
El código Java usa JNDI para obtener la conexión:
```java
InitialContext ctx = new InitialContext();
DataSource dataSource = (DataSource) ctx.lookup("jdbc/eurekabank");
Connection conn = dataSource.getConnection();
```

#### Archivo de configuración (glassfish-resources.xml):
El archivo `web/WEB-INF/glassfish-resources.xml` existe pero **no es procesado automáticamente** en Payara 6. Por eso creamos los recursos manualmente.

### Paso 6: Reiniciar la Aplicación

En NetBeans:
1. Click derecho en el proyecto **EurekaBank_Soap_Java_GR01**
2. Selecciona **Clean and Build**
3. Luego selecciona **Run**

### Paso 7: Probar la Conexión

Abre en el navegador:
```
http://localhost:8080/EurekaBank_Soap_Java_GR01/test-connection
```

Deberías ver:
- ✓ Conexión exitosa!
- Información de la base de datos (MySQL 8.0.x)

## Solución de Problemas Comunes

### Error: "Ping Connection Pool failed... Class name is wrong"
**Causa:** Estás usando el classname antiguo del driver MySQL

**Solución:** Cambia el **Datasource Classname** a:
```
com.mysql.cj.jdbc.MysqlDataSource
```

### Error: "Cannot load JDBC driver class 'com.mysql.cj.jdbc.Driver'"
**Causa:** El driver MySQL no está en la ubicación correcta

**Solución:**
1. Copia `mysql-connector-j-9.4.0.jar` a:
   ```
   C:\Users\[TU_USUARIO]\servers\payara6\glassfish\domains\domain1\lib\
   ```
2. Reinicia Payara Server

### Error: "Access denied for user 'eureka'@'localhost'"
**Causa:** Credenciales incorrectas

**Solución:** Verifica las credenciales en MySQL:
```bash
docker exec -it mysql-monster mysql -u eureka -padmin -e "SHOW DATABASES;"
```

### Error: "Communications link failure"
**Causa:** MySQL no está corriendo o el puerto es incorrecto

**Solución:**
1. Verifica que MySQL está corriendo: `docker ps`
2. Verifica el puerto: debe ser `3306`

### La página test-connection se queda colgada
**Causa:** El recurso JNDI `jdbc/eurekabank` no existe

**Solución:** Sigue el **Paso 4** para crear el JDBC Resource

## Verificación de Recursos Creados

### Ver Connection Pools:
Admin Console → Resources → JDBC → JDBC Connection Pools
- Debe aparecer: **EurekaBankPool**

### Ver JDBC Resources:
Admin Console → Resources → JDBC → JDBC Resources
- Debe aparecer: **jdbc/eurekabank** (apuntando a EurekaBankPool)

## Comandos Útiles para Verificación

### Verificar que MySQL está corriendo:
```bash
docker ps
```

### Ver bases de datos en MySQL:
```bash
docker exec mysql-monster mysql -u eureka -padmin -e "SHOW DATABASES;"
```

### Ver tablas en eurekabank:
```bash
docker exec mysql-monster mysql -u eureka -padmin eurekabank -e "SHOW TABLES;"
```

### Probar conexión desde línea de comandos:
```bash
docker exec -it mysql-monster mysql -u eureka -padmin eurekabank
```

## Servicios SOAP Desplegados

Una vez configurada la conexión, los servicios estarán disponibles en:

- **Autenticación:** http://localhost:8080/EurekaBank_Soap_Java_GR01/ServicioAutenticacion
- **Transacciones:** http://localhost:8080/EurekaBank_Soap_Java_GR01/ServicioTransaccion
- **Reportes:** http://localhost:8080/EurekaBank_Soap_Java_GR01/ServicioReporte

### Ver WSDL:
Agrega `?wsdl` al final de cada URL:
```
http://localhost:8080/EurekaBank_Soap_Java_GR01/ServicioAutenticacion?wsdl
```

## Notas Adicionales

### Diferencias con versiones antiguas de Payara/GlassFish:
- En Payara 6, el archivo `glassfish-resources.xml` NO es procesado automáticamente durante el deployment
- Es necesario crear los recursos JNDI manualmente a través de Admin Console o comandos asadmin
- Jakarta EE 10 usa `jakarta.*` en lugar de `javax.*` para muchas APIs

### Persistencia de la configuración:
- Los recursos JNDI creados en Admin Console son **permanentes**
- No es necesario reconfigurarlos cada vez que se reinicia Payara
- Solo necesitas configurarlos una vez por dominio (domain1)

### Backup de la configuración:
El archivo de configuración del dominio está en:
```
C:\Users\[TU_USUARIO]\servers\payara6\glassfish\domains\domain1\config\domain.xml
```

Puedes hacer backup de este archivo para guardar toda tu configuración.

---

**Documentación generada:** 2025-11-12
**Versiones:** Payara Server 6.2025.9, MySQL 8.0, MySQL Connector/J 9.4.0
