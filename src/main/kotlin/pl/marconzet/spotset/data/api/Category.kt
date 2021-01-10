package pl.marconzet.spotset.data.api

data class Category(
    val href: String,
    val icons: List<Image>,
    val id: String,
    val name: String,
)