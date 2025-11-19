-- =====================================================
-- Script de creación de Base de Datos BanQuito
-- Sistema CORE + Módulo de Crédito
-- SQL Server
-- VERSIÓN MEJORADA - Cubre TODOS los casos de uso
-- =====================================================

USE master;
GO

IF EXISTS (SELECT name FROM sys.databases WHERE name = N'BanquitoDB')
BEGIN
    ALTER DATABASE BanquitoDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE BanquitoDB;
END
GO

CREATE DATABASE BanquitoDB;
GO

USE BanquitoDB;
GO

-- =====================================================
-- CREAR TABLAS
-- =====================================================

-- Tabla Cliente
CREATE TABLE Cliente (
    ClienteId BIGINT IDENTITY(1,1) PRIMARY KEY,
    Cedula VARCHAR(10) NOT NULL UNIQUE,
    Nombres VARCHAR(100) NOT NULL,
    Apellidos VARCHAR(100) NOT NULL,
    FechaNacimiento DATE NOT NULL,
    EstadoCivil VARCHAR(20),
    Direccion VARCHAR(200),
    Telefono VARCHAR(20),
    Email VARCHAR(100),
    INDEX IX_Cliente_Cedula (Cedula)
);
GO

-- Tabla Cuenta
CREATE TABLE Cuenta (
    CuentaId BIGINT IDENTITY(1,1) PRIMARY KEY,
    NumeroCuenta VARCHAR(20) NOT NULL UNIQUE,
    TipoCuenta VARCHAR(20) NOT NULL,
    Saldo DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    FechaApertura DATE NOT NULL,
    Estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA',
    ClienteId BIGINT NOT NULL,
    CONSTRAINT FK_Cuenta_Cliente FOREIGN KEY (ClienteId) REFERENCES Cliente(ClienteId),
    INDEX IX_Cuenta_NumeroCuenta (NumeroCuenta),
    INDEX IX_Cuenta_ClienteId (ClienteId)
);
GO

-- Tabla Movimiento
CREATE TABLE Movimiento (
    MovimientoId BIGINT IDENTITY(1,1) PRIMARY KEY,
    TipoMovimiento VARCHAR(20) NOT NULL,
    Monto DECIMAL(12,2) NOT NULL,
    FechaMovimiento DATETIME NOT NULL,
    Descripcion VARCHAR(200),
    SaldoAnterior DECIMAL(12,2),
    SaldoNuevo DECIMAL(12,2),
    CuentaId BIGINT NOT NULL,
    CONSTRAINT FK_Movimiento_Cuenta FOREIGN KEY (CuentaId) REFERENCES Cuenta(CuentaId),
    INDEX IX_Movimiento_Fecha (FechaMovimiento),
    INDEX IX_Movimiento_Tipo (TipoMovimiento),
    INDEX IX_Movimiento_CuentaId (CuentaId)
);
GO

-- Tabla Credito
CREATE TABLE Credito (
    CreditoId BIGINT IDENTITY(1,1) PRIMARY KEY,
    NumeroCredito VARCHAR(20) NOT NULL UNIQUE,
    MontoCredito DECIMAL(12,2) NOT NULL,
    TasaInteres DECIMAL(5,4) NOT NULL,
    NumeroCuotas INT NOT NULL,
    CuotaMensual DECIMAL(12,2) NOT NULL,
    FechaOtorgamiento DATE NOT NULL,
    Estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    Descripcion VARCHAR(200),
    ClienteId BIGINT NOT NULL,
    CONSTRAINT FK_Credito_Cliente FOREIGN KEY (ClienteId) REFERENCES Cliente(ClienteId),
    INDEX IX_Credito_NumeroCredito (NumeroCredito),
    INDEX IX_Credito_ClienteId (ClienteId),
    INDEX IX_Credito_Estado (Estado)
);
GO

-- Tabla CuotaAmortizacion
CREATE TABLE CuotaAmortizacion (
    CuotaId BIGINT IDENTITY(1,1) PRIMARY KEY,
    NumeroCuota INT NOT NULL,
    ValorCuota DECIMAL(12,2) NOT NULL,
    Interes DECIMAL(12,2) NOT NULL,
    CapitalPagado DECIMAL(12,2) NOT NULL,
    Saldo DECIMAL(12,2) NOT NULL,
    CreditoId BIGINT NOT NULL,
    CONSTRAINT FK_CuotaAmortizacion_Credito FOREIGN KEY (CreditoId) REFERENCES Credito(CreditoId) ON DELETE CASCADE,
    INDEX IX_CuotaAmortizacion_CreditoId (CreditoId),
    INDEX IX_CuotaAmortizacion_NumeroCuota (NumeroCuota)
);
GO

