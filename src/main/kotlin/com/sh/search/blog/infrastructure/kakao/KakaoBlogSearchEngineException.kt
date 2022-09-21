package com.sh.search.blog.infrastructure.kakao

import com.sh.search.blog.domain.BlogSearchEngineException

class KakaoBlogSearchEngineException(
    val kakaoErrorCode:String,
    val kakaoErrorMessage:String) : BlogSearchEngineException(message = "${kakaoErrorMessage}($kakaoErrorCode)") {
        constructor(error:KakaoErrorRes) : this(error.errorType, error.message)
}