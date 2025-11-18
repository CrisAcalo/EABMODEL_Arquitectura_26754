-- =====================================================
-- Script de creación de Base de Datos BanQuito
-- Sistema CORE + Módulo de Crédito
-- =====================================================

-- Crear base de datos
DROP DATABASE IF EXISTS banquito_db;
CREATE DATABASE banquito_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE banquito_db;

-- =====================================================
-- CREAR TABLAS
-- =====================================================

-- Tabla Cliente
CREATE TABLE cliente (
    cliente_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(10) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    estado_civil VARCHAR(20),
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    email VARCHAR(100),
    INDEX idx_cedula (cedula)
) ENGINE=InnoDB;

-- Tabla Cuenta
CREATE TABLE cuenta (
    cuenta_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    fecha_apertura DATE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA',
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id),
    INDEX idx_numero_cuenta (numero_cuenta),
    INDEX idx_cliente (cliente_id)
) ENGINE=InnoDB;

-- Tabla Movimiento
CREATE TABLE movimiento (
    movimiento_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_movimiento VARCHAR(20) NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    fecha_movimiento DATETIME NOT NULL,
    descripcion VARCHAR(200),
    saldo_anterior DECIMAL(12,2),
    saldo_nuevo DECIMAL(12,2),
    cuenta_id BIGINT NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(cuenta_id),
    INDEX idx_fecha (fecha_movimiento),
    INDEX idx_tipo (tipo_movimiento),
    INDEX idx_cuenta (cuenta_id)
) ENGINE=InnoDB;

-- Tabla Credito
CREATE TABLE credito (
    credito_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_credito VARCHAR(20) NOT NULL UNIQUE,
    monto_credito DECIMAL(12,2) NOT NULL,
    tasa_interes DECIMAL(5,4) NOT NULL,
    numero_cuotas INT NOT NULL,
    cuota_mensual DECIMAL(12,2) NOT NULL,
    fecha_otorgamiento DATE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    descripcion VARCHAR(200),
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id),
    INDEX idx_numero_credito (numero_credito),
    INDEX idx_cliente (cliente_id),
    INDEX idx_estado (estado)
) ENGINE=InnoDB;

-- Tabla CuotaAmortizacion
CREATE TABLE cuota_amortizacion (
    cuota_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cuota INT NOT NULL,
    valor_cuota DECIMAL(12,2) NOT NULL,
    interes DECIMAL(12,2) NOT NULL,
    capital_pagado DECIMAL(12,2) NOT NULL,
    saldo DECIMAL(12,2) NOT NULL,
    credito_id BIGINT NOT NULL,
    FOREIGN KEY (credito_id) REFERENCES credito(credito_id) ON DELETE CASCADE,
    INDEX idx_credito (credito_id),
    INDEX idx_numero_cuota (numero_cuota)
) ENGINE=InnoDB;

-- =====================================================
-- INSERTAR DATOS DE PRUEBA
-- =====================================================

-- Insertar 5 Clientes
INSERT INTO cliente (cedula, nombres, apellidos, fecha_nacimiento, estado_civil, direccion, telefono, email) VALUES
('1234567890', 'Juan Carlos', 'Pérez González', '1985-03-15', 'Casado', 'Av. Amazonas N34-451', '0998765432', 'juan.perez@email.com'),
('0987654321', 'María Fernanda', 'López Martínez', '1990-07-22', 'Soltera', 'Calle 10 de Agosto 234', '0987654321', 'maria.lopez@email.com'),
('1122334455', 'Pedro Antonio', 'Ramírez Silva', '1988-11-30', 'Casado', 'Av. 6 de Diciembre 789', '0991234567', 'pedro.ramirez@email.com'),
('5566778899', 'Ana Lucía', 'Torres Vega', '1992-04-18', 'Soltera', 'Calle Colón 456', '0998877665', 'ana.torres@email.com'),
('9988776655', 'Luis Fernando', 'Morales Castro', '1987-09-05', 'Divorciado', 'Av. Shyris N45-123', '0987766554', 'luis.morales@email.com');

