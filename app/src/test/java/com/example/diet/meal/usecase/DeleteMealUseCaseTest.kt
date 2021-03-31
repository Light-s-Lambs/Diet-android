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

class DeleteMealUseCaseTest {
    lateinit var useCase: DeleteMealUseCase

    @MockK
    lateinit var repository: MealRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = DeleteMealUseCase(repository)
    }

    @Test
    fun testInvoke_whenDeleteSuccess_returnUnit() {
        val date = DateTime.now()
        val targetObject = Meal(
            date,
            MealType.Breakfast,
            MealName.Toast,
            "313"
        )
        every { repository.delete(targetObject) } returns flowOf(Unit)

        runBlocking {
            useCase(targetObject)
                .catch { Assert.fail() }
                .collect { Assert.assertEquals(Unit, it) }
        }
    }

}
