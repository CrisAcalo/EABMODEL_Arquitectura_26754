use eurekabank;
go

-- =============================================
-- Eliminar datos existentes (orden inverso por FK)
-- =============================================

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
go

-- =============================================
-- Cargar Datos de Prueba
-- =============================================

-- Tabla: Moneda

insert into moneda values ( '01', 'Soles' );
insert into moneda values ( '02', 'Dolares' );

-- Tabla: CargoMantenimiento

insert into cargomantenimiento values ( '01', 3500.00, 7.00 );
insert into cargomantenimiento values ( '02', 1200.00, 2.50 );

-- Tabla: CargoMovimiento

insert into CostoMovimiento values ( '01', 2.00 );
insert into CostoMovimiento values ( '02', 0.60 );

-- Tabla: InteresMensual

insert into InteresMensual values ( '01', 0.70 );
insert into InteresMensual values ( '02', 0.60 );

-- Tabla: TipoMovimiento

insert into TipoMovimiento values( '001', 'Apertura de Cuenta', 'INGRESO', 'ACTIVO' );
insert into TipoMovimiento values( '002', 'Cancelar Cuenta', 'SALIDA', 'ACTIVO' );
insert into TipoMovimiento values( '003', 'Deposito', 'INGRESO', 'ACTIVO' );
insert into TipoMovimiento values( '004', 'Retiro', 'SALIDA', 'ACTIVO' );
insert into TipoMovimiento values( '005', 'Interes', 'INGRESO', 'ACTIVO' );
insert into TipoMovimiento values( '006', 'Mantenimiento', 'SALIDA', 'ACTIVO' );
insert into TipoMovimiento values( '007', 'ITF', 'SALIDA', 'ACTIVO' );
insert into TipoMovimiento values( '008', 'Transferencia', 'INGRESO', 'ACTIVO' );
insert into TipoMovimiento values( '009', 'Transferencia', 'SALIDA', 'ACTIVO' );
insert into TipoMovimiento values( '010', 'Cargo por Movimiento', 'SALIDA', 'ACTIVO' );

-- Tabla: Sucursal

SET IDENTITY_INSERT sucursal ON;

insert into sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
values( 1, 'Matriz', 'Quito', 'Av. Amazonas N24-03 y Colon', 2, -0.18070000, -78.46780000 );
insert into sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
values( 2, 'Norte', 'Guayaquil', 'Av. Francisco de Orellana - WTC', 3, -2.17000000, -79.92240000 );
insert into sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
values( 3, 'Centro Historico', 'Cuenca', 'Calle Bolivar 7-35 y Sucre', 1, -2.90010000, -79.00590000 );
insert into sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
values( 4, 'Sur', 'Guayaquil', 'Av. Juan Tanca Marengo Km 2.5', 0, -2.22000000, -79.89000000 );
insert into sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
values( 5, 'Centro', 'Ambato', 'Calle Bolivar 12-45 y Castillo', 0, -1.24900000, -78.61670000 );
insert into sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
values( 6, 'Puerto', 'Manta', 'Av. 4 de Noviembre y Calle 13', 0, -0.95360000, -80.73270000 );
insert into sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) 
values( 7, 'Loja', 'Loja', 'Calle 10 de Agosto 14-21', 0, -3.99300000, -79.20400000 );

SET IDENTITY_INSERT sucursal OFF;


-- Tabla: Empleado

