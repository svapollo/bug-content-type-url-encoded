package com.example.calculator.controller

import com.example.calculator.models.CalculatorRequest
import com.example.calculator.models.ErrorResponse
import com.example.calculator.services.CalculatorService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.regex.Pattern
import kotlin.text.matches

@RestController
class CalculatorController {

    @GetMapping("/")
    fun root(): String {
        return "API de Calculadora - Use POST /calculate"
    }

    @PostMapping(
        "/calculate",
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE]
    )
    fun calculate(
        @RequestParam email: String?,
        @RequestParam num1: String?,
        @RequestParam num2: String?,
        @RequestParam operation: String?
    ): ResponseEntity<Any> {
        try {
            println("Email recebido: [$email]")
            // Validar email
            if (email.isNullOrBlank()) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse(message = "Email é obrigatório"))
            }

            if (!isValidEmail(email)) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse(message = "Email inválido"))
            }

            // Validar num1
            if (num1.isNullOrBlank()) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse(message = "num1 é obrigatório"))
            }

            val number1 = num1.toDoubleOrNull()
            if (number1 == null) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse(message = "num1 deve ser um número válido"))
            }

            // Validar num2
            if (num2.isNullOrBlank()) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse(message = "num2 é obrigatório"))
            }

            val number2 = num2.toDoubleOrNull()
            if (number2 == null) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse(message = "num2 deve ser um número válido"))
            }

            // Validar operation
            if (operation.isNullOrBlank()) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse(message = "operation é obrigatória"))
            }

            // Criar request e calcular
            val request = CalculatorRequest(
                email = email.trim(),
                num1 = number1,
                num2 = number2,
                operation = operation.trim()
            )

            val response = CalculatorService.calculate(request)
            return ResponseEntity.ok(response)

        } catch (e: IllegalArgumentException) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse(message = e.message ?: "Erro desconhecido"))
        } catch (e: Exception) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse(message = "Erro interno do servidor: ${e.message}"))
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        )
        return emailPattern.matcher(email).matches()
    }
}

