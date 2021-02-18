package pl.marconzet.spotset.workspace.query

enum class TokenType(val pattern: String) {
    ALL_LIKED("&&"),
    PLAYLIST("[A-Za-z]"),
    UNION("""\+"""),
    INTERSECTION("""\*"""),
    DIFFERENCE("-"),
    LEFT_BRACKET("""\("""),
    RIGHT_BRACKET("""\)""");

    companion object{
        val operations = listOf(UNION, INTERSECTION, DIFFERENCE)
        val dataSource = listOf(ALL_LIKED, PLAYLIST)
    }
}