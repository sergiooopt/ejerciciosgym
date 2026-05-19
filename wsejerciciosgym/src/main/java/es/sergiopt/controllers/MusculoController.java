package es.sergiopt.controllers;

import java.util.List;

import es.sergiopt.map.Musculo;
import es.sergiopt.persistence.MusculoDao;
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

@Path("/musculos")
public class MusculoController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        MusculoDao dao = new MusculoDao();
        List<Musculo> musculos = dao.get();
        return Response.ok(musculos).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)    
    public Response get(@PathParam("id") int id) {
        MusculoDao dao = new MusculoDao();
        Musculo musculo = dao.read(id);
        return musculo == null
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.ok(musculo).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Musculo musculo) {
        MusculoDao dao = new MusculoDao();
        boolean añadido = dao.create(musculo);
        return Boolean.FALSE.equals(añadido)
            ? Response.status(Response.Status.BAD_REQUEST).build()
            : Response.status(Response.Status.OK).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Musculo musculo) {
        MusculoDao dao = new MusculoDao();
        boolean modificado = dao.update(id, musculo);
        return Boolean.FALSE.equals(modificado)
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        MusculoDao dao = new MusculoDao();
        boolean eliminado = dao.delete(id);
        return Boolean.FALSE.equals(eliminado)
            ? Response.status(Response.Status.NOT_FOUND).build()
            : Response.status(Response.Status.OK).build();
    }
}