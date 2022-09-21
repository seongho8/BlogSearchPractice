package com.sh.search.blog.infrastructure.kakao

import com.sh.search.core.Sort

enum class KakaoBlogSearchSort {
    accuracy, recency;

    companion object {
        fun of(sort:Sort): KakaoBlogSearchSort {
            if(sort == Sort.ACCURACY) {
                return accuracy
            }
            return recency
        }
    }

    fun toSort() : Sort {
        if(this == accuracy) {
            return Sort.ACCURACY
        }
        return Sort.RECENCY
    }
}