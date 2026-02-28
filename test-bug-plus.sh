#!/bin/bash

# Script para demonstrar o bug do '+' em application/x-www-form-urlencoded
# Referência: https://github.com/w3c/web-share-target/issues/59

echo "========================================"
echo "Teste do Bug: '+' vira espaço"
echo "========================================"
echo ""

BASE_URL="http://127.0.0.1:8080"

echo "🔴 Teste 1: Bug - Email com '+' (DEVE FALHAR)"
echo "Enviando: usuario+tag@gmail.com"
echo "Comando: curl -X POST $BASE_URL/calculate -H 'Content-Type: application/x-www-form-urlencoded' -d 'email=usuario+tag@gmail.com&num1=10&num2=5&operation=add'"
echo ""
RESPONSE=$(curl -s -X POST "$BASE_URL/calculate" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=usuario+tag@gmail.com&num1=10&num2=5&operation=add")
echo "Resposta:"
echo "$RESPONSE" | jq . 2>/dev/null || echo "$RESPONSE"
echo ""
echo "⚠️  O servidor recebeu 'usuario tag@gmail.com' (com espaço)"
echo "⚠️  A validação rejeitou porque emails não podem ter espaços"
echo ""

echo "----------------------------------------"
echo ""

echo "✅ Teste 2: Solução - Email com '%2B' (DEVE FUNCIONAR)"
echo "Enviando: usuario%2Btag@gmail.com"
echo "Comando: curl -X POST $BASE_URL/calculate -H 'Content-Type: application/x-www-form-urlencoded' -d 'email=usuario%2Btag@gmail.com&num1=10&num2=5&operation=add'"
echo ""
RESPONSE=$(curl -s -X POST "$BASE_URL/calculate" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=usuario%2Btag@gmail.com&num1=10&num2=5&operation=add")
echo "Resposta:"
echo "$RESPONSE" | jq . 2>/dev/null || echo "$RESPONSE"
echo ""
echo "✅ O servidor recebeu 'usuario+tag@gmail.com' (com '+')"
echo "✅ A validação aceitou o email"
echo ""

echo "========================================"
echo "Conclusão:"
echo "========================================"
echo "Em application/x-www-form-urlencoded:"
echo "  • O caractere '+' é reservado e significa espaço"
echo "  • Para enviar '+' literal, use '%2B' (URL encoding)"
echo "  • Este bug é comum em APIs que não fazem encoding correto"
echo ""
echo "Referência: https://github.com/w3c/web-share-target/issues/59"
echo "========================================"

