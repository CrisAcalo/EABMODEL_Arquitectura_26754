-- =====================================================
-- Script DML - Comercializadora
-- Sistema de Facturación de Electrodomésticos
-- MySQL 8.0 (Convertido de SQL Server)
-- =====================================================

USE ComercializadoraDB;

-- =====================================================
-- INSERTAR PRODUCTOS - Todos los Casos
-- =====================================================

SET @ImagenURL = 'https://imagenes.computerhoy.20minutos.es/files/image_640_360/uploads/imagenes/2022/11/28/68e5d483200a5.jpeg';

-- =====================================================
-- PRODUCTOS BAJO COSTO ($50-$99)
-- =====================================================
INSERT INTO Producto (Codigo, Nombre, Descripcion, Precio, Stock, Categoria, ImagenUrl, Estado) VALUES
('PROD-PEQ-001', 'Licuadora Oster 3 Velocidades', 'Licuadora 3 velocidades, 450W, vaso de vidrio', 65.00, 50, 'PEQUEÑOS', NULL, 'ACTIVO'),
('PROD-PEQ-002', 'Batidora de Mano Hamilton', 'Batidora manual 250W, 5 velocidades', 45.00, 40, 'PEQUEÑOS', NULL, 'ACTIVO'),
('PROD-PEQ-003', 'Tostadora Oster 2 Rebanadas', 'Tostadora de pan, 2 ranuras, 700W', 55.00, 35, 'PEQUEÑOS', NULL, 'ACTIVO'),
('PROD-PEQ-004', 'Cafetera Black & Decker 12 Tazas', 'Cafetera con filtro permanente', 75.00, 30, 'PEQUEÑOS', NULL, 'ACTIVO'),
('PROD-PEQ-005', 'Plancha de Vapor Philips', 'Plancha a vapor 1800W, suela antiadherente', 85.00, 25, 'PEQUEÑOS', NULL, 'ACTIVO'),
('PROD-PEQ-006', 'Sandwichera Oster', 'Sandwichera antiadherente, indicador LED', 35.00, 3, 'PEQUEÑOS', NULL, 'ACTIVO'),
('PROD-PEQ-007', 'Exprimidor de Naranjas', 'Exprimidor eléctrico de cítricos 40W', 50.00, 0, 'PEQUEÑOS', NULL, 'ACTIVO');

-- =====================================================
-- PRODUCTOS RANGO MEDIO ($100-$500) - CON URL IMAGEN
-- =====================================================
INSERT INTO Producto (Codigo, Nombre, Descripcion, Precio, Stock, Categoria, ImagenUrl, Estado) VALUES
('PROD-MED-001', 'Licuadora Oster 600W', 'Licuadora 600 watts con 10 velocidades', 120.00, 30, 'LÍNEA BLANCA', @ImagenURL, 'ACTIVO'),
('PROD-MED-002', 'Microondas Samsung 1.1 pies', 'Microondas 1.1 pies cúbicos, 1000W', 180.00, 25, 'LÍNEA BLANCA', @ImagenURL, 'ACTIVO'),
('PROD-MED-003', 'Microondas LG NeoChef 1.5 pies', 'Microondas Inverter NeoChef 1.5 pies', 280.00, 18, 'LÍNEA BLANCA', @ImagenURL, 'ACTIVO'),
('PROD-MED-004', 'Aspiradora Electrolux 1600W', 'Aspiradora de polvo 1600W con bolsa', 220.00, 15, 'LIMPIEZA', @ImagenURL, 'ACTIVO'),
('PROD-MED-005', 'Ventilador de Pedestal Samurai', 'Ventilador 3 velocidades, oscilante, 18"', 95.00, 40, 'CLIMATIZACIÓN', @ImagenURL, 'ACTIVO'),
('PROD-MED-006', 'Horno Eléctrico Oster 42L', 'Horno tostador 42 litros, 1500W', 180.00, 12, 'COCINA', @ImagenURL, 'ACTIVO'),
('PROD-MED-007', 'Procesador de Alimentos Oster', 'Procesador 10 tazas, 500W, accesorios múltiples', 145.00, 20, 'COCINA', @ImagenURL, 'ACTIVO'),
('PROD-MED-008', 'Freidora de Aire Philips 3.5L', 'Air Fryer 3.5 litros, tecnología Rapid Air', 185.00, 22, 'COCINA', @ImagenURL, 'ACTIVO'),
('PROD-MED-009', 'Olla Arrocera Oster 10 Tazas', 'Olla arrocera con vaporera, 700W', 95.00, 28, 'COCINA', @ImagenURL, 'ACTIVO'),
('PROD-MED-010', 'Extractor de Jugos Oster', 'Extractor de jugos 800W, boca ancha', 165.00, 15, 'COCINA', @ImagenURL, 'ACTIVO');

