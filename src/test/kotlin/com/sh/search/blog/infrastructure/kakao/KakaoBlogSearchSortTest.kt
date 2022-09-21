package com.sh.search.blog.infrastructure.kakao

import com.sh.search.core.Sort
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class KakaoBlogSearchSortTest {
    @DisplayName("sort변환이 정상적으로 된다.")
    @Test
    fun sort_convert_correctly() {
        assertEquals(KakaoBlogSearchSort.accuracy, KakaoBlogSearchSort.of(Sort.ACCURACY))
        assertEquals(KakaoBlogSearchSort.recency, KakaoBlogSearchSort.of(Sort.RECENCY))
    }
}