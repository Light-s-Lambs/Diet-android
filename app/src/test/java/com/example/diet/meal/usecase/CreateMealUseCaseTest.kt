package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealName
import com.example.diet.meal.model.MealType
import com.example.diet.meal.repository.MealRepository
import com.example.diet.meal.usecase.exception.ConnectionErrorException
import com.example.diet.meal.usecase.exception.DataAlreadyExIstException
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
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class CreateMealUseCaseTest {
    lateinit var useCase: CreateMealUseCase

    @MockK
    lateinit var repository: MealRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = CreateMealUseCase(repository)
    }

    @Test
    fun `객체가 없고 생성 성공`() {
        val date = DateTime.now()
        val expected = Meal(
            date,
            MealType.Breakfast,
            MealName.Toast,
            "313"
        )
        every { repository.create(date, MealType.Breakfast, MealName.Toast, "313") } returns flowOf(
            expected
        )

        runBlocking {
            useCase(date, MealType.Breakfast, MealName.Toast, "313")
                .catch { fail() }
                .collect { assertEquals(expected, it) }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `연결 실패로 인해 생성 실패`() {
        val expected = ConnectionErrorException()
        val date = DateTime.now()
        every {
            repository.create(
                date,
                MealType.Breakfast,
                MealName.Toast,
                "313"
            )
        } returns callbackFlow { close(expected) }

        runBlocking {
            useCase(date, MealType.Breakfast, MealName.Toast, "313")
                .catch { assertEquals(expected::class, it::class) }
                .collect { fail() }
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `객체가 이미 있어서 생성 실패`() {
        val expected = DataAlreadyExIstException()
        val date = DateTime.now()
        every {
            repository.create(
                date,
                MealType.Breakfast,
                MealName.Toast,
                "313"
            )
        } returns callbackFlow { close(expected) }

        runBlocking {
            useCase(date, MealType.Breakfast, MealName.Toast, "313")
                .catch { assertEquals(expected::class, it::class) }
                .collect { fail() }
        }
    }
}
