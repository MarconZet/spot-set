package pl.marconzet.spotset.data.api

typealias ExternalId = Map<String, String>?
typealias ExternalUrl = Map<String, String>?

data class Tracks(
    val href: String,
    val total: Int
)

data class Paging(
    val nothing: String
)