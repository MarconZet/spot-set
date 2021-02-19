package pl.marconzet.spotset.workspace.query

import org.springframework.stereotype.Service
import pl.marconzet.spotset.exception.InterpretationException
import pl.marconzet.spotset.security.Spotify

@Service
class QueryInterpreter {
    fun lexicalAnalysis(query: String): List<Token> {
        val tokenTypes = enumValues<TokenType>()
        val tokens = tokenTypes.flatMap { type ->
            type.pattern.toRegex().findAll(query).map { Token(type, it.value, it.range.first) }
        }.sortedBy { it.position }
        validateTokenization(query, tokens)
        return tokens
    }

    fun syntaxAnalysis(tokens: List<Token>): AST {
        val rpn = shuntingYardAlgorithm(tokens)
        return buildAST(rpn)
    }

    fun semanticAnalysis(ast: AST, playlistUrls: List<String>, spotify: Spotify): ExecutionTree {
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
                    if (id > playlistUrls.size-1)
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


    private fun validateTokenization(query: String, tokens: List<Token>, lastChar: Int = 0): Boolean {
        if (tokens.isEmpty() and query.isBlank())
            return true
        val head = tokens.first()
        val clearedQuery = query.dropWhile { it.isWhitespace() }
        if (clearedQuery.startsWith(head.value)) {
            return validateTokenization(clearedQuery.removePrefix(head.value), tokens.drop(1), head.position)
        } else {
            throw InterpretationException("Lexical Error: Unknown identifier around characters $lastChar-${head.position}")
        }
    }

    private fun shuntingYardAlgorithm(tokens: List<Token>): List<Token> {
        val operations = mutableListOf<Token>()
        val output = mutableListOf<Token>()

        for (token in tokens) {
            when (token.type) {
                in TokenType.operations -> {
                    while (operations.lastOrNull()?.type in TokenType.operations) {
                        output.add(operations.removeLast())
                    }
                    operations.add(token)
                }
                TokenType.LEFT_BRACKET -> {
                    operations.add(token)
                }
                TokenType.RIGHT_BRACKET -> {
                    while (operations.last().type != TokenType.LEFT_BRACKET) {
                        output.add(operations.removeLast())
                    }
                    operations.removeLast()
                }
                in TokenType.dataSource -> {
                    output.add(token)
                }
                else -> {
                    throw InterpretationException("Syntax Error: Unknown token during RPN conversion: $token")
                }
            }
        }
        output.addAll(operations.reversed())
        return output
    }

    private fun buildAST(tokens: List<Token>): AST {
        val ast = mutableListOf<AST>()

        tokens.forEach { token ->
            when (token.type) {
                TokenType.PLAYLIST -> ast.add(AST.DataSource.Playlist(token))
                TokenType.ALL_LIKED -> ast.add(AST.DataSource.AllLiked(token))
                in TokenType.operations -> {
                    val right = ast.removeLast()
                    val left = ast.removeLast()
                    when (token.type) {
                        TokenType.UNION -> ast.add(AST.Operation.Union(left, token, right))
                        TokenType.INTERSECTION -> ast.add(AST.Operation.Intersection(left, token, right))
                        TokenType.DIFFERENCE -> ast.add(AST.Operation.Difference(left, token, right))
                        else -> throw InterpretationException("Syntax Error: Can't build AST - Invalid token: $token")
                    }

                }
                else -> throw InterpretationException("Syntax Error: Can't build AST - Invalid token: $token")
            }
        }

        if (ast.size != 1)
            throw InterpretationException("Syntax Error: Not a single expression - Next AST node: ${ast[1]}")

        return ast.first()
    }
}