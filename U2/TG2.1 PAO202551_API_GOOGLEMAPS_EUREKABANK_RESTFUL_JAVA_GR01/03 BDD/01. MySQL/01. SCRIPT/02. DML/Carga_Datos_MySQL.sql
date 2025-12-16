USE eurekabank;

-- =============================================
-- Eliminar datos existentes
-- Desactivamos chequeo de llaves foráneas temporalmente para evitar errores
-- =============================================
SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM Movimiento;
DELETE FROM Cuenta;
DELETE FROM Asignado;
DELETE FROM Cliente;
DELETE FROM Empleado;
DELETE FROM Sucursal;
DELETE FROM Parametro;
DELETE FROM TipoMovimiento;
DELETE FROM InteresMensual;
DELETE FROM CostoMovimiento;
DELETE FROM CargoMantenimiento;
DELETE FROM Moneda;
DELETE FROM Contador;

-- Reactivamos chequeo de llaves foráneas
SET FOREIGN_KEY_CHECKS = 1;

-- =============================================
-- Cargar Datos de Prueba
-- =============================================

-- Tabla: Moneda

INSERT INTO Moneda VALUES ( '01', 'Soles' );
INSERT INTO Moneda VALUES ( '02', 'Dolares' );

-- Tabla: CargoMantenimiento

INSERT INTO CargoMantenimiento VALUES ( '01', 3500.00, 7.00 );
INSERT INTO CargoMantenimiento VALUES ( '02', 1200.00, 2.50 );

-- Tabla: CostoMovimiento

INSERT INTO CostoMovimiento VALUES ( '01', 2.00 );
INSERT INTO CostoMovimiento VALUES ( '02', 0.60 );

-- Tabla: InteresMensual

INSERT INTO InteresMensual VALUES ( '01', 0.70 );
INSERT INTO InteresMensual VALUES ( '02', 0.60 );

-- Tabla: TipoMovimiento

INSERT INTO TipoMovimiento VALUES( '001', 'Apertura de Cuenta', 'INGRESO', 'ACTIVO' );
INSERT INTO TipoMovimiento VALUES( '002', 'Cancelar Cuenta', 'SALIDA', 'ACTIVO' );
INSERT INTO TipoMovimiento VALUES( '003', 'Deposito', 'INGRESO', 'ACTIVO' );
INSERT INTO TipoMovimiento VALUES( '004', 'Retiro', 'SALIDA', 'ACTIVO' );
INSERT INTO TipoMovimiento VALUES( '005', 'Interes', 'INGRESO', 'ACTIVO' );
INSERT INTO TipoMovimiento VALUES( '006', 'Mantenimiento', 'SALIDA', 'ACTIVO' );
INSERT INTO TipoMovimiento VALUES( '007', 'ITF', 'SALIDA', 'ACTIVO' );
INSERT INTO TipoMovimiento VALUES( '008', 'Transferencia', 'INGRESO', 'ACTIVO' );
INSERT INTO TipoMovimiento VALUES( '009', 'Transferencia', 'SALIDA', 'ACTIVO' );
INSERT INTO TipoMovimiento VALUES( '010', 'Cargo por Movimiento', 'SALIDA', 'ACTIVO' );

-- Tabla: Sucursal
-- Nota: En MySQL no se requiere SET IDENTITY_INSERT. Se inserta el ID explícitamente y funciona.

INSERT INTO Sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
VALUES( 1, 'Matriz', 'Quito', 'Av. Amazonas N24-03 y Colon', 2, -0.18070000, -78.46780000 );
INSERT INTO Sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
VALUES( 2, 'Norte', 'Guayaquil', 'Av. Francisco de Orellana - WTC', 3, -2.17000000, -79.92240000 );
INSERT INTO Sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
VALUES( 3, 'Centro Historico', 'Cuenca', 'Calle Bolivar 7-35 y Sucre', 1, -2.90010000, -79.00590000 );
INSERT INTO Sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
VALUES( 4, 'Sur', 'Guayaquil', 'Av. Juan Tanca Marengo Km 2.5', 0, -2.22000000, -79.89000000 );
INSERT INTO Sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
VALUES( 5, 'Centro', 'Ambato', 'Calle Bolivar 12-45 y Castillo', 0, -1.24900000, -78.61670000 );
INSERT INTO Sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
VALUES( 6, 'Puerto', 'Manta', 'Av. 4 de Noviembre y Calle 13', 0, -0.95360000, -80.73270000 );
INSERT INTO Sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
VALUES( 7, 'Loja', 'Loja', 'Calle 10 de Agosto 14-21', 0, -3.99300000, -79.20400000 );


