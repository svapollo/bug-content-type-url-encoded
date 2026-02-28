package com.example.calculator.models

data class ErrorResponse(
    val status: String = "error",
    val message: String
)

