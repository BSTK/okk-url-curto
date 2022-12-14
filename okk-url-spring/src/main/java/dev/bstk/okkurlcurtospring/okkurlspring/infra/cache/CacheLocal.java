package dev.bstk.okkurlcurtospring.okkurlspring.infra.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheLocal implements GerenciadorCache {

    private static final Map<String, Object> CACHE_LOCAL = new ConcurrentHashMap<>();

    @Override
    public Object get(Object chave) {
        return CACHE_LOCAL.getOrDefault(String.valueOf(chave), null);
    }

    @Override
    public void put(Object chave, Object valor) {
        CACHE_LOCAL.put(String.valueOf(chave), valor);
    }

    @Override
    public void clear() {
        if (!CACHE_LOCAL.isEmpty()) {
            CACHE_LOCAL.clear();
        }
    }
}