-- Tabla: Empleado

INSERT INTO Empleado VALUES( '9999', 'Internet', 'Internet', 'internet', 'Internet', 'internet', 'internet', 'internet' );
INSERT INTO Empleado VALUES( '0001', 'Romero', 'Castillo', 'Carlos Alberto', 'Guayaquil', 'Av. 9 de Octubre 1456', 'cromero', 'chicho' );
INSERT INTO Empleado VALUES( '0002', 'Castro', 'Vargas', 'Lidia', 'Quito', 'Av. Shyris N34-142', 'lcastro', 'flaca' );
INSERT INTO Empleado VALUES( '0003', 'Reyes', 'Ortiz', 'Claudia', 'Quito', 'Av. Gonzalez Suarez N11-243', 'creyes', 'linda' );
INSERT INTO Empleado VALUES( '0004', 'Ramos', 'Garibay', 'Angelica', 'Cuenca', 'Calle Borrero 8-45', 'aramos', 'china' );
INSERT INTO Empleado VALUES( '0005', 'Ruiz', 'Zabaleta', 'Claudia', 'Loja', 'Calle Bolivar 15-32', 'cvalencia', 'angel' );
INSERT INTO Empleado VALUES( '0006', 'Cruz', 'Tarazona', 'Ricardo', 'Ambato', 'Av. Cevallos 304', 'rcruz', 'cerebro' );
INSERT INTO Empleado VALUES( '0007', 'Diaz', 'Flores', 'Edith', 'Guayaquil', 'Av. Luis Plaza Dañin 546', 'ediaz', 'princesa' );
INSERT INTO Empleado VALUES( '0008', 'Sarmiento', 'Bellido', 'Claudia Rocio', 'Ambato', 'Calle Rocafuerte 1567', 'csarmiento', 'chinita' );
INSERT INTO Empleado VALUES( '0009', 'Pachas', 'Sifuentes', 'Luis Alberto', 'Guayaquil', 'Av. Kennedy Norte 1263', 'lpachas', 'gato' );
INSERT INTO Empleado VALUES( '0010', 'Tello', 'Alarcon', 'Hugo Valentin', 'Loja', 'Av. Manuel Carrion 865', 'htello', 'machupichu' );
INSERT INTO Empleado VALUES( '0011', 'Carrasco', 'Vargas', 'Pedro Hugo', 'Cuenca', 'Calle Gran Colombia 1265', 'pcarrasco', 'tinajones' );

-- Asignado

INSERT INTO Asignado VALUES( '000001', 1, '0004', '20071115', null );
INSERT INTO Asignado VALUES( '000002', 2, '0001', '20071120', null );
INSERT INTO Asignado VALUES( '000003', 3, '0002', '20071128', null );
INSERT INTO Asignado VALUES( '000004', 4, '0003', '20071212', '20080325' );
INSERT INTO Asignado VALUES( '000005', 5, '0006', '20071220', null );
INSERT INTO Asignado VALUES( '000006', 6, '0005', '20080105', '20090415' );
INSERT INTO Asignado VALUES( '000007', 4, '0007', '20080107', null );
INSERT INTO Asignado VALUES( '000008', 5, '0008', '20080107', null );
INSERT INTO Asignado VALUES( '000009', 1, '0011', '20080108', null );
INSERT INTO Asignado VALUES( '000010', 2, '0009', '20080108', null );
INSERT INTO Asignado VALUES( '000011', 6, '0010', '20080108', null );
INSERT INTO Asignado VALUES( '000012', 4, '0005', '20090416', null );


-- Tabla: Parametro

INSERT INTO Parametro VALUES( '001', 'ITF - Impuesto a la Transacciones Financieras', '0.08', 'ACTIVO' );
INSERT INTO Parametro VALUES( '002', 'Número de Operaciones Sin Costo', '15', 'ACTIVO' );

-- Tabla: Cliente

