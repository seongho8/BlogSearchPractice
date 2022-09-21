package com.sh.search.blog.infrastructure.naver

import com.fasterxml.jackson.databind.ObjectMapper
import feign.Request
import feign.RequestInterceptor
import feign.RequestTemplate
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import java.util.concurrent.TimeUnit

class NaverBlogClientConfig {

    @Bean
    fun requestInterceptor(@Value("\${app.infra.naver.api-client-id}")clientId:String,
                           @Value("\${app.infra.naver.api-client-secret}")clientSecret:String,) : RequestInterceptor {
        return RequestInterceptor {
            template: RequestTemplate->
                template.header("X-Naver-Client-Id", clientId)
                        .header("X-Naver-Client-Secret", clientSecret)
        }
    }

    @Bean
    fun requestOption() : Request.Options {
        return Request.Options(1500L, TimeUnit.MILLISECONDS, 1500L, TimeUnit.MILLISECONDS, false)
    }

    @Bean
    fun errorDecoder(objectMapper: ObjectMapper) : ErrorDecoder {
        return ErrorDecoder {
                _:String, response: Response ->
            val errorRes = objectMapper.readValue(response.body().asInputStream(), NaverErrorRes::class.java)
            throw NaverBlogSearchEngineException(errorRes)
        }
    }
}

data class NaverErrorRes(
    val errorCode:String,
    val errorMessage:String,
)