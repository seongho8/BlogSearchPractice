package com.sh.search.blog.infrastructure.kakao

import com.sh.search.blog.domain.BlogContents
import com.sh.search.blog.domain.BlogSearchEngine
import com.sh.search.blog.domain.BlogSearchCommand
import com.sh.search.core.PageableData
import org.springframework.stereotype.Component

@Component
class KakaoBlogSearchEngine(
        private val kakaoBlogClient: KakaoBlogClient
) : BlogSearchEngine {
    override fun search(command: BlogSearchCommand) : PageableData<BlogContents> {
        val res:KakaoBlogSearchRes.Data = kakaoBlogClient.search(
                query = command.query,
                sort = KakaoBlogSearchSort.of(command.sort),
                page = command.page,
                size = command.pageSize
        )

        return res.toPageableData()
    }
}