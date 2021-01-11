package pl.marconzet.spotset.data.api

data class UserPrivate(
    val country : String,
    val display_name : String,
    val email : String,
    val explicit_content : ExplicitContentSettings,
    val external_urls : ExternalUrl,
    val followers : Followers,
    val href : String,
    val id : String,
    val images : List<Image>,
    val product : String,
    val type : String,
    val uri : String,
)
