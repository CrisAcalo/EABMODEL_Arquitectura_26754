-- =====================================================
-- Script de creación de Base de Datos Comercializadora
-- Sistema de Facturación de Electrodomésticos
-- SQL Server
-- =====================================================

USE master;
GO

IF EXISTS (SELECT name FROM sys.databases WHERE name = N'ComercializadoraDB')
BEGIN
    ALTER DATABASE ComercializadoraDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE ComercializadoraDB;
END
GO

CREATE DATABASE ComercializadoraDB;
GO

USE ComercializadoraDB;
GO

-- =====================================================
-- CREAR TABLAS
-- =====================================================

-- Tabla Producto
CREATE TABLE Producto (
    ProductoId INT IDENTITY(1,1) PRIMARY KEY,
    Codigo VARCHAR(20) NOT NULL UNIQUE,
    Nombre VARCHAR(100) NOT NULL,
    Descripcion VARCHAR(500),
    Precio DECIMAL(12,2) NOT NULL,
    Stock INT NOT NULL DEFAULT 0,
    Categoria VARCHAR(50),
    ImagenUrl VARCHAR(500),
    FechaRegistro DATETIME NOT NULL DEFAULT GETDATE(),
    Estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    INDEX IX_Producto_Codigo (Codigo),
    INDEX IX_Producto_Categoria (Categoria),
    INDEX IX_Producto_Estado (Estado)
);
GO

-- Tabla Factura
CREATE TABLE Factura (
    FacturaId INT IDENTITY(1,1) PRIMARY KEY,
    NumeroFactura VARCHAR(20) NOT NULL UNIQUE,
    CedulaCliente VARCHAR(10) NOT NULL,
    NombreCliente VARCHAR(200) NOT NULL,
    FormaPago VARCHAR(20) NOT NULL,
    Subtotal DECIMAL(12,2) NOT NULL,
    Descuento DECIMAL(12,2) NOT NULL DEFAULT 0,
    Total DECIMAL(12,2) NOT NULL,
    NumeroCredito VARCHAR(20),
    FechaEmision DATETIME NOT NULL DEFAULT GETDATE(),
    INDEX IX_Factura_NumeroFactura (NumeroFactura),
    INDEX IX_Factura_CedulaCliente (CedulaCliente),
    INDEX IX_Factura_FormaPago (FormaPago),
    INDEX IX_Factura_FechaEmision (FechaEmision)
);
GO

-- Tabla DetalleFactura
CREATE TABLE DetalleFactura (
    DetalleId INT IDENTITY(1,1) PRIMARY KEY,
    FacturaId INT NOT NULL,
    ProductoId INT NOT NULL,
    Cantidad INT NOT NULL,
    PrecioUnitario DECIMAL(12,2) NOT NULL,
    Subtotal DECIMAL(12,2) NOT NULL,
    CONSTRAINT FK_DetalleFactura_Factura FOREIGN KEY (FacturaId) REFERENCES Factura(FacturaId) ON DELETE CASCADE,
    CONSTRAINT FK_DetalleFactura_Producto FOREIGN KEY (ProductoId) REFERENCES Producto(ProductoId),
    INDEX IX_DetalleFactura_FacturaId (FacturaId),
    INDEX IX_DetalleFactura_ProductoId (ProductoId)
);
GO

-- =====================================================
-- INSERTAR DATOS DE PRUEBA
-- =====================================================

-- Insertar Productos de Electrodomésticos
INSERT INTO Producto (Codigo, Nombre, Descripcion, Precio, Stock, Categoria, ImagenUrl, Estado) VALUES
-- REFRIGERADORAS
('PROD001', 'Refrigeradora Samsung RF28', 'Refrigeradora 28 pies cúbicos, tecnología Twin Cooling Plus', 3500.00, 10, 'REFRIGERACION', 'https://images.samsung.com/is/image/samsung/p6pim/ec/rf28r7201sr-ec-rf7000-rf28r7201sr-ec-frontsilver-275944460', 'ACTIVO'),
('PROD002', 'Refrigeradora LG InstaView', 'Refrigeradora 26 pies con door-in-door', 3200.00, 8, 'REFRIGERACION', 'https://www.lg.com/ec/images/frigorificos/md07506781/gallery/medium01.jpg', 'ACTIVO'),
('PROD003', 'Refrigeradora Whirlpool 18 pies', 'Refrigeradora Top Mount 18 pies', 1800.00, 15, 'REFRIGERACION', 'https://whirlpool.com.ec/wp-content/uploads/2021/03/WRM45AKTWW-1.jpg', 'ACTIVO'),

-- TELEVISORES
('PROD004', 'TV LG 55" OLED', 'Televisor OLED 55 pulgadas 4K Smart TV', 2800.00, 12, 'TELEVISORES', 'https://www.lg.com/ec/images/televisores/md07516549/gallery/medium01.jpg', 'ACTIVO'),
('PROD005', 'TV Samsung 65" QLED', 'Televisor QLED 65 pulgadas 4K HDR', 3500.00, 7, 'TELEVISORES', 'https://images.samsung.com/is/image/samsung/p6pim/ec/qn65q60tavxzd/gallery/ec-qled-tv-q60t-qn65q60tavxzd-front-black-274943234', 'ACTIVO'),
('PROD006', 'TV Sony 50" LED', 'Televisor LED 50 pulgadas Full HD Smart TV', 1500.00, 20, 'TELEVISORES', 'https://sony.scene7.com/is/image/sonyglobalsolutions/sony-tv-kdl50w660f', 'ACTIVO'),

