package es.sergiopt.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.sergiopt.models.Ejercicio;

import java.sql.Statement;
import java.sql.Types;

public class EjercicioDao {

    public EjercicioDao() {
    }

    private Ejercicio rsToEjercicio(ResultSet rs) throws SQLException {
        // Obtener propiedades
        int idEjercicio = rs.getInt("id_ejercicio");
        String nombre = rs.getString("nombre");
        String descripcion = rs.getString("descripcion");
        String rutaImagen = rs.getString("ruta_imagen");

        // Se recibe como Integer para poder recibir null
        Integer pesoMinimo = rs.getObject("peso_minimo", Integer.class);
        Integer pesoMaximo = rs.getObject("peso_maximo", Integer.class);

        // Devolver objeto
        return new Ejercicio(idEjercicio, nombre, descripcion, rutaImagen, pesoMinimo, pesoMaximo);
    }

    public List<Ejercicio> getAll() {
        List<Ejercicio> ejercicios = new ArrayList<>();
        String sql = "SELECT * FROM ejercicios";

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next())
                ejercicios.add(rsToEjercicio(rs));

            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al leer en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return ejercicios;
    }

    public List<Ejercicio> getAllByName(String nombre) {
        List<Ejercicio> ejercicios = new ArrayList<>();
        String sql = "SELECT * FROM ejercicios WHERE nombre LIKE ?";

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, "%" + nombre + "%");

            ResultSet rs = statement.executeQuery();
            while (rs.next())
                ejercicios.add(rsToEjercicio(rs));

            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al leer en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return ejercicios;
    }

    public Ejercicio get(int idEjercicio) {
        Ejercicio ejercicio = null;
        String sql = "SELECT * FROM ejercicios WHERE id_ejercicio = ?";

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idEjercicio);

            ResultSet rs = statement.executeQuery();
            if (rs.next())
                ejercicio = rsToEjercicio(rs);

            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al leer en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return ejercicio;
    }

    public Ejercicio add(Ejercicio ejercicio) {
        String sql = "INSERT INTO ejercicios (nombre, descripcion, ruta_imagen, peso_minimo, peso_maximo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.abrir().getConn();
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ejercicio.getNombre());
            statement.setString(2, ejercicio.getDescripcion());
            statement.setString(3, ejercicio.getRutaImagen());

            // Unica comprobación de null porque son unicos campos que pueden ser null en bd
            // debido a que algunos ejercicios pueden no depender de un peso minimo o máximo
            // como los ejercicios de peso corporal

            if (ejercicio.getPesoMinimo() == null)
                statement.setNull(4, Types.INTEGER);
            else
                statement.setInt(4, ejercicio.getPesoMinimo());

            if (ejercicio.getPesoMaximo() == null)
                statement.setNull(5, Types.INTEGER);
            else
                statement.setInt(5, ejercicio.getPesoMaximo());

            statement.executeUpdate();

            // Obtener ejercicio añadido
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())
                ejercicio.setId(rs.getInt(1));

            return ejercicio;

        } catch (SQLException e) {
            System.err.println("Error al insertar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean update(int idEjercicio, Ejercicio nuevoEjercicio) {
        boolean modificado = false;
        String sql = "UPDATE ejercicios SET nombre = ?, descripcion = ?, ruta_imagen = ?, peso_minimo = ?, peso_maximo = ? WHERE id_ejercicio = ?";

        try (Connection conn = Conexion.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nuevoEjercicio.getNombre());
            statement.setString(2, nuevoEjercicio.getDescripcion());
            statement.setString(3, nuevoEjercicio.getRutaImagen());

            if (nuevoEjercicio.getPesoMinimo() == null)
                statement.setNull(4, Types.INTEGER);
            else
                statement.setInt(4, nuevoEjercicio.getPesoMinimo());

            if (nuevoEjercicio.getPesoMaximo() == null)
                statement.setNull(5, Types.INTEGER);
            else
                statement.setInt(5, nuevoEjercicio.getPesoMaximo());

            statement.setInt(6, idEjercicio);

            modificado = statement.executeUpdate() != 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return modificado;
    }

    public boolean delete(int idEjercicio) {
        boolean eliminado = false;
        String sqlRelacion = "DELETE FROM ejercicio_musculos WHERE id_ejercicio = ?";
        String sqlOriginal = "DELETE FROM ejercicios WHERE id_ejercicio = ?";

        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = Conexion.abrir().getConn();
            conn.setAutoCommit(false);

            // Eliminar relación
            statement = conn.prepareStatement(sqlRelacion);
            statement.setInt(1, idEjercicio);
            statement.executeUpdate();

            statement.close();

            // Eliminar original
            statement = conn.prepareStatement(sqlOriginal);
            statement.setInt(1, idEjercicio);
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
