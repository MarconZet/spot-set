package pl.marconzet.spotset.workspace.query

import org.springframework.stereotype.Service
import pl.marconzet.spotset.exception.InterpretationException
import pl.marconzet.spotset.security.Spotify
import pl.marconzet.spotset.workspace.query.data.AST
import pl.marconzet.spotset.workspace.query.data.ExecutionTree

@Service
class SemanticAnalysisService {

    fun analyze(ast: AST, playlistUrls: List<String>, spotify: Spotify): ExecutionTree {
        val visited = mutableMapOf<AST, ExecutionTree>()

        fun walkTree(ast: AST): ExecutionTree {
            val cache = visited[ast]
            if (cache != null) {
                return cache
            }

            val exec: ExecutionTree = when (ast) {
                is AST.DataSource.AllLiked -> ExecutionTree.AllLiked(spotify)
                is AST.DataSource.Playlist -> {
                    val id = ast.token.value.toLowerCase().first().minus('a')
                    if (id > playlistUrls.size - 1)
                        throw InterpretationException("Semantic Error: No playlist with id ${ast.token.value}")
                    ExecutionTree.Playlist(playlistUrls[id], spotify)
                }
                is AST.Operation -> {
                    val left = walkTree(ast.left)
                    val right = walkTree(ast.right)
                    when (ast) {
                        is AST.Operation.Difference -> ExecutionTree.Difference(left, right)
                        is AST.Operation.Intersection -> ExecutionTree.Intersection(left, right)
                        is AST.Operation.Union -> ExecutionTree.Union(left, right)
                    }
                }
            }

            visited[ast] = exec
            return exec
        }

        return walkTree(ast)
    }
}