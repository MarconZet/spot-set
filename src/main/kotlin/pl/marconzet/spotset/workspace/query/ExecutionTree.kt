package pl.marconzet.spotset.workspace.query

import pl.marconzet.spotset.security.Spotify
import java.lang.RuntimeException

sealed class ExecutionTree {
    class Playlist(url: String, spotify: Spotify) : ExecutionTree()
    class AllLiked(spotify: Spotify) : ExecutionTree()
    class Union(left: ExecutionTree, right: ExecutionTree) : ExecutionTree()
    class Intersection(left: ExecutionTree, right: ExecutionTree) : ExecutionTree()
    class Difference(left: ExecutionTree, right: ExecutionTree) : ExecutionTree()

}
