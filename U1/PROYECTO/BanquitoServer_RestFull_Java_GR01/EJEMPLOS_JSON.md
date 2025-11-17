# 📝 Ejemplos de Respuestas JSON

Este documento contiene ejemplos de las respuestas JSON que retornan los diferentes endpoints del API.

## 1. Validar Sujeto de Crédito

### Request
```
GET /api/credito/validar/1234567890
```

### Response - Éxito
```json
{
  "esValido": true,
  "mensaje": "El cliente es sujeto de crédito",
  "cedula": "1234567890",
  "nombreCompleto": "Juan Carlos Pérez González"
}
```

### Response - Cliente no existe
```json
{
  "esValido": false,
  "mensaje": "La persona no es cliente del banco",
  "cedula": "9999999999",
  "nombreCompleto": null
}
```

### Response - Sin depósitos recientes
```json
{
  "esValido": false,
  "mensaje": "El cliente no tiene depósitos en el último mes",
  "cedula": "1234567890",
  "nombreCompleto": null
}
```

### Response - Edad insuficiente (casado)
```json
{
  "esValido": false,
  "mensaje": "El cliente casado debe tener al menos 25 años. Edad actual: 23",
  "cedula": "1234567890",
  "nombreCompleto": null
}
```

### Response - Ya tiene crédito activo
```json
{
  "esValido": false,
  "mensaje": "El cliente ya tiene un crédito activo",
  "cedula": "1234567890",
  "nombreCompleto": null
}
```

---

## 2. Obtener Monto Máximo de Crédito

### Request
```
GET /api/credito/monto-maximo/1234567890
```

### Response - Éxito
```json
{
  "cedula": "1234567890",
  "montoMaximo": 5832.00,
  "promedioDepositos": 1900.00,
  "promedioRetiros": 1180.00,
  "mensaje": "Monto máximo calculado exitosamente"
}
```

### Cálculo del Monto Máximo
```
Promedio Depósitos = $1,900.00
Promedio Retiros = $1,180.00
Diferencia = $1,900.00 - $1,180.00 = $720.00
Capacidad = $720.00 × 60% = $432.00
Monto Máximo = $432.00 × 9 = $5,832.00
```

### Response - Cliente no válido
```json
{
  "cedula": "1234567890",
  "montoMaximo": 0.00,
  "promedioDepositos": null,
  "promedioRetiros": null,
  "mensaje": "El cliente ya tiene un crédito activo"
}
```

---

## 3. Otorgar Crédito

### Request
```
POST /api/credito/otorgar
Content-Type: application/json

{
  "cedula": "1234567890",
  "precioElectrodomestico": 3000.00,
  "numeroCuotas": 12
}
```

### Response - Éxito (201 Created)
```json
{
  "exito": true,
  "mensaje": "Crédito otorgado exitosamente",
  "numeroCredito": "CRE1731523456789",
  "cedula": "1234567890",
  "montoCredito": 3000.00,
  "numeroCuotas": 12,
  "cuotaMensual": 268.85,
  "tasaInteres": 0.1600,
  "tablaAmortizacion": [
    {
      "numeroCuota": 1,
      "valorCuota": 268.85,
      "interes": 40.00,
      "capitalPagado": 228.85,
      "saldo": 2771.15
    },
    {
      "numeroCuota": 2,
      "valorCuota": 268.85,
      "interes": 36.95,
      "capitalPagado": 231.90,
      "saldo": 2539.25
    },
    {
      "numeroCuota": 3,
      "valorCuota": 268.85,
      "interes": 33.86,
      "capitalPagado": 234.99,
      "saldo": 2304.26
    },
    {
      "numeroCuota": 4,
      "valorCuota": 268.85,
      "interes": 30.72,
      "capitalPagado": 238.13,
      "saldo": 2066.13
    },
    {
      "numeroCuota": 5,
      "valorCuota": 268.85,
      "interes": 27.55,
      "capitalPagado": 241.30,
      "saldo": 1824.83
    },
    {
      "numeroCuota": 6,
      "valorCuota": 268.85,
      "interes": 24.33,
      "capitalPagado": 244.52,
      "saldo": 1580.31
    },
    {
      "numeroCuota": 7,
      "valorCuota": 268.85,
      "interes": 21.07,
      "capitalPagado": 247.78,
      "saldo": 1332.53
    },
    {
      "numeroCuota": 8,
      "valorCuota": 268.85,
      "interes": 17.77,
      "capitalPagado": 251.08,
      "saldo": 1081.45
    },
    {
      "numeroCuota": 9,
      "valorCuota": 268.85,
      "interes": 14.42,
      "capitalPagado": 254.43,
      "saldo": 827.02
    },
    {
      "numeroCuota": 10,
      "valorCuota": 268.85,
      "interes": 11.03,
      "capitalPagado": 257.82,
      "saldo": 569.20
    },
    {
      "numeroCuota": 11,
      "valorCuota": 268.85,
      "interes": 7.59,
      "capitalPagado": 261.26,
      "saldo": 307.94
    },
    {
      "numeroCuota": 12,
      "valorCuota": 312.05,
      "interes": 4.11,
      "capitalPagado": 307.94,
      "saldo": 0.00
    }
  ]
}
```

### Detalles de la Tabla de Amortización
```
Monto del Crédito: $3,000.00
Tasa de Interés Anual: 16%
Tasa Mensual: 16% / 12 = 1.333%
Número de Cuotas: 12
Cuota Mensual: $268.85

Cuota #1:
- Interés: $3,000.00 × 1.333% = $40.00
- Capital Pagado: $268.85 - $40.00 = $228.85
- Saldo: $3,000.00 - $228.85 = $2,771.15

... y así sucesivamente
```

