package com.sh.search.blog.application

import com.sh.search.blog.domain.BlogSearchCommand
import com.sh.search.blog.infrastructure.kakao.KakaoBlogSearchEngine
import com.sh.search.core.Sort
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.context.ApplicationEventPublisher

internal class BlogSearchFacadeTest {

    @DisplayName("카카오 블로그 검색API로 검색을 요청한다.")
    @Test
    fun search_by_kakao_api_correctly() {
        val kakaoBlogSearchEngine = Mockito.mock(KakaoBlogSearchEngine::class.java)
        val command = BlogSearchCommand("query", Sort.ACCURACY, 0, 10)
        val eventPublisher = Mockito.mock(ApplicationEventPublisher::class.java)

        val sut = BlogSearchFacade(kakaoBlogSearchEngine, eventPublisher)
        sut.search(command)

        Mockito.verify(kakaoBlogSearchEngine).search(command)
    }
}