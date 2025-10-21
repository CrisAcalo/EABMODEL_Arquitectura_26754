using System;
using ConUni_Soap_DotNet_GR01.pruebas;

namespace ConUni_Soap_DotNet_GR01
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("????????????????????????????????????????????????????????????????");
            Console.WriteLine("?    EJECUTOR DE PRUEBAS - SISTEMA DE CONVERSI�N DE UNIDADES  ?");
            Console.WriteLine("????????????????????????????????????????????????????????????????");
            Console.WriteLine();

            if (args.Length > 0)
            {
                // Ejecutar pruebas espec�ficas basadas en argumentos
                string tipoDeTest = args[0].ToLower();
                
                switch (tipoDeTest)
                {
                    case "quick":
                    case "rapido":
                        QuickTest.EjecutarPruebaRapida();
                        QuickTest.EjecutarPruebaWCFRapida();
                        break;
                    case "rapidas":
                        TestRunner.EjecutarPruebasRapidas();
                        break;
                    default:
                        TestRunner.EjecutarPruebasEspecificas(tipoDeTest);
                        break;
                }
            }
            else
            {
                // Mostrar men� interactivo
                MostrarMenu();
            }

            Console.WriteLine("\nPresione cualquier tecla para continuar...");
            Console.ReadKey();
        }

        static void MostrarMenu()
        {
            while (true)
            {
                Console.WriteLine("?? OPCIONES DE PRUEBAS DISPONIBLES:");
                Console.WriteLine("   1. Ejecutar TODAS las pruebas (completo)");
                Console.WriteLine("   2. Solo pruebas de Longitud");
                Console.WriteLine("   3. Solo pruebas de Masa");
                Console.WriteLine("   4. Solo pruebas de Temperatura");
                Console.WriteLine("   5. Solo pruebas de Integraci�n WCF");
                Console.WriteLine("   6. Ejecuci�n r�pida (sin integraci�n)");
                Console.WriteLine("   7. Verificaci�n super r�pida (smoke test)");
                Console.WriteLine("   0. Salir");
                Console.WriteLine();
                Console.Write("Seleccione una opci�n (0-7): ");

                string input = Console.ReadLine();
                Console.WriteLine();

                switch (input)
                {
                    case "1":
                        TestRunner.Main(new string[0]);
                        break;
                    case "2":
                        TestRunner.EjecutarPruebasEspecificas("longitud");
                        break;
                    case "3":
                        TestRunner.EjecutarPruebasEspecificas("masa");
                        break;
                    case "4":
                        TestRunner.EjecutarPruebasEspecificas("temperatura");
                        break;
                    case "5":
                        TestRunner.EjecutarPruebasEspecificas("integracion");
                        break;
                    case "6":
                        TestRunner.EjecutarPruebasRapidas();
                        break;
                    case "7":
                        QuickTest.EjecutarPruebaRapida();
                        Console.WriteLine();
                        QuickTest.EjecutarPruebaWCFRapida();
                        break;
                    case "0":
                        Console.WriteLine("�Hasta luego!");
                        return;
                    default:
                        Console.WriteLine("? Opci�n no v�lida. Intente de nuevo.\n");
                        continue;
                }

                Console.WriteLine("\n" + new string('=', 60));
                Console.WriteLine("�Desea ejecutar m�s pruebas? (Presione cualquier tecla para continuar o 'q' para salir)");
                var key = Console.ReadKey().KeyChar;
                Console.WriteLine("\n");

                if (key == 'q' || key == 'Q')
                    break;
            }
        }
    }
}