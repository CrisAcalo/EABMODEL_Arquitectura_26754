#!/bin/bash

# Esperar a que SQL Server esté listo
echo "Esperando a que SQL Server esté listo..."
sleep 30s

# Ejecutar el script de creación de base de datos
echo "Ejecutando script de creación de base de datos..."
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Admin1234 -i /docker-entrypoint-initdb.d/01-Crea_BD.sql

# Ejecutar el script de carga de datos
echo "Ejecutando script de carga de datos..."
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Admin1234 -i /docker-entrypoint-initdb.d/02-Carga_Datos.sql

echo "Base de datos EUREKABANK creada y cargada exitosamente!"
