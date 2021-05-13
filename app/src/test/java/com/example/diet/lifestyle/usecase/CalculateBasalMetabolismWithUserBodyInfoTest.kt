package com.example.diet.lifestyle.usecase

import com.example.diet.extension.timeout
import com.example.diet.lifestyle.model.Gender
import com.example.diet.lifestyle.model.UserBodyInfo
import com.example.diet.lifestyle.service.UserBodyInfoService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

@kotlinx.coroutines.FlowPreview
@ExperimentalCoroutinesApi
class CalculateBasalMetabolismWithUserBodyInfoTest {
    lateinit var calculateBasalMetabolismWithUserBodyInfo: CalculateBasalMetabolismWithUserBodyInfo

    @MockK
    lateinit var userBodyInfoService: UserBodyInfoService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        calculateBasalMetabolismWithUserBodyInfo =
            CalculateBasalMetabolismWithUserBodyInfo(userBodyInfoService)
    }

    @Test
    fun `사용자 신체 정보를 정상적으로 가져온 경우, 기초대사량을 계산해서 전달`() {
        val userBodyInfo = UserBodyInfo(
            84.0,
            184.0,
            24,
            Gender.Male
        )
        val expected = 1979.2
        val delta = 1e-15

        coEvery {
            userBodyInfoService.getCurrentUserBodyInfo()
        } returns flowOf(userBodyInfo)

        runBlocking {
            calculateBasalMetabolismWithUserBodyInfo()
                .catch {
                    fail()
                }.collect {
                    assertEquals(expected, it, delta)
                }
        }
    }

    @Test
    fun `사용자 신체 정보 중 잘못된 값이 있는 경우, 에러 출력`() {
        val userBodyInfo = UserBodyInfo(
            -5.0,
            184.0,
            24,
            Gender.Male
        )
        val expected = IllegalArgumentException()

        coEvery {
            userBodyInfoService.getCurrentUserBodyInfo()
        } returns flowOf(userBodyInfo)

        runBlocking {
            calculateBasalMetabolismWithUserBodyInfo()
                .catch { cause ->
                    assertEquals(expected::class, cause::class)
                }.collect {
                    fail()
                }
        }
    }

    @Test
    fun `사용자 신체 정보를 2초안에 가져오지 못하는 경우, 에러 출력`() {
        val expected = TimeoutCancellationException::class

        coEvery {
            userBodyInfoService.getCurrentUserBodyInfo()
        } returns callbackFlow {
            delay(3000)
        }

        runBlocking {
            calculateBasalMetabolismWithUserBodyInfo()
                .timeout(
                    2000
                ).catch { cause ->
                    assertEquals(expected, cause::class)
                }.collect {
                    fail()
                }
        }
    }

}
