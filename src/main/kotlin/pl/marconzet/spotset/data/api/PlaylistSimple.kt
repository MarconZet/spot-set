package pl.marconzet.spotset.data.api

data class PlaylistSimple(
    val collaborative: Boolean,
    val description: String,
    val external_urls: ExternalUrl,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: UserPublic,
    val public: Boolean?,
    val snapshot_id: String,
    val tracks: Paging<PlaylistTrack>,
    val type: String,
    val uri: String
)
