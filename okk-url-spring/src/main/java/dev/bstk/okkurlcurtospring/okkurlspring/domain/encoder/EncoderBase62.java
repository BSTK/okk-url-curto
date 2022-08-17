package dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder;

import org.springframework.stereotype.Component;

@Component("EncoderBase62")
public class EncoderBase62 implements Encoder {

    private static final int BASE = 62;
    private static final String CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Override
    public String encode(final Object valor) {
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
