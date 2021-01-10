package pl.marconzet.spotset.data.api

data class AudioFeatures(
    val acousticness: Float,
    val analysis_url: String,
    val danceability: Float,
    val duration_ms: Int,
    val energy: Float,
    val id: String,
    val instrumentalness: Float,
    val key: Int,
    val liveness: Float,
    val loudness: Float,
    val mode: Int,
    val speechiness: Float,
    val tempo: Float,
    val time_signature: Int,
    val track_href: String,
    val type: String,
    val uri: String,
    val valence: Float
)
