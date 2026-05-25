/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.sergiopt.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import es.sergiopt.models.Musculo;
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
            
            ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
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
            
            ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            return mapper.readValue(response.body(), Musculo.class);
        
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudo obtener el músculo a través del servicio web", e);
        } 
    }
    
    public static Musculo add(Musculo musculo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(musculo);
            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "musculos")).header("Accept", "application/json")
                    .header("Content-Type", "application/json").header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json)).build();                    
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
            FormularioService.comprobarError(response);
            
            return mapper.readValue(response.body(), Musculo.class);
        
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudo añadir el músculo a través del servicio web", e);
        }
    }
    
    public static void update(int idMusculo, Musculo nuevoMusculo) {
        try {
            ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            String json = mapper.writeValueAsString(nuevoMusculo);
        
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicios/" + idMusculo))
                    .header("Content-Type", "application/json").header("Accept", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json)).build();                    
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            FormularioService.comprobarError(response);
            
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se puede actualizar el ejercicio a través del servicio web", e);
        }
    }
    
    public static void delete(int idMusculo) {
       try {            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicios/" + idMusculo)).DELETE().build();
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            
            FormularioService.comprobarError(response);
            
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudo eliminar el músculo a través del servicio web", e);
        }    
    }
}