INSERT INTO empleado VALUES( '9999', 'Internet', 'Internet', 'internet', 'Internet', 'internet', 'internet', 'internet' );
INSERT INTO empleado VALUES( '0001', 'Romero', 'Castillo', 'Carlos Alberto', 'Guayaquil', 'Av. 9 de Octubre 1456', 'cromero', 'chicho' );
INSERT INTO empleado VALUES( '0002', 'Castro', 'Vargas', 'Lidia', 'Quito', 'Av. Shyris N34-142', 'lcastro', 'flaca' );
INSERT INTO empleado VALUES( '0003', 'Reyes', 'Ortiz', 'Claudia', 'Quito', 'Av. Gonzalez Suarez N11-243', 'creyes', 'linda' );
INSERT INTO empleado VALUES( '0004', 'Ramos', 'Garibay', 'Angelica', 'Cuenca', 'Calle Borrero 8-45', 'aramos', 'china' );
INSERT INTO empleado VALUES( '0005', 'Ruiz', 'Zabaleta', 'Claudia', 'Loja', 'Calle Bolivar 15-32', 'cvalencia', 'angel' );
INSERT INTO empleado VALUES( '0006', 'Cruz', 'Tarazona', 'Ricardo', 'Ambato', 'Av. Cevallos 304', 'rcruz', 'cerebro' );
INSERT INTO empleado VALUES( '0007', 'Diaz', 'Flores', 'Edith', 'Guayaquil', 'Av. Luis Plaza Dañin 546', 'ediaz', 'princesa' );
INSERT INTO empleado VALUES( '0008', 'Sarmiento', 'Bellido', 'Claudia Rocio', 'Ambato', 'Calle Rocafuerte 1567', 'csarmiento', 'chinita' );
INSERT INTO empleado VALUES( '0009', 'Pachas', 'Sifuentes', 'Luis Alberto', 'Guayaquil', 'Av. Kennedy Norte 1263', 'lpachas', 'gato' );
INSERT INTO empleado VALUES( '0010', 'Tello', 'Alarcon', 'Hugo Valentin', 'Loja', 'Av. Manuel Carrion 865', 'htello', 'machupichu' );
INSERT INTO empleado VALUES( '0011', 'Carrasco', 'Vargas', 'Pedro Hugo', 'Cuenca', 'Calle Gran Colombia 1265', 'pcarrasco', 'tinajones' );

-- Asignado

insert into Asignado values( '000001', 1, '0004', '20071115', null );
insert into Asignado values( '000002', 2, '0001', '20071120', null );
insert into Asignado values( '000003', 3, '0002', '20071128', null );
insert into Asignado values( '000004', 4, '0003', '20071212', '20080325' );
insert into Asignado values( '000005', 5, '0006', '20071220', null );
insert into Asignado values( '000006', 6, '0005', '20080105', '20090415' );
insert into Asignado values( '000007', 4, '0007', '20080107', null );
insert into Asignado values( '000008', 5, '0008', '20080107', null );
insert into Asignado values( '000009', 1, '0011', '20080108', null );
insert into Asignado values( '000010', 2, '0009', '20080108', null );
insert into Asignado values( '000011', 6, '0010', '20080108', null );
insert into Asignado values( '000012', 4, '0005', '20090416', null );



-- Tabla: Parametro

insert into Parametro values( '001', 'ITF - Impuesto a la Transacciones Financieras', '0.08', 'ACTIVO' );
insert into Parametro values( '002', 'N�mero de Operaciones Sin Costo', '15', 'ACTIVO' );

-- Tabla: Cliente

