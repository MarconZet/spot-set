package pl.marconzet.spotset.workspace.query

import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class QueryInterpreterTest {
    val interpreter = QueryInterpreter()

    @Test
    fun tokenize() {
        interpreter.tokenize("AAA  bbb bb + + = sadfAFDSFf")
        assertTrue(true)
    }
}