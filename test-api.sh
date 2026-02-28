#!/bin/bash

# Script para testar a API de Calculadora com curl
# Certifique-se de que o servidor está rodando em http://127.0.0.1:8080

echo "=== Testando API de Calculadora ==="
echo ""

# Teste 1: Adição
echo "1. Teste de Adição (10 + 5 = 15)"
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=user@example.com&num1=10&num2=5&operation=add"
echo -e "\n"

# Teste 2: Subtração
echo "2. Teste de Subtração (20 - 8 = 12)"
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=test@gmail.com&num1=20&num2=8&operation=subtract"
echo -e "\n"

# Teste 3: Multiplicação
echo "3. Teste de Multiplicação (6 * 7 = 42)"
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=calc@test.com&num1=6&num2=7&operation=multiply"
echo -e "\n"

# Teste 4: Divisão
echo "4. Teste de Divisão (100 / 4 = 25)"
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=div@test.com&num1=100&num2=4&operation=divide"
echo -e "\n"

# Teste 5: Usando símbolo (/)
echo "5. Teste com Símbolo (15 / 3 = 5)"
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=sym@test.com&num1=15&num2=3&operation=/"
echo -e "\n"

# Teste 6: Divisão por Zero (Erro esperado)
echo "6. Teste de Divisão por Zero (Erro esperado)"
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=div@test.com&num1=10&num2=0&operation=divide"
echo -e "\n"

# Teste 7: Email Inválido (Erro esperado)
echo "7. Teste com Email Inválido (Erro esperado)"
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=invalid-email&num1=10&num2=5&operation=add"
echo -e "\n"

# Teste 8: Operação Inválida (Erro esperado)
echo "8. Teste com Operação Inválida (Erro esperado)"
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=user@example.com&num1=10&num2=5&operation=invalid"
echo -e "\n"

# Teste 9: Parâmetro Faltante (Erro esperado)
echo "9. Teste sem Email (Erro esperado)"
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "num1=10&num2=5&operation=add"
echo -e "\n"

# Teste 10: GET na raiz
echo "10. Teste GET na Raiz"
curl http://127.0.0.1:8080/
echo -e "\n\n=== Testes Concluídos ==="

