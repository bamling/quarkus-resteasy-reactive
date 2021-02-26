package org.acme;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import io.smallrye.mutiny.Uni;
import java.net.URI;
import java.util.UUID;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;

@Path("/dummy")
public class DummyResource {

    private static final Logger LOG = Logger.getLogger(DummyService.class);

    @Inject
    DummyService dummyService;

    @POST
    @Produces(APPLICATION_JSON)
    public Uni<Response> post(@Context final UriInfo uriInfo) {
        LOG.info("in DummyResource#post");
        return dummyService.createAndProcessJob()
            .map(item -> {
                final URI location = uriInfo.getAbsolutePathBuilder().path(item.getId().toString()).build();
                return Response.created(location).entity(item).build();
            });
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Uni<DummyJob> get(@PathParam("id") final UUID id) {
        LOG.info("in DummyResource#get");
        return dummyService.getJob(id);
    }

}
