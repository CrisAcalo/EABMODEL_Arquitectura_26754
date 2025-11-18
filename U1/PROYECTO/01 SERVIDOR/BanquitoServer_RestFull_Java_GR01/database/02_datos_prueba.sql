-- =====================================================
-- Script de Datos de Prueba
-- Banco BanQuito - Módulo de Crédito
-- 5 Clientes, 5 Cuentas, 50 Movimientos
-- =====================================================
-- =====================================================
-- INSERTAR CLIENTES (5 clientes)
-- =====================================================

USE banquito_db;

INSERT INTO cliente (cedula, nombres, apellidos, fecha_nacimiento, estado_civil, direccion, telefono, email, activo) VALUES
('1234567890', 'Juan Carlos', 'Pérez García', '1985-03-15', 'CASADO', 'Av. 10 de Agosto N25-123, Quito', '0998765432', 'juan.perez@email.com', TRUE),
('0987654321', 'María Elena', 'González López', '1990-07-22', 'SOLTERA', 'Calle García Moreno 456, Quito', '0987654321', 'maria.gonzalez@email.com', TRUE),
('1122334455', 'Pedro Antonio', 'Rodríguez Silva', '1988-11-30', 'CASADO', 'Av. América N30-789, Quito', '0991234567', 'pedro.rodriguez@email.com', TRUE),
('5544332211', 'Ana Lucía', 'Martínez Ruiz', '1992-05-18', 'SOLTERA', 'Calle Colón E8-234, Quito', '0989876543', 'ana.martinez@email.com', TRUE),
('9988776655', 'Carlos Alberto', 'Sánchez Torres', '1975-09-08', 'CASADO', 'Av. 6 de Diciembre N33-456, Quito', '0996547832', 'carlos.sanchez@email.com', TRUE);

-- =====================================================
-- INSERTAR CUENTAS (5 cuentas - 1 por cliente)
-- =====================================================

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo, fecha_apertura, estado, cliente_id) VALUES
('1001234567', 'AHORROS', 5000.00, '2023-01-15 10:00:00', 'ACTIVA', 1),
('1001234568', 'CORRIENTE', 8500.00, '2023-02-20 11:30:00', 'ACTIVA', 2),
('1001234569', 'AHORROS', 12000.00, '2023-03-10 09:15:00', 'ACTIVA', 3),
('1001234570', 'AHORROS', 6500.00, '2023-04-05 14:45:00', 'ACTIVA', 4),
('1001234571', 'CORRIENTE', 15000.00, '2023-05-12 16:20:00', 'ACTIVA', 5);

-- =====================================================
-- INSERTAR MOVIMIENTOS (50 movimientos distribuidos)
-- Distribución: 10 movimientos por cuenta
-- =====================================================

-- Movimientos para Cuenta 1 (Juan Carlos Pérez)
INSERT INTO movimiento (tipo_movimiento, monto, descripcion, fecha_movimiento, saldo_anterior, saldo_nuevo, cuenta_id) VALUES
('DEPOSITO', 1000.00, 'Depósito inicial', '2024-08-01 09:00:00', 0.00, 1000.00, 1),
('DEPOSITO', 500.00, 'Transferencia recibida', '2024-08-15 10:30:00', 1000.00, 1500.00, 1),
('RETIRO', 200.00, 'Retiro cajero automático', '2024-08-20 14:00:00', 1500.00, 1300.00, 1),
('DEPOSITO', 800.00, 'Depósito en ventanilla', '2024-09-05 11:00:00', 1300.00, 2100.00, 1),
('DEPOSITO', 1200.00, 'Sueldo', '2024-09-30 08:00:00', 2100.00, 3300.00, 1),
('RETIRO', 150.00, 'Pago de servicios', '2024-10-05 16:00:00', 3300.00, 3150.00, 1),
('DEPOSITO', 950.00, 'Transferencia recibida', '2024-10-15 10:00:00', 3150.00, 4100.00, 1),
('RETIRO', 300.00, 'Retiro ventanilla', '2024-10-25 13:00:00', 4100.00, 3800.00, 1),
('DEPOSITO', 1500.00, 'Sueldo', '2024-11-01 08:00:00', 3800.00, 5300.00, 1),
('RETIRO', 300.00, 'Pago tarjeta', '2024-11-10 15:00:00', 5300.00, 5000.00, 1);

