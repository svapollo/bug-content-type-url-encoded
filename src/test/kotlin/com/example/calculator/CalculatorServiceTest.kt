package com.example.calculator

import org.junit.jupiter.api.Test
import com.example.calculator.services.CalculatorService
import com.example.calculator.models.CalculatorRequest
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CalculatorServiceTest {

    @Test
    fun testAddition() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = 10.0,
            num2 = 5.0,
            operation = "add"
        )

        val response = CalculatorService.calculate(request)
        assertEquals(15.0, response.result)
        assertEquals("success", response.status)
    }

    @Test
    fun testAdditionWithSymbol() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = 10.0,
            num2 = 5.0,
            operation = "+"
        )

        val response = CalculatorService.calculate(request)
        assertEquals(15.0, response.result)
    }

    @Test
    fun testSubtraction() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = 20.0,
            num2 = 8.0,
            operation = "subtract"
        )

        val response = CalculatorService.calculate(request)
        assertEquals(12.0, response.result)
    }

    @Test
    fun testSubtractionWithSymbol() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = 20.0,
            num2 = 8.0,
            operation = "-"
        )

        val response = CalculatorService.calculate(request)
        assertEquals(12.0, response.result)
    }

    @Test
    fun testMultiplication() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = 6.0,
            num2 = 7.0,
            operation = "multiply"
        )

        val response = CalculatorService.calculate(request)
        assertEquals(42.0, response.result)
    }

    @Test
    fun testMultiplicationWithSymbol() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = 6.0,
            num2 = 7.0,
            operation = "*"
        )

        val response = CalculatorService.calculate(request)
        assertEquals(42.0, response.result)
    }

    @Test
    fun testDivision() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = 100.0,
            num2 = 4.0,
            operation = "divide"
        )

        val response = CalculatorService.calculate(request)
        assertEquals(25.0, response.result)
    }

    @Test
    fun testDivisionWithSymbol() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = 100.0,
            num2 = 4.0,
            operation = "/"
        )

        val response = CalculatorService.calculate(request)
        assertEquals(25.0, response.result)
    }

    @Test
    fun testDivisionByZero() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = 10.0,
            num2 = 0.0,
            operation = "divide"
        )

        val exception = assertFailsWith<IllegalArgumentException> {
            CalculatorService.calculate(request)
        }

        assertEquals("Divisão por zero não é permitida", exception.message)
    }

    @Test
    fun testInvalidOperation() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = 10.0,
            num2 = 5.0,
            operation = "invalid"
        )

        val exception = assertFailsWith<IllegalArgumentException> {
            CalculatorService.calculate(request)
        }

        assertEquals("Operação inválida: invalid", exception.message)
    }

    @Test
    fun testNegativeNumbers() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = -10.0,
            num2 = 5.0,
            operation = "add"
        )

        val response = CalculatorService.calculate(request)
        assertEquals(-5.0, response.result)
    }

    @Test
    fun testDecimalNumbers() {
        val request = CalculatorRequest(
            email = "test@example.com",
            num1 = 10.5,
            num2 = 5.5,
            operation = "add"
        )

        val response = CalculatorService.calculate(request)
        assertEquals(16.0, response.result)
    }

    @Test
    fun testResponseDataIntegrity() {
        val request = CalculatorRequest(
            email = "user@test.com",
            num1 = 7.0,
            num2 = 3.0,
            operation = "multiply"
        )

        val response = CalculatorService.calculate(request)

        assertEquals("user@test.com", response.email)
        assertEquals(7.0, response.num1)
        assertEquals(3.0, response.num2)
        assertEquals("multiply", response.operation)
        assertEquals(21.0, response.result)
        assertEquals("success", response.status)
    }
}

