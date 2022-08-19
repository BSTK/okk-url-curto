package dev.bstk.okkurlcurtospring.okkurlspring.infra.cache;

import dev.bstk.okkurlcurtospring.okkurlspring.infra.hanlerexception.exception.CacheException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheRedis implements GerenciadorCache {

    private static final String CACHE_REDIS = "CACHE-REDIS";

    private final CacheManager cacheManager;

    @Override
    public Object get(final Object chave) {
        Cache.ValueWrapper wrapper = cache().get(chave);
        return Objects.nonNull(wrapper) ? wrapper.get() : null;
    }

    @Override
    public void put(final Object chave, final Object valor) {
        cache().put(chave, valor);
    }

    @Override
    public void clear() {
        cache().clear();
    }

    private Cache cache() {
        final Cache cache = cacheManager.getCache(CACHE_REDIS);
        if (Objects.isNull(cache)) {
            log.warn("Cache: [ {} ] não implementado!", CACHE_REDIS);
            throw new CacheException("Cache Redis não implementado!");
        }

        return cache;
    }
}
