-- =====================================================
-- Script DML (Data Manipulation Language) - BanQuito
-- Sistema CORE + Módulo de Crédito
-- MySQL 8.x
-- Contiene: INSERT, UPDATE, SELECT - Datos de prueba
-- VERSIÓN MEJORADA - Cubre TODOS los casos de uso
-- =====================================================

USE BanquitoDB;

-- =====================================================
-- INSERTAR DATOS DE PRUEBA - CASOS DE USO COMPLETOS
-- =====================================================

SELECT '============================================' AS '';
SELECT 'INSERTANDO CLIENTES - Casos de Uso' AS Mensaje;
SELECT '============================================' AS '';

-- ✅ CASO 1: Cliente IDEAL - Cumple TODAS las reglas (soltero, con depósitos, sin crédito)
INSERT INTO Cliente (Cedula, Nombres, Apellidos, FechaNacimiento, EstadoCivil, Direccion, Telefono, Email) VALUES
('1710123456', 'Luis Fernando', 'Morales Castro', '1990-05-15', 'Soltero', 'Av. Shyris N45-123', '0987766554', 'luis.morales@email.com');

-- ✅ CASO 2: Cliente IDEAL casado pero MAYOR de 25 años (cumple todas las reglas)
INSERT INTO Cliente (Cedula, Nombres, Apellidos, FechaNacimiento, EstadoCivil, Direccion, Telefono, Email) VALUES
('1712345678', 'Juan Carlos', 'Pérez González', '1985-03-15', 'Casado', 'Av. Amazonas N34-451', '0998765432', 'juan.perez@email.com');

-- ✅ CASO 3: Cliente mujer casada mayor de 25 años (apta para crédito)
INSERT INTO Cliente (Cedula, Nombres, Apellidos, FechaNacimiento, EstadoCivil, Direccion, Telefono, Email) VALUES
('1723456789', 'María Fernanda', 'López Martínez', '1988-07-22', 'Casada', 'Calle 10 de Agosto 234', '0987654321', 'maria.lopez@email.com');

-- ❌ CASO 4: Cliente casado MENOR de 25 años (NO apto - falla regla de edad)
INSERT INTO Cliente (Cedula, Nombres, Apellidos, FechaNacimiento, EstadoCivil, Direccion, Telefono, Email) VALUES
('1734567890', 'Carlos Alberto', 'Ramírez Silva', '2002-11-30', 'Casado', 'Av. 6 de Diciembre 789', '0991234567', 'carlos.ramirez@email.com');

-- ✅ CASO 5: Cliente con ingresos ALTOS (generará monto máximo alto)
INSERT INTO Cliente (Cedula, Nombres, Apellidos, FechaNacimiento, EstadoCivil, Direccion, Telefono, Email) VALUES
('1745678901', 'Ana Lucía', 'Torres Vega', '1985-04-18', 'Soltera', 'Calle Colón 456', '0998877665', 'ana.torres@email.com');

-- ✅ CASO 6: Cliente con ingresos BAJOS (generará monto máximo bajo)
INSERT INTO Cliente (Cedula, Nombres, Apellidos, FechaNacimiento, EstadoCivil, Direccion, Telefono, Email) VALUES
('1756789012', 'Pedro Antonio', 'García Núñez', '1992-09-05', 'Soltero', 'Av. Naciones Unidas 890', '0987788996', 'pedro.garcia@email.com');

-- ❌ CASO 7: Cliente SIN depósitos en último mes (NO apto - falla regla depósito)
INSERT INTO Cliente (Cedula, Nombres, Apellidos, FechaNacimiento, EstadoCivil, Direccion, Telefono, Email) VALUES
('1767890123', 'Patricia Elena', 'Mora Ruiz', '1989-12-10', 'Soltera', 'Calle La Gasca 345', '0996655443', 'patricia.mora@email.com');

-- ✅ CASO 8: Cliente con crédito ACTIVO (para probar segunda solicitud)
INSERT INTO Cliente (Cedula, Nombres, Apellidos, FechaNacimiento, EstadoCivil, Direccion, Telefono, Email) VALUES
('1778901234', 'Roberto Carlos', 'Sánchez Vega', '1987-06-20', 'Divorciado', 'Av. 10 de Agosto 123', '0995544332', 'roberto.sanchez@email.com');

