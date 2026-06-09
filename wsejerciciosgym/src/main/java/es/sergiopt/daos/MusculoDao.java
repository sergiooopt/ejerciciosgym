package es.sergiopt.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import es.sergiopt.models.Musculo;

public class MusculoDao {

    public MusculoDao() {}

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

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next())
                musculos.add(rsToMusculo(rs));
            
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

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idMusculo);

            ResultSet rs = statement.executeQuery();
            if (rs.next())
                musculo = rsToMusculo(rs);
            
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al leer en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return musculo;
    }

    public Musculo add(Musculo musculo) {
        String sql = "INSERT INTO musculos (nombre, descripcion, zona, grupo) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, musculo.getNombre());
            statement.setString(2, musculo.getDescripcion());
            statement.setString(3, musculo.getZona());
            statement.setString(4, musculo.getGrupo());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                musculo.setId(rs.getInt(1));

            return musculo;

        } catch (SQLException e) {
            System.err.println("Error al insertar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean update(int idMusculo, Musculo nuevoMusculo) {
        boolean modificado = false;
        String sql = "UPDATE musculos SET nombre = ?, descripcion = ?, zona = ?, grupo = ? WHERE id_musculo = ?";

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nuevoMusculo.getNombre());
            statement.setString(2, nuevoMusculo.getDescripcion());
            statement.setString(3, nuevoMusculo.getZona());
            statement.setString(4, nuevoMusculo.getGrupo());
            statement.setInt(5, idMusculo);

            modificado = statement.executeUpdate() != 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return modificado;
    }

    public boolean delete(int idMusculo) {
        boolean eliminado = false;
        String sqlRelacion = "DELETE FROM ejercicio_musculos WHERE id_musculo = ?";
        String sqlOriginal = "DELETE FROM musculos WHERE id_musculo = ?";

        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = Conexion.abrir().getConn();
            conn.setAutoCommit(false);
            
            // Eliminar relación
            statement = conn.prepareStatement(sqlRelacion);            
            statement.setInt(1, idMusculo);
            statement.executeUpdate();

            statement.close();

            // Eliminar original
            statement = conn.prepareStatement(sqlOriginal);
            statement.setInt(1, idMusculo);
            eliminado = statement.executeUpdate() != 0;

            conn.commit();

        } catch (SQLException e1) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException e2) {
                System.err.println("Error al hacer rollback en bd: " + e2.getMessage());
                e1.printStackTrace();
            }

            System.err.println("Error al eliminar en bd: " + e1.getMessage());
            e1.printStackTrace();

        } finally {
            try {
                if (statement != null)
                    statement.close();
                
                if (conn != null)                 
                    conn.setAutoCommit(true);
                    conn.close();

            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión en bd: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return eliminado;
    }
}
