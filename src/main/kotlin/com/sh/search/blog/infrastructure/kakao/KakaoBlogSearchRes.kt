package com.sh.search.blog.infrastructure.kakao

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.sh.search.blog.domain.BlogContents
import com.sh.search.core.PageableData
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class KakaoBlogSearchRes {
    data class Data(
            val meta:Meta,
            val documents:List<Document>
    ) {
        constructor(source: PageableData<BlogContents>) :
                this(meta = Meta(totalCount = source.totalCount, pageableCount = source.pageableCount),
                documents = source.data.map(::Document))

        fun toPageableData() : PageableData<BlogContents> {
            val blogList = documents.map {
                BlogContents(
                        title = it.title,
                        contents = it.contents,
                        link = it.url,
                        bloggerName = it.blogName,
                        thumbnail = it.thumbnail,
                        postedAt = it.dateTime
                )
            }
            DateTimeFormatter.ISO_INSTANT
            return PageableData(
                    totalCount = meta.totalCount,
                    pageableCount = meta.pageableCount,
                    data = blogList
            )
        }
    }

    data class Meta(
            @JsonProperty(value = "total_count")
            val totalCount:Long,
            @JsonProperty(value = "pageable_count")
            val pageableCount:Int,
//            @JsonProperty(value = "is_end")
//            val isEnd:Boolean,
    )

    data class Document(
            val title:String,
            val contents:String,
            val url:String,
            @JsonProperty(value = "blogname")
            val blogName:String,
            val thumbnail:String?,
            @JsonProperty(value = "datetime")
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            val dateTime:ZonedDateTime
    ) {
        constructor(bc: BlogContents) : this(title = bc.title, contents = bc.contents, url = bc.link, blogName = bc.bloggerName, thumbnail = bc.thumbnail, dateTime = bc.postedAt)
    }
}