-- =====================================================
-- PRODUCTOS RANGO MEDIO-ALTO ($500-$1000)
-- =====================================================
INSERT INTO Producto (Codigo, Nombre, Descripcion, Precio, Stock, Categoria, ImagenUrl, Estado) VALUES
('PROD-MED-011', 'Cocina Indurama 4 Quemadores', 'Cocina a gas 4 quemadores con horno', 550.00, 18, 'COCINAS', @ImagenURL, 'ACTIVO'),
('PROD-MED-012', 'Cocina Mabe 6 Quemadores', 'Cocina a gas 6 quemadores con horno grande', 750.00, 12, 'COCINAS', @ImagenURL, 'ACTIVO'),
('PROD-ALT-001', 'TV Sony 43" LED Full HD', 'Televisor LED 43 pulgadas Full HD Smart TV', 650.00, 20, 'TELEVISORES', @ImagenURL, 'ACTIVO'),
('PROD-ALT-002', 'TV Samsung 50" Crystal UHD', 'Televisor 50 pulgadas 4K Crystal UHD', 850.00, 15, 'TELEVISORES', @ImagenURL, 'ACTIVO'),
('PROD-ALT-003', 'Minicomponente LG XBOOM', 'Minicomponente 500W, Bluetooth, USB, Karaoke', 380.00, 10, 'AUDIO', @ImagenURL, 'ACTIVO'),
('PROD-ALT-004', 'Soundbar Samsung 2.1 Canales', 'Barra de sonido con subwoofer inalámbrico', 280.00, 14, 'AUDIO', @ImagenURL, 'ACTIVO');

-- =====================================================
-- PRODUCTOS RANGO ALTO ($1000-$2000)
-- =====================================================
INSERT INTO Producto (Codigo, Nombre, Descripcion, Precio, Stock, Categoria, ImagenUrl, Estado) VALUES
('PROD-ALT-005', 'Lavadora Whirlpool 18kg', 'Lavadora carga frontal 18kg, 14 programas', 1200.00, 18, 'LAVADORAS', NULL, 'ACTIVO'),
('PROD-ALT-006', 'Lavadora LG 22kg TurboDrum', 'Lavadora carga superior 22kg con TurboDrum', 1500.00, 10, 'LAVADORAS', NULL, 'ACTIVO'),
('PROD-ALT-007', 'Lavadora Samsung 20kg AddWash', 'Lavadora carga frontal 20kg con AddWash', 1800.00, 12, 'LAVADORAS', NULL, 'ACTIVO'),
('PROD-ALT-008', 'TV LG 55" NanoCell 4K', 'Televisor NanoCell 55 pulgadas 4K Smart TV', 1400.00, 10, 'TELEVISORES', NULL, 'ACTIVO'),
('PROD-ALT-009', 'TV Samsung 55" QLED 4K', 'Televisor QLED 55 pulgadas 4K HDR Quantum', 1800.00, 8, 'TELEVISORES', NULL, 'ACTIVO'),
('PROD-ALT-010', 'Refrigeradora Whirlpool 18 pies', 'Refrigeradora Top Mount 18 pies cúbicos', 1600.00, 2, 'REFRIGERACIÓN', NULL, 'ACTIVO');

