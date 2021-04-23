package com.example.diet.extension

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeout

@FlowPreview
suspend fun <T> Flow<T>.timeout(timeMillis: Long): Flow<T> = flow {
    withTimeout(timeMillis) { collect { value -> emit(value) } }
}
