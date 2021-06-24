package com.example.diet.weightComposition.usecase

import com.example.diet.weightComposition.model.WeightComposition
import com.example.diet.weightComposition.repository.WeightCompositionRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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

class CreateWeightCompositionUseCaseTest {
    lateinit var createUseCase: CreateWeightCompositionUseCase

    @MockK
    lateinit var repository: WeightCompositionRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        createUseCase = CreateWeightCompositionUseCase(repository)
    }

    @Test
    fun `사용자가 입력한 정보로 만든 WeightComposition 내용이 저장된 활동 정보와 동일하지 않음_활동 생성 성공`() {
        val date = DateTime.now()
        val weightCompositionRequest =
            WeightCompositionRequest(
                date,
                84.0,
                24.0,
                41.0
            )
        val weightComposition = WeightComposition(
            weightCompositionRequest.date,
            weightCompositionRequest.weight,
            weightCompositionRequest.percentageOfBodyFat,
            weightCompositionRequest.amountOfMuscle
        )
        val expected = weightComposition
        coEvery {
            repository.createWeightComposition(
                weightCompositionRequest
            )
        } returns flowOf(expected)

        runBlocking {
            createUseCase(
                weightCompositionRequest
            ).catch {
                fail()
            }.collect {
                assertEquals(expected, it)
            }
        }
    }
}
