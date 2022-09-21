package com.sh.search.core

enum class ErrorCode(val errorMsg:String) {

    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),

    SEARCH_ENGINE_ERROR("검색에 실패했습니다."),
}