package pl.marconzet.spotset.data.api

data class UserPlaylistsPaging(
    val items: List<Playlist>,
    override val next: String?
) : Paging(next)

data class Playlist(
    val id: String,
    val name: String
)