package com.sh.search.popular.infrastructure

import com.sh.search.blog.domain.BlogSearchEvent
import com.sh.search.popular.domain.PopularKeywordId
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PopularKeywordEventListener(
        private val keywordBuffer: KeywordBuffer
) {

    @EventListener
    fun handle(event:BlogSearchEvent) {
        keywordBuffer.put(PopularKeywordId(event.searchedAt.toLocalDate(), event.query))
    }
}