-- Movimientos para Cuenta 2 (María Elena González)
INSERT INTO movimiento (tipo_movimiento, monto, descripcion, fecha_movimiento, saldo_anterior, saldo_nuevo, cuenta_id) VALUES
('DEPOSITO', 2000.00, 'Depósito inicial', '2024-08-02 09:30:00', 0.00, 2000.00, 2),
('DEPOSITO', 1500.00, 'Transferencia', '2024-08-18 11:00:00', 2000.00, 3500.00, 2),
('RETIRO', 400.00, 'Retiro cajero', '2024-08-25 15:00:00', 3500.00, 3100.00, 2),
('DEPOSITO', 2000.00, 'Sueldo', '2024-09-01 08:00:00', 3100.00, 5100.00, 2),
('DEPOSITO', 800.00, 'Bono', '2024-09-10 10:00:00', 5100.00, 5900.00, 2),
('RETIRO', 600.00, 'Pago alquiler', '2024-09-15 14:00:00', 5900.00, 5300.00, 2),
('DEPOSITO', 1800.00, 'Transferencia', '2024-10-01 09:00:00', 5300.00, 7100.00, 2),
('RETIRO', 250.00, 'Retiro cajero', '2024-10-20 16:00:00', 7100.00, 6850.00, 2),
('DEPOSITO', 2100.00, 'Sueldo', '2024-11-01 08:00:00', 6850.00, 8950.00, 2),
('RETIRO', 450.00, 'Pagos varios', '2024-11-08 13:00:00', 8950.00, 8500.00, 2);

-- Movimientos para Cuenta 3 (Pedro Antonio Rodríguez)
INSERT INTO movimiento (tipo_movimiento, monto, descripcion, fecha_movimiento, saldo_anterior, saldo_nuevo, cuenta_id) VALUES
('DEPOSITO', 3000.00, 'Depósito inicial', '2024-08-03 10:00:00', 0.00, 3000.00, 3),
('DEPOSITO', 2500.00, 'Sueldo', '2024-08-30 08:00:00', 3000.00, 5500.00, 3),
('RETIRO', 800.00, 'Pago tarjeta', '2024-09-05 14:00:00', 5500.00, 4700.00, 3),
('DEPOSITO', 1200.00, 'Bono', '2024-09-12 11:00:00', 4700.00, 5900.00, 3),
('DEPOSITO', 3000.00, 'Sueldo', '2024-09-30 08:00:00', 5900.00, 8900.00, 3),
('RETIRO', 1500.00, 'Retiro ventanilla', '2024-10-10 15:00:00', 8900.00, 7400.00, 3),
('DEPOSITO', 2200.00, 'Transferencia', '2024-10-20 10:00:00', 7400.00, 9600.00, 3),
('RETIRO', 900.00, 'Pago de servicios', '2024-10-28 16:00:00', 9600.00, 8700.00, 3),
('DEPOSITO', 3500.00, 'Sueldo', '2024-11-01 08:00:00', 8700.00, 12200.00, 3),
('RETIRO', 200.00, 'Retiro cajero', '2024-11-09 12:00:00', 12200.00, 12000.00, 3);

