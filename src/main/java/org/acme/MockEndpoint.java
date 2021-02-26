package org.acme;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.mockserver.configuration.ConfigurationProperties;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Delay;

@ApplicationScoped
public class MockEndpoint {

    private ClientAndServer clientAndServer;

    void onStart(@Observes StartupEvent ev) {
        ConfigurationProperties.logLevel("OFF");
        clientAndServer = startClientAndServer(1080);

        clientAndServer.when(request()
            .withMethod("GET")
            .withPath("/slowEndpoint"))
            .respond(response()
                .withDelay(new Delay(TimeUnit.SECONDS, 10))
                .withStatusCode(200)
                .withBody("Hello World!"));
    }

    void onStop(@Observes ShutdownEvent ev) {
        clientAndServer.stop();
    }

}