-- =====================================================
-- INSERTAR DATOS DE PRUEBA - CASOS DE USO COMPLETOS
-- =====================================================

PRINT '============================================';
PRINT 'INSERTANDO CLIENTES - Casos de Uso';
PRINT '============================================';

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
('1790123456', 'Jorge Luis', 'Mendoza Castro', DATEADD(YEAR, -25, GETDATE()), 'Casado', 'Av. América 789', '0993322110', 'jorge.mendoza@email.com');

GO

PRINT '============================================';
PRINT 'INSERTANDO CUENTAS';
PRINT '============================================';

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
GO

PRINT '============================================';
PRINT 'INSERTANDO MOVIMIENTOS - Casos Realistas';
PRINT '============================================';

-- =====================================================
-- CLIENTE 1: Luis Fernando (1710123456) - PERFIL IDEAL
-- Depósitos constantes, retiros moderados
-- Monto Máximo Esperado: ~$1,300
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 850.00, DATEADD(DAY, -90, GETDATE()), 'Depósito salario', 0.00, 850.00, 1),
('DEPOSITO', 900.00, DATEADD(DAY, -85, GETDATE()), 'Comisión ventas', 850.00, 1750.00, 1),
('RETIRO', 400.00, DATEADD(DAY, -80, GETDATE()), 'Pago servicios', 1750.00, 1350.00, 1),
('DEPOSITO', 850.00, DATEADD(DAY, -75, GETDATE()), 'Depósito salario', 1350.00, 2200.00, 1),
-- Hace 2 meses
('DEPOSITO', 900.00, DATEADD(DAY, -60, GETDATE()), 'Depósito salario', 2200.00, 3100.00, 1),
('RETIRO', 500.00, DATEADD(DAY, -55, GETDATE()), 'Retiro cajero', 3100.00, 2600.00, 1),
('DEPOSITO', 850.00, DATEADD(DAY, -50, GETDATE()), 'Depósito salario', 2600.00, 3450.00, 1),
('RETIRO', 450.00, DATEADD(DAY, -45, GETDATE()), 'Compras supermercado', 3450.00, 3000.00, 1),
-- Hace 1 mes (CRÍTICO para validación)
('DEPOSITO', 900.00, DATEADD(DAY, -28, GETDATE()), 'Depósito salario', 3000.00, 3900.00, 1),
('RETIRO', 350.00, DATEADD(DAY, -25, GETDATE()), 'Pago tarjeta', 3900.00, 3550.00, 1),
('DEPOSITO', 850.00, DATEADD(DAY, -20, GETDATE()), 'Bono', 3550.00, 4400.00, 1),
('RETIRO', 1900.00, DATEADD(DAY, -15, GETDATE()), 'Compra electrodoméstico', 4400.00, 2500.00, 1);
-- Promedio Depósitos: ~870, Promedio Retiros: ~433
-- Monto Máximo: ((870-433) × 0.6) × 9 = $2,360

-- =====================================================
-- CLIENTE 2: Juan Carlos (1712345678) - CASADO >25 AÑOS
-- Depósitos altos, retiros moderados
-- Monto Máximo Esperado: ~$2,800
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 1200.00, DATEADD(DAY, -88, GETDATE()), 'Depósito salario', 0.00, 1200.00, 2),
('DEPOSITO', 1300.00, DATEADD(DAY, -82, GETDATE()), 'Depósito salario', 1200.00, 2500.00, 2),
('RETIRO', 600.00, DATEADD(DAY, -78, GETDATE()), 'Pago servicios', 2500.00, 1900.00, 2),
('DEPOSITO', 1250.00, DATEADD(DAY, -73, GETDATE()), 'Depósito salario', 1900.00, 3150.00, 2),
-- Hace 2 meses
('DEPOSITO', 1350.00, DATEADD(DAY, -58, GETDATE()), 'Depósito salario', 3150.00, 4500.00, 2),
('RETIRO', 800.00, DATEADD(DAY, -53, GETDATE()), 'Pago colegio hijos', 4500.00, 3700.00, 2),
('DEPOSITO', 1200.00, DATEADD(DAY, -48, GETDATE()), 'Depósito salario', 3700.00, 4900.00, 2),
('RETIRO', 700.00, DATEADD(DAY, -43, GETDATE()), 'Compras', 4900.00, 4200.00, 2),
-- Hace 1 mes
('DEPOSITO', 1280.00, DATEADD(DAY, -27, GETDATE()), 'Depósito salario', 4200.00, 5480.00, 2),
('RETIRO', 600.00, DATEADD(DAY, -22, GETDATE()), 'Pago servicios', 5480.00, 4880.00, 2),
('DEPOSITO', 1300.00, DATEADD(DAY, -18, GETDATE()), 'Bono fiestas', 4880.00, 6180.00, 2),
('RETIRO', 1980.00, DATEADD(DAY, -12, GETDATE()), 'Matrícula universidad', 6180.00, 4200.00, 2);
-- Promedio Depósitos: ~1,265, Promedio Retiros: ~670
-- Monto Máximo: ((1265-670) × 0.6) × 9 = $3,213

