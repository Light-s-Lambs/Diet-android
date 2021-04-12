package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleRepository

class CreateLifeStyleUseCase(
    private val repository: LifeStyleRepository
) {
    operator fun invoke(
        lifeStyleRequestDto: LifeStyleRequestDto
    ) = repository.create(lifeStyleRequestDto)
}