-- ✅ CASO 9: Cliente divorciado (estado civil diferente)
INSERT INTO Cliente (Cedula, Nombres, Apellidos, FechaNacimiento, EstadoCivil, Direccion, Telefono, Email) VALUES
('1789012345', 'Carmen Inés', 'Flores Mendoza', '1990-08-14', 'Divorciada', 'Calle González Suárez 567', '0994433221', 'carmen.flores@email.com');

-- ✅ CASO 10: Cliente límite edad casado (exactamente 25 años)
INSERT INTO Cliente (Cedula, Nombres, Apellidos, FechaNacimiento, EstadoCivil, Direccion, Telefono, Email) VALUES
('1790123456', 'Jorge Luis', 'Mendoza Castro', DATE_SUB(CURDATE(), INTERVAL 25 YEAR), 'Casado', 'Av. América 789', '0993322110', 'jorge.mendoza@email.com');


SELECT '============================================' AS '';
SELECT 'INSERTANDO CUENTAS' AS Mensaje;
SELECT '============================================' AS '';

-- Cuentas correspondientes a cada cliente
INSERT INTO Cuenta (NumeroCuenta, TipoCuenta, Saldo, FechaApertura, Estado, ClienteId) VALUES
('2001234567', 'AHORROS', 2500.00, '2023-01-15', 'ACTIVA', 1),   -- Luis Fernando (ideal)
('2001234568', 'CORRIENTE', 4200.00, '2023-02-20', 'ACTIVA', 2),  -- Juan Carlos (casado >25)
('2001234569', 'AHORROS', 3800.00, '2023-03-10', 'ACTIVA', 3),    -- María (casada >25)
('2001234570', 'AHORROS', 1200.00, '2023-04-05', 'ACTIVA', 4),    -- Carlos (casado <25 - NO APTO)
('2001234571', 'CORRIENTE', 8500.00, '2023-05-12', 'ACTIVA', 5),  -- Ana (ingresos altos)
('2001234572', 'AHORROS', 800.00, '2023-06-18', 'ACTIVA', 6),     -- Pedro (ingresos bajos)
('2001234573', 'AHORROS', 2200.00, '2023-07-25', 'ACTIVA', 7),    -- Patricia (sin depósito reciente)
('2001234574', 'CORRIENTE', 3500.00, '2023-08-30', 'ACTIVA', 8),  -- Roberto (con crédito activo)
('2001234575', 'AHORROS', 2800.00, '2023-09-15', 'ACTIVA', 9),    -- Carmen (divorciada)
('2001234576', 'AHORROS', 3200.00, '2023-10-20', 'ACTIVA', 10);   -- Jorge (exactamente 25 años)

SELECT '============================================' AS '';
SELECT 'INSERTANDO MOVIMIENTOS - Casos Realistas' AS Mensaje;
SELECT '============================================' AS '';

-- =====================================================
-- CLIENTE 1: Luis Fernando (1710123456) - PERFIL IDEAL
-- Depósitos constantes, retiros moderados
-- Monto Máximo Esperado: ~$1,300
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 850.00, DATE_SUB(NOW(), INTERVAL 90 DAY), 'Depósito salario', 0.00, 850.00, 1),
('DEPOSITO', 900.00, DATE_SUB(NOW(), INTERVAL 85 DAY), 'Comisión ventas', 850.00, 1750.00, 1),
('RETIRO', 400.00, DATE_SUB(NOW(), INTERVAL 80 DAY), 'Pago servicios', 1750.00, 1350.00, 1),
('DEPOSITO', 850.00, DATE_SUB(NOW(), INTERVAL 75 DAY), 'Depósito salario', 1350.00, 2200.00, 1),
-- Hace 2 meses
('DEPOSITO', 900.00, DATE_SUB(NOW(), INTERVAL 60 DAY), 'Depósito salario', 2200.00, 3100.00, 1),
('RETIRO', 500.00, DATE_SUB(NOW(), INTERVAL 55 DAY), 'Retiro cajero', 3100.00, 2600.00, 1),
('DEPOSITO', 850.00, DATE_SUB(NOW(), INTERVAL 50 DAY), 'Depósito salario', 2600.00, 3450.00, 1),
('RETIRO', 450.00, DATE_SUB(NOW(), INTERVAL 45 DAY), 'Compras supermercado', 3450.00, 3000.00, 1),
-- Hace 1 mes (CRÍTICO para validación)
('DEPOSITO', 900.00, DATE_SUB(NOW(), INTERVAL 28 DAY), 'Depósito salario', 3000.00, 3900.00, 1),
('RETIRO', 350.00, DATE_SUB(NOW(), INTERVAL 25 DAY), 'Pago tarjeta', 3900.00, 3550.00, 1),
('DEPOSITO', 850.00, DATE_SUB(NOW(), INTERVAL 20 DAY), 'Bono', 3550.00, 4400.00, 1),
('RETIRO', 1900.00, DATE_SUB(NOW(), INTERVAL 15 DAY), 'Compra electrodoméstico', 4400.00, 2500.00, 1);
-- Promedio Depósitos: ~870, Promedio Retiros: ~433
-- Monto Máximo: ((870-433) × 0.6) × 9 = $2,360

