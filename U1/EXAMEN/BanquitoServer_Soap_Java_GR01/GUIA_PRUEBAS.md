# GUÍA DE PRUEBAS - WEB SERVICES BANQUITO

Esta guía proporciona ejemplos para probar todos los Web Services usando diferentes herramientas.

---

## 🧪 HERRAMIENTAS RECOMENDADAS

1. **Postman** - Cliente REST con interfaz gráfica
2. **cURL** - Línea de comandos
3. **Thunder Client** - Extensión de VS Code
4. **Navegador Web** - Para peticiones GET

---

## 🌐 CONFIGURACIÓN BASE

Antes de realizar las pruebas, asegúrese de:

1. El servidor Payara esté ejecutándose
2. La aplicación esté desplegada correctamente
3. La base de datos tenga los datos de prueba

**URL Base:** 
```
http://localhost:8080/banquito-server/api
```

Si está probando desde otra computadora, reemplace `localhost` con la IP del servidor:
```
http://192.168.1.100:8080/banquito-server/api
```

---

## 📋 PRUEBA 1: VERIFICAR QUE EL SERVIDOR ESTÉ ACTIVO

### Endpoint
```
GET /credito/ping
```

### Prueba con cURL
```bash
curl http://localhost:8080/banquito-server/api/credito/ping
```

### Prueba con Navegador
```
http://localhost:8080/banquito-server/api/credito/ping
```

### Respuesta Esperada
```
Servicio de Crédito BanQuito - Activo
```

---

## 📋 PRUEBA 2: VALIDAR SUJETO DE CRÉDITO

### Endpoint
```
GET /credito/validar/{cedula}
```

### CASO 1: Cliente válido (Juan Carlos Pérez)

#### cURL
```bash
curl http://localhost:8080/banquito-server/api/credito/validar/1234567890
```

#### Postman
```
Method: GET
URL: http://localhost:8080/banquito-server/api/credito/validar/1234567890
Headers: 
  Content-Type: application/json
```

#### Respuesta Esperada (200 OK)
```json
{
  "esSujetoCredito": true,
  "mensaje": "El cliente es sujeto de crédito",
  "cedula": "1234567890",
  "nombreCliente": "Juan Carlos Pérez García"
}
```

---

### CASO 2: Cliente no existe

#### cURL
```bash
curl http://localhost:8080/banquito-server/api/credito/validar/9999999999
```

#### Respuesta Esperada (400 Bad Request)
```json
{
  "esSujetoCredito": false,
  "mensaje": "La persona con cédula 9999999999 no es cliente del banco",
  "cedula": "9999999999",
  "nombreCliente": null
}
```

---

### CASO 3: Todos los clientes de prueba

Pruebe con cada una de estas cédulas:
- `1234567890` - Juan Carlos Pérez García (CASADO, >25 años) ✅
- `0987654321` - María Elena González López (SOLTERA) ✅
- `1122334455` - Pedro Antonio Rodríguez Silva (CASADO, >25 años) ✅
- `5544332211` - Ana Lucía Martínez Ruiz (SOLTERA) ✅
- `9988776655` - Carlos Alberto Sánchez Torres (CASADO, >25 años) ✅

---

## 📋 PRUEBA 3: OBTENER MONTO MÁXIMO DE CRÉDITO

### Endpoint
```
GET /credito/monto-maximo/{cedula}
```

### CASO 1: Cálculo exitoso

#### cURL
```bash
curl http://localhost:8080/banquito-server/api/credito/monto-maximo/1234567890
```

#### Postman
```
Method: GET
URL: http://localhost:8080/banquito-server/api/credito/monto-maximo/1234567890
```

#### Respuesta Esperada (200 OK)
```json
{
  "cedula": "1234567890",
  "nombreCliente": "Juan Carlos Pérez García",
  "montoMaximo": 2430.00,
  "promedioDepositos": 1100.00,
  "promedioRetiros": 250.00,
  "aprobado": true,
  "mensaje": "Monto máximo calculado exitosamente"
}
```

**Nota:** Los valores exactos pueden variar según los datos de prueba.

---

### CASO 2: Verificar todos los clientes

```bash
# Cliente 1
curl http://localhost:8080/banquito-server/api/credito/monto-maximo/1234567890

# Cliente 2
curl http://localhost:8080/banquito-server/api/credito/monto-maximo/0987654321

# Cliente 3
curl http://localhost:8080/banquito-server/api/credito/monto-maximo/1122334455

# Cliente 4
curl http://localhost:8080/banquito-server/api/credito/monto-maximo/5544332211

# Cliente 5
curl http://localhost:8080/banquito-server/api/credito/monto-maximo/9988776655
```