-- =====================================================
-- CLIENTE 3: María (1723456789) - CASADA >25 AÑOS
-- Depósitos medios, gastos controlados
-- Monto Máximo Esperado: ~$2,100
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 1000.00, DATEADD(DAY, -87, GETDATE()), 'Depósito salario', 0.00, 1000.00, 3),
('DEPOSITO', 1050.00, DATEADD(DAY, -81, GETDATE()), 'Depósito salario', 1000.00, 2050.00, 3),
('RETIRO', 500.00, DATEADD(DAY, -76, GETDATE()), 'Pago servicios', 2050.00, 1550.00, 3),
('DEPOSITO', 1000.00, DATEADD(DAY, -71, GETDATE()), 'Depósito salario', 1550.00, 2550.00, 3),
-- Hace 2 meses
('DEPOSITO', 1100.00, DATEADD(DAY, -57, GETDATE()), 'Depósito salario', 2550.00, 3650.00, 3),
('RETIRO', 600.00, DATEADD(DAY, -52, GETDATE()), 'Compras mensuales', 3650.00, 3050.00, 3),
('DEPOSITO', 1000.00, DATEADD(DAY, -47, GETDATE()), 'Depósito salario', 3050.00, 4050.00, 3),
('RETIRO', 550.00, DATEADD(DAY, -42, GETDATE()), 'Farmacia', 4050.00, 3500.00, 3),
-- Hace 1 mes
('DEPOSITO', 1050.00, DATEADD(DAY, -26, GETDATE()), 'Depósito salario', 3500.00, 4550.00, 3),
('RETIRO', 450.00, DATEADD(DAY, -21, GETDATE()), 'Supermercado', 4550.00, 4100.00, 3),
('DEPOSITO', 1000.00, DATEADD(DAY, -16, GETDATE()), 'Comisión', 4100.00, 5100.00, 3),
('RETIRO', 1300.00, DATEADD(DAY, -10, GETDATE()), 'Pago préstamo personal', 5100.00, 3800.00, 3);
-- Promedio Depósitos: ~1,025, Promedio Retiros: ~600
-- Monto Máximo: ((1025-600) × 0.6) × 9 = $2,295

-- =====================================================
-- CLIENTE 4: Carlos (1734567890) - CASADO <25 AÑOS (NO APTO)
-- Tiene movimientos pero NO puede crédito por edad
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Tiene depósitos recientes pero NO importa
('DEPOSITO', 600.00, DATEADD(DAY, -85, GETDATE()), 'Depósito salario', 0.00, 600.00, 4),
('RETIRO', 200.00, DATEADD(DAY, -80, GETDATE()), 'Retiro', 600.00, 400.00, 4),
('DEPOSITO', 650.00, DATEADD(DAY, -55, GETDATE()), 'Depósito salario', 400.00, 1050.00, 4),
('RETIRO', 250.00, DATEADD(DAY, -50, GETDATE()), 'Compras', 1050.00, 800.00, 4),
('DEPOSITO', 600.00, DATEADD(DAY, -25, GETDATE()), 'Depósito salario', 800.00, 1400.00, 4),
('RETIRO', 200.00, DATEADD(DAY, -20, GETDATE()), 'Servicios', 1400.00, 1200.00, 4);
-- Resultado: RECHAZADO por edad (casado menor 25 años)

