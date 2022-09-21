package com.sh.search.blog.infrastructure.naver

import com.sh.search.blog.domain.BlogSearchEngineException

class NaverBlogSearchEngineException(
    val naverErrorCode:String,
    val naverErrorMessage:String) : BlogSearchEngineException(message = "${naverErrorMessage}($naverErrorCode)") {
        constructor(error:NaverErrorRes) : this(error.errorCode, error.errorMessage)
}