-- =====================================================
-- CLIENTE 2: Juan Carlos (1712345678) - CASADO >25 AÑOS
-- Depósitos altos, retiros moderados
-- Monto Máximo Esperado: ~$2,800
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 1200.00, DATE_SUB(NOW(), INTERVAL 88 DAY), 'Depósito salario', 0.00, 1200.00, 2),
('DEPOSITO', 1300.00, DATE_SUB(NOW(), INTERVAL 82 DAY), 'Depósito salario', 1200.00, 2500.00, 2),
('RETIRO', 600.00, DATE_SUB(NOW(), INTERVAL 78 DAY), 'Pago servicios', 2500.00, 1900.00, 2),
('DEPOSITO', 1250.00, DATE_SUB(NOW(), INTERVAL 73 DAY), 'Depósito salario', 1900.00, 3150.00, 2),
-- Hace 2 meses
('DEPOSITO', 1350.00, DATE_SUB(NOW(), INTERVAL 58 DAY), 'Depósito salario', 3150.00, 4500.00, 2),
('RETIRO', 800.00, DATE_SUB(NOW(), INTERVAL 53 DAY), 'Pago colegio hijos', 4500.00, 3700.00, 2),
('DEPOSITO', 1200.00, DATE_SUB(NOW(), INTERVAL 48 DAY), 'Depósito salario', 3700.00, 4900.00, 2),
('RETIRO', 700.00, DATE_SUB(NOW(), INTERVAL 43 DAY), 'Compras', 4900.00, 4200.00, 2),
-- Hace 1 mes
('DEPOSITO', 1280.00, DATE_SUB(NOW(), INTERVAL 27 DAY), 'Depósito salario', 4200.00, 5480.00, 2),
('RETIRO', 600.00, DATE_SUB(NOW(), INTERVAL 22 DAY), 'Pago servicios', 5480.00, 4880.00, 2),
('DEPOSITO', 1300.00, DATE_SUB(NOW(), INTERVAL 18 DAY), 'Bono fiestas', 4880.00, 6180.00, 2),
('RETIRO', 1980.00, DATE_SUB(NOW(), INTERVAL 12 DAY), 'Matrícula universidad', 6180.00, 4200.00, 2);
-- Promedio Depósitos: ~1,265, Promedio Retiros: ~670
-- Monto Máximo: ((1265-670) × 0.6) × 9 = $3,213

