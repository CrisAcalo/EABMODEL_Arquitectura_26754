# 📋 Ejemplos JSON para Peticiones REST - Comercializadora

**Base URL:** `http://localhost:8081/comercializadora-server/api`

> **IMPORTANTE:** Este sistema es independiente de BanQuito. Para pagos a CRÉDITO,
> el cliente debe obtener el `numeroCredito` directamente desde BanQuito antes de
> crear la factura en Comercializadora.

---

## 🛒 Productos

### Crear Producto (POST /api/productos)

```json
{
  "codigo": "LAVA-002",
  "nombre": "Lavadora Whirlpool 20kg",
  "descripcion": "Lavadora de carga superior con 15 programas de lavado",
  "precio": 949.99,
  "stock": 15,
  "categoria": "LAVADO",
  "estado": "ACTIVO"
}
```

### Crear Producto - Mínimo

```json
{
  "codigo": "MICRO-002",
  "nombre": "Microondas LG",
  "precio": 179.99,
  "stock": 20,
  "estado": "ACTIVO"
}
```

### Actualizar Producto (PUT /api/productos/{id})

```json
{
  "codigo": "LAVA-002",
  "nombre": "Lavadora Whirlpool 20kg - OFERTA",
  "descripcion": "Lavadora de carga superior con 15 programas de lavado",
  "precio": 849.99,
  "stock": 15,
  "categoria": "LAVADO",
  "estado": "ACTIVO"
}
```

### Actualizar Stock (PATCH /api/productos/{id}/stock)

```json
{
  "stock": 100
}
```

---

## 🧾 Facturas

### ✅ Crear Factura - EFECTIVO (POST /api/facturas)

**Características:**
- ✅ El descuento se calcula **automáticamente** (33% sobre el subtotal)
- ❌ **NO** envíes el campo `descuento` (será ignorado)
- ❌ **NO** envíes el campo `numeroCredito`

```json
{
  "cedulaCliente": "1234567890",
  "nombreCliente": "Juan Carlos Pérez",
  "formaPago": "EFECTIVO",
  "detalles": [
    {
      "productoId": 1,
      "cantidad": 1
    },
    {
      "productoId": 4,
      "cantidad": 2
    }
  ]
}
```

**Ejemplo con un solo producto:**

```json
{
  "cedulaCliente": "0912345678",
  "nombreCliente": "María González",
  "formaPago": "EFECTIVO",
  "detalles": [
    {
      "productoId": 3,
      "cantidad": 1
    }
  ]
}
```

---

### 💳 Crear Factura - CREDITO (POST /api/facturas)

**Características:**
- ✅ **Sin descuento** (0%)
- ✅ **REQUIERE** `numeroCredito` obtenido previamente desde BanQuito
- ❌ Si no proporcionas `numeroCredito`, la factura será **rechazada**

**FLUJO CORRECTO:**
```
1. Cliente → BanQuito (obtener numeroCredito)
2. BanQuito → Cliente (retorna numeroCredito: "CRE-000001")
3. Cliente → Comercializadora (crear factura con numeroCredito)
```

```json
{
  "cedulaCliente": "0987654321",
  "nombreCliente": "Pedro Ramírez",
  "formaPago": "CREDITO",
  "numeroCredito": "CRE-000001",
  "detalles": [
    {
      "productoId": 1,
      "cantidad": 1
    },
    {
      "productoId": 2,
      "cantidad": 1
    }
  ]
}
```

**Ejemplo con múltiples productos:**

```json
{
  "cedulaCliente": "1122334455",
  "nombreCliente": "Ana López",
  "formaPago": "CREDITO",
  "numeroCredito": "CRE-000002",
  "detalles": [
    {
      "productoId": 5,
      "cantidad": 1
    },
    {
      "productoId": 6,
      "cantidad": 2
    },
    {
      "productoId": 7,
      "cantidad": 1
    }
  ]
}
```

