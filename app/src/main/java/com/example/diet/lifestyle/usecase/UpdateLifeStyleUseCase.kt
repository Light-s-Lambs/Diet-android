package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleRepository

class UpdateLifeStyleUseCase(
    private val repository: LifeStyleRepository
) {
    operator fun invoke(
        from: LifeStyleRequest,
        to: LifeStyleRequest
    ) = repository.updateLifeStyle(from, to)
}
