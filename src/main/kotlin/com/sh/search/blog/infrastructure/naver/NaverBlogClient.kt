package com.sh.search.blog.infrastructure.naver

import com.sh.search.blog.infrastructure.FeignClientName
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = FeignClientName.NAVER_BLOG_SEARCH, url = "\${app.infra.naver.api-url}", configuration = [NaverBlogClientConfig::class])
interface NaverBlogClient {
    @GetMapping(value = ["/v1/search/blog.json"])
    fun search(@RequestParam query:String,
               @RequestParam display:Int,
               @RequestParam start:Int,
               @RequestParam sort: NaverBlogSearchSort) : NaverBlogSearchRes.Data
}