/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.sergiopt.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.sergiopt.map.Musculo;
import es.sergiopt.utils.Constantes;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 *
 * @author sergiopt
 */
public class MusculoService {        
    
    public static List<Musculo> getAll() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "musculos")).header("Accept", "application/json").GET().build();        
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
            FormularioService.comprobarError(response);
            
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<List<Musculo>>() {});
        
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudieron obtener los músculos a través del servicio web", e);
        }
    }
    
    public static Musculo get(int id) {
       try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "musculos/" + id)).header("Accept", "application/json").GET().build();        
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
            FormularioService.comprobarError(response);
            
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), Musculo.class);
        
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudo obtener el músculo a través del servicio web", e);
        } 
    }
}
