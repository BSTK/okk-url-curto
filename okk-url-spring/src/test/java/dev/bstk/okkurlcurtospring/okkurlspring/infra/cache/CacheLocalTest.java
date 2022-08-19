package dev.bstk.okkurlcurtospring.okkurlspring.infra.cache;

import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CacheLocalTest {

    private Cache cache;

    @BeforeEach
    void setUp() {
        this.cache = new CacheLocal();
    }

    @Test
    void deveRetonarUmObjetoDoCacheDadoUmaChaveValida() {
        final var request = new UrlRequest();
        request.setUrl("https://mock.com/asL");
        cache.put("CHAVE_1", request);

        final var requestCache = (UrlRequest) cache.get("CHAVE_1");

        Assertions.assertNotNull(requestCache);
        Assertions.assertInstanceOf(UrlRequest.class, requestCache);
        Assertions.assertEquals("https://mock.com/asL", requestCache.getUrl());
    }

    @Test
    void deveRetonarUmNullDoCacheDadoUmaChaveInvalida() {
        final var request = new UrlRequest();
        request.setUrl("https://mock.com/asL");
        cache.put("CHAVE_1", request);

        final var requestCache = (UrlRequest) cache.get("chave_nao_existe");

        Assertions.assertNull(requestCache);
    }

    @Test
    void deveRemoverTodosDadosNoCache() {
        final var request = new UrlRequest();
        request.setUrl("https://mock.com/asL");
        cache.put("CHAVE_1", request);
        cache.put("CHAVE_2", request);
        cache.put("CHAVE_3", request);

        cache.clear();

        final var requestCache1 = (UrlRequest) cache.get("CHAVE_1");
        final var requestCache2 = (UrlRequest) cache.get("CHAVE_2");
        final var requestCache3 = (UrlRequest) cache.get("CHAVE_3");

        Assertions.assertNull(requestCache1);
        Assertions.assertNull(requestCache2);
        Assertions.assertNull(requestCache3);
    }

    @AfterEach
    void tearDown() {
        cache.clear();
    }
}
