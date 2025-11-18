using Comer_CliCon_SOAP_DotNet_GR01.Models;
using Comer_CliCon_SOAP_DotNet_GR01.ServiceClients;
using Comer_CliCon_SOAP_DotNet_GR01.Utils;

namespace Comer_CliCon_SOAP_DotNet_GR01
{
    class Program
    {
        private const string USUARIO_CORRECTO = "MONSTER";
        private const string PASSWORD_CORRECTO = "MONSTER9";
        private const int MAX_INTENTOS = 3;

        static void Main(string[] args)
        {
            // Validar login antes de acceder al sistema
            if (!Login())
            {
                ConsoleHelper.MostrarError("Acceso denegado. Máximo de intentos alcanzado.");
                Console.WriteLine("\nPresione cualquier tecla para salir...");
                Console.ReadKey();
                return;
            }

            bool salir = false;

            while (!salir)
            {
                MostrarMenuPrincipal();
                string opcion = Console.ReadLine() ?? "";

                switch (opcion)
                {
                    case "1":
                        MenuProductos();
                        break;
                    case "2":
                        MenuFacturacion();
                        break;
                    case "3":
                        MenuConsultas();
                        break;
                    case "0":
                        salir = true;
                        Console.WriteLine("¡Hasta luego!");
                        break;
                    default:
                        ConsoleHelper.MostrarError("Opción no válida");
                        Thread.Sleep(1000);
                        break;
                }
            }
        }

        static bool Login()
        {
            Console.Clear();
            ConsoleHelper.MostrarTitulo("SISTEMA DE COMERCIALIZACIÓN - LOGIN");
            Console.WriteLine();

            int intentos = 0;

            while (intentos < MAX_INTENTOS)
            {
                Console.Write("Usuario: ");
                string usuario = Console.ReadLine() ?? "";

                Console.Write("Password: ");
                string password = LeerPassword();

                if (usuario == USUARIO_CORRECTO && password == PASSWORD_CORRECTO)
                {
                    Console.WriteLine();
                    ConsoleHelper.MostrarExito("¡Login exitoso! Bienvenido al sistema.");
                    Thread.Sleep(1500);
                    Console.Clear();
                    return true;
                }

                intentos++;
                int intentosRestantes = MAX_INTENTOS - intentos;

                if (intentosRestantes > 0)
                {
                    ConsoleHelper.MostrarError($"Usuario o contraseña incorrectos. Intentos restantes: {intentosRestantes}");
                    Console.WriteLine();
                }
            }

            return false;
        }

        static string LeerPassword()
        {
            string password = "";
            ConsoleKeyInfo key;

            do
            {
                key = Console.ReadKey(true);

                if (key.Key != ConsoleKey.Backspace && key.Key != ConsoleKey.Enter)
                {
                    password += key.KeyChar;
                    Console.Write("*");
                }
                else if (key.Key == ConsoleKey.Backspace && password.Length > 0)
                {
                    password = password.Substring(0, password.Length - 1);
                    Console.Write("\b \b");
                }
            }
            while (key.Key != ConsoleKey.Enter);

            Console.WriteLine();
            return password;
        }

        static void MostrarMenuPrincipal()
        {
            ConsoleHelper.MostrarTitulo("COMERCIALIZADORA DE ELECTRODOMÉSTICOS - CLIENTE CONSOLA");
            Console.WriteLine("1. Gestión de Productos");
            Console.WriteLine("2. Facturación");
            Console.WriteLine("3. Consultas");
            Console.WriteLine("0. Salir");
            Console.WriteLine();
            Console.Write("Seleccione una opción: ");
        }

