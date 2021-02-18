package pl.marconzet.spotset.workspace.query

import org.springframework.stereotype.Service
import pl.marconzet.spotset.security.Spotify

@Service
class QueryService(
    private val queryInterpreter: QueryInterpreter,
    private val spotify: Spotify
) {

    fun processQuery(query: String): List<String> {
        return listOf(query, "ale", "ma", "kot")
    }

    fun compileQuery(query: String) {
        val tokens = queryInterpreter.lexicalAnalysis(query)
        val ast = queryInterpreter.syntaxAnalysis(tokens)
        val optimisedAst = queryInterpreter.semanticAnalysis(ast, 0)
    }
}