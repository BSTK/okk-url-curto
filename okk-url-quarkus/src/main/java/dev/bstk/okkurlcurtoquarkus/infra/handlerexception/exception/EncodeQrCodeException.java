package dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception;

public class EncodeQrCodeException extends RuntimeException {

    public EncodeQrCodeException(String message) {
        super(message);
    }
}

