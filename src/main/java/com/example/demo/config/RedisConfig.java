package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author cwh
 * @date 2021/1/4 10:48
 */
@Configuration
@EnableCaching  //开启缓存注解
public class RedisConfig extends CachingConfigurerSupport {



    /**
     * <p>Title: redisTemplate</p>
     * <p>Description: </p>
     * @param connectionFactory
     * @return
     */
    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        // 使用Fastjson2JsonRedisSerializer来序列化和反序列化
        FastJson2JsonRedisSerializer fastjsonSerializer = new FastJson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会抛出异常
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        fastjsonSerializer.setObjectMapper(mapper);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(fastjsonSerializer);
        // 使用StringRedisSerializer来序列化和反序列化hash的key值
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hash的value序列化方式采用Fastjson
        redisTemplate.setHashValueSerializer(fastjsonSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * 基于SpringBoot2 对 RedisCacheManager 的自定义配置
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置CacheManager的值序列化方式为json序列化
        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer);
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);

        //设置默认超过时期是1天
        defaultCacheConfig.entryTtl(Duration.ofDays(1));
        //初始化RedisCacheManager
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

    /*@SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(RedisCacheWriter.lockingRedisCacheWriter(),redisTemplate,true);
        // 多个缓存的名称,目前只定义了一个
        rcm.setCacheNames(Arrays.asList("thisredis"));
        //设置缓存过期时间(秒)
        rcm.setDefaultExpiration(600);
        return rcm;
    }*/

}
