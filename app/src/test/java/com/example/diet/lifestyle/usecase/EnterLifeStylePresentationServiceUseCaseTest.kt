package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.Gender
import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.UserBodyInfo
import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.repository.UserBodyInfoRepository
import com.example.diet.lifestyle.service.LifeStylePresentationService
import com.example.diet.lifestyle.usecase.exception.DataNotFoundException
import com.example.diet.lifestyle.usecase.exception.PresentationFailureException
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test

@kotlinx.coroutines.ExperimentalCoroutinesApi
@kotlinx.coroutines.FlowPreview
class EnterLifeStylePresentationServiceUseCaseTest {
    private val calculateBasalMetabolismUseCase = CalculateBasalMetabolismUseCase()

    lateinit var loadInDayToListUseCase: LoadLifeStyleInDayToListUseCase
    lateinit var calculateBasalMetabolismWithUserBodyInfoUseCase: CalculateBasalMetabolismWithUserBodyInfoUseCase
    lateinit var enterLifeStyleServiceUseCase: EnterLifeStyleServiceUseCase

    @MockK
    lateinit var lifeStyleRepository: LifeStyleRepository

    @MockK(relaxed = true)
    lateinit var lifeStylePresentationService: LifeStylePresentationService

    @MockK
    lateinit var userBodyInfoRepository: UserBodyInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadInDayToListUseCase = LoadLifeStyleInDayToListUseCase(lifeStyleRepository)
        calculateBasalMetabolismWithUserBodyInfoUseCase =
            CalculateBasalMetabolismWithUserBodyInfoUseCase(
                userBodyInfoRepository,
                calculateBasalMetabolismUseCase
            )
        enterLifeStyleServiceUseCase =
            EnterLifeStyleServiceUseCase(
                lifeStylePresentationService,
                loadInDayToListUseCase,
                calculateBasalMetabolismWithUserBodyInfoUseCase
            )
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동과 현재 사용자의 기초대사량을 가져왔을 때_기초대사량, 활동 대사량, 활동 리스트를 화면에 출력 성공`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val basalMetabolism = slot<Double>()
        val activityMetabolism = slot<Double>()
        val userBodyInfo = UserBodyInfo(
            84.0,
            184.0,
            24,
            Gender.Male
        )
        every {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(lifeStyleList)
        every {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns flowOf(userBodyInfo)

        every {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                capture(basalMetabolism),
                capture(activityMetabolism),
                lifeStyleList
            )
        } returns flowOf(Unit)

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch {
                fail()
            }.collect()
        }

        verifyOrder {
            loadInDayToListUseCase(date)
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                basalMetabolism.captured,
                activityMetabolism.captured,
                lifeStyleList
            )
        }

        verifyOrder {
            calculateBasalMetabolismWithUserBodyInfoUseCase()
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                basalMetabolism.captured,
                activityMetabolism.captured,
                lifeStyleList
            )
        }
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동들이 없을 때_LifeStyle 로드 실패_에러 발생`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val basalMetabolism = slot<Double>()
        val activityMetabolism = slot<Double>()
        val userBodyInfo = UserBodyInfo(
            84.0,
            184.0,
            24,
            Gender.Male
        )
        val expected = DataNotFoundException()

        every {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns callbackFlow {
            close(expected)
        }
        every {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns flowOf(userBodyInfo)
        every {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                capture(basalMetabolism),
                capture(activityMetabolism),
                lifeStyleList
            )
        } returns flowOf(Unit)

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch { cause ->
                assertEquals(expected::class, cause::class)
            }.collect()
        }

        verify {
            loadInDayToListUseCase(date)
        }
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동들을 1초안에 가져오지 못했을 때_LifeStyle 로드 실패_에러 발생`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val basalMetabolism = slot<Double>()
        val activityMetabolism = slot<Double>()
        val userBodyInfo = UserBodyInfo(
            84.0,
            184.0,
            24,
            Gender.Male
        )
        val expected = TimeoutCancellationException::class

        every {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns callbackFlow {
            delay(3000)
            fail()
        }
        every {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns flowOf(userBodyInfo)
        every {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                capture(basalMetabolism),
                capture(activityMetabolism),
                lifeStyleList
            )
        } returns flowOf(Unit)

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch { cause ->
                assertEquals(expected, cause::class)
            }.collect()
        }

        verify {
            loadInDayToListUseCase(date)
        }
    }

    @Test
    fun `현재 사용자 정보를 2초안에 가져오지 못했을 때_사용자 정보 로드 실패_에러 발생`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val basalMetabolism = slot<Double>()
        val activityMetabolism = slot<Double>()
        val expected = TimeoutCancellationException::class

        every {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(lifeStyleList)
        every {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns callbackFlow {
            delay(3000)
            fail()
        }
        every {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                capture(basalMetabolism),
                capture(activityMetabolism),
                lifeStyleList
            )
        } returns flowOf(Unit)

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch { cause ->
                assertEquals(expected, cause::class)
            }.collect()
        }

        verify {
            calculateBasalMetabolismWithUserBodyInfoUseCase()
        }
    }

    @Test
    fun `현재 사용자 정보를 찾지못해 가져오지 못했을 때_사용자 정보 로드 실패_에러 발생`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val basalMetabolism = slot<Double>()
        val activityMetabolism = slot<Double>()
        val expected = DataNotFoundException()

        every {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(lifeStyleList)
        every {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns callbackFlow {
            close(expected)
        }
        every {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                capture(basalMetabolism),
                capture(activityMetabolism),
                lifeStyleList
            )
        } returns flowOf(Unit)

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch { cause ->
                assertEquals(expected::class, cause::class)
            }.collect()
        }

        verify {
            calculateBasalMetabolismWithUserBodyInfoUseCase()
        }
    }

    @Test
    fun `현재 사용자 정보에 음수가 있는 경우_기초대사량 계산 실패_에러 발생`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val basalMetabolism = slot<Double>()
        val activityMetabolism = slot<Double>()
        val userBodyInfo = UserBodyInfo(
            -1.0,
            184.0,
            24,
            Gender.Male
        )
        val expected = IllegalArgumentException()

        every {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(lifeStyleList)
        every {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns flowOf(
            userBodyInfo
        )
        every {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                capture(basalMetabolism),
                capture(activityMetabolism),
                lifeStyleList
            )
        } returns flowOf(Unit)

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch { cause ->
                assertEquals(expected::class, cause::class)
            }.collect()
        }

        verify {
            calculateBasalMetabolismWithUserBodyInfoUseCase()
        }
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동과 현재 사용자의 기초대사량을 가져왔지만_화면에 Show가 실패한 경우_에러 발생`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val basalMetabolism = slot<Double>()
        val activityMetabolism = slot<Double>()
        val userBodyInfo = UserBodyInfo(
            84.0,
            184.0,
            24,
            Gender.Male

        )
        val expected = PresentationFailureException()

        every {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(lifeStyleList)
        every {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns flowOf(userBodyInfo)
        every {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                capture(basalMetabolism),
                capture(activityMetabolism),
                lifeStyleList
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch { cause ->
                assertEquals(expected::class, cause::class)
            }.collect()
        }

        verifyOrder {
            loadInDayToListUseCase(date)
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                basalMetabolism.captured,
                activityMetabolism.captured,
                lifeStyleList
            )
        }

        verifyOrder {
            calculateBasalMetabolismWithUserBodyInfoUseCase()
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                basalMetabolism.captured,
                activityMetabolism.captured,
                lifeStyleList
            )
        }
    }
}
