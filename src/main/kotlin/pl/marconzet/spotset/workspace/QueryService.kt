package pl.marconzet.spotset.workspace

import org.springframework.stereotype.Service
import pl.marconzet.spotset.security.Spotify
import pl.marconzet.spotset.workspace.query.LexicalAnalysisService
import pl.marconzet.spotset.workspace.query.SemanticAnalysisService
import pl.marconzet.spotset.workspace.query.SyntaxAnalysisService

@Service
class QueryService(
    private val lexicalAnalysis: LexicalAnalysisService,
    private val semanticAnalysis: SemanticAnalysisService,
    private val syntaxAnalysis: SyntaxAnalysisService,
    private val spotify: Spotify
) {

    fun processQuery(query: String): List<String> {
        return listOf(query, "ale", "ma", "kot")
    }

    fun compileQuery(query: String) {
        val tokens = lexicalAnalysis.analyze(query)
        val ast = syntaxAnalysis.analyze(tokens)
        val exec = semanticAnalysis.analyze(ast, emptyList(), spotify)
        TODO()
    }
}