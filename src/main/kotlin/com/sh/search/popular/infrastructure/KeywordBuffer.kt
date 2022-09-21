package com.sh.search.popular.infrastructure

import com.sh.search.popular.domain.PopularKeywordId
import com.sh.search.core.ReentrantLockTemplate
import org.springframework.stereotype.Component

@Component
class KeywordBuffer {
    private val defaultBufferCapacity = 1000
    private var bufferCapacity = defaultBufferCapacity
    private var keywordMap = HashMap<PopularKeywordId, Long>(bufferCapacity)
    private val reentrantLockTemplate = ReentrantLockTemplate()

    fun put(id:PopularKeywordId, hitCount:Long = 1) {
        reentrantLockTemplate.execute {
            keywordMap.compute(id) { _, v ->
                (v ?:0) + hitCount
            }
        }
    }

    fun getAndRefresh() : Map<PopularKeywordId, Long> {
        if(keywordMap.isEmpty()) {
            return emptyMap()
        }
        return reentrantLockTemplate.execute {
            val r = keywordMap
            resizeBufferCapacity(r.size)
            keywordMap = HashMap(bufferCapacity)
            return@execute r
        }
    }

    private fun resizeBufferCapacity(currentBufferSize:Int) {
        bufferCapacity = when {
            currentBufferSize > defaultBufferCapacity -> {
                currentBufferSize
            }
            else -> {
                defaultBufferCapacity
            }
        }
    }
}