-- =====================================================
-- CLIENTE 5: Ana (1745678901) - INGRESOS MUY ALTOS
-- Perfil empresaria con altos depósitos
-- Monto Máximo Esperado: ~$6,000+
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 3000.00, DATEADD(DAY, -86, GETDATE()), 'Depósito ventas negocio', 0.00, 3000.00, 5),
('DEPOSITO', 3500.00, DATEADD(DAY, -79, GETDATE()), 'Depósito ventas', 3000.00, 6500.00, 5),
('RETIRO', 1200.00, DATEADD(DAY, -74, GETDATE()), 'Pago proveedores', 6500.00, 5300.00, 5),
('DEPOSITO', 3200.00, DATEADD(DAY, -69, GETDATE()), 'Cobro cliente', 5300.00, 8500.00, 5),
-- Hace 2 meses
('DEPOSITO', 3800.00, DATEADD(DAY, -56, GETDATE()), 'Depósito ventas', 8500.00, 12300.00, 5),
('RETIRO', 2000.00, DATEADD(DAY, -51, GETDATE()), 'Inversión negocio', 12300.00, 10300.00, 5),
('DEPOSITO', 3300.00, DATEADD(DAY, -46, GETDATE()), 'Depósito ventas', 10300.00, 13600.00, 5),
('RETIRO', 1500.00, DATEADD(DAY, -41, GETDATE()), 'Gastos operacionales', 13600.00, 12100.00, 5),
-- Hace 1 mes
('DEPOSITO', 3600.00, DATEADD(DAY, -26, GETDATE()), 'Depósito ventas', 12100.00, 15700.00, 5),
('RETIRO', 2200.00, DATEADD(DAY, -20, GETDATE()), 'Pago impuestos', 15700.00, 13500.00, 5),
('DEPOSITO', 3400.00, DATEADD(DAY, -14, GETDATE()), 'Cobro facturas', 13500.00, 16900.00, 5),
('RETIRO', 8400.00, DATEADD(DAY, -8, GETDATE()), 'Compra equipos', 16900.00, 8500.00, 5);
-- Promedio Depósitos: ~3,400, Promedio Retiros: ~1,900
-- Monto Máximo: ((3400-1900) × 0.6) × 9 = $8,100

-- =====================================================
-- CLIENTE 6: Pedro (1756789012) - INGRESOS BAJOS
-- Salario mínimo, gastos altos
-- Monto Máximo Esperado: ~$200
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 500.00, DATEADD(DAY, -84, GETDATE()), 'Depósito salario', 0.00, 500.00, 6),
('RETIRO', 400.00, DATEADD(DAY, -78, GETDATE()), 'Pago arriendo', 500.00, 100.00, 6),
('DEPOSITO', 500.00, DATEADD(DAY, -72, GETDATE()), 'Depósito salario', 100.00, 600.00, 6),
('RETIRO', 420.00, DATEADD(DAY, -67, GETDATE()), 'Pago servicios', 600.00, 180.00, 6),
-- Hace 2 meses
('DEPOSITO', 520.00, DATEADD(DAY, -54, GETDATE()), 'Depósito salario', 180.00, 700.00, 6),
('RETIRO', 450.00, DATEADD(DAY, -49, GETDATE()), 'Pago arriendo', 700.00, 250.00, 6),
('DEPOSITO', 500.00, DATEADD(DAY, -44, GETDATE()), 'Depósito salario', 250.00, 750.00, 6),
('RETIRO', 400.00, DATEADD(DAY, -39, GETDATE()), 'Compras', 750.00, 350.00, 6),
-- Hace 1 mes
('DEPOSITO', 510.00, DATEADD(DAY, -24, GETDATE()), 'Depósito salario', 350.00, 860.00, 6),
('RETIRO', 420.00, DATEADD(DAY, -18, GETDATE()), 'Pago arriendo', 860.00, 440.00, 6),
('DEPOSITO', 500.00, DATEADD(DAY, -12, GETDATE()), 'Depósito salario', 440.00, 940.00, 6),
('RETIRO', 140.00, DATEADD(DAY, -6, GETDATE()), 'Compras', 940.00, 800.00, 6);
-- Promedio Depósitos: ~508, Promedio Retiros: ~407
-- Monto Máximo: ((508-407) × 0.6) × 9 = $545