-- Insertar 5 Cuentas (una por cliente)
INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo, fecha_apertura, estado, cliente_id) VALUES
('2001234567', 'AHORROS', 5000.00, '2023-01-15', 'ACTIVA', 1),
('2001234568', 'CORRIENTE', 8500.00, '2023-02-20', 'ACTIVA', 2),
('2001234569', 'AHORROS', 3200.00, '2023-03-10', 'ACTIVA', 3),
('2001234570', 'AHORROS', 6750.00, '2023-04-05', 'ACTIVA', 4),
('2001234571', 'CORRIENTE', 4300.00, '2023-05-12', 'ACTIVA', 5);

-- =====================================================
-- Insertar 50 Movimientos (Depósitos y Retiros)
-- Distribuidos en los últimos 3 meses
-- =====================================================

-- Movimientos para Cliente 1 (cedula: 1234567890) - Cuenta 1
-- Hace 3 meses
INSERT INTO movimiento (tipo_movimiento, monto, fecha_movimiento, descripcion, saldo_anterior, saldo_nuevo, cuenta_id) VALUES
('DEPOSITO', 1500.00, DATE_SUB(NOW(), INTERVAL 90 DAY), 'Depósito inicial', 0.00, 1500.00, 1),
('DEPOSITO', 2000.00, DATE_SUB(NOW(), INTERVAL 85 DAY), 'Depósito salario', 1500.00, 3500.00, 1),
('RETIRO', 500.00, DATE_SUB(NOW(), INTERVAL 80 DAY), 'Retiro cajero', 3500.00, 3000.00, 1),
('DEPOSITO', 1800.00, DATE_SUB(NOW(), INTERVAL 75 DAY), 'Transferencia recibida', 3000.00, 4800.00, 1),
-- Hace 2 meses
('DEPOSITO', 2200.00, DATE_SUB(NOW(), INTERVAL 60 DAY), 'Depósito salario', 4800.00, 7000.00, 1),
('RETIRO', 800.00, DATE_SUB(NOW(), INTERVAL 55 DAY), 'Pago servicios', 7000.00, 6200.00, 1),
('DEPOSITO', 1500.00, DATE_SUB(NOW(), INTERVAL 50 DAY), 'Bono', 6200.00, 7700.00, 1),
('RETIRO', 1200.00, DATE_SUB(NOW(), INTERVAL 45 DAY), 'Compras', 7700.00, 6500.00, 1),
-- Hace 1 mes
('DEPOSITO', 2100.00, DATE_SUB(NOW(), INTERVAL 30 DAY), 'Depósito salario', 6500.00, 8600.00, 1),
('RETIRO', 3600.00, DATE_SUB(NOW(), INTERVAL 25 DAY), 'Pago tarjeta', 8600.00, 5000.00, 1);

-- Movimientos para Cliente 2 (cedula: 0987654321) - Cuenta 2
-- Hace 3 meses
INSERT INTO movimiento (tipo_movimiento, monto, fecha_movimiento, descripcion, saldo_anterior, saldo_nuevo, cuenta_id) VALUES
('DEPOSITO', 3000.00, DATE_SUB(NOW(), INTERVAL 88 DAY), 'Depósito inicial', 0.00, 3000.00, 2),
('DEPOSITO', 2500.00, DATE_SUB(NOW(), INTERVAL 82 DAY), 'Depósito salario', 3000.00, 5500.00, 2),
('RETIRO', 1000.00, DATE_SUB(NOW(), INTERVAL 78 DAY), 'Retiro', 5500.00, 4500.00, 2),
('DEPOSITO', 3500.00, DATE_SUB(NOW(), INTERVAL 73 DAY), 'Comisión', 4500.00, 8000.00, 2),
-- Hace 2 meses
('DEPOSITO', 2800.00, DATE_SUB(NOW(), INTERVAL 58 DAY), 'Depósito salario', 8000.00, 10800.00, 2),
('RETIRO', 1500.00, DATE_SUB(NOW(), INTERVAL 53 DAY), 'Pago servicios', 10800.00, 9300.00, 2),
('DEPOSITO', 2000.00, DATE_SUB(NOW(), INTERVAL 48 DAY), 'Transferencia', 9300.00, 11300.00, 2),
('RETIRO', 2000.00, DATE_SUB(NOW(), INTERVAL 43 DAY), 'Compras', 11300.00, 9300.00, 2),
-- Hace 1 mes
('DEPOSITO', 3200.00, DATE_SUB(NOW(), INTERVAL 28 DAY), 'Depósito salario', 9300.00, 12500.00, 2),
('RETIRO', 4000.00, DATE_SUB(NOW(), INTERVAL 22 DAY), 'Inversión', 12500.00, 8500.00, 2);

