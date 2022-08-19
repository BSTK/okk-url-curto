package dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder;

import dev.bstk.okkurlcurtospring.okkurlspring.infra.hanlerexception.exception.EncodeBase62Exception;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("EncoderBase62")
public class EncoderBase62 implements Encoder<Long> {

    private static final int BASE = 62;
    private static final String CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Override
    public String encode(final Long valor) {
        if (Objects.isNull(valor)) {
            throw new EncodeBase62Exception("Id inválido!");
        }

        final var builder = new StringBuilder(0);
        long id = Long.parseLong(String.valueOf(valor));

        do {
            builder.insert(0, CARACTERES.charAt((int) (id % BASE)));
            id /= BASE;
        }
        while (id > 0);

        return builder.toString();
    }
}
