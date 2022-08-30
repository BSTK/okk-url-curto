package dev.bstk.okkurlcurtoquarkus.domain.encoder;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@QuarkusTest
class EncoderQrCodeTest {

    private static final String IMAGE_PNG_BASE64 = "data:image/png;base64,";

    @Inject
    protected QrCode qrCode;

    @ConfigProperty(name = "okk-qrcode-largura")
    protected int largura;

    @ConfigProperty(name = "okk-qrcode-altura")
    protected int altura;


    @Test
    @DisplayName("Deve retornar um QRCode imagem base 64")
    void deveRetornarUmQRCodeImagemBase64() {
        final var encode = qrCode.criarQrCode("https://mock.com.br");

        Assertions.assertNotNull(encode);
        Assertions.assertTrue(encode.startsWith(IMAGE_PNG_BASE64));
        Assertions.assertTrue(encode.contains(IMAGE_PNG_BASE64));
    }

    @Test
    @DisplayName("Deve retornar um QRCode imagem com aas medidas de altura e largura de acordo com as properties")
    void deveRetonarUmQrCodeImagemComAsMedidadeDeAlturaElarguraDeAcordoComAsProperties() throws IOException {
        final var encode = qrCode.criarQrCode("https://mock.com.br");
        final var encodeRemovidoPrefixoBase64= encode.split(",")[1];
        final var imagemBase64Decodificada = Base64.getDecoder().decode(encodeRemovidoPrefixoBase64);

        final var qrCode = ImageIO.read(new ByteArrayInputStream(imagemBase64Decodificada));

        Assertions.assertNotNull(qrCode);
        Assertions.assertEquals(largura, qrCode.getWidth());
        Assertions.assertEquals(altura, qrCode.getHeight());
    }
}
