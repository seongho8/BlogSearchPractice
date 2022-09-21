package com.sh.search.blog.application

import com.sh.search.blog.domain.BlogContents
import com.sh.search.blog.domain.BlogSearchCommand
import com.sh.search.blog.domain.BlogSearchEngine
import com.sh.search.blog.domain.BlogSearchEvent
import com.sh.search.core.PageableData
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class BlogSearchFacade(
        @Qualifier("kakaoBlogSearchEngine")
        private val searchEngine: BlogSearchEngine,
        private val eventPublisher: ApplicationEventPublisher,
) {

    fun search(command: BlogSearchCommand) : PageableData<BlogContents> {
        val result = searchEngine.search(command)
        eventPublisher.publishEvent(BlogSearchEvent(query = command.query))
        return result
    }
}