package org.acme;

import java.util.UUID;

public class DummyJob {

    public enum Status {
        NEW,
        PENDING,
        DONE
    }

    private UUID id = UUID.randomUUID();
    private Status status = Status.NEW;



    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }
}
