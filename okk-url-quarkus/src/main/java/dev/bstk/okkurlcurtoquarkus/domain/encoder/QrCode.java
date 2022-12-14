package dev.bstk.okkurlcurtoquarkus.domain.encoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception.EncodeQrCodeException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@ApplicationScoped
public class QrCode {

    private static final String FORMATO_IMAGEM = "PNG";
    private static final String IMAGE_PNG_BASE64 = "data:image/png;base64,%s";

    private static final int QR_CODE_IMAGE_CONFIG_ON_COLOR = 0xFF000002;
    private static final int QR_CODE_IMAGE_CONFIG_OFF_COLOR = 0xFFFFC041;

    private static final QRCodeWriter QR_CODE_WRITER = new QRCodeWriter();

    @ConfigProperty(name = "okk-qrcode-largura")
    protected int largura;

    @ConfigProperty(name = "okk-qrcode-altura")
    protected int altura;

    public String criarQrCode(final String url) {
        try {
            final var bitMatrix = QR_CODE_WRITER.encode(String.valueOf(url), BarcodeFormat.QR_CODE, largura, altura);

            final var byteArrayOutputStream = new ByteArrayOutputStream();
            final var matrixToImageConfig = new MatrixToImageConfig(QR_CODE_IMAGE_CONFIG_ON_COLOR, QR_CODE_IMAGE_CONFIG_OFF_COLOR);

            MatrixToImageWriter.writeToStream(bitMatrix, FORMATO_IMAGEM, byteArrayOutputStream, matrixToImageConfig);
            final var qrCodeBytes = byteArrayOutputStream.toByteArray();
            final var imagemBase64 = Base64.getEncoder().encodeToString(qrCodeBytes);

            return String.format(IMAGE_PNG_BASE64, imagemBase64);
        } catch (WriterException | IOException ex) {
            throw new EncodeQrCodeException("Erro ao gerar QRCode!");
        }
    }
}
