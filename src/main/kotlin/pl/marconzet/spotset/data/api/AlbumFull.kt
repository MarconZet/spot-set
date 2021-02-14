package pl.marconzet.spotset.data.api

data class AlbumFull(
    val albumType: String,
    val artist: ArtistSimple,
    val availableMarkets: List<String>,
    val copyrights: List<Copyright>,
    val externalIds: ExternalId,
    val externalUrls: ExternalUrl,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<SpotifyImage>,
    val label: String,
    val name: String,
    val popularity: Int,
    val releaseDate: String,
    val releaseDatePrecision: String,
    val restriction: AlbumRestriction?,
    val tracks: Paging,
    val type: String,
    val uri: String
)