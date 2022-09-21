package com.sh.search.popular.presentation.rest

import com.sh.search.common.CommonResponse
import com.sh.search.popular.application.PopularKeywordFacade
import com.sh.search.popular.domain.PopularKeyword
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RequestMapping(value = [PopularKeywordApiUrl.SEARCH])
@RestController
class PopularKeywordQueryApi(
        private val facade:PopularKeywordFacade
) {

    @GetMapping
    fun get() : CommonResponse<List<PopularKeywordQueryRes>> {
        val page = 0
        val pageSize = 10
        val res = facade.getPopularKeywords(page, pageSize)
        return CommonResponse.success(res.map(::PopularKeywordQueryRes))
    }
}

data class PopularKeywordQueryRes(
        val baseDate: LocalDate,
        val keyword:String,
        val hitCount:Long
) {
    constructor(p:PopularKeyword) : this(p.id.baseDate, p.id.keyword, p.hitCount)
}