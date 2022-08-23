package dev.bstk.okkurlcurtoquarkus.infra.cache;

public interface GerenciadorCache {

    Object get(final Object chave);

    void put(final Object chave, final Object valor);

    void clear();
}
