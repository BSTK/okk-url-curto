package dev.bstk.okkurlcurtoquarkus.infra.handlerexception;

import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception.CacheException;
import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception.EncodeBase62Exception;
import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception.EncodeQrCodeException;
import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception.UrlTokenException;
import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.response.ErroResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.Map;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

class GlobalExceptionHandlerTest {

    @Test
    @DisplayName("Deve retornar erro de validação [ EncodeBase62Exception ]")
    void deveRetornarErroValidacaoEncodeBase62Exception() {
        final var handler = new GlobalExceptionHandler.ErrorMapperEncodeBase62();
        final var response = handler.toResponse(new EncodeBase62Exception("ERRO_EncodeBase62Exception"));

        assertInternalServerError(response);
        assertResponseError(response, "ERRO_EncodeBase62Exception");
    }

    @Test
    @DisplayName("Deve retornar erro de validação [ EncodeQrCodeException ]")
    void deveRetornarErroValidacaoEncodeQrCodeException() {
        final var handler = new GlobalExceptionHandler.ErrorMapperEncodeQrCode();
        final var response = handler.toResponse(new EncodeQrCodeException("ERRO_EncodeQrCodeException"));

        assertInternalServerError(response);
        assertResponseError(response, "ERRO_EncodeQrCodeException");
    }

    @Test
    @DisplayName("Deve retornar erro de validação [ CacheException ]")
    void deveRetornarErroValidacaoCacheException() {
        final var handler = new GlobalExceptionHandler.ErrorMapperCache();
        final var response = handler.toResponse(new CacheException("ERRO_CacheException"));

        assertInternalServerError(response);
        assertResponseError(response, "ERRO_CacheException");
    }

    @Test
    @DisplayName("Deve retornar erro de validação [ UrlTokenException ]")
    void deveRetornarErroValidacaoUrlTokenException() {
        final var handler = new GlobalExceptionHandler.ErrorMapperUrlToken();
        final var response = handler.toResponse(new UrlTokenException("ERRO_UrlTokenException"));

        assertBadRequest(response);
        assertResponseError(response, "ERRO_UrlTokenException");
    }

    private void assertBadRequest(final Response response) {
        Assertions.assertNotNull(response);
        Assertions.assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    private void assertInternalServerError(final Response response) {
        Assertions.assertNotNull(response);
        Assertions.assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    private void assertResponseError(final Response response, final String mensagemException) {
        final var responseErro = (Map<String, ErroResponse>) response.getEntity();
        final var responseErroEsperado = new ErroResponse(mensagemException);

        Assertions.assertNotNull(responseErro.get("error").getDataHora());
        Assertions.assertEquals(responseErroEsperado.getMensagem(), responseErro.get("error").getMensagem());
    }
}
