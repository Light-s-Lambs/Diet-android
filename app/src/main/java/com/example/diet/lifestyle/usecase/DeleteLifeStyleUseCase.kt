package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleRepository

class DeleteLifeStyleUseCase(
    private val repository: LifeStyleRepository
) {
    operator fun invoke(
        lifeStyleRequest: LifeStyleRequest
    ) = repository.deleteLifeStyle(lifeStyleRequest)
}
