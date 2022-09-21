package com.sh.search.popular.infrastructure

import com.sh.search.popular.domain.PopularKeyword
import com.sh.search.popular.domain.PopularKeywordReader
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class PopularKeywordReaderImpl(
        private val jpaRepository: PopularKeywordJpaRepository
) : PopularKeywordReader {
    override fun loadPopularKeyword(page:Int, pageSize:Int): List<PopularKeyword> {
        return jpaRepository.findPopularKeyword(PageRequest.of(page, pageSize)).content
    }
}