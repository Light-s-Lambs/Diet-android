package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealName
import com.example.diet.meal.model.MealType
import com.example.diet.meal.repository.MealRepository
import com.example.diet.meal.usecase.exception.ConnectionErrorException
import com.example.diet.meal.usecase.exception.DataAlreadyExIstException
import com.example.diet.meal.usecase.exception.DataNotFoundException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UpdateMealUseCaseTest {
    lateinit var useCase: UpdateMealUseCase

    @MockK
    lateinit var repository: MealRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = UpdateMealUseCase(repository)
    }

    @Test
    fun `선택된 객체를 입력 데이터로 변경`() {
        val date = DateTime.now()
        val targetObject = Meal(
            date,
            MealType.Breakfast,
            MealName.Toast,
            "313"
        )
        val expected = Meal(
            date,
            MealType.Lunch,
            MealName.Noodle,
            "495"
        )
        every {
            repository.update(
                targetObject,
                date,
                MealType.Lunch,
                MealName.Noodle,
                "495"
            )
        } returns flowOf(expected)

        runBlocking {
            useCase(targetObject, date, MealType.Lunch, MealName.Noodle, "495")
                .catch { Assert.fail() }
                .collect { Assert.assertEquals(expected, it) }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `연결 실패로 인해 변경 실패`() {
        val expected = ConnectionErrorException()
        val date = DateTime.now()
        val targetObject = Meal(
            date,
            MealType.Breakfast,
            MealName.Toast,
            "313"
        )
        every {
            repository.update(
                targetObject,
                date,
                MealType.Lunch,
                MealName.Noodle,
                "495"
            )
        } returns callbackFlow {
            close(
                expected
            )
        }

        runBlocking {
            useCase(targetObject, date, MealType.Lunch, MealName.Noodle, "495")
                .catch { Assert.assertEquals(expected::class, it::class) }
                .collect { Assert.fail() }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `입력한 식단과 동일한 메뉴가 동일한 식사시간에 있음`() {
        val expected = DataAlreadyExIstException()
        val date = DateTime.now()
        val targetObject = Meal(
            date,
            MealType.Breakfast,
            MealName.Toast,
            "313"
        )
        every {
            repository.update(
                targetObject,
                date,
                MealType.Lunch,
                MealName.Noodle,
                "495"
            )
        } returns callbackFlow {
            close(
                expected
            )
        }

        runBlocking {
            useCase(targetObject, date, MealType.Lunch, MealName.Noodle, "495")
                .catch { Assert.assertEquals(expected::class, it::class) }
                .collect { Assert.fail() }
        }
    }
}
