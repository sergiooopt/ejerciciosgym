package es.sergiopt.controllers;

import java.util.List;

import es.sergiopt.map.Ejercicio;
import es.sergiopt.persistence.EjercicioDao;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Path("/ejercicios")
public class EjercicioController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EjercicioDao dao = new EjercicioDao();
        List<Ejercicio> ejercicios = dao.get();
        return Response.ok(ejercicios).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        EjercicioDao dao = new EjercicioDao();
        Ejercicio ejercicio = dao.read(id);
        return ejercicio == null
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.ok(ejercicio).build();
    }        
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Ejercicio ejercicio) {
        EjercicioDao dao = new EjercicioDao();
        Ejercicio ejercicioAñadido = dao.create(ejercicio);
        return ejercicioAñadido == null
            ? Response.status(Response.Status.BAD_REQUEST).build()
            : Response.status(Response.Status.OK).entity(ejercicioAñadido).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Ejercicio ejercicio) {
        EjercicioDao dao = new EjercicioDao();
        boolean modificado = dao.update(id, ejercicio);
        return Boolean.FALSE.equals(modificado)
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        EjercicioDao dao = new EjercicioDao();
        boolean eliminado = dao.delete(id);
        return Boolean.FALSE.equals(eliminado)
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.status(Response.Status.OK).build();
    }
    
    @GET
    @Path("/{id}/imagen")
    @Produces({"image/png", "image/jpeg"})
    public Response getImagen(@PathParam("id") int id) {
        EjercicioDao dao = new EjercicioDao();
        Ejercicio ejercicio = dao.read(id);
        
        if (ejercicio == null) return Response.status(Response.Status.NOT_FOUND).build();
            
        File file = new File(ejercicio.getRutaImagen());
        return !file.exists()
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.ok(file).build();
    }
    
    @POST
    @Path("/{id}/imagen")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response addImagen(@PathParam("id") int id, InputStream input) {
        try {
            EjercicioDao dao = new EjercicioDao();
            Ejercicio ejercicio = dao.read(id);
        
            if (ejercicio == null) return Response.status(Response.Status.NOT_FOUND).build();
                
            String rutaImagen = "/opt/ejerciciosgym/imagenes/" + id + "-" + ejercicio.getNombre() + ".jpg";
            Files.copy(input, Paths.get(rutaImagen), StandardCopyOption.REPLACE_EXISTING);
            
            // Actualizar cambio de ruta en bd
            ejercicio.setRutaImagen(rutaImagen);
            dao.update(id, ejercicio);
            
            // Devolver respuesta 200
            return Response.ok(ejercicio).build();
        
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @DELETE
    @Path("{/id}/imagen")
    public Response deleteImagen(@PathParam("id") int id) {
        try {
            EjercicioDao dao = new EjercicioDao();
            Ejercicio ejercicio = dao.read(id);
        
            if (ejercicio == null) return Response.status(Response.Status.NOT_FOUND).build();
        
            String rutaImagen = ejercicio.getRutaImagen();        
            Files.delete(java.nio.file.Path.of(rutaImagen));
            
            ejercicio.setRutaImagen(null);
            dao.update(id, ejercicio);
            
            return Response.noContent().build();
            
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
