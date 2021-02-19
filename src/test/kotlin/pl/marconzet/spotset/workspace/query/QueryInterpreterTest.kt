package pl.marconzet.spotset.workspace.query

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import pl.marconzet.spotset.exception.InterpretationException
import pl.marconzet.spotset.security.Spotify
import kotlin.reflect.KFunction
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

internal class QueryInterpreterTest {
    private val interpreter = QueryInterpreter()
    private val spotifyMock: Spotify = Mockito.mock(Spotify::class.java)

    @Test
    fun tokenizeTest() {
        val tokens = interpreter.lexicalAnalysis("&&A+*-()").map { it.type }
        val expected = enumValues<TokenType>().asList()
        assertIterableEquals(expected, tokens)
    }

    @Test
    fun noTokenTest() {
        assertThrows(InterpretationException::class.java) {
            interpreter.lexicalAnalysis("Ala&Ala")
        }
    }

    @Test
    fun tokenPositionTest() {
        val tokens = interpreter.lexicalAnalysis("(A+B)")
        val expected = listOf(
            Token(TokenType.LEFT_BRACKET, "(", 0),
            Token(TokenType.PLAYLIST, "A", 1),
            Token(TokenType.UNION, "+", 2),
            Token(TokenType.PLAYLIST, "B", 3),
            Token(TokenType.RIGHT_BRACKET, ")", 4),
        )
        assertIterableEquals(expected, tokens)
    }

    @Test
    fun reversePolishNotationTest() {
        val f: KFunction<*> = QueryInterpreter::class.memberFunctions.find { it.name == "shuntingYardAlgorithm" }
            ?.apply { isAccessible = true }
            ?: throw RuntimeException("Method to test not found")

        run {
            val tokens = interpreter.lexicalAnalysis("(A+B)-C")
            val rpn = f.call(interpreter, tokens) as List<Token>
            val expected = listOf(tokens[1], tokens[3], tokens[2], tokens[6], tokens[5])
            assertIterableEquals(expected, rpn)
        }

        run {
            val tokens = interpreter.lexicalAnalysis("A+(B-C)")
            val rpn = f.call(interpreter, tokens) as List<Token>
            val expected = listOf(tokens[0], tokens[3], tokens[5], tokens[4], tokens[1])
            assertIterableEquals(expected, rpn)
        }
    }

    @Test
    fun buildASTTreeTest() {
        val tokens = interpreter.lexicalAnalysis("A+(B-C)")
        val ast = interpreter.syntaxAnalysis(tokens)
        assertEquals(ast.token.type, TokenType.UNION)
    }

    @Test
    fun multipleExpressionsTest() {
        val tokens = interpreter.lexicalAnalysis("A+BA+B")
        assertThrows(InterpretationException::class.java) {
            interpreter.syntaxAnalysis(tokens)
        }
    }

    @Test
    fun executionTreeTest() {
        val tokens = interpreter.lexicalAnalysis("A+(B-C)")
        val ast = interpreter.syntaxAnalysis(tokens)

        val playlists = listOf("a", "b", "c")

        val exec = interpreter.semanticAnalysis(ast, playlists, spotifyMock)

        assertEquals(exec::class, ExecutionTree.Union::class)
    }

    @Test
    fun unknownPlaylistTest() {
        val tokens = interpreter.lexicalAnalysis("C")
        val ast = interpreter.syntaxAnalysis(tokens)

        val playlists = listOf("a", "b")
        assertThrows(InterpretationException::class.java) {
            interpreter.semanticAnalysis(ast, playlists, spotifyMock)
        }
    }
}