---

## 📊 Respuestas Esperadas

### ✅ Respuesta Exitosa - Crear Producto

```json
{
  "exito": true,
  "mensaje": "Producto creado exitosamente",
  "datos": {
    "productoId": 11,
    "codigo": "LAVA-002",
    "nombre": "Lavadora Whirlpool 20kg",
    "descripcion": "Lavadora de carga superior con 15 programas de lavado",
    "precio": 949.99,
    "stock": 15,
    "categoria": "LAVADO",
    "imagenUrl": null,
    "fechaRegistro": "2025-01-17T10:30:00",
    "estado": "ACTIVO"
  }
}
```

### ✅ Respuesta Exitosa - Crear Factura EFECTIVO (con descuento 33%)

```json
{
  "exito": true,
  "mensaje": "Factura creada exitosamente",
  "datos": {
    "facturaId": 3,
    "numeroFactura": "FAC-20251117-001",
    "cedulaCliente": "1234567890",
    "nombreCliente": "Juan Carlos Pérez",
    "formaPago": "EFECTIVO",
    "subtotal": 1699.97,
    "descuento": 560.99,
    "total": 1138.98,
    "numeroCredito": null,
    "fechaEmision": "2025-11-17T10:35:00",
    "detalles": [
      {
        "detalleId": 5,
        "productoId": 1,
        "codigoProducto": "REFRI-001",
        "nombreProducto": "Refrigeradora LG 20 pies",
        "cantidad": 1,
        "precioUnitario": 1299.99,
        "subtotal": 1299.99
      },
      {
        "detalleId": 6,
        "productoId": 4,
        "codigoProducto": "MICRO-001",
        "nombreProducto": "Microondas Panasonic 1.2 cu.ft",
        "cantidad": 2,
        "precioUnitario": 199.99,
        "subtotal": 399.98
      }
    ]
  }
}
```

**Observaciones:**
- ✅ `descuento`: 560.99 (33% de 1699.97)
- ✅ `total`: 1138.98 (1699.97 - 560.99)
- ✅ `numeroCredito`: null (porque es EFECTIVO)
- ✅ `numeroFactura`: FAC-20251117-001 (formato: FAC-YYYYMMDD-XXX)

### ✅ Respuesta Exitosa - Crear Factura CREDITO (sin descuento)

```json
{
  "exito": true,
  "mensaje": "Factura creada exitosamente",
  "datos": {
    "facturaId": 4,
    "numeroFactura": "FAC-20251117-002",
    "cedulaCliente": "0987654321",
    "nombreCliente": "Pedro Ramírez",
    "formaPago": "CREDITO",
    "subtotal": 2599.98,
    "descuento": 0,
    "total": 2599.98,
    "numeroCredito": "CRE-000001",
    "fechaEmision": "2025-11-17T10:40:00",
    "detalles": [
      {
        "detalleId": 7,
        "productoId": 1,
        "codigoProducto": "REFRI-001",
        "nombreProducto": "Refrigeradora LG 20 pies",
        "cantidad": 1,
        "precioUnitario": 1299.99,
        "subtotal": 1299.99
      },
      {
        "detalleId": 8,
        "productoId": 2,
        "codigoProducto": "REFRI-002",
        "nombreProducto": "Refrigeradora Samsung 18 pies",
        "cantidad": 1,
        "precioUnitario": 1299.99,
        "subtotal": 1299.99
      }
    ]
  }
}
```

**Observaciones:**
- ✅ `descuento`: 0 (sin descuento para CREDITO)
- ✅ `total`: igual al subtotal
- ✅ `numeroCredito`: "CRE-000001" (obtenido previamente de BanQuito)

---

## ❌ Respuestas de Error

### Error - Producto Duplicado

```json
{
  "exito": false,
  "mensaje": "Ya existe un producto con el código: LAVA-002"
}
```

### Error - Stock Insuficiente

