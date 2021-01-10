package pl.marconzet.spotset.data.api

data class ArtistSimple(
    val externalUrls: ExternalUrl,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)