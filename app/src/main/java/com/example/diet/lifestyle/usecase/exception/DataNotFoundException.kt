package com.example.diet.lifestyle.usecase.exception

class DataNotFoundException @JvmOverloads constructor(
    message: String?= null,
    cause: Throwable?= null
) : Exception(message, cause)
