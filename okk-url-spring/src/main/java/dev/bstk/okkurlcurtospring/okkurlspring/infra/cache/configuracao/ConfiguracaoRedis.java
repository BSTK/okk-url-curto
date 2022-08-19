package dev.bstk.okkurlcurtospring.okkurlspring.infra.cache.configuracao;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
public class ConfiguracaoRedis {

    private static final long TEMPO_EM_MINUTOS_CACHE_TTL = 60;


    @Bean
    public CacheManager redisCacheConfiguration(final LettuceConnectionFactory lettuceConnectionFactory) {
        final var redisCacheConfiguration = RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(TEMPO_EM_MINUTOS_CACHE_TTL))
            .disableCachingNullValues()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(String.class)))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager
            .RedisCacheManagerBuilder
            .fromConnectionFactory(lettuceConnectionFactory)
            .cacheDefaults(redisCacheConfiguration)
            .build();
    }
}
