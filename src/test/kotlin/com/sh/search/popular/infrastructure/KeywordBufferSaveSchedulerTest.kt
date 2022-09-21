package com.sh.search.popular.infrastructure

import com.sh.search.popular.domain.PopularKeywordId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
internal class KeywordBufferSaveSchedulerTest {
    @Autowired
    private lateinit var bulkSaveRepository: BulkSaveRepository
    @Autowired
    private lateinit var popularKeywordJpaRepository: PopularKeywordJpaRepository

    @DisplayName("멀티쓰레드 환경에서 저장 검증")
    @Test
    fun save_keyword_at_multi_thread_correctly() {
        val keywordBuffer = KeywordBuffer()
        val scheduler = KeywordBufferSaveScheduler(keywordBuffer, bulkSaveRepository)

        val numberOfThreads = 10
        val hitCount = 3L
        val service = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(numberOfThreads)

        val popularKeywordId = PopularKeywordId(LocalDate.now(), "keyword")
        for (i in 0 until numberOfThreads) {
            service.execute {
                keywordBuffer.put(popularKeywordId, hitCount)
                scheduler.saveKeyword()
                latch.countDown()
            }
        }
        latch.await()
        val popularKeyword = popularKeywordJpaRepository.findPopularKeyword(PageRequest.of(0, 10))
            .filter { it.id.keyword == popularKeywordId.keyword }.first()

        assertEquals(numberOfThreads * hitCount, popularKeyword.hitCount)
    }
}

@Component
class KeywordBufferSaveSchedulerTestSupporter {
    @Autowired
    private lateinit var bulkSaveRepository: BulkSaveRepository

    fun saveKeyword(vararg keywords:Pair<PopularKeywordId, Long>) {
        val keywordBuffer = KeywordBuffer()
        val scheduler = KeywordBufferSaveScheduler(keywordBuffer, bulkSaveRepository)
        keywords.forEach {
            keywordBuffer.put(it.first, it.second)
        }
        scheduler.saveKeyword()
    }
}