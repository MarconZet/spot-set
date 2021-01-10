package pl.marconzet.spotset.data.api

import java.util.*

data class PlaylistTrack(
    val added_at : Date,
    val added_by : UserPublic,
    val is_local : Boolean,
    val track : TrackFull,
)
