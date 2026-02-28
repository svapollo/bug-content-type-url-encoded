# API de Calculadora em Kotlin

Uma simples API REST desenvolvida em **Kotlin** usando **Spring Boot**. 
A API fornece funcionalidades de calculadora básica (adição, subtração, multiplicação e divisão) com validação de email.

## Objetivo do Projeto

Este projeto demonstra um **bug comum** no uso de `application/x-www-form-urlencoded`: 
a conversão automática do caractere `+` em espaço ` ` durante a decodificação de URL.

**Cenário do bug:**
- Quando um email contendo `+` (ex: `usuario+tag@gmail.com`) é enviado via `application/x-www-form-urlencoded`
- O servidor Spring Boot decodifica automaticamente o `+` para espaço
- O email recebido fica como `usuario tag@gmail.com` (com espaço)
- A validação de email falha, pois emails não podem conter espaços segundo RFC 5322

**Como funciona:**
- No encoding `application/x-www-form-urlencoded`, o `+` é um caractere reservado que representa espaço
- Para enviar um `+` literal, é necessário usar `%2B` (URL encoding)
- Este é um problema real encontrado em produção e discutido no [GitHub Issue #59](https://github.com/w3c/web-share-target/issues/59)

**Exemplos práticos:**

| Envio no Cliente | Decodificado no Servidor | Resultado |
|------------------|---------------------------|-----------|
| `usuario+tag@gmail.com` | `usuario tag@gmail.com` | ❌ Inválido (espaço) |
| `usuario%2Btag@gmail.com` | `usuario+tag@gmail.com` | ✅ Válido |


## Características

- ✅ API REST simples e intuitiva com **Spring Boot**
- ✅ Aceita requisições com `application/x-www-form-urlencoded`
- ✅ Validação de email
- ✅ Tratamento de erros robusto
- ✅ Suporte a múltiplas formas de operação (nomes e símbolos)
- ✅ Testes unitários completos com Spring Boot Test
- ✅ Documentação com exemplos de curl e Insomnia

## Instalação e Execução

**Compile e instale:**
```bash
mvn clean install
```

**Inicie o servidor:**
```bash
mvn spring-boot:run
```

O servidor iniciará em `http://127.0.0.1:8080`

**Execute os testes:**
```bash
mvn test
```

**Teste o bug do '+' especificamente:**
```bash
bash test-bug-plus.sh
```

## Rotas disponíveis

### GET /
Retorna uma mensagem de boas-vindas.

**Resposta:**
```
API de Calculadora - Use POST /calculate
```

### POST /calculate
Realiza uma operação matemática.

**Content-Type:** `application/x-www-form-urlencoded`

**Parâmetros (obrigatórios):**
- `email` (string): Email válido (ex: usuario@example.com)
- `num1` (double): Primeiro número
- `num2` (double): Segundo número
- `operation` (string): Operação a realizar

**Operações suportadas:**
- `add` ou `+` - Adição
- `subtract` ou `-` - Subtração
- `multiply` ou `*` - Multiplicação
- `divide` ou `/` - Divisão

## Exemplos de Uso

### Replicando o Bug do '+' em Emails

#### ❌ Exemplo 1: Bug - Email com '+' vira espaço (FALHA)
```bash
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=usuario+tag@gmail.com&num1=10&num2=5&operation=add"
```

**O que acontece:**
- O servidor recebe: `usuario tag@gmail.com` (com espaço)
- Log no console: `Email recebido: [usuario tag@gmail.com]`

**Resposta (400 Bad Request):**
```json
{
  "status": "error",
  "message": "Email inválido"
}
```

**Por quê?** O `+` é decodificado para espaço, e a regex de validação não aceita espaços em emails.

#### ✅ Exemplo 2: Solução - Usar %2B para '+' literal (SUCESSO)
```bash
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=usuario%2Btag@gmail.com&num1=10&num2=5&operation=add"
```

**O que acontece:**
- O servidor recebe: `usuario+tag@gmail.com` (com `+` literal)
- Log no console: `Email recebido: [usuario+tag@gmail.com]`

**Resposta (200 OK):**
```json
{
  "email": "usuario+tag@gmail.com",
  "num1": 10.0,
  "num2": 5.0,
  "operation": "add",
  "result": 15.0,
  "status": "success"
}
```

**Por quê?** O `%2B` é a codificação correta para `+` em `application/x-www-form-urlencoded`.

---

### Usando curl

#### 1. Adição
```bash
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=user@example.com&num1=10&num2=5&operation=add"
```

**Resposta (200 OK):**
```json
{
  "email": "user@example.com",
  "num1": 10.0,
  "num2": 5.0,
  "operation": "add",
  "result": 15.0,
  "status": "success"
}
```

#### 2. Subtração
```bash
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=test@gmail.com&num1=20&num2=8&operation=subtract"
```

**Resposta (200 OK):**
```json
{
  "email": "test@gmail.com",
  "num1": 20.0,
  "num2": 8.0,
  "operation": "subtract",
  "result": 12.0,
  "status": "success"
}
```

#### 3. Multiplicação
```bash
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=calc@test.com&num1=6&num2=7&operation=multiply"
```

**Resposta (200 OK):**
```json
{
  "email": "calc@test.com",
  "num1": 6.0,
  "num2": 7.0,
  "operation": "multiply",
  "result": 42.0,
  "status": "success"
}
```

#### 4. Divisão
```bash
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=div@test.com&num1=100&num2=4&operation=divide"
```

**Resposta (200 OK):**
```json
{
  "email": "div@test.com",
  "num1": 100.0,
  "num2": 4.0,
  "operation": "divide",
  "result": 25.0,
  "status": "success"
}
```

#### 5. Divisão por Zero (Erro)
```bash
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=div@test.com&num1=10&num2=0&operation=divide"
```

**Resposta (400 Bad Request):**
```json
{
  "status": "error",
  "message": "Divisão por zero não é permitida"
}
```

#### 6. Email Inválido (Erro)
```bash
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=invalid-email&num1=10&num2=5&operation=add"
```

**Resposta (400 Bad Request):**
```json
{
  "status": "error",
  "message": "Email inválido"
}
```

#### 7. Parâmetro Faltante (Erro)
```bash
curl -X POST http://127.0.0.1:8080/calculate \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "num1=10&num2=5&operation=add"
```

**Resposta (400 Bad Request):**
```json
{
  "status": "error",
  "message": "Email é obrigatório"
}
```

### Usando Insomnia/Postman

Para replicar o bug em ferramentas de teste de API:

#### Replicar o Bug ('+' vira espaço):
1. Selecione o método **POST** para `http://127.0.0.1:8080/calculate`
2. Em **Body**, selecione **Form URL Encoded**
3. Adicione os campos:
   - `email`: `usuario+tag@gmail.com` (digite o + diretamente)
   - `num1`: `10`
   - `num2`: `5`
   - `operation`: `add`
4. **Envie a requisição**

**Resultado esperado:** Erro 400 - "Email inválido" (porque o `+` foi convertido em espaço)

#### Solução (usar %2B):
- Mude o campo `email` para: `usuario%2Btag@gmail.com`
- **Envie a requisição**

**Resultado esperado:** Sucesso 200 - Email preservado com `+`

**⚠️ Nota:** Algumas ferramentas fazem encoding automático. Se o `+` não virar espaço automaticamente, você pode precisar desabilitar o "auto-encode" nas configurações da ferramenta.

---

### Script de Teste Automatizado

Execute todos os testes com um script bash:

```bash
bash test-api.sh
```

Este script testa:
- Adição
- Subtração
- Multiplicação
- Divisão
- Divisão por zero (erro)
- Email inválido (erro)
- Operação inválida (erro)
- Parâmetro faltante (erro)
- GET na raiz

## Códigos de Status HTTP

| Código | Descrição |
|--------|-----------|
| 200 | Sucesso - Cálculo realizado |
| 400 | Bad Request - Erro na validação ou requisição |
| 500 | Internal Server Error - Erro interno do servidor |

## Validações

- **Email**: Deve ser um email válido no formato `usuario@dominio.com`
- **Números**: Devem ser valores numéricos válidos (Double)
- **Operação**: Deve ser um dos valores suportados (add, subtract, multiply, divide, +, -, *, /)
- **Divisão por Zero**: Não é permitida

## Estrutura de Resposta

### Sucesso
```json
{
  "email": "usuario@example.com",
  "num1": 10.0,
  "num2": 5.0,
  "operation": "add",
  "result": 15.0,
  "status": "success"
}
```

### Erro
```json
{
  "status": "error",
  "message": "Descrição do erro"
}
```