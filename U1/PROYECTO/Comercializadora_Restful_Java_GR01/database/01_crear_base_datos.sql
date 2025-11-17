-- =====================================================
-- Script de Creación de Base de Datos
-- Sistema: Comercializadora RESTful
-- Base de Datos: MySQL 8.0+
-- =====================================================

-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS comercializadora_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE comercializadora_db;

-- =====================================================
-- Tabla: Producto
-- Descripción: Almacena información de productos/electrodomésticos
-- =====================================================
CREATE TABLE IF NOT EXISTS Producto (
    producto_id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    precio DECIMAL(12,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    categoria VARCHAR(50),
    imagen_url VARCHAR(500),
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    
    INDEX idx_codigo (codigo),
    INDEX idx_categoria (categoria),
    INDEX idx_estado (estado),
    
    CONSTRAINT chk_precio_positivo CHECK (precio >= 0),
    CONSTRAINT chk_stock_positivo CHECK (stock >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Tabla: Factura
-- Descripción: Almacena información de facturas de venta
-- =====================================================
CREATE TABLE IF NOT EXISTS Factura (
    factura_id INT AUTO_INCREMENT PRIMARY KEY,
    numero_factura VARCHAR(20) NOT NULL UNIQUE,
    cedula_cliente VARCHAR(10) NOT NULL,
    nombre_cliente VARCHAR(200) NOT NULL,
    forma_pago VARCHAR(20) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    descuento DECIMAL(12,2) DEFAULT 0,
    total DECIMAL(12,2) NOT NULL,
    numero_credito VARCHAR(20),
    fecha_emision DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_numero_factura (numero_factura),
    INDEX idx_cedula_cliente (cedula_cliente),
    INDEX idx_forma_pago (forma_pago),
    INDEX idx_numero_credito (numero_credito),
    INDEX idx_fecha_emision (fecha_emision),
    
    CONSTRAINT chk_forma_pago CHECK (forma_pago IN ('EFECTIVO', 'CREDITO')),
    CONSTRAINT chk_subtotal_positivo CHECK (subtotal >= 0),
    CONSTRAINT chk_descuento_positivo CHECK (descuento >= 0),
    CONSTRAINT chk_total_positivo CHECK (total >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Tabla: DetalleFactura
-- Descripción: Almacena los detalles/líneas de cada factura
-- =====================================================
CREATE TABLE IF NOT EXISTS DetalleFactura (
    detalle_id INT AUTO_INCREMENT PRIMARY KEY,
    factura_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    
    FOREIGN KEY (factura_id) REFERENCES Factura(factura_id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES Producto(producto_id) ON DELETE RESTRICT,
    
    INDEX idx_factura_id (factura_id),
    INDEX idx_producto_id (producto_id),
    
    CONSTRAINT chk_cantidad_positiva CHECK (cantidad > 0),
    CONSTRAINT chk_precio_unitario_positivo CHECK (precio_unitario >= 0),
    CONSTRAINT chk_subtotal_positivo CHECK (subtotal >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Datos de Prueba
-- =====================================================

-- Insertar productos de ejemplo
INSERT INTO Producto (codigo, nombre, descripcion, precio, stock, categoria, estado) VALUES
('REFRI-001', 'Refrigeradora LG 20 pies', 'Refrigeradora moderna con dispensador de agua y hielo', 1299.99, 15, 'REFRIGERACION', 'ACTIVO'),
('LAVA-001', 'Lavadora Samsung 18kg', 'Lavadora de carga frontal con 12 programas', 899.99, 20, 'LAVADO', 'ACTIVO'),
('COCI-001', 'Cocina Indurama 4 quemadores', 'Cocina a gas con horno grande', 599.99, 10, 'COCINA', 'ACTIVO'),
('MICRO-001', 'Microondas Panasonic 1.2 cu.ft', 'Microondas con grill y 10 niveles de potencia', 199.99, 25, 'ELECTRODOMESTICOS', 'ACTIVO'),
('TV-001', 'Smart TV Samsung 55"', 'Televisor LED 4K Ultra HD con Smart TV', 799.99, 12, 'ELECTRONICA', 'ACTIVO'),
('ASPI-001', 'Aspiradora Electrolux', 'Aspiradora potente para el hogar', 149.99, 30, 'LIMPIEZA', 'ACTIVO'),
('PLAN-001', 'Plancha de Vapor Black+Decker', 'Plancha a vapor con control de temperatura', 39.99, 50, 'ELECTRODOMESTICOS', 'ACTIVO'),
('VENT-001', 'Ventilador de Pedestal Oster', 'Ventilador de 3 velocidades con oscilación', 79.99, 40, 'VENTILACION', 'ACTIVO'),
('BATI-001', 'Batidora KitchenAid', 'Batidora de pie de 5 velocidades', 249.99, 18, 'ELECTRODOMESTICOS', 'ACTIVO'),
('CAFETERA-001', 'Cafetera Oster 12 tazas', 'Cafetera programable con filtro permanente', 89.99, 35, 'ELECTRODOMESTICOS', 'ACTIVO');

-- Insertar una factura de ejemplo (EFECTIVO)
INSERT INTO Factura (numero_factura, cedula_cliente, nombre_cliente, forma_pago, subtotal, descuento, total, fecha_emision) 
VALUES ('FAC-000001', '1234567890', 'Juan Pérez', 'EFECTIVO', 1499.98, 50.00, 1449.98, NOW());

-- Insertar detalles de la factura
INSERT INTO DetalleFactura (factura_id, producto_id, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 1, 1299.99, 1299.99),
(1, 4, 1, 199.99, 199.99);

-- Insertar una factura de ejemplo (CREDITO)
INSERT INTO Factura (numero_factura, cedula_cliente, nombre_cliente, forma_pago, subtotal, descuento, total, numero_credito, fecha_emision) 
VALUES ('FAC-000002', '0987654321', 'María González', 'CREDITO', 1699.98, 0.00, 1699.98, 'CRE-000001', NOW());

-- Insertar detalles de la factura a crédito
INSERT INTO DetalleFactura (factura_id, producto_id, cantidad, precio_unitario, subtotal) VALUES
(2, 2, 1, 899.99, 899.99),
(2, 5, 1, 799.99, 799.99);

-- =====================================================
-- Verificación
-- =====================================================
SELECT 'Base de datos creada exitosamente' AS Mensaje;
SELECT COUNT(*) AS TotalProductos FROM Producto;
SELECT COUNT(*) AS TotalFacturas FROM Factura;
SELECT COUNT(*) AS TotalDetalles FROM DetalleFactura;
