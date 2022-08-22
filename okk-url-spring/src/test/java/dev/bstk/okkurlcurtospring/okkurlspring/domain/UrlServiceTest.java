package dev.bstk.okkurlcurtospring.okkurlspring.domain;

import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.data.Url;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.data.UrlRepository;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.encoder.QrCode;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.GerenciadorCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
    void deveRetonarUmaUrlComDadosConvertidos() {
        final var urlMock = new Url();
        urlMock.setId(1L);
        when(repository.save(any(Url.class))).thenReturn(urlMock);

        when(qrCode.criarQrCode(anyString())).thenReturn("QRCODE_MOCK");

        when(cache.get(anyInt())).thenReturn(null);
        doNothing().when(cache).put(anyString(), any(Url.class));
        doNothing().when(cache).put(anyInt(), any(Url.class));

        final var request = new UrlRequest();
        request.setUrl("https://mock-url/ks");

        final var urlEncurtada = urlService.encurtar(request);

        Assertions.assertNotNull(urlEncurtada);
        Assertions.assertNotNull(urlEncurtada.getId());
        Assertions.assertNotNull(urlEncurtada.getToken());
        Assertions.assertNotNull(urlEncurtada.getUrlOriginal());
        Assertions.assertNotNull(urlEncurtada.getUrlEncurtada());
        Assertions.assertNotNull(urlEncurtada.getUrlEncurtadaQRCode());
    }
}
