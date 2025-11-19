-- =====================================================
-- Script DDL (Data Definition Language) - Comercializadora
-- Sistema de Facturación de Electrodomésticos
-- SQL Server
-- Contiene: CREATE DATABASE, CREATE TABLE, INDEX, CONSTRAINTS
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

PRINT '============================================';
PRINT 'ESTRUCTURA DE BASE DE DATOS CREADA';
PRINT '============================================';
PRINT 'Tablas creadas: Producto, Factura, DetalleFactura';
PRINT 'Índices y constraints aplicados correctamente';
PRINT '============================================';

GO