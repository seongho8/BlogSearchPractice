package com.sh.search.popular.domain

import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

@Table(indexes = [Index(columnList = "baseDate DESC, hitCount DESC")])
@Entity
class PopularKeyword(
        @EmbeddedId
        val id:PopularKeywordId,
        val hitCount:Long
) {
}