-- =====================================================
-- PRODUCTOS RANGO PREMIUM ($2000-$4000)
-- =====================================================
INSERT INTO Producto (Codigo, Nombre, Descripcion, Precio, Stock, Categoria, ImagenUrl, Estado) VALUES
('PROD-PREM-001', 'Refrigeradora LG InstaView 26p', 'Refrigeradora 26 pies con door-in-door', 3200.00, 8, 'REFRIGERACIÓN', NULL, 'ACTIVO'),
('PROD-PREM-002', 'Refrigeradora Samsung RF28 Twin', 'Refrigeradora 28 pies, Twin Cooling Plus', 3500.00, 10, 'REFRIGERACIÓN', NULL, 'ACTIVO'),
('PROD-PREM-003', 'TV LG 65" OLED 4K', 'Televisor OLED 65 pulgadas 4K Smart TV', 3800.00, 5, 'TELEVISORES', NULL, 'ACTIVO'),
('PROD-PREM-004', 'TV Samsung 65" Neo QLED 8K', 'Televisor Neo QLED 65 pulgadas 8K HDR', 4200.00, 4, 'TELEVISORES', NULL, 'ACTIVO'),
('PROD-PREM-005', 'Lavadora-Secadora LG TwinWash', 'Lavadora-Secadora combo 21kg, TwinWash', 2800.00, 6, 'LAVADORAS', NULL, 'ACTIVO'),
('PROD-PREM-006', 'Secadora Samsung 22kg', 'Secadora a gas 22kg con tecnología OptimalDry', 2200.00, 7, 'SECADORAS', NULL, 'ACTIVO');

-- =====================================================
-- PRODUCTOS INACTIVOS
-- =====================================================
INSERT INTO Producto (Codigo, Nombre, Descripcion, Precio, Stock, Categoria, ImagenUrl, Estado) VALUES
('PROD-INACT-001', 'Televisor Antiguo 32" CRT', 'Modelo descontinuado', 200.00, 0, 'TELEVISORES', NULL, 'INACTIVO'),
('PROD-INACT-002', 'Lavadora Antigua 10kg', 'Modelo descontinuado', 450.00, 0, 'LAVADORAS', NULL, 'INACTIVO');

-- =====================================================
-- INSERTAR FACTURAS
-- =====================================================

