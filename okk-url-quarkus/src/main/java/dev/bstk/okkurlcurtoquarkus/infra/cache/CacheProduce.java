package dev.bstk.okkurlcurtoquarkus.infra.cache;

import io.quarkus.arc.Arc;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.runtime.configuration.ProfileManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class CacheProduce {

    private static final String AMBIENTE_DEV = "dev";
    private static final String AMBIENTE_HOM = "hom";
    private static final String AMBIENTE_PROD = "prod";

    @Produces
    @ApplicationScoped
    public GerenciadorCache cache() {
        return executandoAmbienteLocal()
            ? new CacheLocal()
            : new CacheRedis(
                Arc.container().instance(RedisDataSource.class).get(),
                Arc.container().instance(ReactiveRedisDataSource.class).get()
            );
    }

    private boolean executandoAmbienteLocal() {
        final var ambiente = ProfileManager.getActiveProfile();
        final var ambienteRemoto = AMBIENTE_DEV.equalsIgnoreCase(ambiente)
            || AMBIENTE_HOM.equalsIgnoreCase(ambiente)
            || AMBIENTE_PROD.equalsIgnoreCase(ambiente);

        return !ambienteRemoto;
    }
}
