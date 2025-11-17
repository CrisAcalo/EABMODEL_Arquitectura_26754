# 📋 Ejemplos JSON para Peticiones REST

## Productos

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

## Facturas

### Crear Factura - Efectivo (POST /api/facturas)

```json
{
  "cedulaCliente": "1234567890",
  "nombreCliente": "Juan Carlos Pérez",
  "formaPago": "EFECTIVO",
  "descuento": 50.00,
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

### Crear Factura - Efectivo Sin Descuento

```json
{
  "cedulaCliente": "1234567890",
  "nombreCliente": "María González",
  "formaPago": "EFECTIVO",
  "descuento": 0,
  "detalles": [
    {
      "productoId": 3,
      "cantidad": 1
    }
  ]
}
```

### Crear Factura - Crédito (POST /api/facturas)

```json
{
  "cedulaCliente": "0987654321",
  "nombreCliente": "Pedro Ramírez",
  "formaPago": "CREDITO",
  "numeroCredito": "CRE-000001",
  "descuento": 0,
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

### Crear Factura - Múltiples Productos

```json
{
  "cedulaCliente": "1122334455",
  "nombreCliente": "Ana López",
  "formaPago": "CREDITO",
  "numeroCredito": "CRE-000002",
  "descuento": 100.00,
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

## Respuestas Esperadas

### Respuesta Exitosa - Crear Producto

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

### Respuesta Exitosa - Crear Factura

```json
{
  "exito": true,
  "mensaje": "Factura creada exitosamente",
  "datos": {
    "facturaId": 3,
    "numeroFactura": "FAC-000003",
    "cedulaCliente": "1234567890",
    "nombreCliente": "Juan Carlos Pérez",
    "formaPago": "EFECTIVO",
    "subtotal": 1699.97,
    "descuento": 50.00,
    "total": 1649.97,
    "numeroCredito": null,
    "fechaEmision": "2025-01-17T10:35:00",
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

### Respuesta Error - Producto Duplicado

```json
{
  "exito": false,
  "mensaje": "Ya existe un producto con el código: LAVA-002"
}
```

### Respuesta Error - Stock Insuficiente

```json
{
  "exito": false,
  "mensaje": "Stock insuficiente para producto: Refrigeradora LG 20 pies"
}
```

### Respuesta Error - Producto No Encontrado

```json
{
  "exito": false,
  "mensaje": "Producto no encontrado con ID: 999"
}
```

## Comandos curl Completos

### Crear Producto

```bash
curl -X POST http://localhost:8080/comercializadora-server/api/productos \
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
curl -X PUT http://localhost:8080/comercializadora-server/api/productos/11 \
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

### Crear Factura (Efectivo)

```bash
curl -X POST http://localhost:8080/comercializadora-server/api/facturas \
  -H "Content-Type: application/json" \
  -d '{
    "cedulaCliente": "1234567890",
    "nombreCliente": "Juan Carlos Pérez",
    "formaPago": "EFECTIVO",
    "descuento": 50.00,
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

### Crear Factura (Crédito)

```bash
curl -X POST http://localhost:8080/comercializadora-server/api/facturas \
  -H "Content-Type: application/json" \
  -d '{
    "cedulaCliente": "0987654321",
    "nombreCliente": "Pedro Ramírez",
    "formaPago": "CREDITO",
    "numeroCredito": "CRE-000001",
    "descuento": 0,
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
curl http://localhost:8080/comercializadora-server/api/productos
```

### Obtener Producto por ID

```bash
curl http://localhost:8080/comercializadora-server/api/productos/1
```

### Buscar Productos por Nombre

```bash
curl "http://localhost:8080/comercializadora-server/api/productos/buscar?nombre=Samsung"
```

### Obtener Facturas por Cliente

```bash
curl http://localhost:8080/comercializadora-server/api/facturas/cliente/1234567890
```

### Obtener Facturas a Crédito

```bash
curl http://localhost:8080/comercializadora-server/api/facturas/credito
```
