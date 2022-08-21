package dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder;

import dev.bstk.okkurlcurtospring.okkurlspring.infra.hanlerexception.exception.EncodeBase62Exception;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EncoderBase62Test {

    private Encoder<Long> encoder;

    @BeforeEach
    void setUp() {
        this.encoder = new EncoderBase62();
    }

    @Test
    @DisplayName("Deve encodar um id varias vezes da mesma forma")
    void deveEncodarUmIdVariasVezesDaMesmaForma() {
        final var encodeA = encoder.encode(1L);
        final var encodeB = encoder.encode(1L);
        final var encodeC = encoder.encode(1L);

        Assertions.assertNotNull(encodeA);
        Assertions.assertNotNull(encodeB);
        Assertions.assertNotNull(encodeC);

        Assertions.assertEquals(encodeA, encodeB);
        Assertions.assertEquals(encodeA, encodeC);

        Assertions.assertEquals(encodeB, encodeA);
        Assertions.assertEquals(encodeB, encodeC);

        Assertions.assertEquals(encodeC, encodeB);
        Assertions.assertEquals(encodeC, encodeA);
    }

    @Test
    @DisplayName("Deve encodar um id diferente de outro id")
    void deveEncodarUmIdDiferenteDeOutroId() {
        final var encodeA = encoder.encode(1L);
        final var encodeB = encoder.encode(1829L);

        Assertions.assertNotNull(encodeA);
        Assertions.assertNotNull(encodeB);

        Assertions.assertNotEquals(encodeA, encodeB);
        Assertions.assertNotEquals(encodeB, encodeA);
    }

    @Test
    @DisplayName("Deve encodar um id zero")
    void deveEncodarUmIdZero() {
        final var encode = encoder.encode(0L);

        Assertions.assertNotNull(encode);
        Assertions.assertEquals("0", encode);
    }

    @Test
    @DisplayName("Deve lancar excecao ao tentar encodar um id nulo")
    void deveLancarExcecaoAoTentarEncodarUmIdNulo() {
        final var exception = Assertions
            .assertThrowsExactly(
                EncodeBase62Exception.class,
                () -> encoder.encode(null)
            );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Id inválido!", exception.getMessage());
    }
}
