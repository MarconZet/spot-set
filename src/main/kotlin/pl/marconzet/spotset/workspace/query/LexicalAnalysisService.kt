package pl.marconzet.spotset.workspace.query

import org.springframework.stereotype.Service
import pl.marconzet.spotset.exception.InterpretationException
import pl.marconzet.spotset.workspace.query.data.Token
import pl.marconzet.spotset.workspace.query.data.TokenType

@Service
class LexicalAnalysisService {

    fun analyze(query: String): List<Token> {
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
        if (tokens.isEmpty()) {
            throw InterpretationException("Lexical Error: Could not tokenize starting form character $lastChar")
        }
        val head = tokens.first()
        val clearedQuery = query.dropWhile { it.isWhitespace() }
        if (clearedQuery.startsWith(head.value)) {
            return validateTokenization(clearedQuery.removePrefix(head.value), tokens.drop(1), head.position)
        } else {
            throw InterpretationException("Lexical Error: Could not tokenize around characters $lastChar-${head.position}")
        }
    }


}