-- LAVADORAS
('PROD007', 'Lavadora Whirlpool 18kg', 'Lavadora carga frontal 18kg', 1200.00, 18, 'LAVADORAS', 'https://whirlpool.com.ec/wp-content/uploads/2021/03/WFW5620HW-1.jpg', 'ACTIVO'),
('PROD008', 'Lavadora LG 22kg TurboDrum', 'Lavadora carga superior 22kg con TurboDrum', 1500.00, 10, 'LAVADORAS', 'https://www.lg.com/ec/images/lavadoras/md05801607/gallery/medium01.jpg', 'ACTIVO'),
('PROD009', 'Lavadora Samsung 20kg AddWash', 'Lavadora carga frontal 20kg con AddWash', 1800.00, 12, 'LAVADORAS', 'https://images.samsung.com/is/image/samsung/p6pim/ec/wf20m5500aw-ec-addwash-wf5500-wf20m5500aw-ec-frontwhite-64524654', 'ACTIVO'),

-- COCINAS
('PROD010', 'Cocina Indurama 6 quemadores', 'Cocina a gas 6 quemadores con horno', 800.00, 15, 'COCINAS', 'https://indurama.com/wp-content/uploads/2020/01/PARMA-VITRO-600.jpg', 'ACTIVO'),
('PROD011', 'Cocina Mabe 4 quemadores', 'Cocina a gas 4 quemadores', 650.00, 20, 'COCINAS', 'https://mabe.com.ec/media/catalog/product/cache/1/thumbnail/600x/17f82f742ffe127f42dca9de82fb58b1/e/m/em7640csix0_1.jpg', 'ACTIVO'),

-- MICROONDAS
('PROD012', 'Microondas Samsung 1.1 pies', 'Microondas 1.1 pies cúbicos', 250.00, 25, 'MICROONDAS', 'https://images.samsung.com/is/image/samsung/p6pim/ec/ms11j5023ag-ec-microwave-ovens-ms11j5023ag-ec-frontblack-63540976', 'ACTIVO'),
('PROD013', 'Microondas LG NeoChef', 'Microondas Inverter NeoChef 1.5 pies', 350.00, 18, 'MICROONDAS', 'https://www.lg.com/ec/images/microondas/md07529931/gallery/medium01.jpg', 'ACTIVO'),

-- LICUADORAS Y PEQUEÑOS ELECTRODOMESTICOS
('PROD014', 'Licuadora Oster 600W', 'Licuadora 600 watts con 10 velocidades', 120.00, 30, 'PEQUENOS', 'https://oster.com.ec/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/b/l/blstmg-b00-013_1.jpg', 'ACTIVO'),
('PROD015', 'Aspiradora Electrolux 1600W', 'Aspiradora de polvo 1600W', 280.00, 15, 'PEQUENOS', 'https://electrolux.com.ec/wp-content/uploads/2021/03/flex_s_sideview_productimage_electrolux_0.png', 'ACTIVO');

GO

-- =====================================================
-- INSERTAR ALGUNAS FACTURAS DE PRUEBA
-- =====================================================

-- Factura en efectivo (con 33% descuento)
INSERT INTO Factura (NumeroFactura, CedulaCliente, NombreCliente, FormaPago, Subtotal, Descuento, Total, FechaEmision) VALUES
('FAC-20250116-001', '1234567890', 'Juan Carlos Pérez', 'EFECTIVO', 2800.00, 924.00, 1876.00, GETDATE());

INSERT INTO DetalleFactura (FacturaId, ProductoId, Cantidad, PrecioUnitario, Subtotal) VALUES
(1, 4, 1, 2800.00, 2800.00);

-- Factura a crédito (sin descuento)
INSERT INTO Factura (NumeroFactura, CedulaCliente, NombreCliente, FormaPago, Subtotal, Descuento, Total, NumeroCredito, FechaEmision) VALUES
('FAC-20250116-002', '0987654321', 'María López', 'CREDITO', 3500.00, 0.00, 3500.00, 'CRE-20250116-001', DATEADD(HOUR, -2, GETDATE()));

INSERT INTO DetalleFactura (FacturaId, ProductoId, Cantidad, PrecioUnitario, Subtotal) VALUES
(2, 1, 1, 3500.00, 3500.00);

GO

-- =====================================================
-- VERIFICACIÓN DE DATOS
-- =====================================================

SELECT 'Productos insertados:' AS Descripcion, COUNT(*) AS Total FROM Producto;
SELECT 'Facturas insertadas:' AS Descripcion, COUNT(*) AS Total FROM Factura;
SELECT 'Detalles insertados:' AS Descripcion, COUNT(*) AS Total FROM DetalleFactura;

-- Mostrar productos por categoría
SELECT Categoria, COUNT(*) AS Total, SUM(Stock) AS StockTotal
FROM Producto
WHERE Estado = 'ACTIVO'
GROUP BY Categoria
ORDER BY Categoria;

-- Mostrar facturas
SELECT f.NumeroFactura, f.NombreCliente, f.FormaPago, f.Total, f.FechaEmision,
       COUNT(d.DetalleId) AS CantidadItems
FROM Factura f
LEFT JOIN DetalleFactura d ON f.FacturaId = d.FacturaId
GROUP BY f.FacturaId, f.NumeroFactura, f.NombreCliente, f.FormaPago, f.Total, f.FechaEmision
ORDER BY f.FechaEmision DESC;

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================
