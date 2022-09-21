package com.sh.search.core

import java.util.concurrent.locks.ReentrantLock

class ReentrantLockTemplate {
    private val locker = ReentrantLock()

    fun <R> execute(callback:()->R) : R {
        locker.lock()
        try{
            return callback.invoke()
        }finally {
            locker.unlock()
        }
    }
}