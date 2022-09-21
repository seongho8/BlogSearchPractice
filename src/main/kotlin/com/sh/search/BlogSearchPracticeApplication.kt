package com.sh.search

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@EnableFeignClients
@SpringBootApplication
@EnableScheduling
class BlogSearchPracticeApplication

fun main(args: Array<String>) {
	runApplication<BlogSearchPracticeApplication>(*args)
}
