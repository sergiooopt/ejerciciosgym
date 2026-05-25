package es.sergiopt.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import es.sergiopt.models.EjercicioMusculo;
import es.sergiopt.utils.Constantes;

public class EjercicioMusculosService {

     public static List<EjercicioMusculo> getAll(int idEjercicio) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicio_musculos/" + idEjercicio))
                    .header("Accept", "application/json").GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            FormularioService.comprobarError(response);
            
            ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            return mapper.readValue(response.body(), new TypeReference<List<EjercicioMusculo>>() {});
        
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudieron obtener los músculos a través del servicio web", e);
        }
    }
    
    public static EjercicioMusculo get(int idEjercicio, int idMusculo) {
         try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicio_musculos/" + idEjercicio + "/" + idMusculo))
                    .header("Accept", "application/json").GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            FormularioService.comprobarError(response);
            
            ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            return mapper.readValue(response.body(), EjercicioMusculo.class);
        
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudieron obtener los músculos a través del servicio web", e);
        }
    }
    
    public static void add(EjercicioMusculo musculoInvolucrado) {
        try {
            ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            String json = mapper.writeValueAsString(musculoInvolucrado);
            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicio_musculos"))
                    .header("Content-Type", "application/json").header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json)).build();        
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            FormularioService.comprobarError(response);
            
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudo añadir el ejercicio a través del servicio web", e);
        }
    }    

    public static void deleteAll(int idEjercicio) {
        try {            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicio_musculos/" + idEjercicio)).DELETE().build();
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            
            FormularioService.comprobarError(response);
            
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudo añadir el ejercicio a través del servicio web", e);
        }
    }    

}
