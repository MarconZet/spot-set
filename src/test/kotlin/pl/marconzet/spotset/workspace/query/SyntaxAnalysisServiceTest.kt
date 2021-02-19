package pl.marconzet.spotset.workspace.query

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import pl.marconzet.spotset.exception.InterpretationException
import pl.marconzet.spotset.workspace.query.data.Token
import pl.marconzet.spotset.workspace.query.data.TokenType
import kotlin.reflect.KFunction
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.isAccessible

internal class SyntaxAnalysisServiceTest {
    private val lexicalAnalysis = LexicalAnalysisService()
    private val syntaxAnalysis = SyntaxAnalysisService()

    @Test
    fun reversePolishNotationTest() {
        val f: KFunction<List<Token>> =
            SyntaxAnalysisService::class.memberFunctions.find { it.name == "shuntingYardAlgorithm" }
                ?.apply { isAccessible = true } as KFunction<List<Token>>

        run {
            val tokens = lexicalAnalysis.analyze("(A+B)-C")
            val rpn = f.call(syntaxAnalysis, tokens)
            val expected = listOf(tokens[1], tokens[3], tokens[2], tokens[6], tokens[5])
            assertIterableEquals(expected, rpn)
        }
        run {
            val tokens = lexicalAnalysis.analyze("A+(B-C)")
            val rpn = f.call(syntaxAnalysis, tokens)
            val expected = listOf(tokens[0], tokens[3], tokens[5], tokens[4], tokens[1])
            assertIterableEquals(expected, rpn)
        }
    }

    @Test
    fun buildASTTreeTest() {
        val tokens = lexicalAnalysis.analyze("A+(B-C)")
        val ast = syntaxAnalysis.analyze(tokens)
        assertEquals(ast.token.type, TokenType.UNION)
    }

    @Test
    fun multipleExpressionsTest() {
        val tokens = lexicalAnalysis.analyze("A+BA+B")
        assertThrows(InterpretationException::class.java) {
            syntaxAnalysis.analyze(tokens)
        }
    }
}