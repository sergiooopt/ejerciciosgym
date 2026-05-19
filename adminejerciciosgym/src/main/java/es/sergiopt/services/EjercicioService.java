/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.sergiopt.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.sergiopt.map.Ejercicio;
import es.sergiopt.utils.Constantes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.List;

/**
 *
 * @author sergiopt
 */
public class EjercicioService {
    
    public static List<Ejercicio> getAllEjercicios() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicios")).header("Accept", "application/json").GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            FormularioService.comprobarError(response);
            
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<List<Ejercicio>>() {});
        
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudieron obtener los ejercicios a través del servicio web", e);
        }
    }
    
    public static Ejercicio getEjercicio(int id) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicios/" + id)).header("Accept", "application/json").GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            FormularioService.comprobarError(response);
            
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), Ejercicio.class);
            
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudo obtener el ejercicio a través del servicio web", e);
        }
    }
    
    public static Ejercicio addEjercicio(Ejercicio ejercicio) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(ejercicio);
        
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicios"))                    
                    .header("Content-Type", "application/json").header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json)).build();                    
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            FormularioService.comprobarError(response);
            
            return mapper.readValue(response.body(), Ejercicio.class);
            
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudo añadir el ejercicio a través del servicio web", e);
        }
    }
      
    
    public static void updateEjercicio(int idEjercicio, Ejercicio ejercicio) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(ejercicio);
        
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicios/" + idEjercicio))                    
                    .header("Content-Type", "application/json").header("Accept", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json)).build();                    
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            FormularioService.comprobarError(response);
            
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se puede actualizar el ejercicio a través del servicio web", e);
        }
    }
    
    public static void deleteEjercicio(int idEjercicio) {
         try {            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicios/" + idEjercicio)).DELETE().build();
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            
            FormularioService.comprobarError(response);
            
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudo añadir el ejercicio a través del servicio web", e);
        }    
    }
    
    // -- IMAGEN --
    
    public static File getImagenEjercicio(int idEjercicio) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicios/" + idEjercicio + "/imagen"))
                    .header("Accept", "image/*").GET().build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            FormularioService.comprobarError(response);

            // Crear archivo temporal
            File archivoTemproral = File.createTempFile("ejercicio-" + idEjercicio + "-", ".jpg");
            Files.write(archivoTemproral.toPath(), response.body());

            return archivoTemproral;

        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error al obtener la imagen", e);
        }
    }
    
    public static void addImagenEjercicio(int idEjercicio, File rutaImagenEjercicio) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicios/" + idEjercicio + "/imagen"))
                    .header("Content-Type", "application/octet-stream").header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofFile(rutaImagenEjercicio.toPath())).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            FormularioService.comprobarError(response);
            
        } catch (FileNotFoundException e) {
            throw new ServiceException("Error: no se pudo subir la imagen", e);
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudo subir la imagen", e);
        }
    }  
    
    public static void deleteImagenEjercicio(int idEjercicio) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constantes.API + "ejercicios/" + idEjercicio + "/imagen")).DELETE().build();
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            
            FormularioService.comprobarError(response);
        
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("Error: no se pudo eliminar la imagen d e el ejercicio a través del servicio web", e);
        } 
    }
    
}
