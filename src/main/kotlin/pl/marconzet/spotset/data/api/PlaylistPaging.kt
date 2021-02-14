package pl.marconzet.spotset.data.api

data class PlaylistPaging(
    val href: String,
    val items: List<PlaylistSimple>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
)
