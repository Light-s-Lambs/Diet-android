package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealName
import com.example.diet.meal.model.MealType
import com.example.diet.meal.repository.MealRepository
import com.example.diet.meal.usecase.exception.ConnectErrorException
import com.example.diet.meal.usecase.exception.DataAlreadyExistException
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
    fun `선택된 객체를 새로 입력받은 데이터로 변경 성공`() {
        val date = DateTime.now()
        val fromMealType = MealType.Breakfast
        val fromMealName = MealName.Toast
        val fromCalorie = "313"
        val toMealType = MealType.Lunch
        val toMealName = MealName.Noodle
        val toCalorie = "495"
        val targetObject = Meal(date, fromMealType, fromMealName, fromCalorie)
        val expected = Meal(date, toMealType, toMealName, toCalorie)
        every {
            repository.update(targetObject, date, toMealType, toMealName, toCalorie)
        } returns flowOf(expected)

        runBlocking {
            useCase(targetObject, date, toMealType, toMealName, toCalorie)
                .catch { Assert.fail() }
                .collect { Assert.assertEquals(expected, it) }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `연결 실패로 인해 변경 실패`() {
        val expected = ConnectErrorException()
        val date = DateTime.now()
        val fromMealType = MealType.Breakfast
        val fromMealName = MealName.Toast
        val fromCalorie = "313"
        val toMealType = MealType.Lunch
        val toMealName = MealName.Noodle
        val toCalorie = "495"
        val targetObject = Meal(date, fromMealType, fromMealName, fromCalorie)
        every {
            repository.update(targetObject, date, toMealType, toMealName, toCalorie)
        } returns callbackFlow { close(expected) }

        runBlocking {
            useCase(targetObject, date, toMealType, toMealName, toCalorie)
                .catch { Assert.assertEquals(expected::class, it::class) }
                .collect { Assert.fail() }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `변경하고자 하는 식단과 동일한 메뉴가 동일한 식사시간에 있어서 변경 실패`() {
        val expected = DataAlreadyExistException()
        val date = DateTime.now()
        val fromMealType = MealType.Breakfast
        val fromMealName = MealName.Toast
        val fromCalorie = "313"
        val toMealType = MealType.Lunch
        val toMealName = MealName.Noodle
        val toCalorie = "495"
        val targetObject = Meal(date, fromMealType, fromMealName, fromCalorie)
        every {
            repository.update(targetObject, date, toMealType, toMealName, toCalorie)
        } returns callbackFlow { close(expected) }

        runBlocking {
            useCase(targetObject, date, toMealType, toMealName, toCalorie)
                .catch { Assert.assertEquals(expected::class, it::class) }
                .collect { Assert.fail() }
        }
    }
}