INSERT INTO Cliente VALUES( '00001', 'CORONEL', 'CASTILLO', 'ERIC GUSTAVO', '17209148', 'Quito', 'La Carolina', '02-966-6445', 'gcoronel@viabcp.com' );
INSERT INTO Cliente VALUES( '00002', 'VALENCIA', 'MORALES', 'PEDRO HUGO', '09157617', 'Guayaquil', 'Kennedy Norte', '04-924-7834', 'pvalencia@terra.com.pe' );
INSERT INTO Cliente VALUES( '00003', 'MARCELO', 'VILLALOBOS', 'RICARDO', '01076236', 'Cuenca', 'El Centro', '07-993-6296', 'ricardomarcelo@hotmail.com' );
INSERT INTO Cliente VALUES( '00004', 'ROMERO', 'CASTILLO', 'CARLOS ALBERTO', '17065319', 'Quito', 'Cumbayá', '02-865-8476', 'c.romero@hotmail.com' );
INSERT INTO Cliente VALUES( '00005', 'ARANDA', 'LUNA', 'ALAN ALBERTO', '17087561', 'Quito', 'Gonzalez Suarez', '02-834-6712', 'a.aranda@hotmail.com' );
INSERT INTO Cliente VALUES( '00006', 'AYALA', 'PAZ', 'JORGE LUIS', '01067924', 'Cuenca', 'El Ejido', '07-963-3476', 'j.ayala@yahoo.com' );
INSERT INTO Cliente VALUES( '00007', 'CHAVEZ', 'CANALES', 'EDGAR RAFAEL', '09014569', 'Guayaquil', 'Samborondón', '04-999-9667', 'e.chavez@gmail.com' );
INSERT INTO Cliente VALUES( '00008', 'FLORES', 'CHAFLOQUE', 'ROSA LIZET', '17077345', 'Quito', 'La Floresta', '02-966-8756', 'r.florez@hotmail.com' );
INSERT INTO Cliente VALUES( '00009', 'FLORES', 'CASTILLO', 'CRISTIAN RAFAEL', '17034672', 'Quito', 'Iñaquito', '02-978-4376', 'c.flores@hotmail.com' );
INSERT INTO Cliente VALUES( '00010', 'GONZALES', 'GARCIA', 'GABRIEL ALEJANDRO', '09019237', 'Guayaquil', 'Urdesa', '04-945-5678', 'g.gonzales@yahoo.es' );
INSERT INTO Cliente VALUES( '00011', 'LAY', 'VALLEJOS', 'JUAN CARLOS', '01094228', 'Cuenca', 'Totoracocha', '07-956-1265', 'j.lay@peru.com' );
INSERT INTO Cliente VALUES( '00012', 'MONTALVO', 'SOTO', 'DEYSI LIDIA', '17061237', 'Quito', 'La Mariscal', '02-965-6723', 'd.montalvo@hotmail.com' );
INSERT INTO Cliente VALUES( '00013', 'RICALDE', 'RAMIREZ', 'ROSARIO ESMERALDA', '09076132', 'Guayaquil', 'La Alborada', '04-991-2354', 'r.ricalde@gmail.com' );
INSERT INTO Cliente VALUES( '00014', 'RODRIGUEZ', 'FLORES', 'ENRIQUE MANUEL', '01077334', 'Cuenca', 'El Batan', '07-976-8283', 'e.rodriguez@gmail.com' );
INSERT INTO Cliente VALUES( '00015', 'ROJAS', 'OSCANOA', 'FELIX NINO', '17023894', 'Quito', 'Centro Historico', '02-962-3215', 'f.rojas@yahoo.com' );
INSERT INTO Cliente VALUES( '00016', 'TEJADA', 'DEL AGUILA', 'TANIA LORENA', '09044679', 'Guayaquil', 'Ceibos', '04-966-2385', 't.tejada@hotmail.com' );
INSERT INTO Cliente VALUES( '00017', 'VALDEVIESO', 'LEYVA', 'LIDIA ROXANA', '17045268', 'Quito', 'La Pradera', '02-956-7895', 'r.valdivieso@terra.com.pe' );
INSERT INTO Cliente VALUES( '00018', 'VALENTIN', 'COTRINA', 'JUAN DIEGO', '01039824', 'Cuenca', 'El Vergel', '07-921-1245', 'j.valentin@terra.com.pe' );
INSERT INTO Cliente VALUES( '00019', 'YAURICASA', 'BAUTISTA', 'YESABETH', '17093458', 'Quito', 'La Vicentina', '02-977-7577', 'y.yauricasa@terra.com.pe' );
INSERT INTO Cliente VALUES( '00020', 'ZEGARRA', 'GARCIA', 'FERNANDO MOISES', '09077236', 'Guayaquil', 'Bellavista', '04-936-4587', 'f.zegarra@hotmail.com' );

-- Tabla: Cuenta

INSERT INTO Cuenta VALUES('00200001','01',2,'0001','00008',7000,'20220105','ACTIVO',15,'123456');
INSERT INTO Cuenta VALUES('00200002','01',2,'0001','00001',6800,'20220109','ACTIVO',3,'123456');
INSERT INTO Cuenta VALUES('00200003','02',2,'0001','00007',6000,'20220111','ACTIVO',6,'123456');
INSERT INTO Cuenta VALUES('00100001','01',1,'0004','00005',6900,'20220106','ACTIVO',7,'123456');
INSERT INTO Cuenta VALUES('00100002','02',1,'0004','00005',4500,'20220108','ACTIVO',4,'123456');
INSERT INTO Cuenta VALUES('00300001','01',3,'0002','00010',0000,'20220107','CANCELADO',3,'123456');