-- Movimientos para Cliente 3 (cedula: 1122334455) - Cuenta 3
-- Hace 3 meses
INSERT INTO movimiento (tipo_movimiento, monto, fecha_movimiento, descripcion, saldo_anterior, saldo_nuevo, cuenta_id) VALUES
('DEPOSITO', 1200.00, DATE_SUB(NOW(), INTERVAL 87 DAY), 'Depósito inicial', 0.00, 1200.00, 3),
('DEPOSITO', 1800.00, DATE_SUB(NOW(), INTERVAL 81 DAY), 'Depósito salario', 1200.00, 3000.00, 3),
('RETIRO', 600.00, DATE_SUB(NOW(), INTERVAL 76 DAY), 'Retiro', 3000.00, 2400.00, 3),
('DEPOSITO', 1500.00, DATE_SUB(NOW(), INTERVAL 71 DAY), 'Transferencia', 2400.00, 3900.00, 3),
-- Hace 2 meses
('DEPOSITO', 1900.00, DATE_SUB(NOW(), INTERVAL 57 DAY), 'Depósito salario', 3900.00, 5800.00, 3),
('RETIRO', 900.00, DATE_SUB(NOW(), INTERVAL 52 DAY), 'Pago servicios', 5800.00, 4900.00, 3),
('DEPOSITO', 1600.00, DATE_SUB(NOW(), INTERVAL 47 DAY), 'Bono', 4900.00, 6500.00, 3),
('RETIRO', 1800.00, DATE_SUB(NOW(), INTERVAL 42 DAY), 'Compras', 6500.00, 4700.00, 3),
-- Hace 1 mes
('DEPOSITO', 2000.00, DATE_SUB(NOW(), INTERVAL 27 DAY), 'Depósito salario', 4700.00, 6700.00, 3),
('RETIRO', 3500.00, DATE_SUB(NOW(), INTERVAL 21 DAY), 'Pago deuda', 6700.00, 3200.00, 3);

-- Movimientos para Cliente 4 (cedula: 5566778899) - Cuenta 4
-- Hace 3 meses
INSERT INTO movimiento (tipo_movimiento, monto, fecha_movimiento, descripcion, saldo_anterior, saldo_nuevo, cuenta_id) VALUES
('DEPOSITO', 2500.00, DATE_SUB(NOW(), INTERVAL 86 DAY), 'Depósito inicial', 0.00, 2500.00, 4),
('DEPOSITO', 2200.00, DATE_SUB(NOW(), INTERVAL 79 DAY), 'Depósito salario', 2500.00, 4700.00, 4),
('RETIRO', 700.00, DATE_SUB(NOW(), INTERVAL 74 DAY), 'Retiro', 4700.00, 4000.00, 4),
('DEPOSITO', 2800.00, DATE_SUB(NOW(), INTERVAL 69 DAY), 'Comisión', 4000.00, 6800.00, 4),
-- Hace 2 meses
('DEPOSITO', 2400.00, DATE_SUB(NOW(), INTERVAL 56 DAY), 'Depósito salario', 6800.00, 9200.00, 4),
('RETIRO', 1200.00, DATE_SUB(NOW(), INTERVAL 51 DAY), 'Pago servicios', 9200.00, 8000.00, 4),
('DEPOSITO', 2100.00, DATE_SUB(NOW(), INTERVAL 46 DAY), 'Transferencia', 8000.00, 10100.00, 4),
('RETIRO', 1500.00, DATE_SUB(NOW(), INTERVAL 41 DAY), 'Compras', 10100.00, 8600.00, 4),
-- Hace 1 mes
('DEPOSITO', 2600.00, DATE_SUB(NOW(), INTERVAL 26 DAY), 'Depósito salario', 8600.00, 11200.00, 4),
('RETIRO', 4450.00, DATE_SUB(NOW(), INTERVAL 20 DAY), 'Pago varios', 11200.00, 6750.00, 4);

