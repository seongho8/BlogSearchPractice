package com.sh.search.blog.infrastructure.naver

import com.sh.search.DomainArgumentsSource
import com.sh.search.blog.domain.BlogContents
import com.sh.search.blog.domain.BlogSearchCommand
import com.sh.search.core.PageableData
import com.sh.search.core.Sort
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.mockito.Mockito

internal class NaverBlogSearchEngineTest {

    @DisplayName("네이버 블로그 검색이 된다.")
    @ParameterizedTest
    @DomainArgumentsSource
    fun search_correctly(searchRes: NaverBlogSearchRes.Data) {
        val command = BlogSearchCommand(query = "네이버", Sort.ACCURACY, 1, 10)
        val naverBlogClient = Mockito.mock(NaverBlogClient::class.java)
        Mockito.`when`(
            naverBlogClient.search(query = command.query, sort = NaverBlogSearchSort.of(command.sort), display = command.pageSize, start = 1)
        ).thenReturn(searchRes)

        val sut = NaverBlogSearchEngine(naverBlogClient)

        val actual : PageableData<BlogContents> = sut.search(command)
        Assertions.assertEquals(searchRes.items.size, actual.data.size)
        Assertions.assertEquals(searchRes.totalCount, actual.totalCount)
        Assertions.assertEquals(searchRes.items.size, actual.pageableCount)
        searchRes.items.forEachIndexed { index, item ->
            Assertions.assertEquals(item.title, actual.data[index].title)
            Assertions.assertEquals(item.blogName, actual.data[index].bloggerName)
            Assertions.assertEquals(item.contents, actual.data[index].contents)
            Assertions.assertEquals(item.link, actual.data[index].link)
            Assertions.assertEquals(null, actual.data[index].thumbnail)
            Assertions.assertEquals(item.postDate, actual.data[index].postedAt.toLocalDate())
        }
    }
}