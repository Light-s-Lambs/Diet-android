package com.example.diet.extension

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout

@FlowPreview
suspend fun <T> Flow<T>.timeout(timeMillis: Long): Flow<T> {
    val mainStream = this
    return flow<T> {
        withTimeout(timeMillis) {
            mainStream.debounce(timeMillis).first()
        }
    }
}
