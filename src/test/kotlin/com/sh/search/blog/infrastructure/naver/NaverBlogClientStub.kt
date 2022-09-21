package com.sh.search.blog.infrastructure.naver

import com.sh.search.core.Profile.TEST
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Profile
import java.time.LocalDate

@Profile(TEST)
@Component
class NaverBlogClientStub : NaverBlogClient {
    override fun search(query: String,
                        display: Int,
                        start: Int,
                        sort: NaverBlogSearchSort): NaverBlogSearchRes.Data {
        return NaverBlogSearchRes.Data(
            totalCount = 1,
            start = start.toLong(),
            display = 1,
            items = listOf(NaverBlogSearchRes.Item(
                title = "title_$query",
                contents = "contents_$query",
                link = "link",
                blogName = "blogName",
                postDate = LocalDate.now().minusDays(15)
            ))
        )
    }
}