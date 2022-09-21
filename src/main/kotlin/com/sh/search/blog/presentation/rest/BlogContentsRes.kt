package com.sh.search.blog.presentation.rest

import com.sh.search.blog.domain.BlogContents
import java.time.ZonedDateTime

data class BlogContentsRes(
        val title:String,
        val contents:String,
        val link:String,
        val postedAt: ZonedDateTime,
        val bloggerName:String,
        val thumbnail:String?
) {
    constructor(bc:BlogContents) : this(
            title = bc.title,
            contents = bc.contents,
            link = bc.link,
            postedAt = bc.postedAt,
            bloggerName = bc.bloggerName,
            thumbnail = bc.thumbnail,
    )
}