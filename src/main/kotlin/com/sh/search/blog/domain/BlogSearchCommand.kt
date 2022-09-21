package com.sh.search.blog.domain

import com.sh.search.core.Sort

data class BlogSearchCommand(
        val query:String,
        val sort: Sort,
        val page:Int,
        val pageSize:Int,
) {
    fun getOffset() : Int {
        return ((page-1) * pageSize) +1
    }
}