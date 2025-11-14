package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Cliente
 *
 * @author EurekaBank
 */
public class ClienteDAO {

    /**
     * Obtiene un cliente por su código
     *
     * @param codigo Código del cliente
     * @return Cliente encontrado o null
     * @throws SQLException si hay error en la BD
     */
    public Cliente obtenerPorCodigo(String codigo) throws SQLException {
        String query = "SELECT chr_cliecodigo, vch_cliepaterno, vch_cliematerno, "
                + "vch_clienombre, vch_cliedninif, vch_clieciudad, vch_cliedireccion, "
                + "vch_clietelefono, vch_clieemail "
                + "FROM cliente WHERE chr_cliecodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
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
        return null;
    }

    /**
     * Obtiene un cliente por su DNI
     *
     * @param dni DNI del cliente
     * @return Cliente encontrado o null
     * @throws SQLException si hay error en la BD
     */
    public Cliente obtenerPorDNI(String dni) throws SQLException {
        String query = "SELECT chr_cliecodigo, vch_cliepaterno, vch_cliematerno, "
                + "vch_clienombre, chr_cliedni, vch_clieciudad, vch_cliedireccion, "
                + "vch_clietelefono, vch_clieemail "
                + "FROM cliente WHERE chr_cliedni = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
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
        return null;
    }

    /**
     * Obtiene todos los clientes
     *
     * @return Lista de clientes
     * @throws SQLException si hay error en la BD
     */
    public List<Cliente> obtenerTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT chr_cliecodigo, vch_cliepaterno, vch_cliematerno, "
                + "vch_clienombre, chr_cliedni, vch_clieciudad, vch_cliedireccion, "
                + "vch_clietelefono, vch_clieemail FROM cliente";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
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
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    /**
     * Inserta un nuevo cliente
     *
     * @param cliente Cliente a insertar
     * @return true si se insertó correctamente
     * @throws SQLException si hay error en la BD
     */
    public boolean insertar(Cliente cliente) throws SQLException {
        String query = "INSERT INTO cliente (chr_cliecodigo, vch_cliepaterno, vch_cliematerno, "
                + "vch_clienombre, chr_cliedni, vch_clieciudad, vch_cliedireccion, "
                + "vch_clietelefono, vch_clieemail) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, cliente.getCodigo());
            ps.setString(2, cliente.getPaterno());
            ps.setString(3, cliente.getMaterno());
            ps.setString(4, cliente.getNombre());
            ps.setString(5, cliente.getDni());
            ps.setString(6, cliente.getCiudad());
            ps.setString(7, cliente.getDireccion());
            ps.setString(8, cliente.getTelefono());
            ps.setString(9, cliente.getEmail());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Actualiza un cliente
     *
     * @param cliente Cliente a actualizar
     * @return true si se actualizó correctamente
     * @throws SQLException si hay error en la BD
     */
    public boolean actualizar(Cliente cliente) throws SQLException {
        String query = "UPDATE cliente SET vch_cliepaterno = ?, vch_cliematerno = ?, "
                + "vch_clienombre = ?, chr_cliedni = ?, vch_clieciudad = ?, "
                + "vch_cliedireccion = ?, vch_clietelefono = ?, vch_clieemail = ? "
                + "WHERE chr_cliecodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, cliente.getPaterno());
            ps.setString(2, cliente.getMaterno());
            ps.setString(3, cliente.getNombre());
            ps.setString(4, cliente.getDni());
            ps.setString(5, cliente.getCiudad());
            ps.setString(6, cliente.getDireccion());
            ps.setString(7, cliente.getTelefono());
            ps.setString(8, cliente.getEmail());
            ps.setString(9, cliente.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }
}
