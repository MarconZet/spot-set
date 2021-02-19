package pl.marconzet.spotset.workspace

import org.springframework.stereotype.Service
import pl.marconzet.spotset.data.dto.WorkspaceDTO
import pl.marconzet.spotset.repository.UserRepository
import pl.marconzet.spotset.security.Spotify
import pl.marconzet.spotset.security.SpotifyOAuth2User

@Service
class WorkspaceService(
    private val userRepository: UserRepository,
    private val spotify: Spotify
) {
    fun getWorkspace(spotifyOAuth2User: SpotifyOAuth2User): WorkspaceDTO {
        val userId = spotifyOAuth2User.name
        val queries = userRepository.getUserById(userId.toLong()).queryHistory.map { it.query_text }
        val playlists = spotify.getUserPlaylists().map { it.name }
        return WorkspaceDTO(playlists, queries)
    }
}