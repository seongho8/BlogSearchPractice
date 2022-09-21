package com.sh.search.core

data class PageableData<D>(
        val totalCount:Long,
        val pageableCount:Int,
        val data:List<D>
)