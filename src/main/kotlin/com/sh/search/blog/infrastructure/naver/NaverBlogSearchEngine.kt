package com.sh.search.blog.infrastructure.naver

import com.sh.search.blog.domain.BlogContents
import com.sh.search.blog.domain.BlogSearchEngine
import com.sh.search.blog.domain.BlogSearchCommand
import com.sh.search.core.PageableData
import org.springframework.stereotype.Component

@Component
class NaverBlogSearchEngine(
        private val naverBlogClient: NaverBlogClient
) : BlogSearchEngine {
    override fun search(command: BlogSearchCommand): PageableData<BlogContents> {
        val res:NaverBlogSearchRes.Data = naverBlogClient.search(
                query = command.query,
                display = command.pageSize,
                start = command.getOffset(),
                sort = NaverBlogSearchSort.of(command.sort)
        )
        return res.toPageableData()
    }
}