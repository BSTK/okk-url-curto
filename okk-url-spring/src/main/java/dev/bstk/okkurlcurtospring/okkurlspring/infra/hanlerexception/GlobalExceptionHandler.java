package dev.bstk.okkurlcurtospring.okkurlspring.infra.hanlerexception;

import dev.bstk.okkurlcurtospring.okkurlspring.infra.hanlerexception.exception.CacheException;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.hanlerexception.exception.EncodeBase62Exception;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.hanlerexception.exception.EncodeQrCodeException;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.hanlerexception.exception.UrlTokenException;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.hanlerexception.response.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ EncodeBase62Exception.class, EncodeQrCodeException.class, CacheException.class })
    public ResponseEntity<Map<Object, Object>> exceptionEncode(final Exception exception) {
        return execute(exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UrlTokenException.class)
    public ResponseEntity<Map<Object, Object>> exceptionUrlToken(final Exception exception) {
        return execute(exception);
    }

    private ResponseEntity<Map<Object, Object>> execute(final Exception exception) {
        return ResponseEntity.ok(Map.of("error", new ErroResponse(exception.getMessage())));
    }
}
