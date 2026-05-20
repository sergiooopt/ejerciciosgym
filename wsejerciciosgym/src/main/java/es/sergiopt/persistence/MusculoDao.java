package es.sergiopt.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.sergiopt.map.Musculo;

public class MusculoDao {

    public MusculoDao() {}

    public boolean add(Musculo musculo) {
        boolean añadido = false;
        String sql = "INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES (?, ?, ?, ?)";
            
        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            statement.setString(1, musculo.getNombre());
            statement.setString(2, musculo.getDescripcion());
            statement.setString(3, musculo.getZona());
            statement.setString(4, musculo.getGrupo());
            
            añadido = statement.executeUpdate() != 0;            

            ConexionBd.comprobarTransaccion(conn, añadido);

        } catch (SQLException e) {
            System.err.println("Error al insertar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return añadido;
    }
    
    private Musculo rsToMusculo(ResultSet rs) throws SQLException {
        // Obtener propiedades
        int idMusculo = rs.getInt("id_musculo");
        String nombre = rs.getString("nombre");
        String descripcion = rs.getString("descripcion");
        String zona = rs.getString("zona");
        String grupo = rs.getString("grupo");

        // Devolver objeto
        return new Musculo(idMusculo, nombre, descripcion, zona, grupo);
    }

    public List<Musculo> getAll() {
        List<Musculo> musculos = new ArrayList<>();
        String sql = "SELECT * FROM musculos";

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) musculos.add(rsToMusculo(rs));
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al leer en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return musculos;
    }

    public Musculo get(int idMusculo) {
        Musculo musculo = null;
        String sql = "SELECT * FROM musculos WHERE id_musculo = ?";

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idMusculo);
            
            ResultSet rs = statement.executeQuery();
            if (rs.next()) musculo = rsToMusculo(rs);   
            rs.close();         
        
        } catch (SQLException e) {
            System.err.println("Error al leer en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return musculo;
    }

    public boolean update(int idMusculo, Musculo nuevoMusculo) {
        boolean modificado = false;
        String sql = "UPDATE musculos SET nombre = ?, descripcion = ?, zona = ?, grupo = ? WHERE id_musculo = ?";

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            statement.setString(1, nuevoMusculo.getNombre());
            statement.setString(2, nuevoMusculo.getDescripcion());
            statement.setString(3, nuevoMusculo.getZona());
            statement.setString(4, nuevoMusculo.getGrupo());
            statement.setInt(5, idMusculo);

            modificado = statement.executeUpdate() != 0;

            ConexionBd.comprobarTransaccion(conn, modificado);

        } catch (SQLException e) {
            System.err.println("Error al actualizar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return modificado;
    }

    public boolean delete(int idMusculo) {
        boolean eliminado = false;
        String sql = "DELETE FROM musculos WHERE id_musculo = ?";

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            statement.setInt(1, idMusculo);
            eliminado = statement.executeUpdate() != 0;

            ConexionBd.comprobarTransaccion(conn, eliminado);

        } catch (SQLException e) {
            System.err.println("Error al eliminar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return eliminado;        
    }
}
