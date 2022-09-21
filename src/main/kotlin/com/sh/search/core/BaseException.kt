package com.sh.search.core

open class BaseException(val errorCode: ErrorCode, message: String) : RuntimeException(message) {
}
