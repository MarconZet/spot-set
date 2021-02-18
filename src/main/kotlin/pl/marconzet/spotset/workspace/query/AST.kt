package pl.marconzet.spotset.workspace.query

import javax.xml.crypto.Data

sealed class AST(val token: Token) {
    sealed class DataSource(token: Token) : AST(token) {
        class AllLiked(token: Token) : DataSource(token)
        class Playlist(token: Token) : DataSource(token)
    }

    sealed class Operation(val left: AST, token: Token, val right: AST) : AST(token) {
        class Union(left: AST, token: Token, right: AST) : Operation(left, token, right)
        class Intersection(left: AST, token: Token, right: AST) : Operation(left, token, right)
        class Difference(left: AST, token: Token, right: AST) : Operation(left, token, right)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AST

        if (other.token.type != token.type)
            return false

        if (other is Operation && this is Operation)
            if (this.left != other.left || this.right != other.right)
                return false

        if (other is DataSource && this is DataSource) {
            if (!other.token.value.equals(token.value, ignoreCase = true))
                return false
        }

        return true
    }

    override fun hashCode(): Int {
        return when (this) {
            is Operation -> token.type.hashCode() + left.hashCode() * if (this is Operation.Intersection) 1000 else 1 + right.hashCode()
            is DataSource -> token.value.toLowerCase().hashCode()
        }
    }


}
