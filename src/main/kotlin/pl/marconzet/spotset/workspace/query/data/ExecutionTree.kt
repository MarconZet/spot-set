package pl.marconzet.spotset.workspace.query.data

import pl.marconzet.spotset.security.Spotify

sealed class ExecutionTree {
    class Playlist(id: String, spotify: Spotify) : ExecutionTree()
    class AllLiked(spotify: Spotify) : ExecutionTree()
    class Union(left: ExecutionTree, right: ExecutionTree) : ExecutionTree()
    class Intersection(left: ExecutionTree, right: ExecutionTree) : ExecutionTree()
    class Difference(left: ExecutionTree, right: ExecutionTree) : ExecutionTree()

}
