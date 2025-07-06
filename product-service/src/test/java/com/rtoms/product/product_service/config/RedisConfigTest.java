package com.rtoms.product.product_service.config;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * @author Manoj Krushna Mohanta
 * 22-06-2025 23:02
 */

class RedisConfigTest {

    @Test
    void testRedisTemplateBean() {
        RedisConfig config = new RedisConfig();
        RedisConnectionFactory factory = mock(RedisConnectionFactory.class);

        RedisTemplate<String, Object> template = config.redisTemplate(factory);

        assertNotNull(template);
        assertTrue(template.getKeySerializer() instanceof StringRedisSerializer);
        assertTrue(template.getValueSerializer() instanceof GenericJackson2JsonRedisSerializer);
        assertEquals(factory, template.getConnectionFactory());
    }
}