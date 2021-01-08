package org.acme;

import static io.restassured.RestAssured.with;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class AnimalResourceTest {

    @Test
    void invalidTypeIdException() {
        with().body("{\"name\":\"mouse\", \"likesCream\":true}").contentType(APPLICATION_JSON)
            .post("/animals")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());
    }

}
