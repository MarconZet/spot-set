package pl.marconzet.spotset.data.api

data class ArtistFull(
    val externalUrls: ExternalUrl,
    val followers: Followers,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)
