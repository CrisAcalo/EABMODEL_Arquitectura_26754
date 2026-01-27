package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones con la tabla Cliente
 */
public class ClienteDAO {

    /**
     * Obtiene todos los clientes
     */
    public List<Cliente> obtenerTodos() throws SQLException {
        String sql = "SELECT chr_cliecodigo, vch_cliepaterno, vch_cliematerno, vch_clienombre, "
                + "chr_cliedni, vch_clieciudad, vch_cliedireccion, vch_clietelefono, vch_clieemail "
                + "FROM cliente ORDER BY vch_cliepaterno, vch_cliematerno, vch_clienombre";

        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        }

        return clientes;
    }

    /**
     * Obtiene un cliente por su código
     */
    public Cliente obtenerPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT chr_cliecodigo, vch_cliepaterno, vch_cliematerno, vch_clienombre, "
                + "chr_cliedni, vch_clieciudad, vch_cliedireccion, vch_clietelefono, vch_clieemail "
                + "FROM cliente WHERE chr_cliecodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearCliente(rs);
            }
            return null;
        }
    }

    /**
     * Obtiene un cliente por su DNI
     */
    public Cliente obtenerPorDNI(String dni) throws SQLException {
        String sql = "SELECT chr_cliecodigo, vch_cliepaterno, vch_cliematerno, vch_clienombre, "
                + "chr_cliedni, vch_clieciudad, vch_cliedireccion, vch_clietelefono, vch_clieemail "
                + "FROM cliente WHERE chr_cliedni = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearCliente(rs);
            }
            return null;
        }
    }

    /**
     * Inserta un nuevo cliente
     */
    public void insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (chr_cliecodigo, vch_cliepaterno, vch_cliematerno, "
                + "vch_clienombre, chr_cliedni, vch_clieciudad, vch_cliedireccion, "
                + "vch_clietelefono, vch_clieemail) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getCodigo());
            ps.setString(2, cliente.getPaterno());
            ps.setString(3, cliente.getMaterno());
            ps.setString(4, cliente.getNombre());
            ps.setString(5, cliente.getDni());
            ps.setString(6, cliente.getCiudad());
            ps.setString(7, cliente.getDireccion());
            ps.setString(8, cliente.getTelefono());
            ps.setString(9, cliente.getEmail());

            ps.executeUpdate();
        }
    }

    /**
     * Actualiza un cliente existente
     */
    public void actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET vch_cliepaterno = ?, vch_cliematerno = ?, "
                + "vch_clienombre = ?, chr_cliedni = ?, vch_clieciudad = ?, "
                + "vch_cliedireccion = ?, vch_clietelefono = ?, vch_clieemail = ? "
                + "WHERE chr_cliecodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getPaterno());
            ps.setString(2, cliente.getMaterno());
            ps.setString(3, cliente.getNombre());
            ps.setString(4, cliente.getDni());
            ps.setString(5, cliente.getCiudad());
            ps.setString(6, cliente.getDireccion());
            ps.setString(7, cliente.getTelefono());
            ps.setString(8, cliente.getEmail());
            ps.setString(9, cliente.getCodigo());

            ps.executeUpdate();
        }
    }

    /**
     * Elimina un cliente
     */
    public void eliminar(String codigo) throws SQLException {
        String sql = "DELETE FROM cliente WHERE chr_cliecodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.executeUpdate();
        }
    }

    /**
     * Mapea un ResultSet a un objeto Cliente
     */
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setCodigo(rs.getString("chr_cliecodigo"));
        cliente.setPaterno(rs.getString("vch_cliepaterno"));
        cliente.setMaterno(rs.getString("vch_cliematerno"));
        cliente.setNombre(rs.getString("vch_clienombre"));
        cliente.setDni(rs.getString("chr_cliedni"));
        cliente.setCiudad(rs.getString("vch_clieciudad"));
        cliente.setDireccion(rs.getString("vch_cliedireccion"));
        cliente.setTelefono(rs.getString("vch_clietelefono"));
        cliente.setEmail(rs.getString("vch_clieemail"));
        return cliente;
    }
}
