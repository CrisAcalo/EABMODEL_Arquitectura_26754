# 🚀 Guía Rápida de Inicio

## Pasos Mínimos para Ejecutar el Proyecto

### 1. Configurar Base de Datos
```bash
mysql -u root -p < database/01_crear_base_datos.sql
```

### 2. Cambiar IP en config.properties
```properties
# Editar: src/main/resources/config.properties
server.host=192.168.1.XXX  # <- Tu IP local
```

### 3. Configurar DataSource en Payara

**Usando la consola web (http://localhost:4848):**

1. Resources → JDBC → JDBC Connection Pools → New
   - Pool Name: `BanquitoPool`
   - Resource Type: `javax.sql.DataSource`
   - Database Driver: `MySQL`

2. Additional Properties:
   ```
   ServerName = localhost
   Port = 3306
   DatabaseName = banquito_db
   User = root
   Password = tu_password
   URL = jdbc:mysql://localhost:3306/banquito_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   ```

3. Ping para verificar

4. Resources → JDBC → JDBC Resources → New
   - JNDI Name: `jdbc/BanquitoDB`
   - Pool Name: `BanquitoPool`

### 4. Compilar y Desplegar
```bash
mvn clean package
asadmin deploy target/banquito-server.war
```

### 5. Verificar
```
http://localhost:8080/banquito-server/api/credito/ping
```

## 🔗 URLs Importantes

**Consola Payara:** http://localhost:4848  
**Base URL API:** http://localhost:8080/banquito-server/api

**Endpoints:**
- GET  `/credito/validar/{cedula}`
- GET  `/credito/monto-maximo/{cedula}`
- POST `/credito/otorgar`
- GET  `/credito/tabla-amortizacion/{numeroCredito}`

## 📝 Cédulas de Prueba

- 1234567890 - Juan Carlos Pérez (Casado)
- 0987654321 - María Fernanda López (Soltera)
- 1122334455 - Pedro Antonio Ramírez (Casado)
- 5566778899 - Ana Lucía Torres (Soltera)
- 9988776655 - Luis Fernando Morales (Divorciado)

**Todas son válidas para crédito.**

## 🔥 Comandos Útiles

### Maven
```bash
# Limpiar y compilar
mvn clean package

# Ver árbol de dependencias
mvn dependency:tree

# Ejecutar pruebas
mvn test
```

### Payara
```bash
# Iniciar servidor
asadmin start-domain

# Detener servidor
asadmin stop-domain

# Ver aplicaciones desplegadas
asadmin list-applications

# Redesplegar
asadmin redeploy target/banquito-server.war

# Ver logs
tail -f payara6/glassfish/domains/domain1/logs/server.log
```

### MySQL
```bash
# Conectar
mysql -u root -p

# Ver bases de datos
SHOW DATABASES;

# Usar la base de datos
USE banquito_db;

# Ver tablas
SHOW TABLES;

# Contar registros
SELECT COUNT(*) FROM movimiento;
```

## 🐛 Errores Comunes

1. **Puerto 8080 ocupado:**
   ```bash
   # Ver qué proceso usa el puerto
   netstat -ano | findstr :8080
   # Matar el proceso
   taskkill /PID <pid> /F
   ```

2. **Cambios no se reflejan:**
   - Asegurarse de recompilar: `mvn clean package`
   - Redesplegar: `asadmin redeploy`

3. **Error de conexión a MySQL:**
   - Verificar que MySQL esté corriendo
   - Verificar usuario/password en config.properties
   - Hacer ping al connection pool en Payara

## 📱 Para Conectar Clientes desde Otro Dispositivo

1. Obtener IP del servidor:
   ```cmd
   ipconfig  # Windows
   ifconfig  # Linux/Mac
   ```

2. Cambiar en `config.properties`:
   ```properties
   server.host=192.168.1.XXX
   ```

3. Recompilar y redesplegar

4. Configurar firewall para permitir puerto 8080

5. Desde el cliente usar:
   ```
   http://192.168.1.XXX:8080/banquito-server/api/...
   ```

## ✅ Checklist de Verificación

- [ ] MySQL instalado y corriendo
- [ ] Base de datos `banquito_db` creada con datos
- [ ] Payara Server 6 instalado
- [ ] DataSource `jdbc/BanquitoDB` configurado en Payara
- [ ] IP correcta en `config.properties`
- [ ] Proyecto compilado sin errores (`mvn clean package`)
- [ ] WAR desplegado en Payara
- [ ] Endpoint `/ping` responde correctamente
- [ ] Firewall configurado (si es necesario)

## 📞 Soporte

Para más detalles, consultar el archivo `README.md` completo.
