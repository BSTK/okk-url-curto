package dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.configuracao;

import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.CacheLocal;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.CacheRedis;
import dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.GerenciadorCache;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class ConfiguracaoCache {

    private final Environment ambiente;
    private final ApplicationContext context;

    @Bean
    public GerenciadorCache cache() {
        if (ambiente.getActiveProfiles().length == 0) {
            return new CacheLocal();
        }

        final var cacheManager = context.getBean(CacheManager.class);
        return new CacheRedis(cacheManager);
    }
}
