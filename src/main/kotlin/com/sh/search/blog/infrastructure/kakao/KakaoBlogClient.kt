package com.sh.search.blog.infrastructure.kakao

import com.sh.search.blog.domain.BlogContents
import com.sh.search.blog.domain.BlogSearchCommand
import com.sh.search.blog.domain.BlogSearchEngine
import com.sh.search.blog.infrastructure.FeignClientName
import com.sh.search.core.PageableData
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = FeignClientName.KAKAO_BLOG_SEARCH,
    url = "\${app.infra.kakao.api-url}",
    configuration = [KakaoBlogClientConfig::class],
    fallback = KakaoBlogClientFallback::class)
interface KakaoBlogClient {
    @GetMapping(value = ["/v2/search/blog"])
    fun search(@RequestParam query:String,
               @RequestParam sort: KakaoBlogSearchSort,
               @RequestParam page:Int,
               @RequestParam size:Int) : KakaoBlogSearchRes.Data
}

@Component
class KakaoBlogClientFallback(
    @Qualifier("naverBlogSearchEngine")
    private val searchEngine:BlogSearchEngine
) : KakaoBlogClient {
    override fun search(query: String, sort: KakaoBlogSearchSort, page: Int, size: Int): KakaoBlogSearchRes.Data {
        val command = BlogSearchCommand(query = query, sort = sort.toSort(), page = page, pageSize = size)
        val pageableData: PageableData<BlogContents> = searchEngine.search(command)
        return KakaoBlogSearchRes.Data(pageableData)
    }

}