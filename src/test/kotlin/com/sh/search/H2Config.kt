package com.sh.search

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource


@Profile(com.sh.search.core.Profile.TEST)
@Configuration
class H2Config {
    @Bean
    fun dataSource(): DataSource {

        return EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .setName("testdb;mode=MySQL")
            .build()
    }
}