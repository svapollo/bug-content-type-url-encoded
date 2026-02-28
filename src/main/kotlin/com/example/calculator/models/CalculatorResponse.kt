package com.example.calculator.models

data class CalculatorResponse(
    val email: String,
    val num1: Double,
    val num2: Double,
    val operation: String,
    val result: Double,
    val status: String = "success"
)

