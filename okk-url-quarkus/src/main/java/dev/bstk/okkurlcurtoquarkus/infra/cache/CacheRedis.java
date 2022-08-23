package dev.bstk.okkurlcurtoquarkus.infra.cache;

public class CacheRedis implements GerenciadorCache {

    @Override
    public Object get(final Object chave) {
        return null;
    }

    @Override
    public void put(final Object chave, final Object valor) { }

    @Override
    public void clear() { }
}
