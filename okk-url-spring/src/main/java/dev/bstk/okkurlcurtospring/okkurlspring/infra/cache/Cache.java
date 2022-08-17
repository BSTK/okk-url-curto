package dev.bstk.okkurlcurtospring.okkurlspring.infra.cache;

public interface Cache {

    Object get(final Object chave);

    void put(final Object chave, final Object valor);

    void clear();
}
