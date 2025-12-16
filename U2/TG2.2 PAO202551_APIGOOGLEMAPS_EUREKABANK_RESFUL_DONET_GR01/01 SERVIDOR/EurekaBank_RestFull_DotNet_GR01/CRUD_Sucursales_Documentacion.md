# CRUD Completo de Sucursales - EurekaBank (ACTUALIZADO)

## Resumen de implementación

Se ha **actualizado completamente** el CRUD para la entidad **Sucursal** para adaptarse a los nuevos cambios en la estructura de la base de datos:

### ? **Cambios implementados:**
- **Código auto-incremental**: `chr_sucucodigo` ahora es `INT IDENTITY(1,1)` 
- **Dirección opcional**: Campo `vch_sucudireccion` es ahora opcional (NULL permitido)
- **Nuevos límites de campos**: Ajustados según el nuevo esquema de BD
- **Referencias actualizadas**: Tablas `Asignado` y `Cuenta` ahora referencian el nuevo tipo de código

## ?? Archivos creados/modificados

### 1. **Modelo actualizado**
- `EurekaBank_RestFull_DotNet_GR01/models/Sucursal.cs`
  - ? Agregadas propiedades `Latitud` y `Longitud`
  - ? Validaciones con Data Annotations
  - ? Propiedad calculada `TieneCoordenadas`

### 2. **Validador de negocio**
- `EurekaBank_RestFull_DotNet_GR01/validators/SucursalValidator.cs`
  - ? Validaciones completas para todos los campos
  - ? Validaciones específicas para coordenadas
  - ? Validaciones para creación y actualización
  - ? Reglas de negocio (ej: no eliminar si tiene cuentas)

### 3. **DTOs específicos**
- `EurekaBank_RestFull_DotNet_GR01/models/DTOs/SucursalDTOs.cs`
  - ? `CrearSucursalDTO` - Para crear sucursales
  - ? `ActualizarSucursalDTO` - Para actualizar sucursales
  - ? `CoordenadasDTO` - Para actualizar coordenadas
  - ? `SucursalDetalleDTO` - Respuesta completa
  - ? `SucursalResumenDTO` - Respuesta resumida

### 4. **Controlador mejorado**
- `EurekaBank_RestFull_DotNet_GR01/Controllers/SucursalController.cs`
  - ? Validaciones de ModelState
  - ? Uso de DTOs específicos
  - ? Validaciones de negocio con SucursalValidator
  - ? Manejo de errores completo
  - ? Respuestas estructuradas

### 5. **Servicio de lógica de negocio**
- `EurekaBank_RestFull_DotNet_GR01/services/SucursalService.cs`
  - ? Separación de responsabilidades
  - ? Lógica de negocio centralizada
  - ? Manejo de errores robusto

## ??? Operaciones CRUD implementadas

### **CREATE** - Crear Sucursal
- **Endpoint**: `POST /api/Sucursal`
- **Body**: `CrearSucursalDTO`
- **Validaciones**:
  - **Código auto-generado** (no se requiere en el DTO)
  - Nombre obligatorio (3-50 caracteres)
  - Ciudad obligatoria (2-30 caracteres)
  - Dirección **opcional** (máx 50 caracteres)
  - Coordenadas opcionales pero válidas si se proporcionan
  - **Sin duplicados**: El código se genera automáticamente

### **READ** - Leer Sucursales
- **Endpoint**: `GET /api/Sucursal` - Lista todas
- **Endpoint**: `GET /api/Sucursal/{codigo:int}` - Una específica (código numérico)
- **Endpoint**: `GET /api/Sucursal/ciudad/{ciudad}` - Por ciudad
- **Endpoint**: `GET /api/Sucursal/con-coordenadas` - Con geolocalización
- **Respuestas**: DTOs apropiados según el contexto

### **UPDATE** - Actualizar Sucursal (Parcial)
- **Endpoint**: `PATCH /api/Sucursal/{codigo:int}` - Código numérico
- **Body**: `ActualizarSucursalDTO` (todos los campos son opcionales)
- **Características**:
  - **Actualización parcial**: Solo se actualizan los campos proporcionados
  - **Flexible**: Puedes actualizar nombre, ciudad, dirección y/o coordenadas
  - **Validaciones**: Solo se validan los campos proporcionados

### **DELETE** - Eliminar Sucursal
- **Endpoint**: `DELETE /api/Sucursal/{codigo:int}` - Código numérico
- **Validaciones**:
  - Verificación de existencia
  - **Regla de negocio**: No se puede eliminar si tiene cuentas asociadas

## ?? Validaciones implementadas

### **Validaciones de entrada**
- ? Data Annotations en modelos y DTOs
- ? ModelState validation en controlador
- ? Validaciones personalizadas en SucursalValidator

### **Validaciones de negocio**
- ? **Código auto-incremental** (generado automáticamente por la BD)
- ? Longitudes de campos apropiadas (nombre: 50, ciudad: 30, dirección: 50)
- ? Rangos válidos para coordenadas (-90/90, -180/180)
- ? Consistencia de coordenadas (ambas nulas o ambas con valor)
- ? **Dirección opcional** (puede ser NULL)
- ? Protección contra eliminación si hay cuentas asociadas

### **Validaciones de datos**
- ? Campos requeridos vs opcionales
- ? Limpieza de datos (Trim, ToUpper en códigos)
- ? Verificación de existencia antes de operaciones

## ?? Códigos de error estandarizados

