package es.sergiopt.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.sergiopt.map.Ejercicio;
import java.sql.Statement;
import java.sql.Types;

public class EjercicioDao {

    public EjercicioDao() {}    

    public Ejercicio add(Ejercicio ejercicio) {        
        String sql = "INSERT INTO ejercicios (nombre, descripcion, ruta_imagen, peso_minimo, peso_maximo) VALUES (?, ?, ?, ?, ?)";
            
            try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                conn.setAutoCommit(false);
                
                statement.setString(1, ejercicio.getNombre());
                statement.setString(2, ejercicio.getDescripcion());
                statement.setString(3, ejercicio.getRutaImagen());
                
                // Unica comprobación de null porque son unicos campos que pueden ser null en bd
                // debido a que algunos ejercicios pueden no depender de un peso minimo o máximo 
                // como los ejercicios de peso corporal
                
                if (ejercicio.getPesoMinimo() == null) statement.setNull(4, Types.INTEGER);
                else statement.setInt(4, ejercicio.getPesoMinimo());
                
                if (ejercicio.getPesoMaximo() == null) statement.setNull(5, Types.INTEGER);
                else statement.setInt(5, ejercicio.getPesoMaximo());                
                
                boolean añadido = statement.executeUpdate() != 0;

                // Obtener ejercicio añadido
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) ejercicio.setId(rs.getInt(1));
                
                ConexionBd.comprobarTransaccion(conn, añadido);
                return ejercicio;                
            
            } catch (SQLException e) {
                System.err.println("Error al insertar en bd: " + e.getMessage());
                e.printStackTrace();
            }

        return null;
    }

    private Ejercicio rsToEjercicio(ResultSet rs) throws SQLException {
        // Obtener propiedades
        int idEjercicio = rs.getInt("id_ejercicio");
        String nombre = rs.getString("nombre");
        String descripcion = rs.getString("descripcion");
        String rutaImagen = rs.getString("ruta_imagen");
        int pesoMinimo = rs.getInt("peso_minimo");
        int pesoMaximo = rs.getInt("peso_maximo");

        // Devolver objeto
        return new Ejercicio(idEjercicio, nombre, descripcion, rutaImagen, pesoMinimo, pesoMaximo);
    }

    public List<Ejercicio> getAll() {
        List<Ejercicio> ejercicios = new ArrayList<>();
        String sql = "SELECT * FROM ejercicios";

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) ejercicios.add(rsToEjercicio(rs));
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

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, "%" + nombre + "%");
            
            ResultSet rs = statement.executeQuery();
            while (rs.next()) ejercicios.add(rsToEjercicio(rs));
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

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idEjercicio);
            
            ResultSet rs = statement.executeQuery();
            if (rs.next()) ejercicio = rsToEjercicio(rs);    
            rs.close();        
        
        } catch (SQLException e) {
            System.err.println("Error al leer en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return ejercicio;
    }

    public boolean update(int idEjercicio, Ejercicio nuevoEjercicio) {
        boolean modificado = false;
        String sql = "UPDATE ejercicios SET nombre = ?, descripcion = ?, ruta_imagen = ?, peso_minimo = ?, peso_maximo = ? WHERE id_ejercicio = ?";

        try (Connection conn = ConexionBd.abrir().getConn(); PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            statement.setString(1, nuevoEjercicio.getNombre());
            statement.setString(2, nuevoEjercicio.getDescripcion());
            statement.setString(3, nuevoEjercicio.getRutaImagen());
            
            if (nuevoEjercicio.getPesoMinimo() == null) statement.setNull(4, Types.INTEGER);
            else statement.setInt(4, nuevoEjercicio.getPesoMinimo());
                
            if (nuevoEjercicio.getPesoMaximo() == null) statement.setNull(5, Types.INTEGER);
            else statement.setInt(5, nuevoEjercicio.getPesoMaximo()); 
            
            statement.setInt(6, idEjercicio);

            modificado = statement.executeUpdate() != 0;

            ConexionBd.comprobarTransaccion(conn, modificado);

        } catch (SQLException e) {
            System.err.println("Error al actualizar en bd: " + e.getMessage());
            e.printStackTrace();
        }

        return modificado;
    }

    public boolean delete(int idEjercicio) {
        boolean eliminado = false;
        String sql = "DELETE FROM ejercicios WHERE id_ejercicio = ?";

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
}
