package pl.marconzet.spotset.data.api

data class PlaylistSimple(
    val collaborative: Boolean,
    val description: String,
    val external_urls: ExternalUrl,
    val href: String,
    val id: String,
    val images: List<SpotifyImage>,
    val name: String,
    val owner: SpotifyUserPublic,
    val public: Boolean?,
    val snapshot_id: String,
    val tracks: Tracks?,
    val type: String,
    val uri: String
)
