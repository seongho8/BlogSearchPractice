package com.sh.search.blog.infrastructure.kakao

import com.fasterxml.jackson.databind.ObjectMapper
import feign.Request
import feign.RequestInterceptor
import feign.RequestTemplate
import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import java.util.concurrent.TimeUnit

class KakaoBlogClientConfig {

    @Bean
    fun requestInterceptor(@Value("\${app.infra.kakao.rest-api-key}")apiKey:String) : RequestInterceptor {
        return RequestInterceptor {
            template: RequestTemplate-> template.header(HttpHeaders.AUTHORIZATION, apiKey)
        }
    }

    @Bean
    fun requestOption() : Request.Options {
        return Request.Options(1500L, TimeUnit.MILLISECONDS, 1500L, TimeUnit.MILLISECONDS, false)
    }

    @Bean
    fun errorDecoder(objectMapper: ObjectMapper) : ErrorDecoder {
        return ErrorDecoder {
            methodKey:String, response:Response ->
            val errorRes = objectMapper.readValue(response.body().asInputStream(), KakaoErrorRes::class.java)

            val log = LoggerFactory.getLogger("KakaoBlogClientErrorDecoder")
            log.warn("kakaoBlogSearchApi error: $errorRes")

            throw KakaoBlogSearchEngineException(errorRes)
        }
    }
}

data class KakaoErrorRes(
    val errorType:String,
    val message:String,
)