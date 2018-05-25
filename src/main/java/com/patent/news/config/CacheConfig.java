package com.patent.news.config;

import com.google.common.cache.CacheBuilder;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
public class CacheConfig {
    private static final int DEFAULT_CACHE_TTL_IN_SECOND = 60 * 60;

    @Bean
    @Primary
    public CacheManager cacheManager() {
        GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
        CacheBuilder cacheBuilder = CacheBuilder.newBuilder().recordStats().expireAfterWrite(DEFAULT_CACHE_TTL_IN_SECOND, TimeUnit.SECONDS).maximumSize(2048);
        guavaCacheManager.setCacheBuilder(cacheBuilder);
        return guavaCacheManager;
    }
}