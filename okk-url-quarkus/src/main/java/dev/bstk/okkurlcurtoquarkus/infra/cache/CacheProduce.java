package dev.bstk.okkurlcurtoquarkus.infra.cache;

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
        final var ambiente = ProfileManager.getActiveProfile();
        final var ambienteRemoto = isDev(ambiente) || isHom(ambiente) || isProd(ambiente);
        return ambienteRemoto ? new CacheRedis() : new CacheLocal();
    }

    private boolean isDev(final String ambiente) {
        return AMBIENTE_DEV.equalsIgnoreCase(ambiente);
    }

    private boolean isHom(final String ambiente) {
        return AMBIENTE_HOM.equalsIgnoreCase(ambiente);
    }

    private boolean isProd(final String ambiente) {
        return AMBIENTE_PROD.equalsIgnoreCase(ambiente);
    }
}
