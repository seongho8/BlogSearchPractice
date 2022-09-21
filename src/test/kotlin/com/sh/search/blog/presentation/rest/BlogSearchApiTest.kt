package com.sh.search.blog.presentation.rest

import com.fasterxml.jackson.core.type.TypeReference
import com.sh.search.AbstractIntegrationTest
import com.sh.search.common.CommonResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

internal class BlogSearchApiTest : AbstractIntegrationTest() {

    @Disabled
    @DisplayName("블로그 검색이 된다.")
    @Test
    fun search_correctly() {
        val action = mockMvc.perform(get(BlogSearchApiUrl.SEARCH)
            .contentType(MediaType.APPLICATION_JSON)
            .param("query", "query").param("sort" ,"ACCURACY").param("page", "1").param("pageSize", "1")
        )
        action.andDo(print())
            .andExpect { status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object: TypeReference<CommonResponse<BlogSearchRes>>(){})
                assertNotNull(res.data)
                assertEquals(CommonResponse.Result.SUCCESS, res.result)
                assertEquals(1, res.data!!.contentsList.size)
            }
    }
}