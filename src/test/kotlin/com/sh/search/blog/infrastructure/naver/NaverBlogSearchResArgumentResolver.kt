package com.sh.search.blog.infrastructure.naver

import com.sh.search.DomainArgumentResolver
import java.time.LocalDate
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class NaverBlogSearchResArgumentResolver : DomainArgumentResolver {
    private val random = ThreadLocalRandom.current()

    override fun tryResolve(parameterType: Class<*>): Any? {
        if(parameterType == NaverBlogSearchRes.Data::class.java) {
            return NaverBlogSearchRes.Data(3, 1, 3, listOf(generateItem(), generateItem(), generateItem()))
        }

        return null
    }

    private fun generateItem(): NaverBlogSearchRes.Item {
        return NaverBlogSearchRes.Item(
            title = "title${UUID.randomUUID()}",
            contents = "contents${UUID.randomUUID()}",
            link = "https://naver.com",
            blogName = "blogName${UUID.randomUUID()}",
            postDate = LocalDate.now().minusDays(random.nextLong(0, 100))
        )
    }
}