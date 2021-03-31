package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealName
import com.example.diet.meal.model.MealType
import com.example.diet.meal.repository.MealRepository
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
    fun testInvoke_whenCreateSuccess_returnMeal() {
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
    fun testInvoke_whenCreateFailed_returnFalse() {
        val expected = IllegalStateException()
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
                .catch { assertEquals(expected, it) }
                .collect { fail() }
        }
    }
}
