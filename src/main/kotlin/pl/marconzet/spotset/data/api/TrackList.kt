package pl.marconzet.spotset.data.api

data class TrackListPaging(
    val items: List<TrackListItem>,
    override val next: String?
) : Paging(next)

data class TrackListItem(
    val track: Track
)

data class Track(
    val id: String,
    val name: String
)