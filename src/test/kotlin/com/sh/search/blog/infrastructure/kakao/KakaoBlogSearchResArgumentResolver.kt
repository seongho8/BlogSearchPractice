package com.sh.search.blog.infrastructure.kakao

import com.sh.search.DomainArgumentResolver
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class KakaoBlogSearchResArgumentResolver : DomainArgumentResolver {
    private val random = ThreadLocalRandom.current()

    override fun tryResolve(parameterType: Class<*>): Any? {
        if(parameterType == KakaoBlogSearchRes.Data::class.java) {
            val meta = KakaoBlogSearchRes.Meta(3, 3)
            return KakaoBlogSearchRes.Data(meta, listOf(generateDocument(), generateDocument(), generateDocument()))
        }

        return null
    }

    private fun generateDocument(): KakaoBlogSearchRes.Document {
        return KakaoBlogSearchRes.Document(
            title = "title${UUID.randomUUID()}",
            contents = "contents${UUID.randomUUID()}",
            url = "https://naver.com",
            blogName = "blogName${UUID.randomUUID()}",
            thumbnail = "https://naver.com",
            dateTime = ZonedDateTime.now().minusDays(random.nextLong(0, 100))
        )
    }
}