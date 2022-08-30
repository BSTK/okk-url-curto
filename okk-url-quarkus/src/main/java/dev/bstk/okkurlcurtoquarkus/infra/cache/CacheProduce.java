package dev.bstk.okkurlcurtoquarkus.infra.cache;

import io.quarkus.arc.Arc;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.runtime.configuration.ProfileManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class CacheProduce {

    private static final String AMBIENTE_DEV = "dev";

    @Produces
    @ApplicationScoped
    public GerenciadorCache cache() {
        return executandoAmbienteDevLocal()
            ? new CacheLocal()
            : new CacheRedis(
                Arc.container().instance(RedisDataSource.class).get(),
                Arc.container().instance(ReactiveRedisDataSource.class).get()
            );
    }

    private boolean executandoAmbienteDevLocal() {
        final var ambiente = ProfileManager.getActiveProfile();
        return AMBIENTE_DEV.equalsIgnoreCase(ambiente);
    }
}
