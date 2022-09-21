package com.sh.search.common

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Configuration
class JacksonConfig {

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        val dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            builder.timeZone(TimeZone.getDefault())
                    .locale(Locale.getDefault())
                    .serializerByType(ZonedDateTime::class.java, ZonedDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimePattern)))
                    .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }
    }
}