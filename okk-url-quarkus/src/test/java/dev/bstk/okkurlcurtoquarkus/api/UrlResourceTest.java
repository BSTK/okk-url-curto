package dev.bstk.okkurlcurtoquarkus.api;

import dev.bstk.okkurlcurtoquarkus.api.request.UrlRequest;
import dev.bstk.okkurlcurtoquarkus.api.response.UrlResponse;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.MOVED_PERMANENTLY;
import static javax.ws.rs.core.Response.Status.OK;

@QuarkusTest
class UrlResourceTest {

    private static final String ENDPOINT_ENCURTAR_URL = "/url";
    private static final String ENDPOINT_REDIRECIONAR_URL = "/{url_token}";
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

    @Test
    @Disabled(
         "TODO - Por algum motivo não está chamando a url corretamente, "
       + "gerando um HttpStatus 404 onde era esperado HttpStatus 301")
    @DisplayName("Deve redirecionar para url original dado um token válido")
    void deveRedirecionarParaUrlOriginaldadoUmTokenValido() {
        final var request = mockRequest();
        final var response = given()
            .body(request)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .post(ENDPOINT_ENCURTAR_URL)
            .then()
            .statusCode(OK.getStatusCode())
            .extract()
            .body()
            .as(UrlResponse.class);

        final var indiceUltimaBarra = response.getUrlEncurtada().lastIndexOf("/");
        final var urlToken = response.getUrlEncurtada().substring((indiceUltimaBarra + 1));

        given()
            .when()
            .pathParam("url_token", urlToken)
            .get(ENDPOINT_REDIRECIONAR_URL)
            .then()
            .statusCode(MOVED_PERMANENTLY.getStatusCode())
            .header(HttpHeaders.LOCATION, Is.is(URL_ORIGINAL));
    }

    private UrlRequest mockRequest() {
        final UrlRequest request = new UrlRequest();
        request.setUrl(URL_ORIGINAL);

        return request;
    }
}
