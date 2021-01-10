package pl.marconzet.spotset.data.api

import java.util.*

data class PlayHistory(
    val track: TrackSimple,
    val played_at: Date,
    val context: Context
)