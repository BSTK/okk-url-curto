package dev.bstk.okkurlcurtoquarkus.infra.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.bstk.okkurlcurtoquarkus.infra.handlerexception.exception.CacheException;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.string.StringCommands;

import java.util.Objects;

public class CacheRedis implements GerenciadorCache {

    private static final String OBJETO_JSON_VALOR = "valor";
    private static final String OBJETO_JSON_CLASS = "class";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final StringCommands<String, Object> cache;
    private final ReactiveKeyCommands<String> cacheKeys;


    public CacheRedis(final RedisDataSource dataSource,
                      final ReactiveRedisDataSource reactiveDataSource) {
        this.cache = dataSource.string(Object.class);
        this.cacheKeys = reactiveDataSource.key();
    }

    @Override
    public Object get(final Object chave) {
        try {
            final var jsonCache = cache.get(String.valueOf(chave));
            if (Objects.isNull(jsonCache)) {
                return null;
            }

            final var json = MAPPER.readValue(jsonCache.toString(), ObjectNode.class);
            final var jsonValor = json.get(OBJETO_JSON_VALOR).asText();
            final var jsonClass = Class.forName(json.get(OBJETO_JSON_CLASS).asText());

            return MAPPER.readValue(jsonValor, jsonClass);
        } catch (Exception e) {
            throw new CacheException("[ Redis ] - Erro ao ler valor do cache!");
        }
    }

    @Override
    public void put(final Object chave, final Object valor) {
        try {
            final var json = MAPPER.createObjectNode();
            json.put(OBJETO_JSON_VALOR, MAPPER.writeValueAsString(valor));
            json.put(OBJETO_JSON_CLASS, valor.getClass().toString().replace(OBJETO_JSON_CLASS, "").trim());

            cache.set(String.valueOf(chave), MAPPER.writeValueAsString(json));
        } catch (Exception e) {
            throw new CacheException("[ Redis ] - Erro ao setar valor no cache!");
        }
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
