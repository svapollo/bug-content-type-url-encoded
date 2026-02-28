package com.example.calculator.models

data class CalculatorRequest(
    val email: String,
    val num1: Double,
    val num2: Double,
    val operation: String // "add", "subtract", "multiply", "divide"
)

