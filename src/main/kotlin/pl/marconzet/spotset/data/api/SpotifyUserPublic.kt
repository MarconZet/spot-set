package pl.marconzet.spotset.data.api

data class SpotifyUserPublic(
    val display_name: String,
    val external_urls: ExternalUrl,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<SpotifyImage>,
    val type: String,
    val uri: String,
)
