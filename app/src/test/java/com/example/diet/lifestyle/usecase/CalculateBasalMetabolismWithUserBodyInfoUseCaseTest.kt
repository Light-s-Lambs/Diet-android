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
class CalculateBasalMetabolismWithUserBodyInfoUseCaseTest {
    lateinit var calculateBasalMetabolismWithUserBodyInfoUseCase: CalculateBasalMetabolismWithUserBodyInfoUseCase

    @MockK
    lateinit var userBodyInfoService: UserBodyInfoService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        calculateBasalMetabolismWithUserBodyInfoUseCase =
            CalculateBasalMetabolismWithUserBodyInfoUseCase(userBodyInfoService)
    }

    @Test
    fun `사용자 신체 정보를 정상적으로 가져온 경우_기초대사량을 계산 성공_전달`() {
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
            calculateBasalMetabolismWithUserBodyInfoUseCase()
                .catch {
                    fail()
                }.collect {
                    assertEquals(expected, it, delta)
                }
        }
    }

    @Test
    fun `사용자 신체 정보 중 음수 값이 있는 경우_기초대사량 계산 실패_에러 출력`() {
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
            calculateBasalMetabolismWithUserBodyInfoUseCase()
                .catch { cause ->
                    assertEquals(expected::class, cause::class)
                }.collect {
                    fail()
                }
        }
    }

    @Test
    fun `사용자 신체 정보를 2초안에 가져오지 못하는 경우_에러 출력`() {
        val expected = TimeoutCancellationException::class

        coEvery {
            userBodyInfoService.getCurrentUserBodyInfo()
        } returns callbackFlow {
            delay(3000)
        }

        runBlocking {
            calculateBasalMetabolismWithUserBodyInfoUseCase()
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
