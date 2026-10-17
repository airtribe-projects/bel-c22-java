package org.airtribe.LearnerManagementSystemBelC22.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;


@Configuration
public class RedisConfig {

//  @Bean
//  public RedisCacheConfiguration cacheConfiguration() {
//    return RedisCacheConfiguration.defaultCacheConfig()
//        .entryTtl(java.time.Duration.ofMinutes(10))
//        .disableCachingNullValues()
//        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
//            GenericJacksonJsonRedisSerializer.builder().build()
//        ));
//  }
}
