package com.sh.search.popular.presentation.rest

import com.fasterxml.jackson.core.type.TypeReference
import com.sh.search.AbstractIntegrationTest
import com.sh.search.common.CommonResponse
import com.sh.search.popular.domain.PopularKeywordId
import com.sh.search.popular.infrastructure.KeywordBufferSaveSchedulerTestSupporter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

internal class PopularKeywordQueryApiTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var keywordSaveSupporter:KeywordBufferSaveSchedulerTestSupporter

    @Transactional
    @DisplayName("인기 검색어를 조회한다.")
    @Test
    fun get_popular_keyword() {
        val keywordId = PopularKeywordId(LocalDate.now(), "키워드임다.")
        val keywordPair = Pair(keywordId, 10L)
        keywordSaveSupporter.saveKeyword(keywordPair)

        val action = mockMvc.perform(
            MockMvcRequestBuilders.get(PopularKeywordApiUrl.SEARCH)
            .contentType(MediaType.APPLICATION_JSON)
        )
        action.andDo(MockMvcResultHandlers.print())
            .andExpect { MockMvcResultMatchers.status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object: TypeReference<CommonResponse<List<PopularKeywordQueryRes>>>(){})
                assertEquals(CommonResponse.Result.SUCCESS, res.result)
                assertNotNull(res.data)
                val apiResult = res.data!!
                assertEquals(1, apiResult.size)
                println(apiResult[0].keyword)
                assertEquals(keywordId.keyword, apiResult[0].keyword)
                assertEquals(keywordId.baseDate, apiResult[0].baseDate)
                assertEquals(keywordPair.second, apiResult[0].hitCount)

            }
    }
}