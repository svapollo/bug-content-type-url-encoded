package com.example.calculator.services

import com.example.calculator.models.CalculatorRequest
import com.example.calculator.models.CalculatorResponse

object CalculatorService {

    fun calculate(request: CalculatorRequest): CalculatorResponse {
        val result = when (request.operation.lowercase()) {
            "add", "+" -> request.num1 + request.num2
            "subtract", "-" -> request.num1 - request.num2
            "multiply", "*" -> request.num1 * request.num2
            "divide", "/" -> {
                if (request.num2 == 0.0) {
                    throw IllegalArgumentException("Divisão por zero não é permitida")
                }
                request.num1 / request.num2
            }
            else -> throw IllegalArgumentException("Operação inválida: ${request.operation}")
        }

        return CalculatorResponse(
            email = request.email,
            num1 = request.num1,
            num2 = request.num2,
            operation = request.operation,
            result = result
        )
    }
}