-- Tabla: Movimiento

INSERT INTO Movimiento VALUES('00100002',01,'20220108','0004','001',1800,null);
INSERT INTO Movimiento VALUES('00100002',02,'20220125','0004','004',1000,null);
INSERT INTO Movimiento VALUES('00100002',03,'20220213','0004','003',2200,null);
INSERT INTO Movimiento VALUES('00100002',04,'20220308','0004','003',1500,null);

INSERT INTO Movimiento VALUES('00100001',01,'20220106','0004','001',2800,null);
INSERT INTO Movimiento VALUES('00100001',02,'20220115','0004','003',3200,null);
INSERT INTO Movimiento VALUES('00100001',03,'20220120','0004','004',0800,null);
INSERT INTO Movimiento VALUES('00100001',04,'20220214','0004','003',2000,null);
INSERT INTO Movimiento VALUES('00100001',05,'20220225','0004','004',0500,null);
INSERT INTO Movimiento VALUES('00100001',06,'20220303','0004','004',0800,null);
INSERT INTO Movimiento VALUES('00100001',07,'20220315','0004','003',1000,null);

INSERT INTO Movimiento VALUES('00200003',01,'20220111','0001','001',2500,null);
INSERT INTO Movimiento VALUES('00200003',02,'20220117','0001','003',1500,null);
INSERT INTO Movimiento VALUES('00200003',03,'20220120','0001','004',0500,null);
INSERT INTO Movimiento VALUES('00200003',04,'20220209','0001','004',0500,null);
INSERT INTO Movimiento VALUES('00200003',05,'20220225','0001','003',3500,null);
INSERT INTO Movimiento VALUES('00200003',06,'20220311','0001','004',0500,null);

INSERT INTO Movimiento VALUES('00200002',01,'20220109','0001','001',3800,null);
INSERT INTO Movimiento VALUES('00200002',02,'20220120','0001','003',4200,null);
INSERT INTO Movimiento VALUES('00200002',03,'20220306','0001','004',1200,null);

INSERT INTO Movimiento VALUES('00200001',01,'20220105','0001','001',5000,null);
INSERT INTO Movimiento VALUES('00200001',02,'20220107','0001','003',4000,null);
INSERT INTO Movimiento VALUES('00200001',03,'20220109','0001','004',2000,null);
INSERT INTO Movimiento VALUES('00200001',04,'20220111','0001','003',1000,null);
INSERT INTO Movimiento VALUES('00200001',05,'20220113','0001','003',2000,null);
INSERT INTO Movimiento VALUES('00200001',06,'20220115','0001','004',4000,null);
INSERT INTO Movimiento VALUES('00200001',07,'20220119','0001','003',2000,null);
INSERT INTO Movimiento VALUES('00200001',08,'20220121','0001','004',3000,null);
INSERT INTO Movimiento VALUES('00200001',09,'20220123','0001','003',7000,null);
INSERT INTO Movimiento VALUES('00200001',10,'20220127','0001','004',1000,null);
INSERT INTO Movimiento VALUES('00200001',11,'20220130','0001','004',3000,null);
INSERT INTO Movimiento VALUES('00200001',12,'20220204','0001','003',2000,null);
INSERT INTO Movimiento VALUES('00200001',13,'20220208','0001','004',4000,null);
INSERT INTO Movimiento VALUES('00200001',14,'20220213','0001','003',2000,null);
INSERT INTO Movimiento VALUES('00200001',15,'20220219','0001','004',1000,null);

INSERT INTO Movimiento VALUES('00300001',01,'20220107','0002','001',5600,null);
INSERT INTO Movimiento VALUES('00300001',02,'20220118','0002','003',1400,null);
INSERT INTO Movimiento VALUES('00300001',03,'20220125','0002','002',7000,null);

--  Tabla: Contador

INSERT INTO Contador VALUES( 'Moneda', 2, 2 );
INSERT INTO Contador VALUES( 'TipoMovimiento', 10, 3 );
INSERT INTO Contador VALUES( 'Sucursal', 7, 3 );
INSERT INTO Contador VALUES( 'Empleado', 11, 4 );
INSERT INTO Contador VALUES( 'Asignado', 12, 6 );
INSERT INTO Contador VALUES( 'Parametro', 2, 3 );
INSERT INTO Contador VALUES( 'Cliente', 20, 5 );