---

## 📋 PRUEBA 4: OTORGAR CRÉDITO

### Endpoint
```
POST /credito/otorgar
```

### CASO 1: Crédito exitoso (12 cuotas)

#### cURL
```bash
curl -X POST http://localhost:8080/banquito-server/api/credito/otorgar \
  -H "Content-Type: application/json" \
  -d '{
    "cedula": "1234567890",
    "precioElectrodomestico": 1500.00,
    "numeroCuotas": 12
  }'
```

#### Postman
```
Method: POST
URL: http://localhost:8080/banquito-server/api/credito/otorgar
Headers:
  Content-Type: application/json
Body (raw JSON):
{
  "cedula": "1234567890",
  "precioElectrodomestico": 1500.00,
  "numeroCuotas": 12
}
```

#### Respuesta Esperada (201 Created)
```json
{
  "aprobado": true,
  "mensaje": "Crédito aprobado y otorgado exitosamente",
  "numeroCredito": "CRE1731513600000",
  "cedula": "1234567890",
  "nombreCliente": "Juan Carlos Pérez García",
  "montoCredito": 1500.00,
  "numeroCuotas": 12,
  "cuotaMensual": 133.69,
  "tasaInteresAnual": 0.1600,
  "totalAPagar": 1604.28,
  "totalIntereses": 104.28,
  "tablaAmortizacion": [
    {
      "numeroCuota": 1,
      "valorCuota": 133.69,
      "interesPagado": 20.00,
      "capitalPagado": 113.69,
      "saldo": 1386.31
    },
    {
      "numeroCuota": 2,
      "valorCuota": 133.69,
      "interesPagado": 18.48,
      "capitalPagado": 115.21,
      "saldo": 1271.10
    },
    ...resto de cuotas...
  ]
}
```

---

### CASO 2: Crédito a 6 meses

```json
{
  "cedula": "0987654321",
  "precioElectrodomestico": 1000.00,
  "numeroCuotas": 6
}
```

---

### CASO 3: Crédito a 24 meses (máximo)

```json
{
  "cedula": "1122334455",
  "precioElectrodomestico": 3000.00,
  "numeroCuotas": 24
}
```

---

### CASO 4: Error - Monto excede el máximo

```json
{
  "cedula": "1234567890",
  "precioElectrodomestico": 50000.00,
  "numeroCuotas": 12
}
```

#### Respuesta Esperada (400 Bad Request)
```json
{
  "aprobado": false,
  "mensaje": "El precio del electrodoméstico ($50000.00) excede el monto máximo aprobado ($2430.00)"
}
```

---

### CASO 5: Error - Número de cuotas inválido

```json
{
  "cedula": "1234567890",
  "precioElectrodomestico": 1500.00,
  "numeroCuotas": 2
}
```

#### Respuesta Esperada (400 Bad Request)
```json
{
  "aprobado": false,
  "mensaje": "El número de cuotas debe estar entre 3 y 24"
}
```

---

### CASO 6: Error - Cliente ya tiene crédito activo

Primero otorgue un crédito a un cliente, luego intente otorgar otro:

**Primera solicitud (exitosa):**
```json
{
  "cedula": "5544332211",
  "precioElectrodomestico": 1200.00,
  "numeroCuotas": 10
}
```

**Segunda solicitud (rechazada):**
```json
{
  "cedula": "5544332211",
  "precioElectrodomestico": 800.00,
  "numeroCuotas": 6
}
```

#### Respuesta Esperada
```json
{
  "aprobado": false,
  "mensaje": "El cliente ya tiene un crédito activo en el banco"
}
```

---

## 📋 PRUEBA 5: OBTENER TABLA DE AMORTIZACIÓN

### Endpoint
```
GET /credito/tabla-amortizacion/{numeroCredito}
```

### CASO 1: Tabla existente

**Primero:** Otorgue un crédito y obtenga el número de crédito generado (ejemplo: `CRE1731513600000`)

**Luego:** Consulte la tabla de amortización

#### cURL
```bash
curl http://localhost:8080/banquito-server/api/credito/tabla-amortizacion/CRE1731513600000
```

#### Postman
```
Method: GET
URL: http://localhost:8080/banquito-server/api/credito/tabla-amortizacion/CRE1731513600000
```