-- =====================================================
-- CLIENTE 3: María (1723456789) - CASADA >25 AÑOS
-- Depósitos medios, gastos controlados
-- Monto Máximo Esperado: ~$2,100
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 1000.00, DATE_SUB(NOW(), INTERVAL 87 DAY), 'Depósito salario', 0.00, 1000.00, 3),
('DEPOSITO', 1050.00, DATE_SUB(NOW(), INTERVAL 81 DAY), 'Depósito salario', 1000.00, 2050.00, 3),
('RETIRO', 500.00, DATE_SUB(NOW(), INTERVAL 76 DAY), 'Pago servicios', 2050.00, 1550.00, 3),
('DEPOSITO', 1000.00, DATE_SUB(NOW(), INTERVAL 71 DAY), 'Depósito salario', 1550.00, 2550.00, 3),
-- Hace 2 meses
('DEPOSITO', 1100.00, DATE_SUB(NOW(), INTERVAL 57 DAY), 'Depósito salario', 2550.00, 3650.00, 3),
('RETIRO', 600.00, DATE_SUB(NOW(), INTERVAL 52 DAY), 'Compras mensuales', 3650.00, 3050.00, 3),
('DEPOSITO', 1000.00, DATE_SUB(NOW(), INTERVAL 47 DAY), 'Depósito salario', 3050.00, 4050.00, 3),
('RETIRO', 550.00, DATE_SUB(NOW(), INTERVAL 42 DAY), 'Farmacia', 4050.00, 3500.00, 3),
-- Hace 1 mes
('DEPOSITO', 1050.00, DATE_SUB(NOW(), INTERVAL 26 DAY), 'Depósito salario', 3500.00, 4550.00, 3),
('RETIRO', 450.00, DATE_SUB(NOW(), INTERVAL 21 DAY), 'Supermercado', 4550.00, 4100.00, 3),
('DEPOSITO', 1000.00, DATE_SUB(NOW(), INTERVAL 16 DAY), 'Comisión', 4100.00, 5100.00, 3),
('RETIRO', 1300.00, DATE_SUB(NOW(), INTERVAL 10 DAY), 'Pago préstamo personal', 5100.00, 3800.00, 3);
-- Promedio Depósitos: ~1,025, Promedio Retiros: ~600
-- Monto Máximo: ((1025-600) × 0.6) × 9 = $2,295

-- =====================================================
-- CLIENTE 4: Carlos (1734567890) - CASADO <25 AÑOS (NO APTO)
-- Tiene movimientos pero NO puede crédito por edad
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Tiene depósitos recientes pero NO importa
('DEPOSITO', 600.00, DATE_SUB(NOW(), INTERVAL 85 DAY), 'Depósito salario', 0.00, 600.00, 4),
('RETIRO', 200.00, DATE_SUB(NOW(), INTERVAL 80 DAY), 'Retiro', 600.00, 400.00, 4),
('DEPOSITO', 650.00, DATE_SUB(NOW(), INTERVAL 55 DAY), 'Depósito salario', 400.00, 1050.00, 4),
('RETIRO', 250.00, DATE_SUB(NOW(), INTERVAL 50 DAY), 'Compras', 1050.00, 800.00, 4),
('DEPOSITO', 600.00, DATE_SUB(NOW(), INTERVAL 25 DAY), 'Depósito salario', 800.00, 1400.00, 4),
('RETIRO', 200.00, DATE_SUB(NOW(), INTERVAL 20 DAY), 'Servicios', 1400.00, 1200.00, 4);
-- Resultado: RECHAZADO por edad (casado menor 25 años)

-- =====================================================
-- CLIENTE 5: Ana (1745678901) - INGRESOS MUY ALTOS
-- Perfil empresaria con altos depósitos
-- Monto Máximo Esperado: ~$6,000+
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 3000.00, DATE_SUB(NOW(), INTERVAL 86 DAY), 'Depósito ventas negocio', 0.00, 3000.00, 5),
('DEPOSITO', 3500.00, DATE_SUB(NOW(), INTERVAL 79 DAY), 'Depósito ventas', 3000.00, 6500.00, 5),
('RETIRO', 1200.00, DATE_SUB(NOW(), INTERVAL 74 DAY), 'Pago proveedores', 6500.00, 5300.00, 5),
('DEPOSITO', 3200.00, DATE_SUB(NOW(), INTERVAL 69 DAY), 'Cobro cliente', 5300.00, 8500.00, 5),
-- Hace 2 meses
('DEPOSITO', 3800.00, DATE_SUB(NOW(), INTERVAL 56 DAY), 'Depósito ventas', 8500.00, 12300.00, 5),
('RETIRO', 2000.00, DATE_SUB(NOW(), INTERVAL 51 DAY), 'Inversión negocio', 12300.00, 10300.00, 5),
('DEPOSITO', 3300.00, DATE_SUB(NOW(), INTERVAL 46 DAY), 'Depósito ventas', 10300.00, 13600.00, 5),
('RETIRO', 1500.00, DATE_SUB(NOW(), INTERVAL 41 DAY), 'Gastos operacionales', 13600.00, 12100.00, 5),
-- Hace 1 mes
('DEPOSITO', 3600.00, DATE_SUB(NOW(), INTERVAL 26 DAY), 'Depósito ventas', 12100.00, 15700.00, 5),
('RETIRO', 2200.00, DATE_SUB(NOW(), INTERVAL 20 DAY), 'Pago impuestos', 15700.00, 13500.00, 5),
('DEPOSITO', 3400.00, DATE_SUB(NOW(), INTERVAL 14 DAY), 'Cobro facturas', 13500.00, 16900.00, 5),
('RETIRO', 8400.00, DATE_SUB(NOW(), INTERVAL 8 DAY), 'Compra equipos', 16900.00, 8500.00, 5);
-- Promedio Depósitos: ~3,400, Promedio Retiros: ~1,900
-- Monto Máximo: ((3400-1900) × 0.6) × 9 = $8,100