-- =====================================================
-- CLIENTE 7: Patricia (1767890123) - SIN DEPÓSITO RECIENTE (NO APTA)
-- Último depósito hace MÁS de 30 días
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses (tiene movimientos antiguos)
('DEPOSITO', 1200.00, DATEADD(DAY, -90, GETDATE()), 'Depósito', 0.00, 1200.00, 7),
('RETIRO', 400.00, DATEADD(DAY, -85, GETDATE()), 'Retiro', 1200.00, 800.00, 7),
-- Hace 2 meses
('DEPOSITO', 1300.00, DATEADD(DAY, -60, GETDATE()), 'Depósito', 800.00, 2100.00, 7),
('RETIRO', 500.00, DATEADD(DAY, -55, GETDATE()), 'Retiro', 2100.00, 1600.00, 7),
-- Último depósito hace 35 días (FUERA del rango de 30 días)
('DEPOSITO', 1100.00, DATEADD(DAY, -35, GETDATE()), 'Último depósito', 1600.00, 2700.00, 7),
-- Solo retiros recientes
('RETIRO', 500.00, DATEADD(DAY, -20, GETDATE()), 'Retiro reciente', 2700.00, 2200.00, 7);
-- Resultado: RECHAZADO por no tener depósito en último mes

-- =====================================================
-- CLIENTE 8: Roberto (1778901234) - CON CRÉDITO ACTIVO
-- Tiene movimientos pero YA tiene un crédito
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Movimientos normales
('DEPOSITO', 1000.00, DATEADD(DAY, -88, GETDATE()), 'Depósito salario', 0.00, 1000.00, 8),
('RETIRO', 300.00, DATEADD(DAY, -83, GETDATE()), 'Pago cuota crédito', 1000.00, 700.00, 8),
('DEPOSITO', 1050.00, DATEADD(DAY, -58, GETDATE()), 'Depósito salario', 700.00, 1750.00, 8),
('RETIRO', 300.00, DATEADD(DAY, -53, GETDATE()), 'Pago cuota crédito', 1750.00, 1450.00, 8),
('DEPOSITO', 1000.00, DATEADD(DAY, -28, GETDATE()), 'Depósito salario', 1450.00, 2450.00, 8),
('RETIRO', 300.00, DATEADD(DAY, -23, GETDATE()), 'Pago cuota crédito', 2450.00, 2150.00, 8),
('DEPOSITO', 1050.00, DATEADD(DAY, -15, GETDATE()), 'Bono', 2150.00, 3200.00, 8);
-- Resultado: RECHAZADO por tener crédito activo

-- =====================================================
-- CLIENTE 9: Carmen (1789012345) - DIVORCIADA (Estado civil diferente)
-- Perfil normal, estado civil no afecta
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
('DEPOSITO', 950.00, DATEADD(DAY, -86, GETDATE()), 'Depósito salario', 0.00, 950.00, 9),
('RETIRO', 350.00, DATEADD(DAY, -80, GETDATE()), 'Pago servicios', 950.00, 600.00, 9),
('DEPOSITO', 1000.00, DATEADD(DAY, -56, GETDATE()), 'Depósito salario', 600.00, 1600.00, 9),
('RETIRO', 400.00, DATEADD(DAY, -50, GETDATE()), 'Compras', 1600.00, 1200.00, 9),
('DEPOSITO', 950.00, DATEADD(DAY, -26, GETDATE()), 'Depósito salario', 1200.00, 2150.00, 9),
('RETIRO', 380.00, DATEADD(DAY, -20, GETDATE()), 'Pago varios', 2150.00, 1770.00, 9),
('DEPOSITO', 1000.00, DATEADD(DAY, -12, GETDATE()), 'Comisión', 1770.00, 2770.00, 9);
-- Monto Máximo: ~$2,500

-- =====================================================
-- CLIENTE 10: Jorge (1790123456) - CASADO EXACTAMENTE 25 AÑOS
-- Caso límite: cumple justo con la edad mínima
-- =====================================================
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
('DEPOSITO', 1100.00, DATEADD(DAY, -85, GETDATE()), 'Depósito salario', 0.00, 1100.00, 10),
('RETIRO', 450.00, DATEADD(DAY, -79, GETDATE()), 'Pago servicios', 1100.00, 650.00, 10),
('DEPOSITO', 1150.00, DATEADD(DAY, -55, GETDATE()), 'Depósito salario', 650.00, 1800.00, 10),
('RETIRO', 500.00, DATEADD(DAY, -49, GETDATE()), 'Compras', 1800.00, 1300.00, 10),
('DEPOSITO', 1100.00, DATEADD(DAY, -25, GETDATE()), 'Depósito salario', 1300.00, 2400.00, 10),
('RETIRO', 480.00, DATEADD(DAY, -19, GETDATE()), 'Pago tarjeta', 2400.00, 1920.00, 10),
('DEPOSITO', 1150.00, DATEADD(DAY, -10, GETDATE()), 'Bono', 1920.00, 3070.00, 10);
-- Monto Máximo: ~$2,700

