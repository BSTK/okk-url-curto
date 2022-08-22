package dev.bstk.okkurlcurtospring.okkurlspring.infra.cache;

import dev.bstk.okkurlcurtospring.okkurlspring.api.request.UrlRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheRedisTest {

    @Mock
    private Cache cacheSpringData;

    @Mock
    private CacheManager cacheManager;

    private GerenciadorCache cache;

    @BeforeEach
    void setUp() {
        this.cache = new CacheRedis(cacheManager);
        when(cacheManager.getCache(anyString())).thenReturn(cacheSpringData);
    }

    @Test
    @DisplayName("Deve retonar um objeto do cache dado uma chave valida")
    void deveRetonarUmObjetoDoCacheDadoUmaChaveValida() {
        final var request = new UrlRequest();
        request.setUrl("https://mock.com/asL");
        when(cacheSpringData.get(anyString())).thenReturn(new SimpleValueWrapper(request));

        final var requestCache = (UrlRequest) cache.get("CHAVE_1");

        Assertions.assertNotNull(requestCache);
        Assertions.assertInstanceOf(UrlRequest.class, requestCache);
        Assertions.assertEquals("https://mock.com/asL", requestCache.getUrl());
    }

    @Test
    @DisplayName("Deve retonar um null do cache dado uma chave inválida")
    void deveRetonarUmNullDoCacheDadoUmaChaveInvalida() {
        final var request = new UrlRequest();
        request.setUrl("https://mock.com/asL");
        cache.put("CHAVE_1", request);

        final var requestCache = (UrlRequest) cache.get("chave_nao_existe");

        Assertions.assertNull(requestCache);
    }

    @Test
    @DisplayName("Deve remover todos os objetos do cache")
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