-- Movimientos para Cuenta 4 (Ana Lucía Martínez)
INSERT INTO movimiento (tipo_movimiento, monto, descripcion, fecha_movimiento, saldo_anterior, saldo_nuevo, cuenta_id) VALUES
('DEPOSITO', 1500.00, 'Depósito inicial', '2024-08-04 11:00:00', 0.00, 1500.00, 4),
('DEPOSITO', 900.00, 'Transferencia', '2024-08-20 10:00:00', 1500.00, 2400.00, 4),
('RETIRO', 300.00, 'Retiro cajero', '2024-08-28 15:00:00', 2400.00, 2100.00, 4),
('DEPOSITO', 1800.00, 'Sueldo', '2024-09-02 08:00:00', 2100.00, 3900.00, 4),
('DEPOSITO', 600.00, 'Bono', '2024-09-18 11:00:00', 3900.00, 4500.00, 4),
('RETIRO', 500.00, 'Pago alquiler', '2024-09-25 14:00:00', 4500.00, 4000.00, 4),
('DEPOSITO', 1100.00, 'Transferencia', '2024-10-08 09:00:00', 4000.00, 5100.00, 4),
('RETIRO', 400.00, 'Retiro ventanilla', '2024-10-22 16:00:00', 5100.00, 4700.00, 4),
('DEPOSITO', 2000.00, 'Sueldo', '2024-11-01 08:00:00', 4700.00, 6700.00, 4),
('RETIRO', 200.00, 'Pago servicios', '2024-11-10 13:00:00', 6700.00, 6500.00, 4);

-- Movimientos para Cuenta 5 (Carlos Alberto Sánchez)
INSERT INTO movimiento (tipo_movimiento, monto, descripcion, fecha_movimiento, saldo_anterior, saldo_nuevo, cuenta_id) VALUES
('DEPOSITO', 4000.00, 'Depósito inicial', '2024-08-05 09:00:00', 0.00, 4000.00, 5),
('DEPOSITO', 3500.00, 'Sueldo', '2024-08-30 08:00:00', 4000.00, 7500.00, 5),
('RETIRO', 1200.00, 'Pago tarjeta', '2024-09-08 14:00:00', 7500.00, 6300.00, 5),
('DEPOSITO', 2000.00, 'Bono', '2024-09-15 11:00:00', 6300.00, 8300.00, 5),
('DEPOSITO', 4000.00, 'Sueldo', '2024-09-30 08:00:00', 8300.00, 12300.00, 5),
('RETIRO', 2000.00, 'Retiro ventanilla', '2024-10-12 15:00:00', 12300.00, 10300.00, 5),
('DEPOSITO', 2500.00, 'Transferencia', '2024-10-25 10:00:00', 10300.00, 12800.00, 5),
('RETIRO', 1500.00, 'Pago de servicios', '2024-11-03 16:00:00', 12800.00, 11300.00, 5),
('DEPOSITO', 4500.00, 'Sueldo', '2024-11-01 08:00:00', 11300.00, 15800.00, 5),
('RETIRO', 800.00, 'Retiro cajero', '2024-11-11 12:00:00', 15800.00, 15000.00, 5);

-- =====================================================
-- VERIFICACIÓN DE DATOS
-- =====================================================

-- Contar registros insertados
SELECT 'Clientes insertados: ' || COUNT(*) FROM cliente;
SELECT 'Cuentas insertadas: ' || COUNT(*) FROM cuenta;
SELECT 'Movimientos insertados: ' || COUNT(*) FROM movimiento;

-- Mostrar resumen de saldos
SELECT 
    c.cedula,
    c.nombres || ' ' || c.apellidos AS nombre_completo,
    cu.numero_cuenta,
    cu.saldo,
    COUNT(m.id) AS total_movimientos
FROM cliente c
JOIN cuenta cu ON c.id = cu.cliente_id
LEFT JOIN movimiento m ON cu.id = m.cuenta_id
GROUP BY c.cedula, c.nombres, c.apellidos, cu.numero_cuenta, cu.saldo
ORDER BY c.cedula;

SELECT 'Datos de prueba insertados exitosamente' AS mensaje;
