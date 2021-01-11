package pl.marconzet.spotset.data.api

data class UserPublic(
    val display_name: String,
    val external_urls: ExternalUrl,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<Image>,
    val type: String,
    val uri: String,
)
