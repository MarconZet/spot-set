package pl.marconzet.spotset.workspace

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import pl.marconzet.spotset.data.dto.WorkspaceDTO
import pl.marconzet.spotset.exception.WrongPrincipalException
import pl.marconzet.spotset.repository.SsUserRepository
import pl.marconzet.spotset.webapi.Spotify
import pl.marconzet.spotset.security.SpotifyOAuth2User

@Service
class WorkspaceService(
    private val ssUserRepository: SsUserRepository,
    private val spotify: Spotify
) {
    fun getWorkspace(authentication: Authentication): WorkspaceDTO {
        val principal = authentication.principal
        if (principal !is SpotifyOAuth2User)
            throw WrongPrincipalException()

        val userId = principal.ssUser.id
        val queries = ssUserRepository.getSsUserById(userId).queryHistory.map { it.query_text }
        val playlists = spotify.getUserPlaylists().withIndex().map { "${'A' + it.index}: ${it.value.name}" }
        return WorkspaceDTO(playlists, queries)
    }
}