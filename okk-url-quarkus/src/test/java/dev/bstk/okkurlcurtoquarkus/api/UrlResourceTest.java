package dev.bstk.okkurlcurtoquarkus.api;

import dev.bstk.okkurlcurtoquarkus.api.request.UrlRequest;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.*;

@QuarkusTest
class UrlResourceTest {

    private static final String ENDPOINT_ENCURTAR_URL = "/url";
    private static final String ENDPOINT_REDIRECIONAR_URL = "/{url_id}";

    @Test
    @DisplayName("Deve retonar uma url encurtada")
    void deveRetonarUmaUrlEncurtada() {
        final var urlOriginal = "https://www.google.com.br/hahhskka/skksjja-oosj";
        final var request = mockRequest(urlOriginal);

        given()
            .body(request)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .post(ENDPOINT_ENCURTAR_URL)
            .then()
            .statusCode(OK.getStatusCode())
            .body("url_original", Is.is(urlOriginal))
            .body("url_encurtada", IsNull.notNullValue())
            .body("url_qr_code", IsNull.notNullValue());
    }

    private UrlRequest mockRequest(final String url) {
        final UrlRequest request = new UrlRequest();
        request.setUrl(url);

        return request;
    }
}
