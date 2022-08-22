package dev.bstk.okkurlcurtospring.okkurlspring.domain;

import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.data.Url;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.data.UrlRepository;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder.QrCode;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.GerenciadorCache;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.handlerexception.exception.UrlTokenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private QrCode qrCode;

    @Mock
    private GerenciadorCache cache;

    @Mock
    private UrlRepository repository;


    @Test
    @DisplayName("Deve retornar uma url com dados convertidos")
    void deveRetonarUmaUrlComDadosConvertidos() {
        final var urlMock = new Url();
        urlMock.setId(1L);
        when(repository.save(any(Url.class))).thenReturn(urlMock);

        when(qrCode.criarQrCode(anyString())).thenReturn("QRCODE_MOCK");

        when(cache.get(anyInt())).thenReturn(null);
        doNothing().when(cache).put(anyString(), any(Url.class));
        doNothing().when(cache).put(anyInt(), any(Url.class));

        final var urlEncurtada = urlService.encurtar(mockRequest());

        assertions(urlEncurtada);
    }

    @Test
    @DisplayName("Deve retornar uma url com dados convertidos obtidos do cache")
    void deveRetonarUmaUrlComDadosConvertidosObtidosDoCache() {
        when(cache.get(anyInt())).thenReturn(mockUrlCache());

        final var urlEncurtada = urlService.encurtar(mockRequest());

        verifyNoInteractions(repository, qrCode);
        verify(cache, times(0)).put(anyString(), any(Url.class));
        verify(cache, times(0)).put(anyInt(), any(Url.class));

        assertions(urlEncurtada);
    }

    @Test
    @DisplayName("Deve retornar url original dado um token valido")
    void deveRetornarUrlOriginalDadoUmTokenValido() {
        when(cache.get(anyString())).thenReturn(null);
        when(repository.urlOriginal(anyString())).thenReturn(Optional.of("https://mock-url/aaa-aaaaaaa-aaa"));

        URI urlOriginal = urlService.redirecionar("token");

        Assertions.assertNotNull(urlOriginal);
    }

    @Test
    @DisplayName("Deve retornar url original dado um token valido obtidos do cache")
    void deveRetornarUrlOriginalDadoUmTokenValidoObtidosDoCache() {
        when(cache.get(anyString())).thenReturn(mockUrlCache());

        URI urlOriginal = urlService.redirecionar("token");

        verify(repository, times(0)).urlOriginal(anyString());

        Assertions.assertNotNull(urlOriginal);
    }

    @Test
    @DisplayName("Deve lancar excecao ao tentar redirecionar para uma Url na qual não existe token vinculado")
    void deveLancarExcecaoAoTentarRedirecionarParaUmaUrlNaQualExisteTokenVinculado() {
        final var exception = Assertions
            .assertThrowsExactly(
                UrlTokenException.class,
                () -> urlService.redirecionar("token")
            );

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Não existe url curta para token: [ token ]", exception.getMessage());
    }

    private void assertions(final Url urlEncurtada) {
        Assertions.assertNotNull(urlEncurtada);
        Assertions.assertNotNull(urlEncurtada.getId());
        Assertions.assertNotNull(urlEncurtada.getToken());
        Assertions.assertNotNull(urlEncurtada.getUrlOriginal());
        Assertions.assertNotNull(urlEncurtada.getUrlEncurtada());
        Assertions.assertNotNull(urlEncurtada.getUrlOriginalQRCode());
    }

    private Url mockUrlCache() {
        final var url = new Url();
        url.setId(1L);
        url.setToken("token");
        url.setUrlOriginalQRCode("QR_CODE");
        url.setUrlEncurtada("https://mock-url/token");
        url.setUrlOriginal("https://mock-url.com/aaa-aaaaaaa-aaa");

        return url;
    }

    private UrlRequest mockRequest() {
        final var request = new UrlRequest();
        request.setUrl("https://mock-url/ks");

        return request;
    }
}
