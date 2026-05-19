package es.sergiopt.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConexionBd {
    private static ConexionBd instancia;
    private DataSource dataSource;  

    private ConexionBd() {
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
    public static ConexionBd abrir() {
        if (instancia == null) instancia = new ConexionBd();
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

    /**
     * Método que modifica la conexión para realizar commit o rollback.
     * 
     * @param conn conexión a la base de datos
     * @param condicion que si es true hace commit y si es false hace rollback
     * @throws SQLException
     */
    public static void comprobarTransaccion(Connection conn, boolean condicion) throws SQLException {        
        if (conn != null) {
            if (condicion) {
                conn.commit();
            } else {            
                conn.rollback();
            }
        }
    }
}
