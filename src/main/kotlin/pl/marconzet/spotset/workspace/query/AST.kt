package pl.marconzet.spotset.workspace.query

sealed class AST(val token: Token) {
    class AllLiked(token: Token) : AST(token)
    class Playlist(token: Token) : AST(token)
    class Union(left: AST, token: Token, right: AST) : AST(token)
    class Intersection(left: AST, token: Token, right: AST) : AST(token)
    class Difference(left: AST, token: Token, right: AST) : AST(token)
}
