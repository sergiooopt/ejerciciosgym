/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.sergiopt.utils;

/**
 *
 * @author sergiopt
 */
public class Constantes {
    
    // Si no se pasa parámetro el host es tomcat en local por defecto
    private static String host = "localhost:8080";

    static {
        // Comprueba -Dhost al arrancar aplicación
        if (System.getProperty("host") != null) 
            host = System.getProperty("host");
    }

    // Constante para acceder a servicio web
    public static final String API = "http://" + host + "/wsejerciciosgym/api/";

}
