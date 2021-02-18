package pl.marconzet.spotset.workspace.query

import org.springframework.stereotype.Service
import pl.marconzet.spotset.exception.InterpretationException

@Service
class QueryInterpreter {
    fun tokenize(query: String): List<Token> {
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
}