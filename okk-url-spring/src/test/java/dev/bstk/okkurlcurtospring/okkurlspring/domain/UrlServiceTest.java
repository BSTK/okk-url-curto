package dev.bstk.okkurlcurtospring.okkurlspring.domain;

import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import dev.bstk.okkurlcurtospring.okkurlspring.domain.data.UrlRepository;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.Cache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlServiceTest {

    @Autowired
    private Cache cache;

    @Autowired
    private UrlRepository repository;

    private UrlService urlService;

    @BeforeEach
    void setUp() {
        this.urlService = new UrlService(
            number -> "mock-base62",
            number -> "mock-qrcode",
            cache,
            repository
        );
    }

    @Test
    void deveRetonarUmaUrlComDadosConvertidos() {
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

    @AfterEach
    void teraDown() {
        cache.clear();
    }
}
