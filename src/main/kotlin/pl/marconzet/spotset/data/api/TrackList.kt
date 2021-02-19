package pl.marconzet.spotset.data.api

data class TrackListPaging(
    val itemTracks: List<TrackListItem>,
    val next: String?
)

data class TrackListItem(
    val track: Track
)

data class Track(
    val id: String,
    val name: String
)