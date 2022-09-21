package com.sh.search.blog.domain

import java.time.ZonedDateTime

data class BlogContents(
        val title:String,
        val contents:String,
        val link:String,
        val postedAt:ZonedDateTime,
        val bloggerName:String,
        val thumbnail:String?
)