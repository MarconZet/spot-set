package pl.marconzet.spotset.workspace.query

import org.springframework.stereotype.Service
import pl.marconzet.spotset.exception.InterpretationException
import pl.marconzet.spotset.workspace.query.data.AST
import pl.marconzet.spotset.workspace.query.data.Token
import pl.marconzet.spotset.workspace.query.data.TokenType

@Service
class SyntaxAnalysisService {

    fun analyze(tokens: List<Token>): AST {
        val rpn = shuntingYardAlgorithm(tokens)
        return buildAST(rpn)
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
                    try {
                        val right = ast.removeLast()
                        val left = ast.removeLast()
                        when (token.type) {
                            TokenType.UNION -> ast.add(AST.Operation.Union(left, token, right))
                            TokenType.INTERSECTION -> ast.add(AST.Operation.Intersection(left, token, right))
                            TokenType.DIFFERENCE -> ast.add(AST.Operation.Difference(left, token, right))
                            else -> throw InterpretationException("Syntax Error: Can't build AST - Invalid token: $token")
                        }
                    } catch (ex: NoSuchElementException) {
                        throw InterpretationException("Syntax Error: Missing required expressions around $token")
                    }
                }
                else -> throw InterpretationException("Syntax Error: Can't build AST - Invalid token: $token")
            }
        }

        if (ast.size != 1)
            throw InterpretationException("Syntax Error: Not a single expression - Next AST node: ${ast[1].token}")

        return ast.first()
    }
}