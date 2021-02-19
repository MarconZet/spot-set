package pl.marconzet.spotset.workspace.query.data

import pl.marconzet.spotset.data.api.Track
import pl.marconzet.spotset.security.Spotify

sealed class ExecutionTree {
    class Playlist(val id: String, val spotify: Spotify) : ExecutionTree()
    class AllLiked(val spotify: Spotify) : ExecutionTree()
    class Union(val left: ExecutionTree, val right: ExecutionTree) : ExecutionTree()
    class Intersection(val left: ExecutionTree, val right: ExecutionTree) : ExecutionTree()
    class Difference(val left: ExecutionTree, val right: ExecutionTree) : ExecutionTree()

    val result: Set<Track> by lazy {
        when (this) {
            is AllLiked -> spotify.getAllLikedSongs().toSet()
            is Difference -> left.result.minus(right.result)
            is Intersection -> left.result.intersect(right.result)
            is Playlist -> spotify.getPlaylistsTracks(id).toSet()
            is Union -> left.result.union(right.result)
        }
    }

}
