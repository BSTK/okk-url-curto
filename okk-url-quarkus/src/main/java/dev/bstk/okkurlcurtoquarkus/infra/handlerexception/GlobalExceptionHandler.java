package dev.bstk.okkurlcurtoquarkus.infra.handlerexception;

import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception.CacheException;
import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception.EncodeBase62Exception;
import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception.EncodeQrCodeException;
import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception.UrlTokenException;
import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.response.ErroResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

public final class GlobalExceptionHandler {

    private GlobalExceptionHandler() { }

    @Provider
    public static class ErrorMapperEncodeBase62 implements ExceptionMapper<EncodeBase62Exception> {
        @Override
        public Response toResponse(final EncodeBase62Exception exception) {
            return execute(Response.Status.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Provider
    public static class ErrorMapperEncodeQrCode implements ExceptionMapper<EncodeQrCodeException> {
        @Override
        public Response toResponse(final EncodeQrCodeException exception) {
            return execute(Response.Status.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Provider
    public static class ErrorMapperCache implements ExceptionMapper<CacheException> {
        @Override
        public Response toResponse(final CacheException exception) {
            return execute(Response.Status.INTERNAL_SERVER_ERROR, exception);
        }
    }

    @Provider
    public static class ErrorMapperUrlToken implements ExceptionMapper<UrlTokenException> {
        @Override
        public Response toResponse(final UrlTokenException exception) {
            return execute(Response.Status.BAD_REQUEST, exception);
        }
    }

    private static Response execute(final Response.Status status, final Exception exception) {
        return Response
            .status(status)
            .entity(Map.of("error", new ErroResponse(exception.getMessage())))
            .build();
    }
}
