package com.example.diet.meal.usecase.exception

class ConnectErrorException @JvmOverloads constructor(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)
