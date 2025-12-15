-- =====================================================
-- Script DDL (Data Definition Language) - BanQuito
-- Sistema CORE + Módulo de Crédito
-- MySQL 8.x
-- Contiene: CREATE DATABASE, CREATE TABLE, INDEX, CONSTRAINTS
-- =====================================================

-- Eliminar base de datos si existe
DROP DATABASE IF EXISTS BanquitoDB;

-- Crear base de datos
CREATE DATABASE BanquitoDB
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE BanquitoDB;

-- =====================================================
-- CREAR TABLAS
-- =====================================================

-- Tabla Cliente
CREATE TABLE Cliente (
    ClienteId BIGINT AUTO_INCREMENT PRIMARY KEY,
    Cedula VARCHAR(10) NOT NULL,
    Nombres VARCHAR(100) NOT NULL,
    Apellidos VARCHAR(100) NOT NULL,
    FechaNacimiento DATE NOT NULL,
    EstadoCivil VARCHAR(20),
    Direccion VARCHAR(200),
    Telefono VARCHAR(20),
    Email VARCHAR(100),
    
    -- Índices
    UNIQUE INDEX IX_Cliente_Cedula (Cedula),
    INDEX IX_Cliente_Nombres (Nombres)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla Cuenta
CREATE TABLE Cuenta (
    CuentaId BIGINT AUTO_INCREMENT PRIMARY KEY,
    NumeroCuenta VARCHAR(20) NOT NULL,
    TipoCuenta VARCHAR(20) NOT NULL,
    Saldo DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    FechaApertura DATE NOT NULL,
    Estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA',
    ClienteId BIGINT NOT NULL,
    
    -- Índices
    UNIQUE INDEX IX_Cuenta_NumeroCuenta (NumeroCuenta),
    INDEX IX_Cuenta_ClienteId (ClienteId),
    
    -- Foreign Key
    CONSTRAINT FK_Cuenta_Cliente 
        FOREIGN KEY (ClienteId) REFERENCES Cliente(ClienteId)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla Movimiento
CREATE TABLE Movimiento (
    MovimientoId BIGINT AUTO_INCREMENT PRIMARY KEY,
    TipoMovimiento VARCHAR(20) NOT NULL,
    Monto DECIMAL(12,2) NOT NULL,
    FechaMovimiento DATETIME NOT NULL,
    Descripcion VARCHAR(200),
    SaldoAnterior DECIMAL(12,2),
    SaldoNuevo DECIMAL(12,2),
    CuentaId BIGINT NOT NULL,
    
    -- Índices
    INDEX IX_Movimiento_Fecha (FechaMovimiento),
    INDEX IX_Movimiento_Tipo (TipoMovimiento),
    INDEX IX_Movimiento_CuentaId (CuentaId),
    
    -- Foreign Key
    CONSTRAINT FK_Movimiento_Cuenta 
        FOREIGN KEY (CuentaId) REFERENCES Cuenta(CuentaId)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla Credito
CREATE TABLE Credito (
    CreditoId BIGINT AUTO_INCREMENT PRIMARY KEY,
    NumeroCredito VARCHAR(20) NOT NULL,
    MontoCredito DECIMAL(12,2) NOT NULL,
    TasaInteres DECIMAL(5,4) NOT NULL,
    NumeroCuotas INT NOT NULL,
    CuotaMensual DECIMAL(12,2) NOT NULL,
    FechaOtorgamiento DATETIME NOT NULL,
    Estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    Descripcion VARCHAR(200),
    ClienteId BIGINT NOT NULL,
    
    -- Índices
    UNIQUE INDEX IX_Credito_NumeroCredito (NumeroCredito),
    INDEX IX_Credito_ClienteId (ClienteId),
    INDEX IX_Credito_Estado (Estado),
    
    -- Foreign Key
    CONSTRAINT FK_Credito_Cliente 
        FOREIGN KEY (ClienteId) REFERENCES Cliente(ClienteId)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla CuotaAmortizacion
CREATE TABLE CuotaAmortizacion (
    CuotaId BIGINT AUTO_INCREMENT PRIMARY KEY,
    NumeroCuota INT NOT NULL,
    ValorCuota DECIMAL(12,2) NOT NULL,
    Interes DECIMAL(12,2) NOT NULL,
    CapitalPagado DECIMAL(12,2) NOT NULL,
    Saldo DECIMAL(12,2) NOT NULL,
    CreditoId BIGINT NOT NULL,
    
    -- Índices
    INDEX IX_CuotaAmortizacion_CreditoId (CreditoId),
    INDEX IX_CuotaAmortizacion_NumeroCuota (NumeroCuota),
    
    -- Foreign Key con CASCADE DELETE
    CONSTRAINT FK_CuotaAmortizacion_Credito 
        FOREIGN KEY (CreditoId) REFERENCES Credito(CreditoId)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- MENSAJE DE CONFIRMACIÓN
-- =====================================================
SELECT '============================================' AS '';
SELECT 'ESTRUCTURA DE BASE DE DATOS CREADA' AS Mensaje;
SELECT '============================================' AS '';
SELECT 'Tablas creadas: Cliente, Cuenta, Movimiento, Credito, CuotaAmortizacion' AS Detalle;
SELECT 'Índices y constraints aplicados correctamente' AS Estado;
SELECT '============================================' AS '';