insert into cliente values( '00001', 'CORONEL', 'CASTILLO', 'ERIC GUSTAVO', '17209148', 'Quito', 'La Carolina', '02-966-6445', 'gcoronel@viabcp.com' );
insert into cliente values( '00002', 'VALENCIA', 'MORALES', 'PEDRO HUGO', '09157617', 'Guayaquil', 'Kennedy Norte', '04-924-7834', 'pvalencia@terra.com.pe' );
insert into cliente values( '00003', 'MARCELO', 'VILLALOBOS', 'RICARDO', '01076236', 'Cuenca', 'El Centro', '07-993-6296', 'ricardomarcelo@hotmail.com' );
insert into cliente values( '00004', 'ROMERO', 'CASTILLO', 'CARLOS ALBERTO', '17065319', 'Quito', 'Cumbayá', '02-865-8476', 'c.romero@hotmail.com' );
insert into cliente values( '00005', 'ARANDA', 'LUNA', 'ALAN ALBERTO', '17087561', 'Quito', 'Gonzalez Suarez', '02-834-6712', 'a.aranda@hotmail.com' );
insert into cliente values( '00006', 'AYALA', 'PAZ', 'JORGE LUIS', '01067924', 'Cuenca', 'El Ejido', '07-963-3476', 'j.ayala@yahoo.com' );
insert into cliente values( '00007', 'CHAVEZ', 'CANALES', 'EDGAR RAFAEL', '09014569', 'Guayaquil', 'Samborondón', '04-999-9667', 'e.chavez@gmail.com' );
insert into cliente values( '00008', 'FLORES', 'CHAFLOQUE', 'ROSA LIZET', '17077345', 'Quito', 'La Floresta', '02-966-8756', 'r.florez@hotmail.com' );
insert into cliente values( '00009', 'FLORES', 'CASTILLO', 'CRISTIAN RAFAEL', '17034672', 'Quito', 'Iñaquito', '02-978-4376', 'c.flores@hotmail.com' );
insert into cliente values( '00010', 'GONZALES', 'GARCIA', 'GABRIEL ALEJANDRO', '09019237', 'Guayaquil', 'Urdesa', '04-945-5678', 'g.gonzales@yahoo.es' );
insert into cliente values( '00011', 'LAY', 'VALLEJOS', 'JUAN CARLOS', '01094228', 'Cuenca', 'Totoracocha', '07-956-1265', 'j.lay@peru.com' );
insert into cliente values( '00012', 'MONTALVO', 'SOTO', 'DEYSI LIDIA', '17061237', 'Quito', 'La Mariscal', '02-965-6723', 'd.montalvo@hotmail.com' );
insert into cliente values( '00013', 'RICALDE', 'RAMIREZ', 'ROSARIO ESMERALDA', '09076132', 'Guayaquil', 'La Alborada', '04-991-2354', 'r.ricalde@gmail.com' );
insert into cliente values( '00014', 'RODRIGUEZ', 'FLORES', 'ENRIQUE MANUEL', '01077334', 'Cuenca', 'El Batan', '07-976-8283', 'e.rodriguez@gmail.com' );
insert into cliente values( '00015', 'ROJAS', 'OSCANOA', 'FELIX NINO', '17023894', 'Quito', 'Centro Historico', '02-962-3215', 'f.rojas@yahoo.com' );
insert into cliente values( '00016', 'TEJADA', 'DEL AGUILA', 'TANIA LORENA', '09044679', 'Guayaquil', 'Ceibos', '04-966-2385', 't.tejada@hotmail.com' );
insert into cliente values( '00017', 'VALDEVIESO', 'LEYVA', 'LIDIA ROXANA', '17045268', 'Quito', 'La Pradera', '02-956-7895', 'r.valdivieso@terra.com.pe' );
insert into cliente values( '00018', 'VALENTIN', 'COTRINA', 'JUAN DIEGO', '01039824', 'Cuenca', 'El Vergel', '07-921-1245', 'j.valentin@terra.com.pe' );
insert into cliente values( '00019', 'YAURICASA', 'BAUTISTA', 'YESABETH', '17093458', 'Quito', 'La Vicentina', '02-977-7577', 'y.yauricasa@terra.com.pe' );
insert into cliente values( '00020', 'ZEGARRA', 'GARCIA', 'FERNANDO MOISES', '09077236', 'Guayaquil', 'Bellavista', '04-936-4587', 'f.zegarra@hotmail.com' );

-- Tabla: Cuenta

