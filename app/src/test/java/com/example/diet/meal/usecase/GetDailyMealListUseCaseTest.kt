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
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class GetDailyMealListUseCaseTest {
    lateinit var useCase: GetDailyListUseCase

    @MockK
    lateinit var repository: MealRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetDailyListUseCase(repository)
    }

    @Test
    fun testInvoke_whenGetListSuccess_returnMealList() {
        val expected = mutableListOf<Meal>()
        val date = DateTime.now()
        expected.add(
            Meal(
                date,
                MealType.Breakfast,
                MealName.Toast,
                "313"
            )
        )
        expected.add(
            Meal(
                date,
                MealType.Lunch,
                MealName.Noodle,
                "495"
            )
        )
        every { repository.getDailyList(date) } returns flowOf(expected)

        runBlocking {
            useCase(date)
                .catch { fail() }
                .collect { assertEquals(expected, it) }
        }
    }

}
