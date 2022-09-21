package com.sh.search.blog.infrastructure.kakao

import com.sh.search.DomainArgumentsSource
import com.sh.search.blog.domain.BlogContents
import com.sh.search.blog.domain.BlogSearchCommand
import com.sh.search.core.PageableData
import com.sh.search.core.Sort
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

internal class KakaoBlogSearchEngineTest {

    @DisplayName("카카오 블로그 검색이 된다.")
    @ParameterizedTest
    @DomainArgumentsSource
    fun search_correctly(searchRes:KakaoBlogSearchRes.Data) {
        val command = BlogSearchCommand(query = "네이버", Sort.ACCURACY, 1, 10)
        val kakaoBlogClient = mock(KakaoBlogClient::class.java)
        `when`(
            kakaoBlogClient.search(query = command.query, sort = KakaoBlogSearchSort.of(command.sort), page = command.page, size = command.pageSize)
        ).thenReturn(searchRes)

        val sut = KakaoBlogSearchEngine(kakaoBlogClient)

        val actual : PageableData<BlogContents> = sut.search(command)
        assertEquals(searchRes.documents.size, actual.data.size)
        assertEquals(searchRes.meta.totalCount, actual.totalCount)
        assertEquals(searchRes.meta.pageableCount, actual.pageableCount)
        searchRes.documents.forEachIndexed { index, document ->
            assertEquals(document.title, actual.data[index].title)
            assertEquals(document.blogName, actual.data[index].bloggerName)
            assertEquals(document.contents, actual.data[index].contents)
            assertEquals(document.url, actual.data[index].link)
            assertEquals(document.thumbnail, actual.data[index].thumbnail)
            assertEquals(document.dateTime, actual.data[index].postedAt)
        }
    }
}