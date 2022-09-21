package com.sh.search.popular.infrastructure

import com.sh.search.popular.domain.PopularKeywordId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors


internal class KeywordBufferTest {
    @DisplayName("멀티쓰레드 환경 입력 출력 검증")
    @Test
    fun put_at_multi_thread_correctly() {
        val numberOfThreads = 4
        val hitCount = 3L
        val service = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(numberOfThreads)

        val keywordBuffer = KeywordBuffer()
        val popularKeywordId = PopularKeywordId(LocalDate.now(), "keyword")
        for (i in 0 until numberOfThreads) {
            service.execute {
                keywordBuffer.put(popularKeywordId, hitCount)
                latch.countDown()
            }
        }
        latch.await()
        Assertions.assertEquals(hitCount * numberOfThreads, keywordBuffer.getAndRefresh()[popularKeywordId])
    }
}