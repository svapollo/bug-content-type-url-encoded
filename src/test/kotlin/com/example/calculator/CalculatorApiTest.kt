package com.example.calculator

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorApiTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testCalculateAddition() {
        mockMvc.perform(
            post("/calculate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "user@example.com")
                .param("num1", "10")
                .param("num2", "5")
                .param("operation", "add")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.result").value(15.0))
            .andExpect(jsonPath("$.email").value("user@example.com"))
            .andExpect(jsonPath("$.status").value("success"))
    }

    @Test
    fun testCalculateSubtraction() {
        mockMvc.perform(
            post("/calculate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "test@gmail.com")
                .param("num1", "20")
                .param("num2", "8")
                .param("operation", "subtract")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.result").value(12.0))
    }

    @Test
    fun testCalculateMultiplication() {
        mockMvc.perform(
            post("/calculate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "calc@test.com")
                .param("num1", "6")
                .param("num2", "7")
                .param("operation", "multiply")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.result").value(42.0))
    }

    @Test
    fun testCalculateDivision() {
        mockMvc.perform(
            post("/calculate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "div@test.com")
                .param("num1", "100")
                .param("num2", "4")
                .param("operation", "divide")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.result").value(25.0))
    }

    @Test
    fun testCalculateDivisionByZero() {
        mockMvc.perform(
            post("/calculate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "div@test.com")
                .param("num1", "10")
                .param("num2", "0")
                .param("operation", "divide")
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("Divisão por zero não é permitida"))
    }

    @Test
    fun testMissingEmail() {
        mockMvc.perform(
            post("/calculate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("num1", "10")
                .param("num2", "5")
                .param("operation", "add")
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("Email é obrigatório"))
    }

    @Test
    fun testMissingNum1() {
        mockMvc.perform(
            post("/calculate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "user@example.com")
                .param("num2", "5")
                .param("operation", "add")
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("num1 é obrigatório"))
    }

    @Test
    fun testInvalidEmail() {
        mockMvc.perform(
            post("/calculate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "invalid-email")
                .param("num1", "10")
                .param("num2", "5")
                .param("operation", "add")
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("Email inválido"))
    }

    @Test
    fun testInvalidOperation() {
        mockMvc.perform(
            post("/calculate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "user@example.com")
                .param("num1", "10")
                .param("num2", "5")
                .param("operation", "invalid")
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("Operação inválida: invalid"))
    }

    @Test
    fun testGetRoot() {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(content().string("API de Calculadora - Use POST /calculate"))
    }

    @Test
    fun testOperationSymbols() {
        mockMvc.perform(
            post("/calculate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "sym@test.com")
                .param("num1", "15")
                .param("num2", "3")
                .param("operation", "/")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.result").value(5.0))
    }
}

