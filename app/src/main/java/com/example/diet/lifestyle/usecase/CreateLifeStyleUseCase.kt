package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleRepository

class CreateLifeStyleUseCase(
    private val repository: LifeStyleRepository
) {
    operator fun invoke(
        lifeStyleRequest: LifeStyleRequest
    ) = repository.createLifeStyle(lifeStyleRequest)
}
