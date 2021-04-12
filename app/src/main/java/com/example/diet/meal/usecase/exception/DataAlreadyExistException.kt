package com.example.diet.meal.usecase.exception

class DataAlreadyExistException @JvmOverloads constructor(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)
