package pl.marconzet.spotset.workspace

import org.springframework.stereotype.Service
import pl.marconzet.spotset.data.model.Query
import pl.marconzet.spotset.repository.QueryRepository
import pl.marconzet.spotset.webapi.Spotify
import pl.marconzet.spotset.workspace.query.LexicalAnalysisService
import pl.marconzet.spotset.workspace.query.SemanticAnalysisService
import pl.marconzet.spotset.workspace.query.SyntaxAnalysisService

@Service
class QueryService(
    private val lexicalAnalysis: LexicalAnalysisService,
    private val syntaxAnalysis: SyntaxAnalysisService,
    private val semanticAnalysis: SemanticAnalysisService,
    private val spotify: Spotify,
    private val queryRepository: QueryRepository
) {

    fun processQuery(query: String): List<String> {
        val playlists = spotify.getUserPlaylists().map { it.id }

        val tokens = lexicalAnalysis.analyze(query)
        val ast = syntaxAnalysis.analyze(tokens)
        val exec = semanticAnalysis.analyze(ast, playlists, spotify)

        queryRepository.save(Query(0, spotify.ssUser, query))
        return exec.result.map { it.name }
    }

}