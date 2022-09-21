package com.sh.search.blog.domain

import com.sh.search.core.BaseException
import com.sh.search.core.ErrorCode

open class BlogSearchEngineException(message:String = ErrorCode.SEARCH_ENGINE_ERROR.errorMsg)
    : BaseException(errorCode = ErrorCode.SEARCH_ENGINE_ERROR, message = message) {
}