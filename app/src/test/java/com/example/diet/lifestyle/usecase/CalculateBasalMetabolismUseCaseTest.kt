package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.Gender
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CalculateBasalMetabolismUseCaseTest {

    val calculateBasalMetabolismUseCase = CalculateBasalMetabolismUseCase()

    @Test
    fun `남성 사용자가 입력한 체중 신장 나이가 모두 음수가 아닐경우_기초대사랑을 계산함`() {
        val expected = 1979.2

        val result = calculateBasalMetabolismUseCase(84.0, 184.0, 24, Gender.male)

        assertEquals(expected, result)
    }

    @Test
    fun `여성 사용자가 입력한 체중 신장 나이가 모두 음수가 아닐경우_기초대사랑을 계산함`() {
        val expected = 1686.2

        val result = calculateBasalMetabolismUseCase(84.0, 184.0, 24, Gender.female)

        assertEquals(expected, result)
    }

    @Test
    fun `남성 사용자가 입력한 체중이 음수인 경우 경우_에러 출력`() {
        val expected = IllegalArgumentException()

        try {
            calculateBasalMetabolismUseCase(-84.0, 184.0, 24, Gender.male)
        } catch (e: Exception) {
            assertEquals(expected::class, e::class)
        }
    }

    @Test
    fun `남성 사용자가 입력한 신장이 음수인 경우 경우_에러 출력`() {
        val expected = IllegalArgumentException()

        try {
            calculateBasalMetabolismUseCase(84.0, -184.0, 24, Gender.male)
        } catch (e: Exception) {
            assertEquals(expected::class, e::class)
        }
    }

    @Test
    fun `남성 사용자가 입력한 나이가 음수인 경우 경우_에러 출력`() {
        val expected = IllegalArgumentException()

        try {
            calculateBasalMetabolismUseCase(84.0, 184.0, -24, Gender.male)
        } catch (e: Exception) {
            assertEquals(expected::class, e::class)
        }
    }
}