### Response - Error: Plazo inválido (400 Bad Request)
```json
{
  "exito": false,
  "mensaje": "El número de cuotas debe estar entre 3 y 24",
  "numeroCredito": null,
  "cedula": "1234567890",
  "montoCredito": null,
  "numeroCuotas": null,
  "cuotaMensual": null,
  "tasaInteres": null,
  "tablaAmortizacion": []
}
```

### Response - Error: Monto excede el máximo (400 Bad Request)
```json
{
  "exito": false,
  "mensaje": "El monto del crédito ($50000.0) supera el monto máximo autorizado ($5832.0)",
  "numeroCredito": null,
  "cedula": "1234567890",
  "montoCredito": null,
  "numeroCuotas": null,
  "cuotaMensual": null,
  "tasaInteres": null,
  "tablaAmortizacion": []
}
```

### Response - Error: Cliente no válido (400 Bad Request)
```json
{
  "exito": false,
  "mensaje": "El cliente ya tiene un crédito activo",
  "numeroCredito": null,
  "cedula": "1234567890",
  "montoCredito": null,
  "numeroCuotas": null,
  "cuotaMensual": null,
  "tasaInteres": null,
  "tablaAmortizacion": []
}
```

---

## 4. Obtener Tabla de Amortización

### Request
```
GET /api/credito/tabla-amortizacion/CRE1731523456789
```

### Response - Éxito (200 OK)
```json
[
  {
    "numeroCuota": 1,
    "valorCuota": 268.85,
    "interes": 40.00,
    "capitalPagado": 228.85,
    "saldo": 2771.15
  },
  {
    "numeroCuota": 2,
    "valorCuota": 268.85,
    "interes": 36.95,
    "capitalPagado": 231.90,
    "saldo": 2539.25
  },
  {
    "numeroCuota": 3,
    "valorCuota": 268.85,
    "interes": 33.86,
    "capitalPagado": 234.99,
    "saldo": 2304.26
  },
  {
    "numeroCuota": 4,
    "valorCuota": 268.85,
    "interes": 30.72,
    "capitalPagado": 238.13,
    "saldo": 2066.13
  },
  {
    "numeroCuota": 5,
    "valorCuota": 268.85,
    "interes": 27.55,
    "capitalPagado": 241.30,
    "saldo": 1824.83
  },
  {
    "numeroCuota": 6,
    "valorCuota": 268.85,
    "interes": 24.33,
    "capitalPagado": 244.52,
    "saldo": 1580.31
  },
  {
    "numeroCuota": 7,
    "valorCuota": 268.85,
    "interes": 21.07,
    "capitalPagado": 247.78,
    "saldo": 1332.53
  },
  {
    "numeroCuota": 8,
    "valorCuota": 268.85,
    "interes": 17.77,
    "capitalPagado": 251.08,
    "saldo": 1081.45
  },
  {
    "numeroCuota": 9,
    "valorCuota": 268.85,
    "interes": 14.42,
    "capitalPagado": 254.43,
    "saldo": 827.02
  },
  {
    "numeroCuota": 10,
    "valorCuota": 268.85,
    "interes": 11.03,
    "capitalPagado": 257.82,
    "saldo": 569.20
  },
  {
    "numeroCuota": 11,
    "valorCuota": 268.85,
    "interes": 7.59,
    "capitalPagado": 261.26,
    "saldo": 307.94
  },
  {
    "numeroCuota": 12,
    "valorCuota": 312.05,
    "interes": 4.11,
    "capitalPagado": 307.94,
    "saldo": 0.00
  }
]
```

### Response - Error: Crédito no encontrado (404 Not Found)
```json
{
  "exito": false,
  "mensaje": "Crédito no encontrado",
  "numeroCredito": null,
  "cedula": null,
  "montoCredito": null,
  "numeroCuotas": null,
  "cuotaMensual": null,
  "tasaInteres": null,
  "tablaAmortizacion": []
}
```

---

## 5. Ping (Health Check)

### Request
```
GET /api/credito/ping
```

### Response - Éxito (200 OK)
```
Servicio de Crédito BanQuito está activo
```

---

## Códigos de Estado HTTP

| Código | Descripción | Cuándo se usa |
|--------|-------------|---------------|
| 200 OK | Operación exitosa | GET exitoso |
| 201 Created | Recurso creado | POST exitoso (otorgar crédito) |
| 400 Bad Request | Datos inválidos | Validaciones fallidas |
| 404 Not Found | Recurso no encontrado | Crédito no existe |
| 500 Internal Server Error | Error del servidor | Error inesperado |

---

## Pruebas con curl

### Validar Sujeto de Crédito
```bash
curl http://localhost:8080/banquito-server/api/credito/validar/1234567890
```

### Obtener Monto Máximo
```bash
curl http://localhost:8080/banquito-server/api/credito/monto-maximo/1234567890
```

### Otorgar Crédito
```bash
curl -X POST http://localhost:8080/banquito-server/api/credito/otorgar \
  -H "Content-Type: application/json" \
  -d '{
    "cedula": "1234567890",
    "precioElectrodomestico": 3000.00,
    "numeroCuotas": 12
  }'
```

### Obtener Tabla de Amortización
```bash
curl http://localhost:8080/banquito-server/api/credito/tabla-amortizacion/CRE1731523456789
```

---

## Formato JSON Pretty Print

Para hacer más legibles las respuestas en curl, agregar al final:
```bash
| python -m json.tool
```

Ejemplo:
```bash
curl http://localhost:8080/banquito-server/api/credito/validar/1234567890 | python -m json.tool
```
