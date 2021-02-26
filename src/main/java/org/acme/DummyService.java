package org.acme;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.acme.DummyJob.Status;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class DummyService {

    private static final Map<UUID, DummyJob> JOBS = new HashMap<>();
    private static final Logger LOG = Logger.getLogger(DummyService.class);

    @Inject
    EventBus eventBus;

    @RestClient
    DummyClient dummyClient;

    public Uni<DummyJob> createAndProcessJob() {
        LOG.info("in DummyService#createAndProcessJob");
        return Uni.createFrom().item(new DummyJob())
            .onItem().invoke(item -> JOBS.put(item.getId(), item))
            .onItem().invoke(item -> eventBus.sendAndForget("process-job", item.getId()));
    }

    public Uni<DummyJob> getJob(final UUID jobId) {
        LOG.info("in DummyService#getJob");
        return Uni.createFrom().item(JOBS.get(jobId));
    }

    @ConsumeEvent("process-job")
    public Uni<DummyJob> processJob(final UUID jobId) {
        LOG.info("in DummyService#processJob");
        final DummyJob job = JOBS.get(jobId);
        job.setStatus(Status.PENDING);

        return Multi.createBy().repeating()
            .uni(() -> {
                LOG.info("exec dummyClient.get()");
                return dummyClient.get();
            })
            .atMost(10)
            .collect().asList()
            .map(ignored -> job)
            .onItem().invoke(item -> item.setStatus(Status.DONE));
    }

}
