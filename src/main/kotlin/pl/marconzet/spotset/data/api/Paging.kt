package pl.marconzet.spotset.data.api

data class Paging<T>(
    val href: String,
    val items: List<T>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int,
)
