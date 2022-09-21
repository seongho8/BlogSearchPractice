package com.sh.search.blog.infrastructure.naver

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.sh.search.blog.domain.BlogContents
import com.sh.search.core.PageableData
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NaverBlogSearchRes {
    data class Data(
            @JsonProperty(value = "total")
            val totalCount:Long,
            val start:Long,
            val display:Int,
            val items:List<Item>
    ) {
        companion object {
            const val NAVER_BLOG_MAX_SEARCH_COUNT = 1100
        }

        fun toPageableData() : PageableData<BlogContents> {
            val blogList = items.map {
                BlogContents(
                        title = it.title,
                        contents = it.contents,
                        link = it.link,
                        bloggerName = it.blogName,
                        thumbnail = null,
                        postedAt = it.postDate.atStartOfDay(ZoneId.systemDefault())
                )
            }
            DateTimeFormatter.ISO_INSTANT
            return PageableData(
                    totalCount = totalCount,
                    pageableCount = getPageableCount(),
                    data = blogList
            )
        }

        private fun getPageableCount() : Int {
            return if(NAVER_BLOG_MAX_SEARCH_COUNT > totalCount) {
                totalCount.toInt()
            } else {
                NAVER_BLOG_MAX_SEARCH_COUNT
            }
        }
    }

    data class Item(
            val title:String,
            @JsonProperty(value = "description")
            val contents:String,
            val link:String,
            @JsonProperty(value = "bloggername")
            val blogName:String,
            @JsonProperty(value = "postdate")
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
            val postDate:LocalDate
    )
}