-- FACTURA 1: EFECTIVO - Producto pequeño (con 33% descuento)
INSERT INTO Factura (NumeroFactura, CedulaCliente, NombreCliente, FormaPago, Subtotal, Descuento, Total, FechaEmision) VALUES
('FAC-20251116-001', '1710123456', 'Luis Fernando Morales Castro', 'EFECTIVO', 120.00, 39.60, 80.40, DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO DetalleFactura (FacturaId, ProductoId, Cantidad, PrecioUnitario, Subtotal) VALUES
(1, 8, 1, 120.00, 120.00);

-- FACTURA 2: CRÉDITO - Refrigeradora grande (sin descuento)
INSERT INTO Factura (NumeroFactura, CedulaCliente, NombreCliente, FormaPago, Subtotal, Descuento, Total, NumeroCredito, FechaEmision) VALUES
('FAC-20251116-002', '1712345678', 'Juan Carlos Pérez González', 'CREDITO', 3200.00, 0.00, 3200.00, 'CRE20251116080001', DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO DetalleFactura (FacturaId, ProductoId, Cantidad, PrecioUnitario, Subtotal) VALUES
(2, 27, 1, 3200.00, 3200.00);

-- FACTURA 3: EFECTIVO - Múltiples productos pequeños
INSERT INTO Factura (NumeroFactura, CedulaCliente, NombreCliente, FormaPago, Subtotal, Descuento, Total, FechaEmision) VALUES
('FAC-20251117-001', '1723456789', 'María Fernanda López Martínez', 'EFECTIVO', 315.00, 103.95, 211.05, DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO DetalleFactura (FacturaId, ProductoId, Cantidad, PrecioUnitario, Subtotal) VALUES
(3, 1, 1, 65.00, 65.00),
(3, 3, 1, 55.00, 55.00),
(3, 4, 1, 75.00, 75.00),
(3, 8, 1, 120.00, 120.00);

-- FACTURA 4: CRÉDITO - TV grande
INSERT INTO Factura (NumeroFactura, CedulaCliente, NombreCliente, FormaPago, Subtotal, Descuento, Total, NumeroCredito, FechaEmision) VALUES
('FAC-20251117-002', '1745678901', 'Ana Lucía Torres Vega', 'CREDITO', 1800.00, 0.00, 1800.00, 'CRE20251117090001', DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO DetalleFactura (FacturaId, ProductoId, Cantidad, PrecioUnitario, Subtotal) VALUES
(4, 25, 1, 1800.00, 1800.00);

-- FACTURA 5: EFECTIVO - Cocina mediana
INSERT INTO Factura (NumeroFactura, CedulaCliente, NombreCliente, FormaPago, Subtotal, Descuento, Total, FechaEmision) VALUES
('FAC-20251117-003', '1789012345', 'Carmen Inés Flores Mendoza', 'EFECTIVO', 550.00, 181.50, 368.50, DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO DetalleFactura (FacturaId, ProductoId, Cantidad, PrecioUnitario, Subtotal) VALUES
(5, 17, 1, 550.00, 550.00);

-- FACTURA 6: CRÉDITO - Lavadora premium
INSERT INTO Factura (NumeroFactura, CedulaCliente, NombreCliente, FormaPago, Subtotal, Descuento, Total, NumeroCredito, FechaEmision) VALUES
('FAC-20251118-001', '1790123456', 'Jorge Luis Mendoza Castro', 'CREDITO', 1500.00, 0.00, 1500.00, 'CRE20251118101500', NOW());

INSERT INTO DetalleFactura (FacturaId, ProductoId, Cantidad, PrecioUnitario, Subtotal) VALUES
(6, 22, 1, 1500.00, 1500.00);

-- FACTURA 7: EFECTIVO - Compra múltiple de audio
INSERT INTO Factura (NumeroFactura, CedulaCliente, NombreCliente, FormaPago, Subtotal, Descuento, Total, FechaEmision) VALUES
('FAC-20251118-002', '1756789012', 'Pedro Antonio García Núñez', 'EFECTIVO', 660.00, 217.80, 442.20, NOW());

INSERT INTO DetalleFactura (FacturaId, ProductoId, Cantidad, PrecioUnitario, Subtotal) VALUES
(7, 20, 1, 380.00, 380.00),
(7, 21, 1, 280.00, 280.00);

-- FACTURA 8: CRÉDITO - Combo cocina + microondas
INSERT INTO Factura (NumeroFactura, CedulaCliente, NombreCliente, FormaPago, Subtotal, Descuento, Total, NumeroCredito, FechaEmision) VALUES
('FAC-20251118-003', '1710123456', 'Luis Fernando Morales Castro', 'CREDITO', 930.00, 0.00, 930.00, 'CRE20251118103000', NOW());

INSERT INTO DetalleFactura (FacturaId, ProductoId, Cantidad, PrecioUnitario, Subtotal) VALUES
(8, 18, 1, 750.00, 750.00),
(8, 9, 1, 180.00, 180.00);

-- =====================================================
-- ACTUALIZAR STOCK POR VENTAS
-- =====================================================
UPDATE Producto SET Stock = Stock - 2 WHERE ProductoId = 8;
UPDATE Producto SET Stock = Stock - 1 WHERE ProductoId = 27;
UPDATE Producto SET Stock = Stock - 1 WHERE ProductoId = 1;
UPDATE Producto SET Stock = Stock - 1 WHERE ProductoId = 3;
UPDATE Producto SET Stock = Stock - 1 WHERE ProductoId = 4;
UPDATE Producto SET Stock = Stock - 1 WHERE ProductoId = 25;
UPDATE Producto SET Stock = Stock - 1 WHERE ProductoId = 17;
UPDATE Producto SET Stock = Stock - 1 WHERE ProductoId = 22;
UPDATE Producto SET Stock = Stock - 1 WHERE ProductoId = 20;
UPDATE Producto SET Stock = Stock - 1 WHERE ProductoId = 21;
UPDATE Producto SET Stock = Stock - 1 WHERE ProductoId = 18;
UPDATE Producto SET Stock = Stock - 1 WHERE ProductoId = 9;

-- =====================================================
-- VERIFICACIÓN
-- =====================================================
SELECT 'Productos Totales' AS Descripcion, COUNT(*) AS Total FROM Producto
UNION ALL
SELECT 'Productos Activos', COUNT(*) FROM Producto WHERE Estado = 'ACTIVO'
UNION ALL
SELECT 'Productos Inactivos', COUNT(*) FROM Producto WHERE Estado = 'INACTIVO'
UNION ALL
SELECT 'Facturas', COUNT(*) FROM Factura
UNION ALL
SELECT 'Detalles Factura', COUNT(*) FROM DetalleFactura;

SELECT 'Datos insertados correctamente' AS Resultado;
