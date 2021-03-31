package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealName
import com.example.diet.meal.model.MealType
import com.example.diet.meal.repository.MealRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
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
    fun testInvoke_whenUpdateSuccess_returnMeal() {
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
        every { repository.update(targetObject, expected) } returns flowOf(expected)

        runBlocking {
            useCase(targetObject, expected)
                .catch { Assert.fail() }
                .collect { Assert.assertEquals(expected, it) }
        }
    }
}