insert into cuenta values('00200001','01',2,'0001','00008',7000,'20220105','ACTIVO',15,'123456');
insert into cuenta values('00200002','01',2,'0001','00001',6800,'20220109','ACTIVO',3,'123456');
insert into cuenta values('00200003','02',2,'0001','00007',6000,'20220111','ACTIVO',6,'123456');
insert into cuenta values('00100001','01',1,'0004','00005',6900,'20220106','ACTIVO',7,'123456');
insert into cuenta values('00100002','02',1,'0004','00005',4500,'20220108','ACTIVO',4,'123456');
insert into cuenta values('00300001','01',3,'0002','00010',0000,'20220107','CANCELADO',3,'123456');

-- Tabla: Movimiento

insert into movimiento values('00100002',01,'20220108','0004','001',1800,null);
insert into movimiento values('00100002',02,'20220125','0004','004',1000,null);
insert into movimiento values('00100002',03,'20220213','0004','003',2200,null);
insert into movimiento values('00100002',04,'20220308','0004','003',1500,null);

insert into movimiento values('00100001',01,'20220106','0004','001',2800,null);
insert into movimiento values('00100001',02,'20220115','0004','003',3200,null);
insert into movimiento values('00100001',03,'20220120','0004','004',0800,null);
insert into movimiento values('00100001',04,'20220214','0004','003',2000,null);
insert into movimiento values('00100001',05,'20220225','0004','004',0500,null);
insert into movimiento values('00100001',06,'20220303','0004','004',0800,null);
insert into movimiento values('00100001',07,'20220315','0004','003',1000,null);

insert into movimiento values('00200003',01,'20220111','0001','001',2500,null);
insert into movimiento values('00200003',02,'20220117','0001','003',1500,null);
insert into movimiento values('00200003',03,'20220120','0001','004',0500,null);
insert into movimiento values('00200003',04,'20220209','0001','004',0500,null);
insert into movimiento values('00200003',05,'20220225','0001','003',3500,null);
insert into movimiento values('00200003',06,'20220311','0001','004',0500,null);

insert into movimiento values('00200002',01,'20220109','0001','001',3800,null);
insert into movimiento values('00200002',02,'20220120','0001','003',4200,null);
insert into movimiento values('00200002',03,'20220306','0001','004',1200,null);

insert into movimiento values('00200001',01,'20220105','0001','001',5000,null);
insert into movimiento values('00200001',02,'20220107','0001','003',4000,null);
insert into movimiento values('00200001',03,'20220109','0001','004',2000,null);
insert into movimiento values('00200001',04,'20220111','0001','003',1000,null);
insert into movimiento values('00200001',05,'20220113','0001','003',2000,null);
insert into movimiento values('00200001',06,'20220115','0001','004',4000,null);
insert into movimiento values('00200001',07,'20220119','0001','003',2000,null);
insert into movimiento values('00200001',08,'20220121','0001','004',3000,null);
insert into movimiento values('00200001',09,'20220123','0001','003',7000,null);
insert into movimiento values('00200001',10,'20220127','0001','004',1000,null);
insert into movimiento values('00200001',11,'20220130','0001','004',3000,null);
insert into movimiento values('00200001',12,'20220204','0001','003',2000,null);
insert into movimiento values('00200001',13,'20220208','0001','004',4000,null);
insert into movimiento values('00200001',14,'20220213','0001','003',2000,null);
insert into movimiento values('00200001',15,'20220219','0001','004',1000,null);

insert into movimiento values('00300001',01,'20220107','0002','001',5600,null);
insert into movimiento values('00300001',02,'20220118','0002','003',1400,null);
insert into movimiento values('00300001',03,'20220125','0002','002',7000,null);

--  Tabla: Contador

insert into Contador Values( 'Moneda', 2, 2 );
insert into Contador Values( 'TipoMovimiento', 10, 3 );
insert into Contador Values( 'Sucursal', 7, 3 );
insert into Contador Values( 'Empleado', 11, 4 );
insert into Contador Values( 'Asignado', 12, 6 );
insert into Contador Values( 'Parametro', 2, 3 );
insert into Contador Values( 'Cliente', 20, 5 );