package dev.bstk.okkurlcurtoquarkus.infra.cache;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class CacheRedis implements GerenciadorCache {

    @Override
    public Object get(final Object chave) {
        // Cache.ValueWrapper wrapper = cache().get(chave);
        // return Objects.nonNull(wrapper) ? wrapper.get() : null;

        return null;
    }

    @Override
    public void put(final Object chave, final Object valor) {
        // cache().put(chave, valor);
    }

    @Override
    public void clear() {
        // cache().clear();
    }
}
