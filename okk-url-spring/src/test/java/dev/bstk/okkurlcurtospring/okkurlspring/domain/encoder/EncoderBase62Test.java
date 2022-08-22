package dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder;

import dev.bstk.okkurlcurtospring.okkurlspring.infra.hanlerexception.exception.EncodeBase62Exception;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EncoderBase62Test {

    @Test
    @DisplayName("Deve encodar um id varias vezes da mesma forma")
    void deveEncodarUmIdVariasVezesDaMesmaForma() {
        final var encodeA = Base62.encode(1L);
        final var encodeB = Base62.encode(1L);
        final var encodeC = Base62.encode(1L);

        Assertions.assertAll(() -> {
            Assertions.assertNotNull(encodeA);
            Assertions.assertNotNull(encodeB);
            Assertions.assertNotNull(encodeC);
        });

        Assertions.assertAll(() -> {
            Assertions.assertEquals(encodeA, encodeB);
            Assertions.assertEquals(encodeA, encodeC);
        });

        Assertions.assertAll(() -> {
            Assertions.assertEquals(encodeB, encodeA);
            Assertions.assertEquals(encodeB, encodeC);
        });

        Assertions.assertAll(() -> {
            Assertions.assertEquals(encodeC, encodeB);
            Assertions.assertEquals(encodeC, encodeA);
        });
    }

    @Test
    @DisplayName("Deve encodar um id diferente de outro id")
    void deveEncodarUmIdDiferenteDeOutroId() {
        final var encodeA = Base62.encode(1L);
        final var encodeB = Base62.encode(1829L);

        Assertions.assertAll(() -> {
            Assertions.assertNotNull(encodeA);
            Assertions.assertNotNull(encodeB);
        });

        Assertions.assertAll(() -> {
            Assertions.assertNotEquals(encodeA, encodeB);
            Assertions.assertNotEquals(encodeB, encodeA);
        });
    }

    @Test
    @DisplayName("Deve encodar um id zero")
    void deveEncodarUmIdZero() {
        final var encode = Base62.encode(0L);

        Assertions.assertNotNull(encode);
        Assertions.assertEquals("0", encode);
    }

    @Test
    @DisplayName("Deve lancar excecao ao tentar encodar um id nulo")
    void deveLancarExcecaoAoTentarEncodarUmIdNulo() {
        final var exception = Assertions
            .assertThrowsExactly(
                EncodeBase62Exception.class,
                () -> Base62.encode(null)
            );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Id inválido!", exception.getMessage());
    }
}
