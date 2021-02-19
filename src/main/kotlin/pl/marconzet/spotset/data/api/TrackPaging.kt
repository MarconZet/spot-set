package pl.marconzet.spotset.data.api

data class TrackPaging(
    val href: String,
    val items: List<TrackSimple>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
)