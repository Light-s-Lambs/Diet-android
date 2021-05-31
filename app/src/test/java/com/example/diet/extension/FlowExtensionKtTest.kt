package com.example.diet.extension

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

@kotlinx.coroutines.FlowPreview
class FlowExtensionKtTest {
    lateinit var upperStream: Flow<Int>

    @Test
    fun `timeout 대기 시간이 실행 시간보다 김_timeout 발생하지 않음_upperStream의 결과를 전달`() {
        val emitDelayTime: Long = 0
        val timeoutDelayTime: Long = 1000
        val expected = 1
        upperStream = flow {
            delay(emitDelayTime)
            emit(expected)
        }

        runBlocking {
            upperStream
                .timeout(
                    timeoutDelayTime
                ).catch {
                    fail()
                }.collect {
                    assertEquals(it, expected)
                }
        }
    }

    @Test
    fun `timeout 대기 시간과 실행 시간이 동일한 경우_timeout 발생_TimeoutCancellationException 전달`() {
        val emitDelayTime: Long = 1000
        val timeoutDelayTime: Long = 1000
        val expected = 1
        upperStream = flow {
            delay(emitDelayTime)
            emit(expected)
        }

        runBlocking {
            upperStream
                .timeout(
                    timeoutDelayTime
                ).catch {
                    assertEquals(it::class, TimeoutCancellationException::class)
                }.collect {
                    fail()
                }
        }
    }

    @Test
    fun `timeout 대기 시간이 실행 시간보다 짧음_timeout 발생_TimeoutCancellationException 전달`() {
        val emitDelayTime: Long = 2000
        val timeoutDelayTime: Long = 1000
        val expected = 1
        upperStream = flow {
            delay(emitDelayTime)
            emit(expected)
        }

        runBlocking {
            upperStream
                .timeout(
                    timeoutDelayTime
                ).catch {
                    assertEquals(it::class, TimeoutCancellationException::class)
                }.collect {
                    fail()
                }
        }
    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @Test
    fun `timeout 대기 시간안에 upstream에서 에러가 발생함_upperStream의 에러 전달`() {
        val expected = IllegalStateException()
        val timeoutDelayTime: Long = 1000
        upperStream = callbackFlow {
            close(expected)
        }

        runBlocking {
            upperStream
                .timeout(
                    timeoutDelayTime
                ).catch {
                    assertEquals(it::class, expected::class)
                }.collect {
                    fail()
                }
        }
    }

    @Test
    fun `timeout 인자로 음수가 넘어오는 경우_IllegalArgumentException 전달`(){
        val expected = IllegalArgumentException()
        val emitDelayTime: Long = 2000
        val timeoutDelayTime: Long = -1

        upperStream = flow {
            delay(emitDelayTime)
            emit(1)
        }

        runBlocking {
            upperStream
                .timeout(
                    timeoutDelayTime
                ).catch {
                    assertEquals(it::class, expected::class)
                }.collect {
                    fail()
                }
        }
    }
}
