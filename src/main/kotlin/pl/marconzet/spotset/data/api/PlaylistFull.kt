package pl.marconzet.spotset.data.api

data class PlaylistFull(
    val collaborative: Boolean,
    val description: String,
    val external_urls: ExternalUrl,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<SpotifyImage>,
    val name: String,
    val owner: SpotifyUserPublic,
    val public: Boolean?,
    val snapshot_id: String,
    val tracks: Paging,
    val type: String,
    val uri: String
)
