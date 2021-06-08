package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.Gender
import com.example.diet.lifestyle.model.UserBodyInfo
import com.example.diet.lifestyle.repository.UserBodyInfoRepository
import com.example.diet.lifestyle.usecase.exception.DataNotFoundException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.verify
import io.mockk.verifyOrder
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

    @SpyK
    private var calculateBasalMetabolismUseCase: CalculateBasalMetabolismUseCase =
        CalculateBasalMetabolismUseCase()

    @MockK
    lateinit var userBodyInfoRepository: UserBodyInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        calculateBasalMetabolismWithUserBodyInfoUseCase =
            CalculateBasalMetabolismWithUserBodyInfoUseCase(
                userBodyInfoRepository,
                calculateBasalMetabolismUseCase
            )
    }

    @Test
    fun `사용자 신체 정보를 2초안에 가져옴_사용자 신체 정보로 기초대사량을 계산 성공_전달`() {
        val userBodyInfo = UserBodyInfo(
            84.0,
            184.0,
            24,
            Gender.Male
        )

        every {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns flowOf(userBodyInfo)

        runBlocking {
            calculateBasalMetabolismWithUserBodyInfoUseCase()
                .catch {
                    fail()
                }.collect()
        }

        verify(exactly = 1) {
            calculateBasalMetabolismUseCase.invoke(
                userBodyInfo.weight,
                userBodyInfo.height,
                userBodyInfo.age,
                userBodyInfo.gender
            )
        }
        verifyOrder {
            userBodyInfoRepository.getCurrentUserBodyInfo()
            calculateBasalMetabolismUseCase.invoke(
                userBodyInfo.weight,
                userBodyInfo.height,
                userBodyInfo.age,
                userBodyInfo.gender
            )
        }
    }

    @Test
    fun `사용자 신체 정보를 2초안에 가져옴_사용자 신체 정보에 음수 값이 있는 경우_기초대사량 계산 실패_에러 발생`() {
        val userBodyInfo = UserBodyInfo(
            -5.0,
            184.0,
            24,
            Gender.Male
        )
        val expected = IllegalArgumentException()

        every {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns flowOf(userBodyInfo)

        runBlocking {
            calculateBasalMetabolismWithUserBodyInfoUseCase()
                .catch { cause ->
                    assertEquals(expected::class, cause::class)
                }.collect {
                    fail()
                }
        }

        verify(exactly = 1) {
            calculateBasalMetabolismUseCase.invoke(
                userBodyInfo.weight,
                userBodyInfo.height,
                userBodyInfo.age,
                userBodyInfo.gender
            )
        }
        verifyOrder {
            userBodyInfoRepository.getCurrentUserBodyInfo()
            calculateBasalMetabolismUseCase.invoke(
                userBodyInfo.weight,
                userBodyInfo.height,
                userBodyInfo.age,
                userBodyInfo.gender
            )
        }
    }

    @Test
    fun `사용자 신체 정보를 찾지 못한경우_기초대사량 계산 실패_에러 발생`() {
        val expected = DataNotFoundException()

        every {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            calculateBasalMetabolismWithUserBodyInfoUseCase()
                .catch { cause ->
                    assertEquals(expected, cause)
                }.collect {
                    fail()
                }
        }
    }

    @Test
    fun `사용자 신체 정보를 2초안에 가져오지 못하는 경우_에러 발생`() {
        val expected = TimeoutCancellationException::class

        every {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns callbackFlow {
            delay(3000)
        }

        runBlocking {
            calculateBasalMetabolismWithUserBodyInfoUseCase()
                .catch { cause ->
                    assertEquals(expected, cause::class)
                }.collect {
                    fail()
                }
        }
    }
}
