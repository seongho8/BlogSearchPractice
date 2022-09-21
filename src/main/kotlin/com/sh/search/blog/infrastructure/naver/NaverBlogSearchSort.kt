package com.sh.search.blog.infrastructure.naver

import com.sh.search.core.Sort

enum class NaverBlogSearchSort {
    sim, date;

    companion object {
        fun of(sort:Sort): NaverBlogSearchSort {
            if(sort == Sort.ACCURACY) {
                return sim
            }
            return date
        }
    }
}