package es.sergiopt.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.sergiopt.map.EjercicioMusculo;

public class EjercicioMusculoDao {

    public EjercicioMusculoDao() {}

    public boolean add(EjercicioMusculo ejercicioMusculo) {
        boolean añadido = false;
        String sql = "INSERT INTO ejercicio_musculos (id_ejercicio, id_musculo, descripcion, es_directo, porcentaje_activacion) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            statement.setInt(1, ejercicioMusculo.getIdEjercicio());
            statement.setInt(2, ejercicioMusculo.getIdMusculo());
            statement.setString(3, ejercicioMusculo.getDescripcion());
            statement.setBoolean(4, ejercicioMusculo.getEsDirecto());
            statement.setInt(5, ejercicioMusculo.getPorcentajeActivacion());

            añadido = statement.executeUpdate() != 0;

            ConexionBd.comprobarTransaccion(conn, añadido);

        } catch (SQLException e) {
            System.err.println("Error al insertar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return añadido;
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

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) ejerciciosMusculos.add(rsToEjercicioMusculo(rs));
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
        
        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idEjercicio);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) ejerciciosMusculos.add(rsToEjercicioMusculo(rs));
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

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idEjercicio);
            statement.setInt(2, idMusculo);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) ejercicioMusculo = rsToEjercicioMusculo(rs);
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al leer en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return ejercicioMusculo;
    }

    public boolean update(int idEjercicio, int idMusculo, EjercicioMusculo ejercicioMusculo) {
        boolean modificado = false;
        String sql = "UPDATE ejercicio_musculos SET descripcion = ?, es_directo = ?, porcentaje_activacion = ? WHERE id_ejercicio = ? AND id_musculo = ?";

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            statement.setString(1, ejercicioMusculo.getDescripcion());
            statement.setBoolean(2, ejercicioMusculo.getEsDirecto());
            statement.setInt(3, ejercicioMusculo.getPorcentajeActivacion());
            statement.setInt(4, idEjercicio);
            statement.setInt(5, idMusculo);

            modificado = statement.executeUpdate() != 0;

            ConexionBd.comprobarTransaccion(conn, modificado);
        
        } catch (SQLException e) {
            System.err.println("Error al actualizar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return modificado;
    }
    
    public boolean deleteAll(int idEjercicio) {
        boolean eliminado = false;
        String sql = "DELETE FROM ejercicio_musculos WHERE id_ejercicio = ?";

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            statement.setInt(1, idEjercicio);

            eliminado = statement.executeUpdate() != 0;

            ConexionBd.comprobarTransaccion(conn, eliminado);
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return eliminado;
    }
    
    public boolean delete(int idEjercicio, int idMusculo) {
        boolean eliminado = false;
        String sql = "DELETE FROM ejercicio_musculos WHERE id_ejercicio = ? AND id_musculo = ?";

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            statement.setInt(1, idEjercicio);
            statement.setInt(2, idMusculo);

            eliminado = statement.executeUpdate() != 0;

            ConexionBd.comprobarTransaccion(conn, eliminado);
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return eliminado;
    }
}
