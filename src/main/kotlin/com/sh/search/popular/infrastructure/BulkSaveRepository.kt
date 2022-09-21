package com.sh.search.popular.infrastructure

import com.sh.search.popular.domain.PopularKeyword
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement

@Repository
class BulkSaveRepository(
        private val jdbcTemplate: JdbcTemplate
) {
    companion object {
        private const val batchSize = 1000
    }

    internal fun bulkSave(keywords:List<PopularKeyword>) {
        if(keywords.isEmpty()) {
            return
        }

        jdbcTemplate.batchUpdate(
            """
                INSERT INTO popular_keyword(base_date , keyword, hit_count) 
                VALUES (?, ?, ?)
                ON DUPLICATE KEY UPDATE hit_count = hit_count + ?
            """.trimIndent(),
            keywords,
            batchSize,
            ParameterizedPreparedStatementSetter<PopularKeyword> { ps, argument ->
                ps.setString(1, argument.id.baseDate.toString())
                ps.setString(2, argument.id.keyword)
                ps.setLong(3, argument.hitCount)
                ps.setLong(4, argument.hitCount)
            }
        )
    }
}