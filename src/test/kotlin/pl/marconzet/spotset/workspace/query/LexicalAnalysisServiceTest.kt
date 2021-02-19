package pl.marconzet.spotset.workspace.query

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import pl.marconzet.spotset.exception.InterpretationException
import pl.marconzet.spotset.security.Spotify
import pl.marconzet.spotset.workspace.query.data.ExecutionTree
import pl.marconzet.spotset.workspace.query.data.Token
import pl.marconzet.spotset.workspace.query.data.TokenType
import kotlin.reflect.KFunction
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.isAccessible

internal class LexicalAnalysisServiceTest {
    private val lexicalAnalysis = LexicalAnalysisService()

    @Test
    fun tokenizeTest() {
        val tokens = lexicalAnalysis.analyze("&&A+*-()").map { it.type }
        val expected = enumValues<TokenType>().asList()
        assertIterableEquals(expected, tokens)
    }

    @Test
    fun noTokenTest() {
        assertThrows(InterpretationException::class.java) {
            lexicalAnalysis.analyze("Ala&Ala")
        }
    }

    @Test
    fun tokenPositionTest() {
        val tokens = lexicalAnalysis.analyze("(A+B)")
        val expected = listOf(
            Token(TokenType.LEFT_BRACKET, "(", 0),
            Token(TokenType.PLAYLIST, "A", 1),
            Token(TokenType.UNION, "+", 2),
            Token(TokenType.PLAYLIST, "B", 3),
            Token(TokenType.RIGHT_BRACKET, ")", 4),
        )
        assertIterableEquals(expected, tokens)
    }
}