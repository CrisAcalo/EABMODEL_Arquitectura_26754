-- =====================================================
-- Script DDL - Comercializadora
-- Sistema de Facturación de Electrodomésticos
-- MySQL 8.0 (Convertido de SQL Server)
-- =====================================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS ComercializadoraDB
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE ComercializadoraDB;

-- =====================================================
-- CREAR TABLAS
-- =====================================================

-- Tabla Producto
DROP TABLE IF EXISTS DetalleFactura;
DROP TABLE IF EXISTS Factura;
DROP TABLE IF EXISTS Producto;

CREATE TABLE Producto (
    ProductoId INT AUTO_INCREMENT PRIMARY KEY,
    Codigo VARCHAR(20) NOT NULL UNIQUE,
    Nombre VARCHAR(100) NOT NULL,
    Descripcion VARCHAR(500),
    Precio DECIMAL(12,2) NOT NULL,
    Stock INT NOT NULL DEFAULT 0,
    Categoria VARCHAR(50),
    ImagenUrl VARCHAR(500),
    FechaRegistro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    INDEX IX_Producto_Codigo (Codigo),
    INDEX IX_Producto_Categoria (Categoria),
    INDEX IX_Producto_Estado (Estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla Factura
CREATE TABLE Factura (
    FacturaId INT AUTO_INCREMENT PRIMARY KEY,
    NumeroFactura VARCHAR(20) NOT NULL UNIQUE,
    CedulaCliente VARCHAR(10) NOT NULL,
    NombreCliente VARCHAR(200) NOT NULL,
    FormaPago VARCHAR(20) NOT NULL,
    Subtotal DECIMAL(12,2) NOT NULL,
    Descuento DECIMAL(12,2) NOT NULL DEFAULT 0,
    Total DECIMAL(12,2) NOT NULL,
    NumeroCredito VARCHAR(20),
    FechaEmision DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX IX_Factura_NumeroFactura (NumeroFactura),
    INDEX IX_Factura_CedulaCliente (CedulaCliente),
    INDEX IX_Factura_FormaPago (FormaPago),
    INDEX IX_Factura_FechaEmision (FechaEmision)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla DetalleFactura
CREATE TABLE DetalleFactura (
    DetalleId INT AUTO_INCREMENT PRIMARY KEY,
    FacturaId INT NOT NULL,
    ProductoId INT NOT NULL,
    Cantidad INT NOT NULL,
    PrecioUnitario DECIMAL(12,2) NOT NULL,
    Subtotal DECIMAL(12,2) NOT NULL,
    CONSTRAINT FK_DetalleFactura_Factura FOREIGN KEY (FacturaId) 
        REFERENCES Factura(FacturaId) ON DELETE CASCADE,
    CONSTRAINT FK_DetalleFactura_Producto FOREIGN KEY (ProductoId) 
        REFERENCES Producto(ProductoId),
    INDEX IX_DetalleFactura_FacturaId (FacturaId),
    INDEX IX_DetalleFactura_ProductoId (ProductoId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- ESTRUCTURA DE BASE DE DATOS CREADA
-- Tablas: Producto, Factura, DetalleFactura
-- =====================================================
