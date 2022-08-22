package dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.configuracao;

import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.CacheLocal;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.CacheRedis;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.GerenciadorCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfiguracaoCacheTest {

    @InjectMocks
    private ConfiguracaoCache configuracaoCache;

    @Mock
    private Environment ambiente;

    @Mock
    private ApplicationContext context;

    @Test
    void deveRetonarUmaInstanciaaDeCacheLocal() {
        when(ambiente.getActiveProfiles()).thenReturn(new String[] {});

        final var cache = configuracaoCache.cache();

        verify(context, times(0)).getBean(CacheManager.class);

        Assertions.assertNotNull(cache);
        Assertions.assertInstanceOf(CacheLocal.class, cache);
    }

    @Test
    void deveRetonarUmaInstanciaaDeCacheRedis() {
        when(ambiente.getActiveProfiles()).thenReturn(new String[] {"dev"});

        final var cache = configuracaoCache.cache();

        verify(context).getBean(CacheManager.class);

        Assertions.assertNotNull(cache);
        Assertions.assertInstanceOf(CacheRedis.class, cache);
    }
}
