package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.usecase.dto.LifeStyleRequestDto

class UpdateLifeStyleUseCase(
    private val repository: LifeStyleRepository
) {
    operator fun invoke(
        from: LifeStyleRequestDto,
        to: LifeStyleRequestDto
    ) = repository.update(from, to)
}
