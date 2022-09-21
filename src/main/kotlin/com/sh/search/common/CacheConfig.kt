package com.sh.search.common

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled


@EnableCaching
@Configuration
class CacheConfig {
    @Bean
    fun popularKeywordCacheManager(): CacheManager {
        return ConcurrentMapCacheManager(CacheName.POPULAR_KEYWORD)
    }

    @CacheEvict(allEntries = true, value = [CacheName.POPULAR_KEYWORD])
    @Scheduled(fixedDelay = 5 * 1000, initialDelay = 1000)
    fun popularKeywordCacheEvict() {
    }
}

object CacheName {
    const val POPULAR_KEYWORD = "POPULAR_KEYWORD"
}