-- =====================================================
-- CLIENTE 6: Pedro (1756789012) - INGRESOS BAJOS
-- Salario mínimo, gastos altos
-- Monto Máximo Esperado: ~$200
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 500.00, DATE_SUB(NOW(), INTERVAL 84 DAY), 'Depósito salario', 0.00, 500.00, 6),
('RETIRO', 400.00, DATE_SUB(NOW(), INTERVAL 78 DAY), 'Pago arriendo', 500.00, 100.00, 6),
('DEPOSITO', 500.00, DATE_SUB(NOW(), INTERVAL 72 DAY), 'Depósito salario', 100.00, 600.00, 6),
('RETIRO', 420.00, DATE_SUB(NOW(), INTERVAL 67 DAY), 'Pago servicios', 600.00, 180.00, 6),
-- Hace 2 meses
('DEPOSITO', 520.00, DATE_SUB(NOW(), INTERVAL 54 DAY), 'Depósito salario', 180.00, 700.00, 6),
('RETIRO', 450.00, DATE_SUB(NOW(), INTERVAL 49 DAY), 'Pago arriendo', 700.00, 250.00, 6),
('DEPOSITO', 500.00, DATE_SUB(NOW(), INTERVAL 44 DAY), 'Depósito salario', 250.00, 750.00, 6),
('RETIRO', 400.00, DATE_SUB(NOW(), INTERVAL 39 DAY), 'Compras', 750.00, 350.00, 6),
-- Hace 1 mes
('DEPOSITO', 510.00, DATE_SUB(NOW(), INTERVAL 24 DAY), 'Depósito salario', 350.00, 860.00, 6),
('RETIRO', 420.00, DATE_SUB(NOW(), INTERVAL 18 DAY), 'Pago arriendo', 860.00, 440.00, 6),
('DEPOSITO', 500.00, DATE_SUB(NOW(), INTERVAL 12 DAY), 'Depósito salario', 440.00, 940.00, 6),
('RETIRO', 140.00, DATE_SUB(NOW(), INTERVAL 6 DAY), 'Compras', 940.00, 800.00, 6);
-- Promedio Depósitos: ~508, Promedio Retiros: ~407
-- Monto Máximo: ((508-407) × 0.6) × 9 = $545

-- =====================================================
-- CLIENTE 7: Patricia (1767890123) - SIN DEPÓSITO RECIENTE (NO APTA)
-- Último depósito hace MÁS de 30 días
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses (tiene movimientos antiguos)
('DEPOSITO', 1200.00, DATE_SUB(NOW(), INTERVAL 90 DAY), 'Depósito', 0.00, 1200.00, 7),
('RETIRO', 400.00, DATE_SUB(NOW(), INTERVAL 85 DAY), 'Retiro', 1200.00, 800.00, 7),
-- Hace 2 meses
('DEPOSITO', 1300.00, DATE_SUB(NOW(), INTERVAL 60 DAY), 'Depósito', 800.00, 2100.00, 7),
('RETIRO', 500.00, DATE_SUB(NOW(), INTERVAL 55 DAY), 'Retiro', 2100.00, 1600.00, 7),
-- Último depósito hace 35 días (FUERA del rango de 30 días)
('DEPOSITO', 1100.00, DATE_SUB(NOW(), INTERVAL 35 DAY), 'Último depósito', 1600.00, 2700.00, 7),
-- Solo retiros recientes
('RETIRO', 500.00, DATE_SUB(NOW(), INTERVAL 20 DAY), 'Retiro reciente', 2700.00, 2200.00, 7);
-- Resultado: RECHAZADO por no tener depósito en último mes

