-- =====================================================
-- Script de creación de Base de Datos BanQuito
-- Sistema CORE + Módulo de Crédito
-- SQL Server
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
-- INSERTAR DATOS DE PRUEBA
-- =====================================================

-- Insertar 5 Clientes
INSERT INTO Cliente (Cedula, Nombres, Apellidos, FechaNacimiento, EstadoCivil, Direccion, Telefono, Email) VALUES
('1234567890', 'Juan Carlos', 'Pérez González', '1985-03-15', 'Casado', 'Av. Amazonas N34-451', '0998765432', 'juan.perez@email.com'),
('0987654321', 'María Fernanda', 'López Martínez', '1990-07-22', 'Soltera', 'Calle 10 de Agosto 234', '0987654321', 'maria.lopez@email.com'),
('1122334455', 'Pedro Antonio', 'Ramírez Silva', '1988-11-30', 'Casado', 'Av. 6 de Diciembre 789', '0991234567', 'pedro.ramirez@email.com'),
('5566778899', 'Ana Lucía', 'Torres Vega', '1992-04-18', 'Soltera', 'Calle Colón 456', '0998877665', 'ana.torres@email.com'),
('9988776655', 'Luis Fernando', 'Morales Castro', '1987-09-05', 'Divorciado', 'Av. Shyris N45-123', '0987766554', 'luis.morales@email.com');
GO

-- Insertar 5 Cuentas (una por cliente)
INSERT INTO Cuenta (NumeroCuenta, TipoCuenta, Saldo, FechaApertura, Estado, ClienteId) VALUES
('2001234567', 'AHORROS', 5000.00, '2023-01-15', 'ACTIVA', 1),
('2001234568', 'CORRIENTE', 8500.00, '2023-02-20', 'ACTIVA', 2),
('2001234569', 'AHORROS', 3200.00, '2023-03-10', 'ACTIVA', 3),
('2001234570', 'AHORROS', 6750.00, '2023-04-05', 'ACTIVA', 4),
('2001234571', 'CORRIENTE', 4300.00, '2023-05-12', 'ACTIVA', 5);
GO

-- =====================================================
-- Insertar 50 Movimientos (Depósitos y Retiros)
-- =====================================================

-- Movimientos para Cliente 1 (cedula: 1234567890) - Cuenta 1
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 1500.00, DATEADD(DAY, -90, GETDATE()), 'Depósito inicial', 0.00, 1500.00, 1),
('DEPOSITO', 2000.00, DATEADD(DAY, -85, GETDATE()), 'Depósito salario', 1500.00, 3500.00, 1),
('RETIRO', 500.00, DATEADD(DAY, -80, GETDATE()), 'Retiro cajero', 3500.00, 3000.00, 1),
('DEPOSITO', 1800.00, DATEADD(DAY, -75, GETDATE()), 'Transferencia recibida', 3000.00, 4800.00, 1),
-- Hace 2 meses
('DEPOSITO', 2200.00, DATEADD(DAY, -60, GETDATE()), 'Depósito salario', 4800.00, 7000.00, 1),
('RETIRO', 800.00, DATEADD(DAY, -55, GETDATE()), 'Pago servicios', 7000.00, 6200.00, 1),
('DEPOSITO', 1500.00, DATEADD(DAY, -50, GETDATE()), 'Bono', 6200.00, 7700.00, 1),
('RETIRO', 1200.00, DATEADD(DAY, -45, GETDATE()), 'Compras', 7700.00, 6500.00, 1),
-- Hace 1 mes
('DEPOSITO', 2100.00, DATEADD(DAY, -30, GETDATE()), 'Depósito salario', 6500.00, 8600.00, 1),
('RETIRO', 3600.00, DATEADD(DAY, -25, GETDATE()), 'Pago tarjeta', 8600.00, 5000.00, 1);

-- Movimientos para Cliente 2 (cedula: 0987654321) - Cuenta 2
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 3000.00, DATEADD(DAY, -88, GETDATE()), 'Depósito inicial', 0.00, 3000.00, 2),
('DEPOSITO', 2500.00, DATEADD(DAY, -82, GETDATE()), 'Depósito salario', 3000.00, 5500.00, 2),
('RETIRO', 1000.00, DATEADD(DAY, -78, GETDATE()), 'Retiro', 5500.00, 4500.00, 2),
('DEPOSITO', 3500.00, DATEADD(DAY, -73, GETDATE()), 'Comisión', 4500.00, 8000.00, 2),
-- Hace 2 meses
('DEPOSITO', 2800.00, DATEADD(DAY, -58, GETDATE()), 'Depósito salario', 8000.00, 10800.00, 2),
('RETIRO', 1500.00, DATEADD(DAY, -53, GETDATE()), 'Pago servicios', 10800.00, 9300.00, 2),
('DEPOSITO', 2000.00, DATEADD(DAY, -48, GETDATE()), 'Transferencia', 9300.00, 11300.00, 2),
('RETIRO', 2000.00, DATEADD(DAY, -43, GETDATE()), 'Compras', 11300.00, 9300.00, 2),
-- Hace 1 mes
('DEPOSITO', 3200.00, DATEADD(DAY, -28, GETDATE()), 'Depósito salario', 9300.00, 12500.00, 2),
('RETIRO', 4000.00, DATEADD(DAY, -22, GETDATE()), 'Inversión', 12500.00, 8500.00, 2);

