package es.sergiopt.daos;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Conexion {
    private static Conexion instancia;
    private DataSource dataSource;

    private Conexion() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/ejerciciosgym-ds");

        } catch (NamingException e) {
            System.err.println("Error al obtener contexto de la bd: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método que obtiene una instancia de {@code ConexionBd}.
     * 
     * @return instancia
     */
    public static Conexion abrir() {
        if (instancia == null)
            instancia = new Conexion();
        
        return instancia;
    }

    /**
     * Método que devuelve la conexión a través del {@code DataSource}.
     * 
     * @return conexión a base de datos
     * @throws SQLException
     */
    public Connection getConn() throws SQLException {
        if (dataSource == null) {
            System.err.println("DataSource está vacio, no se puede devolver la conexión :(");
            throw new SQLException();
        }

        return dataSource.getConnection();
    }
}