| Código | Descripción |
|--------|-------------|
| VAL001 | Errores de validación de entrada |
| VAL002 | Datos requeridos faltantes |
| VAL003 | Errores de validación de negocio |
| SUC001 | Sucursal no encontrada |
| SUC002 | Error al actualizar coordenadas |
| SUC003 | Sucursal ya existe (duplicado) |
| SUC004 | Error al crear sucursal |
| SUC005 | Error al actualizar sucursal |
| SUC006 | Error al eliminar sucursal |
| SUC007 | No se puede eliminar (tiene cuentas) |
| SRV001 | Error interno del servidor |

## ?? Mejores prácticas aplicadas

### **Arquitectura**
- ? Separación de responsabilidades (Controller ? Service ? DAO)
- ? DTOs específicos para cada operación
- ? Validadores de negocio separados
- ? Manejo centralizado de errores

### **Seguridad**
- ? Validación de entrada en múltiples capas
- ? Sanitización de datos (Trim, ToUpper)
- ? Prevención de operaciones peligrosas

### **Usabilidad**
- ? Mensajes de error claros y específicos
- ? Códigos de error consistentes
- ? Respuestas estructuradas con RespuestaDTO

### **Mantenibilidad**
- ? Código bien documentado
- ? Validaciones reutilizables
- ? Estructura consistente entre operaciones

## ?? Ejemplos de uso

### Crear sucursal
```json
POST /api/Sucursal
{
  "nombre": "Sucursal Lima Centro",
  "ciudad": "Lima",
  "direccion": "Av. Javier Prado 123",
  "latitud": -12.0464,
  "longitud": -77.0428
}
```

**Respuesta:**
```json
{
  "exitoso": true,
  "mensaje": "Sucursal creada correctamente",
  "datos": {
    "codigo": 1,  // Auto-generado
    "nombre": "Sucursal Lima Centro",
    "ciudad": "Lima",
    "direccion": "Av. Javier Prado 123",
    "contadorCuentas": 0,
    "latitud": -12.0464,
    "longitud": -77.0428,
    "tieneCoordenadas": true
  }
}
```

### Actualizar sucursal completa
```json
PATCH /api/Sucursal/1
{
  "nombre": "Sucursal Lima Centro - Renovada",
  "ciudad": "Lima",
  "direccion": "Av. Javier Prado 125",
  "latitud": -12.0464,
  "longitud": -77.0428
}
```

### Actualizar solo el nombre
```json
PATCH /api/Sucursal/1
{
  "nombre": "Nuevo Nombre de Sucursal"
}
```

### Actualizar solo coordenadas
```json
PATCH /api/Sucursal/1
{
  "latitud": -12.0500,
  "longitud": -77.0400
}
```

### Actualizar solo dirección
```json
PATCH /api/Sucursal/1
{
  "direccion": "Nueva dirección 456"
}
```

## ? Estado del proyecto

- ? **Compilación exitosa**
- ? **Todas las validaciones implementadas**
- ? **CRUD completo funcional**
- ? **Arquitectura robusta y escalable**
- ? **Documentación actualizada**
- ? **Adaptado a nuevo esquema de BD**

## ?? **Cambios principales realizados**

### **1. Estructura de Base de Datos**
```sql
-- ANTES
chr_sucucodigo CHAR(5) PRIMARY KEY

-- DESPUÉS  
chr_sucucodigo INT IDENTITY(1,1) PRIMARY KEY
```

### **2. Modelo Sucursal**
- **Código**: Cambiado de `string` a `int` (auto-incremental)
- **Dirección**: Ahora es opcional (puede ser NULL)
- **Ciudad**: Límite actualizado a 30 caracteres
- **Dirección**: Límite actualizado a 50 caracteres

### **3. DTOs actualizados**
- **CrearSucursalDTO**: Sin campo `Codigo` (se auto-genera)
- **SucursalDetalleDTO**: Código como `int`
- **SucursalResumenDTO**: Código como `int`

### **4. Controlador actualizado**
- **GET simplificado**: Solo 2 endpoints (todas las sucursales y una específica)
- **PATCH unificado**: Una sola operación para actualización parcial
- **Eliminado PUT**: Tanto para datos generales como para coordenadas
- **Validaciones mejoradas**: Para coordenadas y campos opcionales
- Rutas con restricción `{codigo:int}`
- Validación de código > 0
- Lógica de creación adaptada para auto-incremento

### **5. Validador actualizado**
- Validaciones adaptadas para códigos enteros
- Dirección como campo opcional
- Nuevos límites de caracteres
- Soporte para validación parcial

## ? **Estado final del controlador**

### **?? Operaciones disponibles:**

1. **`GET /api/Sucursal`** - Obtiene todas las sucursales
2. **`GET /api/Sucursal/{codigo:int}`** - Obtiene una sucursal específica
3. **`POST /api/Sucursal`** - Crea una nueva sucursal
4. **`PATCH /api/Sucursal/{codigo:int}`** - Actualiza parcialmente una sucursal
5. **`DELETE /api/Sucursal/{codigo:int}`** - Elimina una sucursal

### **??? Operaciones eliminadas:**
- ? `PUT /api/Sucursal/{codigo:int}` (reemplazado por PATCH)
- ? `PUT /api/Sucursal/{codigo:int}/coordenadas` (integrado en PATCH)
- ? `GET /api/Sucursal/ciudad/{ciudad}` (simplificado)
- ? `GET /api/Sucursal/con-coordenadas` (simplificado)

### **?? Validaciones del PATCH:**
- ? Al menos un campo debe ser proporcionado
- ? Validación de rangos para coordenadas (-90/90, -180/180)
- ? Consistencia de coordenadas (ambas nulas o ambas con valor)
- ? Validación de longitudes de campos
- ? Verificación de existencia de sucursal

El CRUD de sucursales está **completamente optimizado** y adaptado al nuevo esquema de base de datos, con una API más limpia y semánticamente correcta, listo para uso en producción.