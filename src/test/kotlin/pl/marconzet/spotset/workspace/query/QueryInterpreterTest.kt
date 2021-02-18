package pl.marconzet.spotset.workspace.query

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import pl.marconzet.spotset.exception.InterpretationException

internal class QueryInterpreterTest {
    private val interpreter = QueryInterpreter()

    @Test
    fun tokenizeTest() {
        val tokens = interpreter.tokenize("&&A+*-()").map { it.type }
        val expected = enumValues<TokenType>().asList()
        assertTrue(tokens.containsAll(expected))
        assertEquals(expected.size, tokens.size)
    }

    @Test
    fun noTokenTest() {
        assertThrows(InterpretationException::class.java) {
            interpreter.tokenize("Ala&Ala")
        }
    }

    @Test
    fun tokenPositionTest() {
        val tokens = interpreter.tokenize("(A+B)")
        val expected = listOf(
            Token(TokenType.LEFT_BRACKET, "(", 0),
            Token(TokenType.PLAYLIST, "A", 1),
            Token(TokenType.UNION, "+", 2),
            Token(TokenType.PLAYLIST, "B", 3),
            Token(TokenType.RIGHT_BRACKET, ")", 4),
        )
        assertTrue(tokens.containsAll(expected))
        assertEquals(expected.size, tokens.size)
    }
}