package com.sh.search.popular.application

import com.sh.search.common.CacheName
import com.sh.search.popular.domain.PopularKeyword
import com.sh.search.popular.domain.PopularKeywordReader
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class PopularKeywordFacade(
        private val reader: PopularKeywordReader
) {
    @Cacheable(cacheNames = [CacheName.POPULAR_KEYWORD], key = "#page + '_' + #pageSize")
    fun getPopularKeywords(page:Int, pageSize:Int) : List<PopularKeyword> {
        return reader.loadPopularKeyword(page, pageSize)
    }
}