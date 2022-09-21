package com.sh.search.blog.infrastructure.naver

import com.sh.search.core.Sort
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class NaverBlogSearchSortTest {
    @DisplayName("sort변환이 정상적으로 된다.")
    @Test
    fun sort_convert_correctly() {
        assertEquals(NaverBlogSearchSort.sim, NaverBlogSearchSort.of(Sort.ACCURACY))
        assertEquals(NaverBlogSearchSort.date, NaverBlogSearchSort.of(Sort.RECENCY))
    }
}