#### Respuesta Esperada (200 OK)
```json
{
  "aprobado": true,
  "mensaje": "Tabla de amortización encontrada",
  "numeroCredito": "CRE1731513600000",
  "cedula": "1234567890",
  "nombreCliente": "Juan Carlos Pérez García",
  "montoCredito": 1500.00,
  "numeroCuotas": 12,
  "cuotaMensual": 133.69,
  "tasaInteresAnual": 0.1600,
  "totalAPagar": 1604.28,
  "totalIntereses": 104.28,
  "tablaAmortizacion": [
    ... tabla completa ...
  ]
}
```

---

### CASO 2: Crédito no existe

#### cURL
```bash
curl http://localhost:8080/banquito-server/api/credito/tabla-amortizacion/CRE9999999999
```

#### Respuesta Esperada (404 Not Found)
```json
{
  "aprobado": false,
  "mensaje": "No se encontró un crédito con el número: CRE9999999999"
}
```

---

## 🔄 FLUJO COMPLETO DE PRUEBA

Aquí hay un script completo para probar todo el flujo:

```bash
#!/bin/bash

BASE_URL="http://localhost:8080/banquito-server/api"
CEDULA="1234567890"

echo "========================================="
echo "PRUEBA COMPLETA DEL SISTEMA DE CRÉDITO"
echo "========================================="
echo ""

echo "1. Verificando servidor..."
curl -s $BASE_URL/credito/ping
echo -e "\n"

echo "2. Validando sujeto de crédito..."
curl -s $BASE_URL/credito/validar/$CEDULA | jq '.'
echo -e "\n"

echo "3. Calculando monto máximo..."
curl -s $BASE_URL/credito/monto-maximo/$CEDULA | jq '.'
echo -e "\n"

echo "4. Otorgando crédito..."
RESPONSE=$(curl -s -X POST $BASE_URL/credito/otorgar \
  -H "Content-Type: application/json" \
  -d "{
    \"cedula\": \"$CEDULA\",
    \"precioElectrodomestico\": 1500.00,
    \"numeroCuotas\": 12
  }")

echo "$RESPONSE" | jq '.'

# Extraer número de crédito
NUM_CREDITO=$(echo "$RESPONSE" | jq -r '.numeroCredito')
echo -e "\n"

echo "5. Consultando tabla de amortización..."
if [ "$NUM_CREDITO" != "null" ]; then
  curl -s $BASE_URL/credito/tabla-amortizacion/$NUM_CREDITO | jq '.tablaAmortizacion[0:3]'
else
  echo "No se pudo obtener el número de crédito"
fi

echo -e "\n========================================="
echo "PRUEBA COMPLETADA"
echo "========================================="
```

**Nota:** Este script requiere `jq` para formatear JSON. Instalar con:
```bash
# Ubuntu/Debian
sudo apt-get install jq

# MacOS
brew install jq
```

---

## 📊 VALIDACIÓN DE RESULTADOS

### Verificar en la Base de Datos

Después de otorgar créditos, puede verificar en la base de datos:

```sql
-- Ver créditos otorgados
SELECT 
    c.numero_credito,
    cl.cedula,
    cl.nombres || ' ' || cl.apellidos AS cliente,
    c.monto_credito,
    c.numero_cuotas,
    c.cuota_mensual,
    c.estado,
    c.fecha_otorgamiento
FROM credito c
JOIN cliente cl ON c.cliente_id = cl.id
ORDER BY c.fecha_otorgamiento DESC;

-- Ver tabla de amortización de un crédito
SELECT 
    numero_cuota,
    valor_cuota,
    interes_pagado,
    capital_pagado,
    saldo
FROM tabla_amortizacion
WHERE credito_id = 1  -- ID del crédito
ORDER BY numero_cuota;
```

---

## ⚠️ PROBLEMAS COMUNES

### Error: Connection refused
- Verificar que Payara esté ejecutándose
- Verificar que la aplicación esté desplegada

### Error: 404 Not Found
- Verificar la URL completa
- Verificar el context root en Payara

### Error: 500 Internal Server Error
- Revisar logs de Payara
- Verificar conexión a la base de datos
- Verificar que existan datos de prueba

### Error: CORS
- Si prueba desde un navegador en otra computadora
- Verificar que el filtro CORS esté configurado correctamente

---

## 📞 CONTACTO Y SOPORTE

Para más ayuda, revisar:
- README.md - Documentación general
- CONFIGURACION_SERVIDOR.md - Guía de configuración
- Logs de Payara: `PAYARA_HOME/glassfish/domains/domain1/logs/server.log`

---

¡Pruebas completadas exitosamente! El servidor está listo para ser usado por los clientes.