-- Movimientos para Cliente 5 (cedula: 9988776655) - Cuenta 5
-- Hace 3 meses
INSERT INTO movimiento (tipo_movimiento, monto, fecha_movimiento, descripcion, saldo_anterior, saldo_nuevo, cuenta_id) VALUES
('DEPOSITO', 1800.00, DATE_SUB(NOW(), INTERVAL 84 DAY), 'Depósito inicial', 0.00, 1800.00, 5),
('DEPOSITO', 2000.00, DATE_SUB(NOW(), INTERVAL 77 DAY), 'Depósito salario', 1800.00, 3800.00, 5),
('RETIRO', 800.00, DATE_SUB(NOW(), INTERVAL 72 DAY), 'Retiro', 3800.00, 3000.00, 5),
('DEPOSITO', 2300.00, DATE_SUB(NOW(), INTERVAL 67 DAY), 'Transferencia', 3000.00, 5300.00, 5),
-- Hace 2 meses
('DEPOSITO', 2100.00, DATE_SUB(NOW(), INTERVAL 54 DAY), 'Depósito salario', 5300.00, 7400.00, 5),
('RETIRO', 1000.00, DATE_SUB(NOW(), INTERVAL 49 DAY), 'Pago servicios', 7400.00, 6400.00, 5),
('DEPOSITO', 1900.00, DATE_SUB(NOW(), INTERVAL 44 DAY), 'Bono', 6400.00, 8300.00, 5),
('RETIRO', 1600.00, DATE_SUB(NOW(), INTERVAL 39 DAY), 'Compras', 8300.00, 6700.00, 5),
-- Hace 1 mes
('DEPOSITO', 2200.00, DATE_SUB(NOW(), INTERVAL 24 DAY), 'Depósito salario', 6700.00, 8900.00, 5),
('RETIRO', 4600.00, DATE_SUB(NOW(), INTERVAL 18 DAY), 'Pago tarjeta', 8900.00, 4300.00, 5);

-- =====================================================
-- VERIFICACIÓN DE DATOS
-- =====================================================

-- Contar clientes
SELECT 'Clientes insertados:' AS descripcion, COUNT(*) AS total FROM cliente;

-- Contar cuentas
SELECT 'Cuentas insertadas:' AS descripcion, COUNT(*) AS total FROM cuenta;

-- Contar movimientos
SELECT 'Movimientos insertados:' AS descripcion, COUNT(*) AS total FROM movimiento;

-- Contar movimientos por tipo
SELECT tipo_movimiento, COUNT(*) AS total 
FROM movimiento 
GROUP BY tipo_movimiento;

-- Ver resumen de clientes y sus cuentas
SELECT 
    c.cedula, 
    c.nombres, 
    c.apellidos,
    cu.numero_cuenta,
    cu.saldo,
    COUNT(m.movimiento_id) AS total_movimientos
FROM cliente c
INNER JOIN cuenta cu ON c.cliente_id = cu.cliente_id
LEFT JOIN movimiento m ON cu.cuenta_id = m.cuenta_id
GROUP BY c.cedula, c.nombres, c.apellidos, cu.numero_cuenta, cu.saldo
ORDER BY c.cedula;

-- Ver promedio de depósitos de los últimos 3 meses por cliente
SELECT 
    cl.cedula,
    cl.nombres,
    cl.apellidos,
    AVG(CASE WHEN m.tipo_movimiento = 'DEPOSITO' THEN m.monto ELSE 0 END) AS promedio_depositos,
    AVG(CASE WHEN m.tipo_movimiento = 'RETIRO' THEN m.monto ELSE 0 END) AS promedio_retiros
FROM cliente cl
INNER JOIN cuenta cu ON cl.cliente_id = cu.cliente_id
INNER JOIN movimiento m ON cu.cuenta_id = m.cuenta_id
WHERE m.fecha_movimiento >= DATE_SUB(NOW(), INTERVAL 90 DAY)
GROUP BY cl.cedula, cl.nombres, cl.apellidos;

COMMIT;

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================
