package pl.marconzet.spotset.data.api

data class AlbumSimple (
    val albumGroup: String?,
    val albumType: String,
    val artist: ArtistSimple,
    val availableMarkets: List<String>,
    val externalUrls: ExternalUrl,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val releaseDate: String,
    val releaseDatePrecision: String,
    val restriction: AlbumRestriction?,
    val tracks: Paging<TrackSimple>,
    val type: String,
    val uri: String
)