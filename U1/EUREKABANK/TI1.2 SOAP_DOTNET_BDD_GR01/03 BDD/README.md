# EurekaBank - Docker Setup

## Requisitos
- Docker
- Docker Compose

## Instrucciones de uso

### Iniciar el contenedor

```bash
docker-compose up -d
```

### Ver los logs

```bash
docker-compose logs -f
```

### Ejecutar scripts manualmente (si es necesario)

```bash
# Crear la base de datos
docker exec -it sql-server-2022 /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Admin1234 -i /docker-entrypoint-initdb.d/01-Crea_BD.sql

# Cargar datos
docker exec -it sql-server-2022 /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Admin1234 -i /docker-entrypoint-initdb.d/02-Carga_Datos.sql
```

### Conectarse a SQL Server

- **Host**: localhost
- **Puerto**: 1433
- **Usuario**: sa
- **Contraseña**: Admin1234
- **Base de datos**: EUREKABANK

### Detener el contenedor

```bash
docker-compose down
```

### Detener y eliminar volúmenes

```bash
docker-compose down -v
```

## Notas

- El volumen `sqlvolume` persiste los datos de la base de datos
- Los scripts se ejecutan automáticamente al iniciar el contenedor por primera vez
- El healthcheck verifica que SQL Server esté listo para aceptar conexiones
