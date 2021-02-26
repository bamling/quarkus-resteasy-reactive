package org.acme;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@Path("/slowEndpoint")
@RegisterRestClient(configKey = "dummy")
public interface DummyClient {

    @GET
    @Produces(TEXT_PLAIN)
    Uni<String> get();

}
