package com.example.diet.meal.usecase.exception

class ConnectionErrorException @JvmOverloads constructor(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)