```json
{
  "exito": false,
  "mensaje": "Stock insuficiente para producto: Refrigeradora LG 20 pies"
}
```

### Error - Producto No Encontrado

```json
{
  "exito": false,
  "mensaje": "Producto no encontrado: 999"
}
```

### ❌ Error - Falta numeroCredito para CREDITO

```json
{
  "exito": false,
  "mensaje": "El número de crédito es requerido para pagos a CREDITO. Debe obtenerlo desde el servicio de BanQuito antes de crear la factura."
}
```

**Request que causó el error:**
```json
{
  "cedulaCliente": "0987654321",
  "nombreCliente": "Pedro Ramírez",
  "formaPago": "CREDITO",
  "detalles": [
    {
      "productoId": 1,
      "cantidad": 1
    }
  ]
}
```

---

## 🖥️ Comandos curl Completos

### Crear Producto

```bash
curl -X POST http://localhost:8081/comercializadora-server/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "LAVA-002",
    "nombre": "Lavadora Whirlpool 20kg",
    "descripcion": "Lavadora de carga superior con 15 programas de lavado",
    "precio": 949.99,
    "stock": 15,
    "categoria": "LAVADO",
    "estado": "ACTIVO"
  }'
```

### Actualizar Producto

```bash
curl -X PUT http://localhost:8081/comercializadora-server/api/productos/11 \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "LAVA-002",
    "nombre": "Lavadora Whirlpool 20kg - OFERTA",
    "precio": 849.99,
    "stock": 15,
    "categoria": "LAVADO",
    "estado": "ACTIVO"
  }'
```

### Crear Factura - EFECTIVO (descuento automático 33%)

```bash
curl -X POST http://localhost:8081/comercializadora-server/api/facturas \
  -H "Content-Type: application/json" \
  -d '{
    "cedulaCliente": "1234567890",
    "nombreCliente": "Juan Carlos Pérez",
    "formaPago": "EFECTIVO",
    "detalles": [
      {
        "productoId": 1,
        "cantidad": 1
      },
      {
        "productoId": 4,
        "cantidad": 2
      }
    ]
  }'
```

### Crear Factura - CREDITO (requiere numeroCredito)

```bash
curl -X POST http://localhost:8081/comercializadora-server/api/facturas \
  -H "Content-Type: application/json" \
  -d '{
    "cedulaCliente": "0987654321",
    "nombreCliente": "Pedro Ramírez",
    "formaPago": "CREDITO",
    "numeroCredito": "CRE-000001",
    "detalles": [
      {
        "productoId": 1,
        "cantidad": 1
      }
    ]
  }'
```

### Obtener Todos los Productos

```bash
curl http://localhost:8081/comercializadora-server/api/productos
```

### Obtener Productos Activos

```bash
curl http://localhost:8081/comercializadora-server/api/productos/activos
```

### Obtener Producto por ID

```bash
curl http://localhost:8081/comercializadora-server/api/productos/1
```

### Obtener Producto por Código

```bash
curl http://localhost:8081/comercializadora-server/api/productos/codigo/REFRI-001
```

### Buscar Productos por Nombre

```bash
curl "http://localhost:8081/comercializadora-server/api/productos/buscar?nombre=Samsung"
```

### Buscar Productos por Categoría

```bash
curl http://localhost:8081/comercializadora-server/api/productos/categoria/REFRIGERACION
```

### Obtener Todas las Facturas

```bash
curl http://localhost:8081/comercializadora-server/api/facturas
```

### Obtener Factura por ID

```bash
curl http://localhost:8081/comercializadora-server/api/facturas/1
```

### Obtener Factura por Número

```bash
curl http://localhost:8081/comercializadora-server/api/facturas/numero/FAC-20251117-001
```

### Obtener Facturas por Cliente

```bash
curl http://localhost:8081/comercializadora-server/api/facturas/cliente/1234567890
```

