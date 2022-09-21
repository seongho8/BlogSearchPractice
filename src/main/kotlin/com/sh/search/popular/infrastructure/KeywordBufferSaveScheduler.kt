package com.sh.search.popular.infrastructure

import com.sh.search.popular.domain.PopularKeyword
import com.sh.search.popular.domain.PopularKeywordId
import com.sh.search.core.Profile.TEST
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy

@Profile("!$TEST")
@Component
class KeywordBufferSaveScheduler(
        private val keywordBuffer: KeywordBuffer,
        private val bulkSaveRepository: BulkSaveRepository
) {
    /**
     * 5초마다 키워드 저장
     */
    @Scheduled(cron = "0/5 * * * * ?")
    fun saveKeyword() {
        val buffer:Map<PopularKeywordId, Long> = keywordBuffer.getAndRefresh()
        val keywords = buffer.map { PopularKeyword(it.key, it.value) }
        bulkSaveRepository.bulkSave(keywords)
    }

    @PreDestroy
    fun preDestroy() {
        saveKeyword()
    }
}