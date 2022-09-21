package com.sh.search.blog.infrastructure

import feign.Target
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.reflect.Method
import java.time.Duration


@Configuration
class CircuitBreakerConfig {

    @Bean
    fun circuitBreakerFactoryCustomizer(): Customizer<Resilience4JCircuitBreakerFactory> {
        val cbConfig = CircuitBreakerConfig.custom()
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .slidingWindowSize(100)
            .failureRateThreshold(30.0f)
            .waitDurationInOpenState(Duration.ofSeconds(600))
            .build()
        val timeLimiterConfig = TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofMillis(1500))
            .build()


        return Customizer<Resilience4JCircuitBreakerFactory> { resilience4JCircuitBreakerFactory ->
            resilience4JCircuitBreakerFactory.configure({ builder ->
                builder.circuitBreakerConfig(cbConfig)
                    .timeLimiterConfig(timeLimiterConfig)
            }, FeignClientName.KAKAO_BLOG_SEARCH, FeignClientName.NAVER_BLOG_SEARCH)
        }
    }

    @Bean
    fun circuitBreakerNameResolver(): CircuitBreakerNameResolver {
        return CircuitBreakerNameResolver { feignClientName: String, target: Target<*>, method: Method ->
            feignClientName
        }
    }
}