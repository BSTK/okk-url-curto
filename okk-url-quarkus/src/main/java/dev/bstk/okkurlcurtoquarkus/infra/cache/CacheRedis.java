package dev.bstk.okkurlcurtoquarkus.infra.cache;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.string.StringCommands;

public class CacheRedis implements GerenciadorCache {

    private final StringCommands<String, Object> cache;
    private final ReactiveKeyCommands<String> cacheKeys;

    public CacheRedis(final RedisDataSource dataSource,
                      final ReactiveRedisDataSource reactiveDataSource) {
        this.cache = dataSource.string(Object.class);
        this.cacheKeys = reactiveDataSource.key();
    }

    @Override
    public Object get(final Object chave) {
        return cache.get(String.valueOf(chave));
    }

    @Override
    public void put(final Object chave, final Object valor) {
        cache.set(String.valueOf(chave), valor);
    }

    @Override
    public void clear() {
        cacheKeys
            .keys("*")
            .subscribe()
            .with(keys -> cacheKeys
                .del(keys.toArray(keys.toArray(new String[0])))
                .replaceWithVoid());
    }
}
