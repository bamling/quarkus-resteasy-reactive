package org.acme;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Path("/animals")
public class AnimalResource {

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Animal post(final Animal animal) {
        System.out.println(animal.getName());
        System.out.println(animal.getClass().getName());

        return animal;
    }

    @ServerExceptionMapper({InvalidTypeIdException.class})
    public Response invalidTypeIdException(final InvalidTypeIdException e) {
        return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
    }

    @ServerExceptionMapper({Exception.class})
    public Response exception(final Exception e) {
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }

}
