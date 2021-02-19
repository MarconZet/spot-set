package pl.marconzet.spotset.data.api

data class PlaylistTrackPaging(
    val items: List<PlaylistItem>,
    val next: String?
)

data class PlaylistItem(
    val track: Track
)

data class Track(
    val id: String,
    val name: String
)