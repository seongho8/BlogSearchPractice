package com.sh.search.popular.infrastructure

import com.sh.search.popular.domain.PopularKeyword
import com.sh.search.popular.domain.PopularKeywordId
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PopularKeywordJpaRepository : JpaRepository<PopularKeyword, PopularKeywordId> {

    @Query(value = "select p from PopularKeyword p order by p.id.baseDate desc , p.hitCount desc ")
    fun findPopularKeyword(pageable: Pageable) : Slice<PopularKeyword>

}