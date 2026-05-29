package es.sergiopt.controllers;

import java.util.List;

import es.sergiopt.daos.EjercicioDao;
import es.sergiopt.models.Ejercicio;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
    
    private static final String RUTA_IMAGEN = "/opt/ejerciciosgym/imagenes/";
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("nombre") String nombre) {
        EjercicioDao dao = new EjercicioDao();
        List<Ejercicio> ejercicios = (nombre != null && !nombre.isEmpty())
            ? dao.getAllByName(nombre)
            : dao.getAll();
        return Response.ok(ejercicios).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        EjercicioDao dao = new EjercicioDao();
        Ejercicio ejercicio = dao.get(id);
        return ejercicio == null
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.ok(ejercicio).build();
    }        
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Ejercicio ejercicio) {
        EjercicioDao dao = new EjercicioDao();
        Ejercicio ejercicioAñadido = dao.add(ejercicio);
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
        Ejercicio ejercicio = dao.get(id);
        
        if (ejercicio == null) return Response.status(Response.Status.NOT_FOUND).build();
            
        File file = new File(RUTA_IMAGEN + ejercicio.getRutaImagen());
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
            Ejercicio ejercicio = dao.get(id);
        
            if (ejercicio == null) return Response.status(Response.Status.NOT_FOUND).build();
                
            String nombreEjercicio = ejercicio.getNombre().trim().toLowerCase().replaceAll(" ", "_").replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
            java.nio.file.Path ruta = Paths.get(RUTA_IMAGEN, id + "-" + nombreEjercicio + ".jpg");
            Files.copy(input, ruta, StandardCopyOption.REPLACE_EXISTING);
            
            // Guardar ruta relativa en bd
            ejercicio.setRutaImagen(id + "-" + nombreEjercicio + ".jpg");
            dao.update(id, ejercicio);
            
            // Devolver respuesta 200
            return Response.ok(ejercicio).build();
        
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @DELETE
    @Path("/{id}/imagen")
    public Response deleteImagen(@PathParam("id") int id) {
        try {
            EjercicioDao dao = new EjercicioDao();
            Ejercicio ejercicio = dao.get(id);
        
            if (ejercicio == null) return Response.status(Response.Status.NOT_FOUND).build();
        
            String rutaImagen = ejercicio.getRutaImagen();
            Files.delete(java.nio.file.Path.of(RUTA_IMAGEN + rutaImagen));
            
            ejercicio.setRutaImagen(null);
            dao.update(id, ejercicio);
            
            return Response.noContent().build();
            
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
