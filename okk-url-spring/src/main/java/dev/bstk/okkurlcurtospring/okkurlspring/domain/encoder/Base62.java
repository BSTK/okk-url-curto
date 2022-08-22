package dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder;

import dev.bstk.okkurlcurtospring.okkurlspring.infra.hanlerexception.exception.EncodeBase62Exception;

import java.util.Objects;

public final class Base62 {

    private static final int BASE = 62;
    private static final String CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private Base62() { }

    public static String encode(final Long valor) {
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
