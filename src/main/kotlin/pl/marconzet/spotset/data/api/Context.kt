package pl.marconzet.spotset.data.api

data class Context(
    val type : String,
    val href : String,
    val external_urls : ExternalUrl,
    val uri : String,
)