        #region Menú Productos
        static void MenuProductos()
        {
            bool regresar = false;

            while (!regresar)
            {
                ConsoleHelper.MostrarTitulo("GESTIÓN DE PRODUCTOS");
                Console.WriteLine("1. Listar todos los productos");
                Console.WriteLine("2. Buscar producto por ID");
                Console.WriteLine("3. Buscar producto por código");
                Console.WriteLine("4. Buscar productos por categoría");
                Console.WriteLine("5. Buscar productos por rango de precio");
                Console.WriteLine("6. Crear nuevo producto");
                Console.WriteLine("7. Actualizar producto");
                Console.WriteLine("8. Eliminar producto");
                Console.WriteLine("0. Regresar al menú principal");
                Console.WriteLine();
                Console.Write("Seleccione una opción: ");

                string opcion = Console.ReadLine() ?? "";

                switch (opcion)
                {
                    case "1":
                        ListarProductos();
                        break;
                    case "2":
                        BuscarProductoPorId();
                        break;
                    case "3":
                        BuscarProductoPorCodigo();
                        break;
                    case "4":
                        BuscarProductosPorCategoria();
                        break;
                    case "5":
                        BuscarProductosPorPrecio();
                        break;
                    case "6":
                        CrearProducto();
                        break;
                    case "7":
                        ActualizarProducto();
                        break;
                    case "8":
                        EliminarProducto();
                        break;
                    case "0":
                        regresar = true;
                        break;
                    default:
                        ConsoleHelper.MostrarError("Opción no válida");
                        Thread.Sleep(1000);
                        break;
                }
            }
        }

