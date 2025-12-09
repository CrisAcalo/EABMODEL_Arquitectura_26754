-- =====================================================
-- Script DDL (Data Definition Language) - BanQuito
-- Sistema CORE + Módulo de Crédito
-- SQL Server
-- Contiene: CREATE DATABASE, CREATE TABLE, INDEX, CONSTRAINTS
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

PRINT '============================================';
PRINT 'ESTRUCTURA DE BASE DE DATOS CREADA';
PRINT '============================================';
PRINT 'Tablas creadas: Cliente, Cuenta, Movimiento, Credito, CuotaAmortizacion';
PRINT 'Índices y constraints aplicados correctamente';
PRINT '============================================';

GO