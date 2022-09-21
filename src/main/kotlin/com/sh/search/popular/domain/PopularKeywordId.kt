package com.sh.search.popular.domain

import java.io.Serializable
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class PopularKeywordId(
        val baseDate:LocalDate,
        @Column(length = 16)
        val keyword:String
) : Serializable
