package com.sh.search

import com.sh.search.blog.infrastructure.kakao.KakaoBlogSearchResArgumentResolver
import com.sh.search.blog.infrastructure.naver.NaverBlogSearchResArgumentResolver

interface DomainArgumentResolver {
    fun tryResolve(parameterType: Class<*>): Any?
    companion object {
        var instance: DomainArgumentResolver = CompositeArgumentResolver(
            KakaoBlogSearchResArgumentResolver(), NaverBlogSearchResArgumentResolver()
        )
    }
}