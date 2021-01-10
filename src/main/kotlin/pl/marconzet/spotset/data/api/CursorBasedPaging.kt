package pl.marconzet.spotset.data.api

data class CursorBasedPaging<T>(
    val href: String,
    val items: List<T>,
    val limit: Int,
    val next: String,
    val cursors: Cursor,
    val total: Int,
)