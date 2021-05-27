package com.example.diet.extension

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout

@FlowPreview
fun <T> Flow<T>.timeout(timeMillis: Long): Flow<T> = flow {
    require(timeMillis >= 0L) { "Timeout should not be negative" }
    withTimeout(timeMillis) {
        collect { value ->
            emit(value)
        }
    }
}