GO

PRINT '============================================';
PRINT 'INSERTANDO CRÉDITO ACTIVO (Cliente 8)';
PRINT '============================================';

-- Crédito activo para Roberto (Cliente 8) - Para probar rechazo de segunda solicitud
INSERT INTO Credito (NumeroCredito, MontoCredito, TasaInteres, NumeroCuotas, CuotaMensual, FechaOtorgamiento, Estado, Descripcion, ClienteId) VALUES
('CRE20251115093421', 1800.00, 0.0133, 6, 320.00, '2024-11-15', 'ACTIVO', 'Compra electrodoméstico', 8);

-- Tabla de amortización para el crédito activo (6 cuotas)
INSERT INTO CuotaAmortizacion (NumeroCuota, ValorCuota, Interes, CapitalPagado, Saldo, CreditoId) VALUES
(1, 320.00, 23.94, 296.06, 1503.94, 1),
(2, 320.00, 20.00, 300.00, 1203.94, 1),
(3, 320.00, 16.01, 303.99, 899.95, 1),
(4, 320.00, 11.97, 308.03, 591.92, 1),
(5, 320.00, 7.87, 312.13, 279.79, 1),
(6, 320.00, 3.72, 316.28, 0.00, 1);

GO

-- =====================================================
-- VERIFICACIÓN Y PRUEBAS
-- =====================================================

PRINT '';
PRINT '============================================';
PRINT 'RESUMEN DE DATOS INSERTADOS';
PRINT '============================================';

SELECT 'Clientes' AS Tabla, COUNT(*) AS Total FROM Cliente
UNION ALL
SELECT 'Cuentas', COUNT(*) FROM Cuenta
UNION ALL
SELECT 'Movimientos', COUNT(*) FROM Movimiento
UNION ALL
SELECT 'Créditos', COUNT(*) FROM Credito
UNION ALL
SELECT 'Cuotas Amortización', COUNT(*) FROM CuotaAmortizacion;

PRINT '';
PRINT '============================================';
PRINT 'ANÁLISIS DE CASOS DE USO';
PRINT '============================================';

-- Mostrar clientes y su categorización
SELECT
    Cedula,
    Nombres + ' ' + Apellidos AS NombreCompleto,
    EstadoCivil,
    DATEDIFF(YEAR, FechaNacimiento, GETDATE()) AS Edad,
    CASE
        WHEN EstadoCivil = 'Casado' AND DATEDIFF(YEAR, FechaNacimiento, GETDATE()) < 25 THEN '❌ NO APTO: Casado menor 25 años'
        WHEN EstadoCivil = 'Casada' AND DATEDIFF(YEAR, FechaNacimiento, GETDATE()) < 25 THEN '❌ NO APTO: Casada menor 25 años'
        WHEN Cedula = '1767890123' THEN '❌ NO APTO: Sin depósito reciente'
        WHEN Cedula = '1778901234' THEN '❌ NO APTO: Tiene crédito activo'
        ELSE '✅ APTO para crédito'
    END AS EstadoElegibilidad
FROM Cliente
ORDER BY ClienteId;

PRINT '';
PRINT '============================================';
PRINT 'MOVIMIENTOS POR TIPO';
PRINT '============================================';

SELECT
    TipoMovimiento,
    COUNT(*) AS Total,
    FORMAT(SUM(Monto), 'C', 'es-EC') AS MontoTotal
FROM Movimiento
GROUP BY TipoMovimiento;

PRINT '';
PRINT '============================================';
PRINT 'CRÉDITOS ACTIVOS';
PRINT '============================================';

SELECT
    c.NumeroCredito,
    cl.Nombres + ' ' + cl.Apellidos AS Cliente,
    FORMAT(c.MontoCredito, 'C', 'es-EC') AS Monto,
    c.NumeroCuotas AS Cuotas,
    FORMAT(c.CuotaMensual, 'C', 'es-EC') AS CuotaMensual,
    c.Estado
FROM Credito c
INNER JOIN Cliente cl ON c.ClienteId = cl.ClienteId;

PRINT '';
PRINT '============================================';
PRINT 'FIN DEL SCRIPT - BanquitoDB';
PRINT '============================================';

GO
