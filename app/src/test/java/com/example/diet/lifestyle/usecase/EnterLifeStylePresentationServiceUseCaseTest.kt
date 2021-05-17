package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.Gender
import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.UserInfo
import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.service.LifeStylePresentationService
import com.example.diet.lifestyle.service.UserInfoService
import com.example.diet.lifestyle.usecase.exception.DataNotFoundException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
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
    lateinit var userInfoService: UserInfoService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadInDayToListUseCase = LoadLifeStyleInDayToListUseCase(lifeStyleRepository)
        enterLifeStyleServiceUseCase =
            EnterLifeStyleServiceUseCase(
                lifeStylePresentationService,
                userInfoService,
                loadInDayToListUseCase,
                calculateBasalMetabolismUseCase
            )
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동들이 있고 사용자 정보를 가져왔을 때_기초대사량, 활동 대사량, 활동 리스트를 화면에 출력 성공`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val basalMetabolism = 1979.2
        val activityMetabolism = 3837.2

        coEvery {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                basalMetabolism,
                activityMetabolism,
                lifeStyleList
            )
        } returns flowOf(Unit)

        coEvery {
            userInfoService.getCurrentUserInfo()
        } returns flowOf(
            UserInfo(
                84.0,
                184.0,
                24,
                Gender.Male
            )
        )

        coEvery {
            lifeStyleRepository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(lifeStyleList)

        runBlocking {
            enterLifeStyleServiceUseCase(
                date
            ).catch {
                fail()
            }.collect {
            }
        }
    }

    @Test
    fun `사용자 정보는 가져왔지만 지정된 날짜에 저장된 기존 활동들이 없을 때_기초대사량, 활동 대사량, 활동 리스트를 화면에 출력 실패`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val basalMetabolism = 1979.2
        val activityMetabolism = 3837.2
        val expected = DataNotFoundException()

        coEvery {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                basalMetabolism,
                activityMetabolism,
                lifeStyleList
            )
        } returns flowOf(Unit)

        coEvery {
            userInfoService.getCurrentUserInfo()
        } returns flowOf(
            UserInfo(
                84.0,
                184.0,
                24,
                Gender.Male
            )
        )

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
                assertEquals(cause::class, expected::class)
            }.collect {
                fail()
            }
        }
    }
}