-- =====================================================
-- CLIENTE 8: Roberto (1778901234) - CON CRÉDITO ACTIVO
-- Tiene movimientos pero YA tiene un crédito
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Movimientos normales
('DEPOSITO', 1000.00, DATE_SUB(NOW(), INTERVAL 88 DAY), 'Depósito salario', 0.00, 1000.00, 8),
('RETIRO', 300.00, DATE_SUB(NOW(), INTERVAL 83 DAY), 'Pago cuota crédito', 1000.00, 700.00, 8),
('DEPOSITO', 1050.00, DATE_SUB(NOW(), INTERVAL 58 DAY), 'Depósito salario', 700.00, 1750.00, 8),
('RETIRO', 300.00, DATE_SUB(NOW(), INTERVAL 53 DAY), 'Pago cuota crédito', 1750.00, 1450.00, 8),
('DEPOSITO', 1000.00, DATE_SUB(NOW(), INTERVAL 28 DAY), 'Depósito salario', 1450.00, 2450.00, 8),
('RETIRO', 300.00, DATE_SUB(NOW(), INTERVAL 23 DAY), 'Pago cuota crédito', 2450.00, 2150.00, 8),
('DEPOSITO', 1050.00, DATE_SUB(NOW(), INTERVAL 15 DAY), 'Bono', 2150.00, 3200.00, 8);
-- Resultado: RECHAZADO por tener crédito activo

-- =====================================================
-- CLIENTE 9: Carmen (1789012345) - DIVORCIADA (Estado civil diferente)
-- Perfil normal, estado civil no afecta
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
('DEPOSITO', 950.00, DATE_SUB(NOW(), INTERVAL 86 DAY), 'Depósito salario', 0.00, 950.00, 9),
('RETIRO', 350.00, DATE_SUB(NOW(), INTERVAL 80 DAY), 'Pago servicios', 950.00, 600.00, 9),
('DEPOSITO', 1000.00, DATE_SUB(NOW(), INTERVAL 56 DAY), 'Depósito salario', 600.00, 1600.00, 9),
('RETIRO', 400.00, DATE_SUB(NOW(), INTERVAL 50 DAY), 'Compras', 1600.00, 1200.00, 9),
('DEPOSITO', 950.00, DATE_SUB(NOW(), INTERVAL 26 DAY), 'Depósito salario', 1200.00, 2150.00, 9),
('RETIRO', 380.00, DATE_SUB(NOW(), INTERVAL 20 DAY), 'Pago varios', 2150.00, 1770.00, 9),
('DEPOSITO', 1000.00, DATE_SUB(NOW(), INTERVAL 12 DAY), 'Comisión', 1770.00, 2770.00, 9);
-- Monto Máximo: ~$2,500

-- =====================================================
-- CLIENTE 10: Jorge (1790123456) - CASADO EXACTAMENTE 25 AÑOS
-- Caso límite: cumple justo con la edad mínima
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
('DEPOSITO', 1100.00, DATE_SUB(NOW(), INTERVAL 85 DAY), 'Depósito salario', 0.00, 1100.00, 10),
('RETIRO', 450.00, DATE_SUB(NOW(), INTERVAL 79 DAY), 'Pago servicios', 1100.00, 650.00, 10),
('DEPOSITO', 1150.00, DATE_SUB(NOW(), INTERVAL 55 DAY), 'Depósito salario', 650.00, 1800.00, 10),
('RETIRO', 500.00, DATE_SUB(NOW(), INTERVAL 49 DAY), 'Compras', 1800.00, 1300.00, 10),
('DEPOSITO', 1100.00, DATE_SUB(NOW(), INTERVAL 25 DAY), 'Depósito salario', 1300.00, 2400.00, 10),
('RETIRO', 480.00, DATE_SUB(NOW(), INTERVAL 19 DAY), 'Pago tarjeta', 2400.00, 1920.00, 10),
('DEPOSITO', 1150.00, DATE_SUB(NOW(), INTERVAL 10 DAY), 'Bono', 1920.00, 3070.00, 10);
-- Monto Máximo: ~$2,700


SELECT '============================================' AS '';
SELECT 'INSERTANDO CRÉDITO ACTIVO (Cliente 8)' AS Mensaje;
SELECT '============================================' AS '';

