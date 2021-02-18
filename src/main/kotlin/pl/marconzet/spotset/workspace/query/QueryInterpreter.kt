package pl.marconzet.spotset.workspace.query

import org.springframework.stereotype.Service
import pl.marconzet.spotset.exception.InterpretationException
import java.util.*

@Service
class QueryInterpreter {
    fun lexicalAnalysis(query: String): List<Token> {
        val tokenTypes = enumValues<TokenType>()
        val tokens = tokenTypes.flatMap { type ->
            type.pattern.toRegex().findAll(query).map { Token(type, it.value, it.range.first) }
        }.sortedBy { it.position }
        validateTokenization(query, tokens)
        return tokens
    }

    private fun validateTokenization(query: String, tokens: List<Token>, lastChar: Int = 0): Boolean {
        if (tokens.isEmpty() and query.isBlank())
            return true
        val head = tokens.first()
        val clearedQuery = query.dropWhile { it.isWhitespace() }
        if (clearedQuery.startsWith(head.value)) {
            return validateTokenization(clearedQuery.removePrefix(head.value), tokens.drop(1), head.position)
        } else {
            throw InterpretationException("Unable to tokenize input: unknown identifier around characters $lastChar-${head.position}")
        }
    }

    fun syntaxAnalysis(tokens: List<Token>): AST {
        TODO()
    }

    private fun shuntingYardAlgorithm(tokens: List<Token>): List<Token> {
        val operations = mutableListOf<Token>()
        val output = mutableListOf<Token>()

        for (token in tokens) {
            when (token.type) {
                in TokenType.operations -> {
                    while (operations.lastOrNull()?.type in TokenType.operations) {
                        output.add(operations.removeLast())
                    }
                    operations.add(token)
                }
                TokenType.LEFT_BRACKET -> {
                    operations.add(token)
                }
                TokenType.RIGHT_BRACKET -> {
                    while (operations.last().type != TokenType.LEFT_BRACKET) {
                        output.add(operations.removeLast())
                    }
                    operations.removeLast()
                }
                in TokenType.dataSource -> {
                    output.add(token)
                }
                else -> {
                    throw InterpretationException("Syntax Error: Unknown token $token")
                }
            }
        }
        output.addAll(operations.reversed())
        return output
    }
}