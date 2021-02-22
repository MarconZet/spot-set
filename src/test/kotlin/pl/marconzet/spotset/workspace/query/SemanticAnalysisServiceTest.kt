package pl.marconzet.spotset.workspace.query

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import pl.marconzet.spotset.exception.InterpretationException
import pl.marconzet.spotset.webapi.Spotify
import pl.marconzet.spotset.workspace.query.data.ExecutionTree

internal class SemanticAnalysisServiceTest {
    private val lexicalAnalysis = LexicalAnalysisService()
    private val syntaxAnalysis = SyntaxAnalysisService()
    private val semanticAnalysis = SemanticAnalysisService()

    private val spotifyMock: Spotify = Mockito.mock(Spotify::class.java)

    @Test
    fun executionTreeTest() {
        val tokens = lexicalAnalysis.analyze("A+(B-C)")
        val ast = syntaxAnalysis.analyze(tokens)

        val playlists = listOf("a", "b", "c")

        val exec = semanticAnalysis.analyze(ast, playlists, spotifyMock)

        assertEquals(exec::class, ExecutionTree.Union::class)
    }

    @Test
    fun unknownPlaylistTest() {
        val tokens = lexicalAnalysis.analyze("C")
        val ast = syntaxAnalysis.analyze(tokens)

        val playlists = listOf("a", "b")
        assertThrows(InterpretationException::class.java) {
            semanticAnalysis.analyze(ast, playlists, spotifyMock)
        }
    }
}