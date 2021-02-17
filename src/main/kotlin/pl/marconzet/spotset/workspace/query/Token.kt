package pl.marconzet.spotset.workspace.query

data class Token(
    val type: TokenType,
    val value: String,
    val position: Int
)
