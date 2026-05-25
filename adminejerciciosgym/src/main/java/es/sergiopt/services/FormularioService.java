/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.sergiopt.services;

import java.net.http.HttpResponse;

/**
 *
 * @author sergiopt
 */
public class FormularioService {
    
    public static void comprobarError(HttpResponse<?> response) {
        if (response.statusCode() != 200 && response.statusCode() != 204) 
            throw new ServiceException("Error: respuesta fallida, código " + response.statusCode() + " - " + response.body());
    }
}