### Obtener Todas las Facturas a Crédito

```bash
curl http://localhost:8081/comercializadora-server/api/facturas/credito
```

### Obtener Factura por Número de Crédito

```bash
curl http://localhost:8081/comercializadora-server/api/facturas/credito/CRE-000001
```

### Eliminar Producto (Soft Delete)

```bash
curl -X DELETE http://localhost:8081/comercializadora-server/api/productos/11
```

### Actualizar Solo el Stock

```bash
curl -X PATCH http://localhost:8081/comercializadora-server/api/productos/1/stock \
  -H "Content-Type: application/json" \
  -d '{
    "stock": 50
  }'
```

### Ping - Verificar Servicio Productos

```bash
curl http://localhost:8081/comercializadora-server/api/productos/ping
```

### Ping - Verificar Servicio Facturas

```bash
curl http://localhost:8081/comercializadora-server/api/facturas/ping
```

---

## 📋 Casos de Prueba Recomendados

### Test 1: Factura EFECTIVO - Verificar descuento 33%

**Request:**
```json
{
  "cedulaCliente": "1234567890",
  "nombreCliente": "Test Usuario",
  "formaPago": "EFECTIVO",
  "detalles": [
    {
      "productoId": 1,
      "cantidad": 1
    }
  ]
}
```

**Verificaciones:**
- ✅ Subtotal = Precio del producto
- ✅ Descuento = Subtotal * 0.33
- ✅ Total = Subtotal - Descuento
- ✅ numeroCredito = null

### Test 2: Factura CREDITO - Verificar sin descuento

**Request:**
```json
{
  "cedulaCliente": "0987654321",
  "nombreCliente": "Test Usuario",
  "formaPago": "CREDITO",
  "numeroCredito": "CRE-TEST-001",
  "detalles": [
    {
      "productoId": 1,
      "cantidad": 1
    }
  ]
}
```

**Verificaciones:**
- ✅ Subtotal = Precio del producto
- ✅ Descuento = 0
- ✅ Total = Subtotal
- ✅ numeroCredito = "CRE-TEST-001"

### Test 3: Factura CREDITO sin numeroCredito - Debe fallar

**Request:**
```json
{
  "cedulaCliente": "0987654321",
  "nombreCliente": "Test Usuario",
  "formaPago": "CREDITO",
  "detalles": [
    {
      "productoId": 1,
      "cantidad": 1
    }
  ]
}
```

**Resultado esperado:**
- ❌ Error 400 Bad Request
- ❌ Mensaje: "El número de crédito es requerido para pagos a CREDITO..."

### Test 4: Verificar formato de número de factura

**Verificaciones:**
- ✅ Formato: FAC-YYYYMMDD-XXX
- ✅ Ejemplo: FAC-20251117-001
- ✅ Consecutivo reinicia cada día

---

## 🔧 Diferencias con la Versión Anterior

### ❌ Eliminado:
- Campo `descuento` en request de facturas (ahora se calcula automáticamente)
- Integración directa con BanQuito (ahora es independiente)
- Endpoint `/api/facturas/test-banquito`
- Tabla de amortización en respuestas

### ✅ Agregado:
- Cálculo automático de descuento 33% para EFECTIVO
- Validación obligatoria de `numeroCredito` para CREDITO
- Formato de número de factura: FAC-YYYYMMDD-XXX
- Sistemas completamente independientes

---

## 📝 Notas Importantes

1. **Puerto:** El servicio corre en el puerto **8081** (no 8080)
2. **BanQuito:** Corre en el puerto **8080**
3. **Descuento EFECTIVO:** Se calcula automáticamente (33%)
4. **Descuento CREDITO:** Siempre 0%
5. **numeroCredito:** Obligatorio para CREDITO, debe obtenerse de BanQuito primero
6. **Formato de factura:** FAC-YYYYMMDD-XXX (incluye fecha)
