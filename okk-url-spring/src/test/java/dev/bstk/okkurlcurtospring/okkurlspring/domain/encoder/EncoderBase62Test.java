package dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EncoderBase62Test {

    private Encoder encoder;

    @BeforeEach
    void setUp() {
        this.encoder = new EncoderBase62();
    }

    @Test
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
    void deveEncodarUmIdDiferenteDeoutroId() {
        final var encodeA = encoder.encode(1L);
        final var encodeB = encoder.encode(1829L);

        Assertions.assertNotNull(encodeA);
        Assertions.assertNotNull(encodeB);

        Assertions.assertNotEquals(encodeA, encodeB);
        Assertions.assertNotEquals(encodeB, encodeA);
    }

    @Test
    void deveEncodarUmIdZero() {
        final var encode = encoder.encode(0L);

        Assertions.assertNotNull(encode);
        Assertions.assertEquals("0", encode);
    }

    @Test
    void deveLancarExcecaoAoTentarEncodarUmIdNulo() {
        final var exception = Assertions
            .assertThrowsExactly(
                IllegalArgumentException.class,
                () -> encoder.encode(null)
            );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Id inválido!", exception.getMessage());
    }
}
