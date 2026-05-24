package es.sergiopt.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import es.sergiopt.models.EjercicioMusculo;

public class EjercicioMusculoDao {

    public EjercicioMusculoDao() {
    }

    private EjercicioMusculo rsToEjercicioMusculo(ResultSet rs) throws SQLException {
        // Obtener propiedades
        int idEjercicio = rs.getInt("id_ejercicio");
        int idMusculo = rs.getInt("id_musculo");
        String descripcion = rs.getString("descripcion");
        boolean esDirecto = rs.getBoolean("es_directo");
        int porcentajeActivacion = rs.getInt("porcentaje_activacion");

        // Devolver objeto
        return new EjercicioMusculo(idEjercicio, idMusculo, descripcion, esDirecto, porcentajeActivacion);
    }

    public List<EjercicioMusculo> getAll() {
        List<EjercicioMusculo> ejerciciosMusculos = new ArrayList<>();
        String sql = "SELECT * FROM ejercicio_musculos";

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next())
                ejerciciosMusculos.add(rsToEjercicioMusculo(rs));
            
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al leer en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return ejerciciosMusculos;
    }

    public List<EjercicioMusculo> getMusculos(int idEjercicio) {
        List<EjercicioMusculo> ejerciciosMusculos = new ArrayList<>();
        String sql = "SELECT * FROM ejercicio_musculos WHERE id_ejercicio = ?";

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idEjercicio);

            ResultSet rs = statement.executeQuery();
            while (rs.next())
                ejerciciosMusculos.add(rsToEjercicioMusculo(rs));
           
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al leer en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return ejerciciosMusculos;
    }

    public EjercicioMusculo get(int idEjercicio, int idMusculo) {
        EjercicioMusculo ejercicioMusculo = null;
        String sql = "SELECT * FROM ejercicio_musculos WHERE id_ejercicio = ? AND id_musculo = ?";

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idEjercicio);
            statement.setInt(2, idMusculo);

            ResultSet rs = statement.executeQuery();
            if (rs.next())
                ejercicioMusculo = rsToEjercicioMusculo(rs);
            
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al leer en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return ejercicioMusculo;
    }

    public EjercicioMusculo add(EjercicioMusculo ejercicioMusculo) {
        String sql = "INSERT INTO ejercicio_musculos (id_ejercicio, id_musculo, descripcion, es_directo, porcentaje_activacion) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, ejercicioMusculo.getIdEjercicio());
            statement.setInt(2, ejercicioMusculo.getIdMusculo());
            statement.setString(3, ejercicioMusculo.getDescripcion());
            statement.setBoolean(4, ejercicioMusculo.getEsDirecto());
            statement.setInt(5, ejercicioMusculo.getPorcentajeActivacion());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                ejercicioMusculo.setIdEjercicio(rs.getInt(1));
                ejercicioMusculo.setIdMusculo(rs.getInt(2));
            }

            return ejercicioMusculo;

        } catch (SQLException e) {
            System.err.println("Error al insertar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean update(int idEjercicio, int idMusculo, EjercicioMusculo ejercicioMusculo) {
        boolean modificado = false;
        String sql = "UPDATE ejercicio_musculos SET descripcion = ?, es_directo = ?, porcentaje_activacion = ? WHERE id_ejercicio = ? AND id_musculo = ?";

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, ejercicioMusculo.getDescripcion());
            statement.setBoolean(2, ejercicioMusculo.getEsDirecto());
            statement.setInt(3, ejercicioMusculo.getPorcentajeActivacion());
            statement.setInt(4, idEjercicio);
            statement.setInt(5, idMusculo);

            modificado = statement.executeUpdate() != 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return modificado;
    }

    public boolean deleteAll(int idEjercicio) {
        boolean eliminados = false;
        String sql = "DELETE FROM ejercicio_musculos WHERE id_ejercicio = ?";

        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = Conexion.abrir().getConn();
            statement = conn.prepareStatement(sql);

            conn.setAutoCommit(false);

            statement.setInt(1, idEjercicio);
            eliminados = statement.executeUpdate() != 0;

            conn.commit();

        } catch (SQLException e1) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException e2) {
                System.err.println("Error al hacer rollback en bd: " + e2.getMessage());
                e1.printStackTrace();
            }

            System.err.println("Error al insertar en bd: " + e1.getMessage());
            e1.printStackTrace();

        } finally {
            try {
                if (conn != null)                 
                    conn.close();

                if (statement != null)
                    statement.close();

            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión en bd: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return eliminados;
    }

    public boolean delete(int idEjercicio, int idMusculo) {
        boolean eliminado = false;
        String sql = "DELETE FROM ejercicio_musculos WHERE id_ejercicio = ? AND id_musculo = ?";

        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = Conexion.abrir().getConn();
            statement = conn.prepareStatement(sql);

            conn.setAutoCommit(false);

            statement.setInt(1, idEjercicio);
            statement.setInt(2, idMusculo);

            eliminado = statement.executeUpdate() != 0;

            conn.commit();

        }catch (SQLException e1) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException e2) {
                System.err.println("Error al hacer rollback en bd: " + e2.getMessage());
                e1.printStackTrace();
            }

            System.err.println("Error al insertar en bd: " + e1.getMessage());
            e1.printStackTrace();

        } finally {
            try {
                if (conn != null)                 
                    conn.close();

                if (statement != null)
                    statement.close();

            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión en bd: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return eliminado;
    }
}
