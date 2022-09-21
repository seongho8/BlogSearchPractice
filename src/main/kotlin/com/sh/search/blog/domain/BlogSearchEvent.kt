package com.sh.search.blog.domain

import java.time.ZonedDateTime

data class BlogSearchEvent(
        val query:String,
        val searchedAt:ZonedDateTime = ZonedDateTime.now()
)
