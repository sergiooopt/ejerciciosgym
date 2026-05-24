package es.sergiopt.controllers;

import java.util.List;

import es.sergiopt.daos.EjercicioMusculoDao;
import es.sergiopt.models.EjercicioMusculo;
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

@Path("/ejercicio_musculos")
public class EjercicioMusculoController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EjercicioMusculoDao dao = new EjercicioMusculoDao();
        List<EjercicioMusculo> ejerciciosMusculos = dao.getAll();
        return Response.ok(ejerciciosMusculos).build();
    }

    @GET
    @Path("/{idEjercicio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMusculos(@PathParam("idEjercicio") int idEjercicio) {
        EjercicioMusculoDao dao = new EjercicioMusculoDao();
        List<EjercicioMusculo> ejerciciosMusculos = dao.getMusculos(idEjercicio);
        return ejerciciosMusculos.isEmpty()
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.ok(ejerciciosMusculos).build();
    }
    
    @GET
    @Path("/{idEjercicio}/{idMusculo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("idEjercicio") int idEjercicio, @PathParam("idMusculo") int idMusculo) {
        EjercicioMusculoDao dao = new EjercicioMusculoDao();
        EjercicioMusculo ejercicioMusculo = dao.get(idEjercicio, idMusculo);
        return ejercicioMusculo == null
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.ok(ejercicioMusculo).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(EjercicioMusculo ejercicioMusculo) {
        EjercicioMusculoDao dao = new EjercicioMusculoDao();
        EjercicioMusculo musculoInvolucrado = dao.add(ejercicioMusculo);
        return musculoInvolucrado == null
            ? Response.status(Response.Status.BAD_REQUEST).build()
            : Response.status(Response.Status.OK).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idEjercicio}/{idMusculo}")
    public Response update(@PathParam("idEjercicio") int idEjercicio, @PathParam("idMusculo") int idMusculo, EjercicioMusculo ejercicioMusculo) {
        EjercicioMusculoDao dao = new EjercicioMusculoDao();
        boolean modificado = dao.update(idEjercicio, idMusculo, ejercicioMusculo);
        return Boolean.FALSE.equals(modificado)
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.status(Response.Status.OK).build();
    }
    
    @DELETE
    @Path("/{idEjercicio}")
    public Response deleteAll(@PathParam("idEjercicio") int idEjercicio) {
        EjercicioMusculoDao dao = new EjercicioMusculoDao();
        boolean eliminado = dao.deleteAll(idEjercicio);
        return Boolean.FALSE.equals(eliminado)
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.status(Response.Status.OK).build();
    }
    
    @DELETE
    @Path("/{idEjercicio}/{idMusculo}")
    public Response delete(@PathParam("idEjercicio") int idEjercicio, @PathParam("idMusculo") int idMusculo) {
        EjercicioMusculoDao dao = new EjercicioMusculoDao();
        boolean eliminado = dao.delete(idEjercicio, idMusculo);
        return Boolean.FALSE.equals(eliminado)
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.status(Response.Status.OK).build();
    }
}