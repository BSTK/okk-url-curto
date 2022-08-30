package dev.bstk.okkurlcurtoquarkus.api;

import dev.bstk.okkurlcurtoquarkus.api.request.UrlRequest;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.OK;

@QuarkusTest
class UrlResourceTest {

    private static final String ENDPOINT_ENCURTAR_URL = "/url";
    private static final String URL_ORIGINAL = "https://www.google.com.br/hahhskka/skksjja-oosj";

    @Test
    @DisplayName("Deve retonar uma url encurtada")
    void deveRetonarUmaUrlEncurtada() {
        final var request = mockRequest();

        given()
            .body(request)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .post(ENDPOINT_ENCURTAR_URL)
            .then()
            .statusCode(OK.getStatusCode())
            .body("url_original", Is.is(URL_ORIGINAL))
            .body("url_encurtada", IsNull.notNullValue())
            .body("url_qr_code", IsNull.notNullValue());
    }

    /// TODO: Teste do endpoint /{url_token} (Método: redirecionar)
    /// TODO: - Por algum motivo não está chamando a url corretamente,
    /// TODO:  gerando um HttpStatus 404 onde era esperado HttpStatus 301

    private UrlRequest mockRequest() {
        final UrlRequest request = new UrlRequest();
        request.setUrl(URL_ORIGINAL);

        return request;
    }
}
