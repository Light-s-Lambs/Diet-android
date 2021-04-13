package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.usecase.dto.LifeStyleRequestDto

class DeleteLifeStyleUseCase(
    private val repository: LifeStyleRepository
) {
    operator fun invoke(
        lifeStyleRequestDto: LifeStyleRequestDto
    ) = repository.delete(lifeStyleRequestDto)
}
