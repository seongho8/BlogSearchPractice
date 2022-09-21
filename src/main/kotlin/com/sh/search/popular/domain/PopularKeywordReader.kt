package com.sh.search.popular.domain

interface PopularKeywordReader {
    fun loadPopularKeyword(page:Int, pageSize:Int) : List<PopularKeyword>
}