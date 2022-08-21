package dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@SpringBootTest
@TestPropertySource(
    locations = "/application.properties",
    properties = {
        "okk-qrcode-largura=500",
        "okk-qrcode-altura=500"
    }
)
class EncoderQrCodeTest {

    private static final String IMAGE_PNG_BASE64 = "data:image/png;base64,";

    @Autowired
    @Qualifier("EncoderQrCode")
    private Encoder<String> urlQrCode;

    @Value("${okk-qrcode-largura}")
    private int largura;

    @Value("${okk-qrcode-altura}")
    private int altura;


    @Test
    @DisplayName("Deve retornar um QRCode imagem base 64")
    void deveRetornarUmQRCodeImagemBase64() {
        final var encode = urlQrCode.encode("https://mock.com.br");

        Assertions.assertNotNull(encode);
        Assertions.assertTrue(encode.startsWith(IMAGE_PNG_BASE64));
        Assertions.assertTrue(encode.contains(IMAGE_PNG_BASE64));
    }

    @Test
    @DisplayName("Deve retornar um QRCode imagem com aas medidas de altura e largura de acordo com as properties")
    void deveRetonarUmQrCodeImagemComAsMedidadeDeAlturaElarguraDeAcordoComAsProperties() throws IOException {
        final var encode = urlQrCode.encode("https://mock.com.br");
        final var encodeRemovidoPrefixoBase64= encode.split(",")[1];
        final var imagemBase64Decodificada = Base64.getDecoder().decode(encodeRemovidoPrefixoBase64);

        final var qrCode = ImageIO.read(new ByteArrayInputStream(imagemBase64Decodificada));

        Assertions.assertNotNull(qrCode);
        Assertions.assertEquals(largura, qrCode.getWidth());
        Assertions.assertEquals(altura, qrCode.getHeight());
    }
}
