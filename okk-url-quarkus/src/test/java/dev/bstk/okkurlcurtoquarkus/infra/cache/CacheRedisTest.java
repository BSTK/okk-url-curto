package dev.bstk.okkurlcurtoquarkus.infra.cache;

import dev.bstk.okkurlcurtoquarkus.api.request.UrlRequest;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.string.StringCommands;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheRedisTest {

    @Mock
    private RedisDataSource dataSource;

    @Mock
    private ReactiveRedisDataSource dataSourceReactive;

    @Mock
    private ReactiveKeyCommands<String> cacheKeys;

    @Mock
    private StringCommands<String, Object> stringCommands;

    private GerenciadorCache cache;

    @BeforeEach
    public void setUp() {
        when(dataSourceReactive.key()).thenReturn(cacheKeys);
        when(dataSource.string(Mockito.any())).thenReturn(stringCommands);

        this.cache = new CacheRedis(dataSource, dataSourceReactive);
    }

    @Test
    @DisplayName("Deve retonar um objeto do cache dado uma chave valida")
    void deveRetonarUmObjetoDoCacheDadoUmaChaveValida() {
        final var request = new UrlRequest();
        request.setUrl("https://mock.com/asL");

        when(stringCommands.get(anyString())).thenReturn(request);

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

        when(stringCommands.get(anyString())).thenReturn(null);

        final var requestCache = (UrlRequest) cache.get("chave_nao_existe");

        Assertions.assertNull(requestCache);
    }

    @Test
    @DisplayName("Deve remover todos os objetos do cache")
    void deveRemoverTodosDadosNoCache() {
        final var uniItemList = Uni
            .createFrom()
            .item(List.of("key_cache_1", "key_cache_2"));

        final var request = new UrlRequest();
        request.setUrl("https://mock.com/asL");

        when(stringCommands.get(anyString())).thenReturn(null);
        when(cacheKeys.keys(anyString())).thenReturn(uniItemList);

        cache.clear();

        final var requestCache1 = (UrlRequest) cache.get("CHAVE_1");
        final var requestCache2 = (UrlRequest) cache.get("CHAVE_2");
        final var requestCache3 = (UrlRequest) cache.get("CHAVE_3");

        Assertions.assertNull(requestCache1);
        Assertions.assertNull(requestCache2);
        Assertions.assertNull(requestCache3);
    }
}