-- Crédito activo para Roberto (Cliente 8) - Para probar rechazo de segunda solicitud
INSERT INTO Credito (NumeroCredito, MontoCredito, TasaInteres, NumeroCuotas, CuotaMensual, FechaOtorgamiento, Estado, Descripcion, ClienteId) VALUES
('CRE20251115093421', 1800.00, 0.0133, 6, 320.00, '2024-11-15 09:34:21', 'ACTIVO', 'Compra electrodoméstico', 8);

-- Tabla de amortización para el crédito activo (6 cuotas)
INSERT INTO CuotaAmortizacion (NumeroCuota, ValorCuota, Interes, CapitalPagado, Saldo, CreditoId) VALUES
(1, 320.00, 23.94, 296.06, 1503.94, 1),
(2, 320.00, 20.00, 300.00, 1203.94, 1),
(3, 320.00, 16.01, 303.99, 899.95, 1),
(4, 320.00, 11.97, 308.03, 591.92, 1),
(5, 320.00, 7.87, 312.13, 279.79, 1),
(6, 320.00, 3.72, 316.28, 0.00, 1);


-- =====================================================
-- VERIFICACIÓN Y PRUEBAS
-- =====================================================

SELECT '' AS '';
SELECT '============================================' AS '';
SELECT 'RESUMEN DE DATOS INSERTADOS' AS Mensaje;
SELECT '============================================' AS '';

SELECT 'Clientes' AS Tabla, COUNT(*) AS Total FROM Cliente
UNION ALL
SELECT 'Cuentas', COUNT(*) FROM Cuenta
UNION ALL
SELECT 'Movimientos', COUNT(*) FROM Movimiento
UNION ALL
SELECT 'Créditos', COUNT(*) FROM Credito
UNION ALL
SELECT 'Cuotas Amortización', COUNT(*) FROM CuotaAmortizacion;

SELECT '' AS '';
SELECT '============================================' AS '';
SELECT 'ANÁLISIS DE CASOS DE USO' AS Mensaje;
SELECT '============================================' AS '';

-- Mostrar clientes y su categorización
SELECT
    Cedula,
    CONCAT(Nombres, ' ', Apellidos) AS NombreCompleto,
    EstadoCivil,
    TIMESTAMPDIFF(YEAR, FechaNacimiento, CURDATE()) AS Edad,
    CASE
        WHEN EstadoCivil = 'Casado' AND TIMESTAMPDIFF(YEAR, FechaNacimiento, CURDATE()) < 25 THEN '❌ NO APTO: Casado menor 25 años'
        WHEN EstadoCivil = 'Casada' AND TIMESTAMPDIFF(YEAR, FechaNacimiento, CURDATE()) < 25 THEN '❌ NO APTO: Casada menor 25 años'
        WHEN Cedula = '1767890123' THEN '❌ NO APTO: Sin depósito reciente'
        WHEN Cedula = '1778901234' THEN '❌ NO APTO: Tiene crédito activo'
        ELSE '✅ APTO para crédito'
    END AS EstadoElegibilidad
FROM Cliente
ORDER BY ClienteId;

SELECT '' AS '';
SELECT '============================================' AS '';
SELECT 'MOVIMIENTOS POR TIPO' AS Mensaje;
SELECT '============================================' AS '';

SELECT
    TipoMovimiento,
    COUNT(*) AS Total,
    CONCAT('$', FORMAT(SUM(Monto), 2)) AS MontoTotal
FROM Movimiento
GROUP BY TipoMovimiento;

SELECT '' AS '';
SELECT '============================================' AS '';
SELECT 'CRÉDITOS ACTIVOS' AS Mensaje;
SELECT '============================================' AS '';

SELECT
    c.NumeroCredito,
    CONCAT(cl.Nombres, ' ', cl.Apellidos) AS Cliente,
    CONCAT('$', FORMAT(c.MontoCredito, 2)) AS Monto,
    c.NumeroCuotas AS Cuotas,
    CONCAT('$', FORMAT(c.CuotaMensual, 2)) AS CuotaMensual,
    c.Estado
FROM Credito c
INNER JOIN Cliente cl ON c.ClienteId = cl.ClienteId;

SELECT '' AS '';
SELECT '============================================' AS '';
SELECT 'FIN DEL SCRIPT DML - BanquitoDB' AS Mensaje;
SELECT '============================================' AS '';
