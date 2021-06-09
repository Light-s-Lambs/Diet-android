package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.Gender
import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.UserBodyInfo
import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.repository.UserBodyInfoRepository
import com.example.diet.lifestyle.service.LifeStylePresentationService
import com.example.diet.lifestyle.usecase.exception.DataNotFoundException
import com.example.diet.lifestyle.usecase.exception.PresentationFailureException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

@kotlinx.coroutines.ExperimentalCoroutinesApi
@kotlinx.coroutines.FlowPreview
class EnterLifeStylePresentationServiceUseCaseTest {
    private val calculateBasalMetabolismUseCase = CalculateBasalMetabolismUseCase()

    lateinit var enterLifeStyleServiceUseCase: EnterLifeStyleServiceUseCase
    lateinit var loadInDayToListUseCase: LoadLifeStyleInDayToListUseCase

    @MockK
    lateinit var lifeStyleRepository: LifeStyleRepository

    @MockK
    lateinit var lifeStylePresentationService: LifeStylePresentationService

    @MockK
    lateinit var userBodyInfoRepository: UserBodyInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadInDayToListUseCase = LoadLifeStyleInDayToListUseCase(lifeStyleRepository)
        enterLifeStyleServiceUseCase =
            EnterLifeStyleServiceUseCase(
                lifeStylePresentationService,
                userBodyInfoRepository,
                loadInDayToListUseCase,
                calculateBasalMetabolismUseCase
            )
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동들이 있으며_사용자 정보를 가져왔을 때_기초대사량 계산을 성공하면_기초대사량, 활동 대사량, 활동 리스트를 화면에 출력 성공`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val basalMetabolism = 1979.2
        val activityMetabolism = 3837.2
        val expected = true

        coEvery {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(lifeStyleList)
        coEvery {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns flowOf(
            UserBodyInfo(
                84.0,
                184.0,
                24,
                Gender.Male
            )
        )
        coEvery {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                basalMetabolism,
                activityMetabolism,
                lifeStyleList
            )
        } returns flowOf(expected)

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch {
                fail()
            }.collect { actual ->
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동들이 없을 때_LifeStyle 로드 실패_에러 발생`() {
        val date = DateTime.now()
        val expected = DataNotFoundException()

        coEvery {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch { cause ->
                assertEquals(expected::class, cause::class)
            }.collect {
                fail()
            }
        }
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동들을 1초안에 가져오지 못했을 때_LifeStyle 로드 실패_에러 발생`() {
        val date = DateTime.now()
        val expected = TimeoutCancellationException::class

        coEvery {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns callbackFlow {
            delay(3000)
            fail()
        }

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch { cause ->
                assertEquals(expected, cause::class)
            }.collect {
                fail()
            }
        }
    }
    
    @Test
    fun `지정된 날짜에 저장된 기존 활동들이 있지만_사용자 정보를 2초안에 가져오지 못했을 때_사용자 정보 로드 실패_에러 발생`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val expected = TimeoutCancellationException::class

        coEvery {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(lifeStyleList)
        coEvery {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns callbackFlow {
            delay(3000)
            fail()
        }

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch { cause ->
                assertEquals(expected, cause::class)
            }.collect {
                fail()
            }
        }
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동들이 있지만_사용자 정보를 찾지못해 가져오지 못했을 때_사용자 정보 로드 실패_에러 발생`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val expected = DataNotFoundException()

        coEvery {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(lifeStyleList)
        coEvery {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch { cause ->
                assertEquals(expected::class, cause::class)
            }.collect {
                fail()
            }
        }
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동들이 있으며_사용자 정보를 가져왔지만_사용자 정보에 음수가 있는 경우_기초대사량 계산 실패_에러 발생`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val expected = IllegalArgumentException()

        coEvery {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(lifeStyleList)
        coEvery {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns flowOf(
            UserBodyInfo(
                -1.0,
                184.0,
                24,
                Gender.Male
            )
        )

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch { cause ->
                assertEquals(expected::class, cause::class)
            }.collect {
                fail()
            }
        }
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동들이 있으며_사용자 정보를 가져왔을 때_기초대사량 계산을 성공하였지만_화면에 Show가 실패한 경우_에러 발생`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val basalMetabolism = 1979.2
        val activityMetabolism = 3837.2
        val expected = PresentationFailureException()

        coEvery {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(lifeStyleList)
        coEvery {
            userBodyInfoRepository.getCurrentUserBodyInfo()
        } returns flowOf(
            UserBodyInfo(
                84.0,
                184.0,
                24,
                Gender.Male
            )
        )
        coEvery {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                basalMetabolism,
                activityMetabolism,
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
            }.collect {
                fail()
            }
        }
    }
}