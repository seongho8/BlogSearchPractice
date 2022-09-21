package com.sh.search.blog.presentation.rest

import com.sh.search.blog.application.BlogSearchFacade
import com.sh.search.blog.domain.BlogContents
import com.sh.search.blog.domain.BlogSearchCommand
import com.sh.search.common.CommonResponse
import com.sh.search.core.PageableData
import com.sh.search.core.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@RequestMapping(BlogSearchApiUrl.SEARCH)
@RestController
class BlogSearchApi(
        private val facade: BlogSearchFacade
) {
    @GetMapping
    fun search(@Valid req:BlogSearchReq) : CommonResponse<BlogSearchRes> {
        val result: PageableData<BlogContents> = facade.search(req.toCommand())
        return CommonResponse.success(
                BlogSearchRes(result)
        )
    }
}

data class BlogSearchReq(
        @field:NotBlank(message = "검색어는 필수로 입력해야 합니다.")
        val query:String?,
        val sort: Sort = Sort.ACCURACY,
        @field:Min(1) @field:Max(50)
        val page:Int = 1,
        @field:Min(1) @field:Max(50)
        val pageSize:Int = 10,
) {
    fun toCommand() : BlogSearchCommand {
        return BlogSearchCommand(query = query!!, sort = sort, page = page, pageSize = pageSize)
    }
}

data class BlogSearchRes(
        val totalCount:Long,
        val pageableCount:Int,
        val contentsList:List<BlogContentsRes>
) {
    constructor(r:PageableData<BlogContents>) :
            this(totalCount = r.totalCount, pageableCount = r.pageableCount, contentsList = r.data.map(::BlogContentsRes))
}