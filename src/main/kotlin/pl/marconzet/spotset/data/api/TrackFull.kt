package pl.marconzet.spotset.data.api

data class TrackFull(
    val album: AlbumSimple,
    val artists: List<ArtistSimple>,
    val available_markets: List<String>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val external_ids: ExternalId,
    val external_urls: ExternalUrl,
    val href: String,
    val id: String,
    val is_playable: Boolean,
    val linked_from: TrackLink,
    val restrictions: TrackRestriction?,
    val name: String,
    val popularity: Int,
    val preview_url: String,
    val track_number: Int,
    val type: String,
    val uri: String,
    val is_local: Boolean,
)