-- Movimientos para Cliente 3 (cedula: 1122334455) - Cuenta 3
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 1200.00, DATEADD(DAY, -87, GETDATE()), 'Depósito inicial', 0.00, 1200.00, 3),
('DEPOSITO', 1800.00, DATEADD(DAY, -81, GETDATE()), 'Depósito salario', 1200.00, 3000.00, 3),
('RETIRO', 600.00, DATEADD(DAY, -76, GETDATE()), 'Retiro', 3000.00, 2400.00, 3),
('DEPOSITO', 1500.00, DATEADD(DAY, -71, GETDATE()), 'Transferencia', 2400.00, 3900.00, 3),
-- Hace 2 meses
('DEPOSITO', 1900.00, DATEADD(DAY, -57, GETDATE()), 'Depósito salario', 3900.00, 5800.00, 3),
('RETIRO', 900.00, DATEADD(DAY, -52, GETDATE()), 'Pago servicios', 5800.00, 4900.00, 3),
('DEPOSITO', 1600.00, DATEADD(DAY, -47, GETDATE()), 'Bono', 4900.00, 6500.00, 3),
('RETIRO', 1800.00, DATEADD(DAY, -42, GETDATE()), 'Compras', 6500.00, 4700.00, 3),
-- Hace 1 mes
('DEPOSITO', 2000.00, DATEADD(DAY, -27, GETDATE()), 'Depósito salario', 4700.00, 6700.00, 3),
('RETIRO', 3500.00, DATEADD(DAY, -21, GETDATE()), 'Pago deuda', 6700.00, 3200.00, 3);

-- Movimientos para Cliente 4 (cedula: 5566778899) - Cuenta 4
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 2500.00, DATEADD(DAY, -86, GETDATE()), 'Depósito inicial', 0.00, 2500.00, 4),
('DEPOSITO', 2200.00, DATEADD(DAY, -79, GETDATE()), 'Depósito salario', 2500.00, 4700.00, 4),
('RETIRO', 700.00, DATEADD(DAY, -74, GETDATE()), 'Retiro', 4700.00, 4000.00, 4),
('DEPOSITO', 2800.00, DATEADD(DAY, -69, GETDATE()), 'Comisión', 4000.00, 6800.00, 4),
-- Hace 2 meses
('DEPOSITO', 2400.00, DATEADD(DAY, -56, GETDATE()), 'Depósito salario', 6800.00, 9200.00, 4),
('RETIRO', 1200.00, DATEADD(DAY, -51, GETDATE()), 'Pago servicios', 9200.00, 8000.00, 4),
('DEPOSITO', 2100.00, DATEADD(DAY, -46, GETDATE()), 'Transferencia', 8000.00, 10100.00, 4),
('RETIRO', 1500.00, DATEADD(DAY, -41, GETDATE()), 'Compras', 10100.00, 8600.00, 4),
-- Hace 1 mes
('DEPOSITO', 2600.00, DATEADD(DAY, -26, GETDATE()), 'Depósito salario', 8600.00, 11200.00, 4),
('RETIRO', 4450.00, DATEADD(DAY, -20, GETDATE()), 'Pago varios', 11200.00, 6750.00, 4);

-- Movimientos para Cliente 5 (cedula: 9988776655) - Cuenta 5
INSERT INTO Movimiento (TipoMovimiento, Monto, FechaMovimiento, Descripcion, SaldoAnterior, SaldoNuevo, CuentaId) VALUES
-- Hace 3 meses
('DEPOSITO', 1800.00, DATEADD(DAY, -84, GETDATE()), 'Depósito inicial', 0.00, 1800.00, 5),
('DEPOSITO', 2000.00, DATEADD(DAY, -77, GETDATE()), 'Depósito salario', 1800.00, 3800.00, 5),
('RETIRO', 800.00, DATEADD(DAY, -72, GETDATE()), 'Retiro', 3800.00, 3000.00, 5),
('DEPOSITO', 2300.00, DATEADD(DAY, -67, GETDATE()), 'Transferencia', 3000.00, 5300.00, 5),
-- Hace 2 meses
('DEPOSITO', 2100.00, DATEADD(DAY, -54, GETDATE()), 'Depósito salario', 5300.00, 7400.00, 5),
('RETIRO', 1000.00, DATEADD(DAY, -49, GETDATE()), 'Pago servicios', 7400.00, 6400.00, 5),
('DEPOSITO', 1900.00, DATEADD(DAY, -44, GETDATE()), 'Bono', 6400.00, 8300.00, 5),
('RETIRO', 1600.00, DATEADD(DAY, -39, GETDATE()), 'Compras', 8300.00, 6700.00, 5),
-- Hace 1 mes
('DEPOSITO', 2200.00, DATEADD(DAY, -24, GETDATE()), 'Depósito salario', 6700.00, 8900.00, 5),
('RETIRO', 4600.00, DATEADD(DAY, -18, GETDATE()), 'Pago tarjeta', 8900.00, 4300.00, 5);
GO

-- =====================================================
-- VERIFICACIÓN DE DATOS
-- =====================================================

SELECT 'Clientes insertados:' AS Descripcion, COUNT(*) AS Total FROM Cliente;
SELECT 'Cuentas insertadas:' AS Descripcion, COUNT(*) AS Total FROM Cuenta;
SELECT 'Movimientos insertados:' AS Descripcion, COUNT(*) AS Total FROM Movimiento;

SELECT TipoMovimiento, COUNT(*) AS Total
FROM Movimiento
GROUP BY TipoMovimiento;

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================
