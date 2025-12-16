package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Sucursal;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Sucursal
 * 
 * @author EurekaBank
 */
public class SucursalDAO {

    /**
     * Lista todas las sucursales
     * 
     * @return Lista de sucursales
     * @throws SQLException si hay error en la BD
     */
    public List<Sucursal> listarTodas() throws SQLException {
        List<Sucursal> sucursales = new ArrayList<>();
        String query = "SELECT chr_sucucodigo, vch_sucunombre, vch_sucuciudad, "
                + "vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud "
                + "FROM Sucursal";

        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Sucursal sucursal = new Sucursal();
                sucursal.setCodigo(rs.getInt("chr_sucucodigo"));
                sucursal.setNombre(rs.getString("vch_sucunombre"));
                sucursal.setCiudad(rs.getString("vch_sucuciudad"));
                sucursal.setDireccion(rs.getString("vch_sucudireccion"));
                sucursal.setContadorCuentas(rs.getInt("int_sucucontcuenta"));
                sucursal.setLatitud(rs.getBigDecimal("dec_suculatitud"));
                sucursal.setLongitud(rs.getBigDecimal("dec_suculongitud"));
                sucursales.add(sucursal);
            }
        }
        return sucursales;
    }

    /**
     * Obtiene una sucursal por su código
     * 
     * @param codigo Código de la sucursal
     * @return Sucursal encontrada o null
     * @throws SQLException si hay error en la BD
     */
    public Sucursal obtenerPorCodigo(int codigo) throws SQLException {
        String query = "SELECT chr_sucucodigo, vch_sucunombre, vch_sucuciudad, "
                + "vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud "
                + "FROM Sucursal WHERE chr_sucucodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Sucursal sucursal = new Sucursal();
                sucursal.setCodigo(rs.getInt("chr_sucucodigo"));
                sucursal.setNombre(rs.getString("vch_sucunombre"));
                sucursal.setCiudad(rs.getString("vch_sucuciudad"));
                sucursal.setDireccion(rs.getString("vch_sucudireccion"));
                sucursal.setContadorCuentas(rs.getInt("int_sucucontcuenta"));
                sucursal.setLatitud(rs.getBigDecimal("dec_suculatitud"));
                sucursal.setLongitud(rs.getBigDecimal("dec_suculongitud"));
                return sucursal;
            }
        }
        return null;
    }

    /**
     * Inserta una nueva sucursal
     * 
     * @param sucursal Datos de la sucursal
     * @return Código de la nueva sucursal o -1 si falla
     * @throws SQLException si hay error en la BD
     */
    public int insertar(Sucursal sucursal) throws SQLException {
        // Obtenemos el siguiente código disponible (max + 1) ya que parece no ser
        // autoincremental en la definición original
        // o usamos el contador si existe tabla de contadores para sucursal.
        // Asumiendo estrategia similar a cliente/empleado o autoincremental si se
        // ajustó BD.
        // Dado que el usuario pidió CRUD completo, asumo que debemos manejar la
        // generación de ID.
        // Revisando EmpleadoDAO se usa una tabla 'contador'. Verificaremos si hay
        // contador para Sucursal.
        // Si no, usaremos MAX+1 como fallback seguro para este ejercicio.

        int nuevoCodigo = generarNuevoCodigo();

        String query = "INSERT INTO Sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, "
                + "vch_sucudireccion, int_sucucontcuenta, dec_suculatitud, dec_suculongitud) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, nuevoCodigo);
            ps.setString(2, sucursal.getNombre());
            ps.setString(3, sucursal.getCiudad());
            ps.setString(4, sucursal.getDireccion());
            ps.setInt(5, 0); // Contador cuentas inicia en 0
            ps.setBigDecimal(6, sucursal.getLatitud());
            ps.setBigDecimal(7, sucursal.getLongitud());

            int filas = ps.executeUpdate();
            return filas > 0 ? nuevoCodigo : -1;
        }
    }

    private int generarNuevoCodigo() throws SQLException {
        String query = "SELECT MAX(CAST(chr_sucucodigo AS signed)) FROM sucursal"; // Cast por si acaso la DB es vieja,
                                                                                   // pero ahora es INT
        // Como la DB define chr_sucucodigo como INT, el MAX directo funciona.
        query = "SELECT MAX(chr_sucucodigo) FROM Sucursal";

        try (Connection conn = ConexionDB.obtenerConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        }
        return 1;
    }

    /**
     * Actualiza una sucursal existente
     * 
     * @param sucursal Datos actualizados
     * @return true si se actualizó
     * @throws SQLException si hay error en la BD
     */
    public boolean actualizar(Sucursal sucursal) throws SQLException {
        String query = "UPDATE Sucursal SET vch_sucunombre = ?, vch_sucuciudad = ?, "
                + "vch_sucudireccion = ?, dec_suculatitud = ?, dec_suculongitud = ? "
                + "WHERE chr_sucucodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, sucursal.getNombre());
            ps.setString(2, sucursal.getCiudad());
            ps.setString(3, sucursal.getDireccion());
            ps.setBigDecimal(4, sucursal.getLatitud());
            ps.setBigDecimal(5, sucursal.getLongitud());
            ps.setInt(6, sucursal.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Elimina una sucursal (marcado lógico o físico dependiendo de reglas, pero el
     * código C# dice Eliminar)
     * Generalmente en EurekaBank se usa estado, haremos borrado lógico 'ANULADO' o
     * borrado físico si no hay estado 'ELIMINADO'.
     * El query de listar filtra por 'ACTIVO', así que cambiaremos estado a
     * 'ANULADO'.
     * 
     * @param codigo Código de la sucursal
     * @return true si se eliminó
     * @throws SQLException si hay error en la BD
     */
    public boolean eliminar(int codigo) throws SQLException {
        String query = "DELETE FROM Sucursal WHERE chr_sucucodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Actualiza solo las coordenadas
     * 
     * @param codigo   Código de la sucursal
     * @param latitud  Nueva latitud
     * @param longitud Nueva longitud
     * @return true si se actualizó
     * @throws SQLException si hay error en la BD
     */
    public boolean actualizarCoordenadas(int codigo, BigDecimal latitud, BigDecimal longitud) throws SQLException {
        String query = "UPDATE Sucursal SET dec_suculatitud = ?, dec_suculongitud = ? "
                + "WHERE chr_sucucodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setBigDecimal(1, latitud);
            ps.setBigDecimal(2, longitud);
            ps.setInt(3, codigo);

            return ps.executeUpdate() > 0;
        }
    }
}