        static void ListarProductos()
        {
            ConsoleHelper.MostrarTitulo("LISTA DE PRODUCTOS");

            try
            {
                using var client = new ProductoServiceClient();
                var productos = client.ObtenerProductos();

                if (productos == null || !productos.Any())
                {
                    ConsoleHelper.MostrarAdvertencia("No hay productos registrados");
                }
                else
                {
                    Console.WriteLine($"{"ID",-5} {"Código",-10} {"Nombre",-30} {"Categoría",-15} {"Precio",10} {"Stock",8}");
                    ConsoleHelper.MostrarLinea();

                    foreach (var p in productos)
                    {
                        Console.WriteLine($"{p.ProductoId,-5} {p.Codigo,-10} {p.Nombre,-30} {p.Categoria,-15} {p.Precio,10:C} {p.Stock,8}");
                    }

                    Console.WriteLine();
                    ConsoleHelper.MostrarInfo($"Total de productos: {productos.Count}");
                }
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error al obtener productos: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void BuscarProductoPorId()
        {
            ConsoleHelper.MostrarTitulo("BUSCAR PRODUCTO POR ID");

            int id = ConsoleHelper.LeerEntero("Ingrese el ID del producto", 1);

            try
            {
                using var client = new ProductoServiceClient();
                var producto = client.ObtenerProductoPorId(id);

                if (producto != null)
                {
                    MostrarDetalleProducto(producto);
                }
                else
                {
                    ConsoleHelper.MostrarAdvertencia($"No se encontró producto con ID: {id}");
                }
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void BuscarProductoPorCodigo()
        {
            ConsoleHelper.MostrarTitulo("BUSCAR PRODUCTO POR CÓDIGO");

            string codigo = ConsoleHelper.LeerTexto("Ingrese el código del producto");

            try
            {
                using var client = new ProductoServiceClient();
                var producto = client.ObtenerProductoPorCodigo(codigo);

                if (producto != null)
                {
                    MostrarDetalleProducto(producto);
                }
                else
                {
                    ConsoleHelper.MostrarAdvertencia($"No se encontró producto con código: {codigo}");
                }
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void BuscarProductosPorCategoria()
        {
            ConsoleHelper.MostrarTitulo("BUSCAR PRODUCTOS POR CATEGORÍA");

            string categoria = ConsoleHelper.LeerTexto("Ingrese la categoría");

            try
            {
                using var client = new ProductoServiceClient();
                var productos = client.ObtenerProductosPorCategoria(categoria);

                if (productos == null || !productos.Any())
                {
                    ConsoleHelper.MostrarAdvertencia($"No se encontraron productos en la categoría: {categoria}");
                }
                else
                {
                    Console.WriteLine($"\n{"ID",-5} {"Código",-10} {"Nombre",-30} {"Precio",10} {"Stock",8}");
                    ConsoleHelper.MostrarLinea();

                    foreach (var p in productos)
                    {
                        Console.WriteLine($"{p.ProductoId,-5} {p.Codigo,-10} {p.Nombre,-30} {p.Precio,10:C} {p.Stock,8}");
                    }

                    ConsoleHelper.MostrarInfo($"\nTotal encontrados: {productos.Count}");
                }
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void BuscarProductosPorPrecio()
        {
            ConsoleHelper.MostrarTitulo("BUSCAR PRODUCTOS POR RANGO DE PRECIO");

            decimal precioMin = ConsoleHelper.LeerDecimal("Precio mínimo", 0);
            decimal precioMax = ConsoleHelper.LeerDecimal("Precio máximo", precioMin);

            try
            {
                using var client = new ProductoServiceClient();
                var productos = client.ObtenerProductosPorPrecio(precioMin, precioMax);

                if (productos == null || !productos.Any())
                {
                    ConsoleHelper.MostrarAdvertencia($"No se encontraron productos entre {precioMin:C} y {precioMax:C}");
                }
                else
                {
                    Console.WriteLine($"\n{"ID",-5} {"Código",-10} {"Nombre",-30} {"Precio",10} {"Stock",8}");
                    ConsoleHelper.MostrarLinea();

                    foreach (var p in productos)
                    {
                        Console.WriteLine($"{p.ProductoId,-5} {p.Codigo,-10} {p.Nombre,-30} {p.Precio,10:C} {p.Stock,8}");
                    }

                    ConsoleHelper.MostrarInfo($"\nTotal encontrados: {productos.Count}");
                }
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void CrearProducto()
        {
            ConsoleHelper.MostrarTitulo("CREAR NUEVO PRODUCTO");

            var producto = new ProductoDTO
            {
                Codigo = ConsoleHelper.LeerTexto("Código"),
                Nombre = ConsoleHelper.LeerTexto("Nombre"),
                Descripcion = ConsoleHelper.LeerTexto("Descripción", false),
                Precio = ConsoleHelper.LeerDecimal("Precio", 0),
                Stock = ConsoleHelper.LeerEntero("Stock inicial", 0),
                Categoria = ConsoleHelper.LeerTexto("Categoría")
            };

            try
            {
                using var client = new ProductoServiceClient();
                var productoCreado = client.CrearProducto(producto);

                ConsoleHelper.MostrarExito($"Producto creado exitosamente con ID: {productoCreado.ProductoId}");
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error al crear producto: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void ActualizarProducto()
        {
            ConsoleHelper.MostrarTitulo("ACTUALIZAR PRODUCTO");

            int id = ConsoleHelper.LeerEntero("Ingrese el ID del producto a actualizar", 1);

            try
            {
                using var client = new ProductoServiceClient();
                var producto = client.ObtenerProductoPorId(id);

                if (producto == null)
                {
                    ConsoleHelper.MostrarError($"No se encontró producto con ID: {id}");
                    ConsoleHelper.PausarConsola();
                    return;
                }

                Console.WriteLine("\nProducto actual:");
                MostrarDetalleProducto(producto);

                Console.WriteLine("\nIngrese los nuevos datos (Enter para mantener el actual):");

                string codigo = ConsoleHelper.LeerTexto($"Código [{producto.Codigo}]", false);
                if (!string.IsNullOrWhiteSpace(codigo)) producto.Codigo = codigo;

                string nombre = ConsoleHelper.LeerTexto($"Nombre [{producto.Nombre}]", false);
                if (!string.IsNullOrWhiteSpace(nombre)) producto.Nombre = nombre;

                string descripcion = ConsoleHelper.LeerTexto($"Descripción [{producto.Descripcion}]", false);
                if (!string.IsNullOrWhiteSpace(descripcion)) producto.Descripcion = descripcion;

                Console.Write($"Precio [{producto.Precio:C}]: ");
                string precioStr = Console.ReadLine() ?? "";
                if (decimal.TryParse(precioStr, out decimal precio)) producto.Precio = precio;

                Console.Write($"Stock [{producto.Stock}]: ");
                string stockStr = Console.ReadLine() ?? "";
                if (int.TryParse(stockStr, out int stock)) producto.Stock = stock;

                string categoria = ConsoleHelper.LeerTexto($"Categoría [{producto.Categoria}]", false);
                if (!string.IsNullOrWhiteSpace(categoria)) producto.Categoria = categoria;

                var productoActualizado = client.ActualizarProducto(producto);
                ConsoleHelper.MostrarExito("Producto actualizado exitosamente");
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error al actualizar producto: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void EliminarProducto()
        {
            ConsoleHelper.MostrarTitulo("ELIMINAR PRODUCTO");

            int id = ConsoleHelper.LeerEntero("Ingrese el ID del producto a eliminar", 1);

            try
            {
                using var client = new ProductoServiceClient();
                var producto = client.ObtenerProductoPorId(id);

                if (producto == null)
                {
                    ConsoleHelper.MostrarError($"No se encontró producto con ID: {id}");
                    ConsoleHelper.PausarConsola();
                    return;
                }

                Console.WriteLine("\nProducto a eliminar:");
                MostrarDetalleProducto(producto);

                Console.Write("\n¿Está seguro de eliminar este producto? (S/N): ");
                string confirmacion = Console.ReadLine()?.ToUpper() ?? "";

                if (confirmacion == "S")
                {
                    bool eliminado = client.EliminarProducto(id);
                    if (eliminado)
                        ConsoleHelper.MostrarExito("Producto eliminado exitosamente");
                    else
                        ConsoleHelper.MostrarError("No se pudo eliminar el producto");
                }
                else
                {
                    ConsoleHelper.MostrarInfo("Eliminación cancelada");
                }
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error al eliminar producto: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void MostrarDetalleProducto(ProductoDTO producto)
        {
            Console.WriteLine($"\nID:          {producto.ProductoId}");
            Console.WriteLine($"Código:      {producto.Codigo}");
            Console.WriteLine($"Nombre:      {producto.Nombre}");
            Console.WriteLine($"Descripción: {producto.Descripcion}");
            Console.WriteLine($"Precio:      {producto.Precio:C}");
            Console.WriteLine($"Stock:       {producto.Stock}");
            Console.WriteLine($"Categoría:   {producto.Categoria}");
        }
        #endregion

        #region Menú Facturación
        static void MenuFacturacion()
        {
            bool regresar = false;

            while (!regresar)
            {
                ConsoleHelper.MostrarTitulo("FACTURACIÓN");
                Console.WriteLine("1. Venta en EFECTIVO (33% descuento)");
                Console.WriteLine("2. Venta a CRÉDITO (BanQuito)");
                Console.WriteLine("0. Regresar al menú principal");
                Console.WriteLine();
                Console.Write("Seleccione una opción: ");

                string opcion = Console.ReadLine() ?? "";

                switch (opcion)
                {
                    case "1":
                        FacturarEfectivo();
                        break;
                    case "2":
                        FacturarCredito();
                        break;
                    case "0":
                        regresar = true;
                        break;
                    default:
                        ConsoleHelper.MostrarError("Opción no válida");
                        Thread.Sleep(1000);
                        break;
                }
            }
        }

        static void FacturarEfectivo()
        {
            ConsoleHelper.MostrarTitulo("VENTA EN EFECTIVO (33% DESCUENTO)");

            try
            {
                var solicitud = new SolicitudFacturaDTO
                {
                    CedulaCliente = ConsoleHelper.LeerTexto("Cédula del cliente"),
                    NombreCliente = ConsoleHelper.LeerTexto("Nombre del cliente"),
                    FormaPago = "EFECTIVO",
                    Items = new List<ItemFacturaDTO>()
                };

                bool agregarMas = true;
                while (agregarMas)
                {
                    Console.WriteLine($"\n--- Producto #{solicitud.Items.Count + 1} ---");
                    int productoId = ConsoleHelper.LeerEntero("ID del producto", 1);
                    int cantidad = ConsoleHelper.LeerEntero("Cantidad", 1);

                    solicitud.Items.Add(new ItemFacturaDTO
                    {
                        ProductoId = productoId,
                        Cantidad = cantidad
                    });

                    Console.Write("¿Agregar otro producto? (S/N): ");
                    string respuesta = Console.ReadLine()?.ToUpper() ?? "";
                    agregarMas = respuesta == "S";
                }

                using var client = new FacturacionServiceClient();
                var factura = client.GenerarFactura(solicitud);

                if (factura.NumeroFactura == "ERROR")
                {
                    ConsoleHelper.MostrarError($"Error al generar factura: {factura.NombreCliente}");
                }
                else
                {
                    MostrarDetalleFactura(factura);
                    ConsoleHelper.MostrarExito("¡Factura generada exitosamente!");
                }
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void FacturarCredito()
        {
            ConsoleHelper.MostrarTitulo("VENTA A CRÉDITO (BANQUITO)");

            try
            {
                string cedula = ConsoleHelper.LeerTexto("Cédula del cliente");

                // Paso 1: Validar si es sujeto de crédito
                Console.WriteLine("\nValidando cliente en BanQuito...");
                decimal montoMaximo = 0;
                using (var creditoClient = new CreditoServiceClient())
                {
                    var validacion = creditoClient.ValidarSujetoCredito(cedula);

                    if (!validacion.EsValido)
                    {
                        ConsoleHelper.MostrarError($"Cliente NO es sujeto de crédito: {validacion.Mensaje}");
                        ConsoleHelper.PausarConsola();
                        return;
                    }

                    ConsoleHelper.MostrarExito($"Cliente validado: {validacion.Mensaje}");

                    // Paso 2: Obtener monto máximo de crédito
                    var montoMax = creditoClient.ObtenerMontoMaximo(cedula);
                    montoMaximo = montoMax.MontoMaximo;
                    ConsoleHelper.MostrarInfo($"Monto máximo autorizado: {montoMaximo:C}");

                    Console.WriteLine();
                }

                // Paso 3: Crear solicitud con productos
                var solicitud = new SolicitudFacturaDTO
                {
                    CedulaCliente = cedula,
                    NombreCliente = ConsoleHelper.LeerTexto("Nombre del cliente"),
                    FormaPago = "CREDITO",
                    Items = new List<ItemFacturaDTO>()
                };

                bool agregarMas = true;
                while (agregarMas)
                {
                    Console.WriteLine($"\n--- Producto #{solicitud.Items.Count + 1} ---");
                    int productoId = ConsoleHelper.LeerEntero("ID del producto", 1);
                    int cantidad = ConsoleHelper.LeerEntero("Cantidad", 1);

                    solicitud.Items.Add(new ItemFacturaDTO
                    {
                        ProductoId = productoId,
                        Cantidad = cantidad
                    });

                    Console.Write("¿Agregar otro producto? (S/N): ");
                    string respuesta = Console.ReadLine()?.ToUpper() ?? "";
                    agregarMas = respuesta == "S";
                }

                // Paso 4: CALCULAR TOTAL DE LA FACTURA (sin generarla)
                Console.WriteLine("\nCalculando total de la compra...");
                decimal montoTotal = 0;
                using (var facturacionClient = new FacturacionServiceClient())
                {
                    // Crear solicitud de cálculo solo con los items
                    var solicitudCalculo = new SolicitudCalculoDTO
                    {
                        Items = solicitud.Items
                    };

                    var calculo = facturacionClient.CalcularTotalFactura(solicitudCalculo);

                    if (!calculo.Exitoso)
                    {
                        ConsoleHelper.MostrarError($"Error al calcular total: {calculo.Mensaje}");
                        ConsoleHelper.PausarConsola();
                        return;
                    }

                    montoTotal = calculo.Total;

                    // Mostrar desglose de productos
                    if (calculo.Detalles != null && calculo.Detalles.Count > 0)
                    {
                        Console.WriteLine("\n--- DESGLOSE DE PRODUCTOS ---");
                        Console.WriteLine($"{"Producto",-30} {"Cant.",6} {"P.Unit.",12} {"Subtotal",12}");
                        ConsoleHelper.MostrarLinea();
                        foreach (var detalle in calculo.Detalles)
                        {
                            Console.WriteLine($"{detalle.NombreProducto,-30} {detalle.Cantidad,6} {detalle.PrecioUnitario,12:C} {detalle.Subtotal,12:C}");
                        }
                        ConsoleHelper.MostrarLinea();
                    }

                    ConsoleHelper.MostrarExito($"TOTAL A FINANCIAR: {montoTotal:C}");
                    Console.WriteLine();
                }

                // Paso 5: Validar que el monto no exceda el máximo autorizado
                if (montoTotal > montoMaximo)
                {
                    ConsoleHelper.MostrarError($"El monto total ({montoTotal:C}) excede el monto máximo autorizado ({montoMaximo:C})");
                    ConsoleHelper.PausarConsola();
                    return;
                }

                // Paso 6: Solicitar número de cuotas
                int cuotas = ConsoleHelper.LeerEntero("Número de cuotas (3-24)", 3, 24);

                // Paso 7: Otorgar crédito en BanQuito con el monto exacto
                Console.WriteLine("\nOtorgando crédito en BanQuito...");
                using (var creditoClient = new CreditoServiceClient())
                {
                    var solicitudCredito = new SolicitudCreditoDTO
                    {
                        Cedula = cedula,
                        PrecioElectrodomestico = montoTotal.ToString("F2", System.Globalization.CultureInfo.InvariantCulture),
                        NumeroCuotas = cuotas.ToString()
                    };
                    var credito = creditoClient.OtorgarCredito(solicitudCredito);

                    if (!credito.Exito)
                    {
                        ConsoleHelper.MostrarError($"Error al otorgar crédito: {credito.Mensaje}");
                        ConsoleHelper.PausarConsola();
                        return;
                    }

                    solicitud.NumeroCredito = credito.NumeroCredito;

                    ConsoleHelper.MostrarExito($"Crédito otorgado: {credito.NumeroCredito}");
                    ConsoleHelper.MostrarInfo($"Cuota mensual: {credito.CuotaMensual:C}");
                }

                // Paso 8: Generar factura con el número de crédito
                Console.WriteLine("\nGenerando factura...");
                using (var facturacionClient = new FacturacionServiceClient())
                {
                    var factura = facturacionClient.GenerarFactura(solicitud);

                    if (factura.NumeroFactura == "ERROR")
                    {
                        ConsoleHelper.MostrarError($"Error al generar factura: {factura.NombreCliente}");
                    }
                    else
                    {
                        MostrarDetalleFactura(factura);
                        ConsoleHelper.MostrarExito("¡Venta a crédito completada exitosamente!");
                    }
                }
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void MostrarDetalleFactura(FacturaDTO factura)
        {
            Console.WriteLine("\n╔══════════════════════════════════════════════════════╗");
            Console.WriteLine("║                    FACTURA                           ║");
            Console.WriteLine("╚══════════════════════════════════════════════════════╝");
            Console.WriteLine($"Número:        {factura.NumeroFactura}");
            Console.WriteLine($"Fecha:         {factura.FechaEmision:dd/MM/yyyy HH:mm}");
            Console.WriteLine($"Cliente:       {factura.NombreCliente}");
            Console.WriteLine($"Cédula:        {factura.CedulaCliente}");
            Console.WriteLine($"Forma de Pago: {factura.FormaPago}");
            if (!string.IsNullOrEmpty(factura.NumeroCredito))
                Console.WriteLine($"Nº Crédito:    {factura.NumeroCredito}");

            Console.WriteLine("\n--- DETALLE ---");
            Console.WriteLine($"{"Producto",-30} {"Cant.",5} {"P.Unit.",12} {"Subtotal",12}");
            ConsoleHelper.MostrarLinea();

            foreach (var detalle in factura.Detalles)
            {
                Console.WriteLine($"{detalle.NombreProducto,-30} {detalle.Cantidad,5} {detalle.PrecioUnitario,12:C} {detalle.Subtotal,12:C}");
            }

            ConsoleHelper.MostrarLinea();
            Console.WriteLine($"{"Subtotal:",-50} {factura.Subtotal,12:C}");
            Console.WriteLine($"{"Descuento:",-50} {factura.Descuento,12:C}");
            Console.WriteLine($"{"TOTAL:",-50} {factura.Total,12:C}");
            Console.WriteLine("══════════════════════════════════════════════════════════");
        }
        #endregion

        #region Menú Consultas
        static void MenuConsultas()
        {
            bool regresar = false;

            while (!regresar)
            {
                ConsoleHelper.MostrarTitulo("CONSULTAS");
                Console.WriteLine("1. Facturas por cliente");
                Console.WriteLine("2. Factura por número");
                Console.WriteLine("3. Tabla de amortización de crédito");
                Console.WriteLine("0. Regresar al menú principal");
                Console.WriteLine();
                Console.Write("Seleccione una opción: ");

                string opcion = Console.ReadLine() ?? "";

                switch (opcion)
                {
                    case "1":
                        ConsultarFacturasPorCliente();
                        break;
                    case "2":
                        ConsultarFacturaPorNumero();
                        break;
                    case "3":
                        ConsultarTablaAmortizacion();
                        break;
                    case "0":
                        regresar = true;
                        break;
                    default:
                        ConsoleHelper.MostrarError("Opción no válida");
                        Thread.Sleep(1000);
                        break;
                }
            }
        }

        static void ConsultarFacturasPorCliente()
        {
            ConsoleHelper.MostrarTitulo("FACTURAS POR CLIENTE");

            string cedula = ConsoleHelper.LeerTexto("Cédula del cliente");

            try
            {
                using var client = new FacturacionServiceClient();
                var facturas = client.ObtenerFacturasPorCliente(cedula);

                if (facturas == null || !facturas.Any())
                {
                    ConsoleHelper.MostrarAdvertencia($"No se encontraron facturas para la cédula: {cedula}");
                }
                else
                {
                    Console.WriteLine($"\n{"Número",-15} {"Fecha",12} {"Cliente",-25} {"Forma Pago",-12} {"Total",12}");
                    ConsoleHelper.MostrarLinea();

                    foreach (var f in facturas)
                    {
                        Console.WriteLine($"{f.NumeroFactura,-15} {f.FechaEmision:dd/MM/yyyy} {f.NombreCliente,-25} {f.FormaPago,-12} {f.Total,12:C}");
                    }

                    ConsoleHelper.MostrarInfo($"\nTotal de facturas: {facturas.Count}");
                    ConsoleHelper.MostrarInfo($"Total facturado: {facturas.Sum(f => f.Total):C}");
                }
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void ConsultarFacturaPorNumero()
        {
            ConsoleHelper.MostrarTitulo("CONSULTAR FACTURA POR NÚMERO");

            string numeroFactura = ConsoleHelper.LeerTexto("Número de factura");

            try
            {
                using var client = new FacturacionServiceClient();
                var factura = client.ObtenerFacturaPorNumero(numeroFactura);

                if (factura != null)
                {
                    MostrarDetalleFactura(factura);
                }
                else
                {
                    ConsoleHelper.MostrarAdvertencia($"No se encontró factura con número: {numeroFactura}");
                }
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }

        static void ConsultarTablaAmortizacion()
        {
            ConsoleHelper.MostrarTitulo("TABLA DE AMORTIZACIÓN DE CRÉDITO");

            string numeroCredito = ConsoleHelper.LeerTexto("Número de crédito");

            try
            {
                using var client = new CreditoServiceClient();
                var tablaAmortizacion = client.ObtenerTablaAmortizacion(numeroCredito);

                if (tablaAmortizacion != null && tablaAmortizacion.Any())
                {
                    Console.WriteLine($"\nNúmero de Crédito: {numeroCredito}");

                    Console.WriteLine("\n--- TABLA DE AMORTIZACIÓN ---");
                    Console.WriteLine($"{"#",4} {"Cuota",12} {"Interés",12} {"Capital",12} {"Saldo",12}");
                    ConsoleHelper.MostrarLinea();

                    foreach (var cuota in tablaAmortizacion)
                    {
                        Console.WriteLine($"{cuota.NumeroCuota,4} {cuota.ValorCuota,12:C} {cuota.Interes,12:C} {cuota.CapitalPagado,12:C} {cuota.Saldo,12:C}");
                    }

                    ConsoleHelper.MostrarLinea();
                }
                else
                {
                    ConsoleHelper.MostrarAdvertencia($"No se encontró crédito con número: {numeroCredito}");
                }
            }
            catch (Exception ex)
            {
                ConsoleHelper.MostrarError($"Error: {ex.Message}");
            }

            ConsoleHelper.PausarConsola();
        }
        #endregion
    }
}
