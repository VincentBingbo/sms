package com.vincent.core.config;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class ShiroCache<K, V> implements Cache<K, V> {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public V get(K k) throws CacheException {
        return (V) redisTemplate.opsForValue().get(k);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        V old = get(k);
        redisTemplate.opsForValue().set(k, v);
        return old;
    }

    @Override
    public V remove(K k) throws CacheException {
        V old = get(k);
        redisTemplate.delete(k);
        return old;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys("*");
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> list = new ArrayList<>();
        keys.forEach(k -> {
            list.add(get(k));
        });
        return list;
    }
}
