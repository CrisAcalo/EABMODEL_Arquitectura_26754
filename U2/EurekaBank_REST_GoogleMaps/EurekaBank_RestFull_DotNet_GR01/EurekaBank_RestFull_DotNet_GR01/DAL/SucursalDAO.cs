using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using Dapper;
using EurekaBank_RestFull_DotNet_GR01.Models;

namespace EurekaBank_RestFull_DotNet_GR01.DAL
{
    /// <summary>
    /// Data Access Object para la entidad Sucursal usando Dapper
    /// </summary>
    public class SucursalDAO
    {
        /// <summary>
        /// Obtiene una sucursal por su código
        /// </summary>
        /// <param name="codigo">Código de la sucursal</param>
        /// <returns>Sucursal encontrada o null</returns>
        public Sucursal ObtenerPorCodigo(int codigo)
        {
            try
            {
                using (var conn = ConexionDB.ObtenerConexion())
                {
                    string query = @"SELECT 
                                    chr_sucucodigo AS Codigo,
                                    vch_sucunombre AS Nombre,
                                    vch_sucuciudad AS Ciudad,
                                    vch_sucudireccion AS Direccion,
                                    int_sucucontcuenta AS ContadorCuentas,
                                    dec_suculatitud AS Latitud,
                                    dec_suculongitud AS Longitud
                                    FROM Sucursal 
                                    WHERE chr_sucucodigo = @Codigo";
                    
                    return conn.QueryFirstOrDefault<Sucursal>(query, new { Codigo = codigo });
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al obtener sucursal: {ex.Message}", ex);
            }
        }

        /// <summary>
        /// Lista todas las sucursales
        /// </summary>
        /// <returns>Lista de todas las sucursales</returns>
        public List<Sucursal> ListarTodas()
        {
            try
            {
                using (var conn = ConexionDB.ObtenerConexion())
                {
                    string query = @"SELECT 
                                    chr_sucucodigo AS Codigo,
                                    vch_sucunombre AS Nombre,
                                    vch_sucuciudad AS Ciudad,
                                    vch_sucudireccion AS Direccion,
                                    int_sucucontcuenta AS ContadorCuentas,
                                    dec_suculatitud AS Latitud,
                                    dec_suculongitud AS Longitud
                                    FROM Sucursal 
                                    ORDER BY vch_sucunombre";
                    
                    return conn.Query<Sucursal>(query).ToList();
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al listar sucursales: {ex.Message}", ex);
            }
        }

        /// <summary>
        /// Lista todas las sucursales que tienen coordenadas de geolocalización
        /// </summary>
        /// <returns>Lista de sucursales con coordenadas</returns>
        public List<Sucursal> ListarConCoordenadas()
        {
            try
            {
                using (var conn = ConexionDB.ObtenerConexion())
                {
                    string query = @"SELECT 
                                    chr_sucucodigo AS Codigo,
                                    vch_sucunombre AS Nombre,
                                    vch_sucuciudad AS Ciudad,
                                    vch_sucudireccion AS Direccion,
                                    int_sucucontcuenta AS ContadorCuentas,
                                    dec_suculatitud AS Latitud,
                                    dec_suculongitud AS Longitud
                                    FROM Sucursal 
                                    WHERE dec_suculatitud IS NOT NULL 
                                    AND dec_suculongitud IS NOT NULL
                                    ORDER BY vch_sucunombre";
                    
                    return conn.Query<Sucursal>(query).ToList();
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al listar sucursales con coordenadas: {ex.Message}", ex);
            }
        }

        /// <summary>
        /// Lista las sucursales por ciudad
        /// </summary>
        /// <param name="ciudad">Nombre de la ciudad</param>
        /// <returns>Lista de sucursales en la ciudad especificada</returns>
        public List<Sucursal> ListarPorCiudad(string ciudad)
        {
            try
            {
                using (var conn = ConexionDB.ObtenerConexion())
                {
                    string query = @"SELECT 
                                    chr_sucucodigo AS Codigo,
                                    vch_sucunombre AS Nombre,
                                    vch_sucuciudad AS Ciudad,
                                    vch_sucudireccion AS Direccion,
                                    int_sucucontcuenta AS ContadorCuentas,
                                    dec_suculatitud AS Latitud,
                                    dec_suculongitud AS Longitud
                                    FROM Sucursal 
                                    WHERE UPPER(vch_sucuciudad) = UPPER(@Ciudad)
                                    ORDER BY vch_sucunombre";
                    
                    return conn.Query<Sucursal>(query, new { Ciudad = ciudad }).ToList();
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al listar sucursales por ciudad: {ex.Message}", ex);
            }
        }

        /// <summary>
        /// Actualiza las coordenadas de una sucursal
        /// </summary>
        /// <param name="codigo">Código de la sucursal</param>
        /// <param name="latitud">Nueva latitud</param>
        /// <param name="longitud">Nueva longitud</param>
        /// <returns>True si se actualizó correctamente</returns>
        public bool ActualizarCoordenadas(int codigo, decimal? latitud, decimal? longitud)
        {
            try
            {
                using (var conn = ConexionDB.ObtenerConexion())
                {
                    string query = @"UPDATE Sucursal 
                                    SET dec_suculatitud = @Latitud,
                                        dec_suculongitud = @Longitud
                                    WHERE chr_sucucodigo = @Codigo";
                    
                    int filasAfectadas = conn.Execute(query, new { 
                        Codigo = codigo, 
                        Latitud = latitud, 
                        Longitud = longitud 
                    });
                    return filasAfectadas > 0;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al actualizar coordenadas: {ex.Message}", ex);
            }
        }

        /// <summary>
        /// Inserta una nueva sucursal
        /// </summary>
        /// <param name="sucursal">Datos de la sucursal a insertar</param>
        /// <returns>Código de la sucursal creada o 0 si falló</returns>
        public int Insertar(Sucursal sucursal)
        {
            try
            {
                using (var conn = ConexionDB.ObtenerConexion())
                {
                    string query = @"INSERT INTO Sucursal 
                                    (vch_sucunombre, vch_sucuciudad, 
                                    vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud)
                                    VALUES 
                                    (@Nombre, @Ciudad, @Direccion, 
                                    @ContadorCuentas, @Latitud, @Longitud);
                                    SELECT CAST(SCOPE_IDENTITY() as int)";
                    
                    var nuevoCodigo = conn.QuerySingle<int>(query, sucursal);
                    return nuevoCodigo;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al insertar sucursal: {ex.Message}", ex);
            }
        }

        /// <summary>
        /// Actualiza los datos de una sucursal
        /// </summary>
        /// <param name="sucursal">Sucursal con los datos actualizados</param>
        /// <returns>True si se actualizó correctamente</returns>
        public bool Actualizar(Sucursal sucursal)
        {
            try
            {
                using (var conn = ConexionDB.ObtenerConexion())
                {
                    string query = @"UPDATE Sucursal SET 
                                    vch_sucunombre = @Nombre,
                                    vch_sucuciudad = @Ciudad,
                                    vch_sucudireccion = @Direccion,
                                    int_sucucontcuenta = @ContadorCuentas,
                                    dec_suculatitud = @Latitud,
                                    dec_suculongitud = @Longitud
                                    WHERE chr_sucucodigo = @Codigo";
                    
                    int filasAfectadas = conn.Execute(query, sucursal);
                    return filasAfectadas > 0;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al actualizar sucursal: {ex.Message}", ex);
            }
        }

        /// <summary>
        /// Elimina una sucursal por su código
        /// </summary>
        /// <param name="codigo">Código de la sucursal a eliminar</param>
        /// <returns>True si se eliminó correctamente</returns>
        public bool Eliminar(int codigo)
        {
            try
            {
                using (var conn = ConexionDB.ObtenerConexion())
                {
                    string query = "DELETE FROM Sucursal WHERE chr_sucucodigo = @Codigo";
                    int filasAfectadas = conn.Execute(query, new { Codigo = codigo });
                    return filasAfectadas > 0;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al eliminar sucursal: {ex.Message}", ex);
            }
        }
    }
}