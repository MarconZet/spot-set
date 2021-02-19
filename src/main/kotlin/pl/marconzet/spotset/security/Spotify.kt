package pl.marconzet.spotset.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope
import pl.marconzet.spotset.configuration.ApiBinding
import pl.marconzet.spotset.configuration.SpotifyConfig
import pl.marconzet.spotset.data.api.*
import pl.marconzet.spotset.logger

@Service
@RequestScope
class Spotify(
    authorizedClientService: OAuth2AuthorizedClientService,
    spotifyConfig: SpotifyConfig
) {
    val logger = logger()

    private val authentication = SecurityContextHolder.getContext().authentication as OAuth2AuthenticationToken
    val principal = authentication.principal as SpotifyOAuth2User

    private val baseUrl = spotifyConfig.baseUrl
    private val restTemplate = ApiBinding(getAccessToken(authorizedClientService)).restTemplate

    private val market: String by lazy { getProfile().country ?: "US" }

    fun getProfile(): SpotifyUserPrivate {
        return restTemplate.getForObject("$baseUrl/me", SpotifyUserPrivate::class.java) ?: throw RuntimeException()
    }

    fun getPlaylists(): List<PlaylistSimple> {
        return restTemplate.getForObject("$baseUrl/me/playlists", PlaylistPaging::class.java)?.items
            ?: throw RuntimeException()
    }

    fun getPlaylistsTracks(playlistId: String): List<TrackSimple> {
        val args = mapOf("market" to market, "additional_types" to "track", "fields" to "next,items.track(name)")

        fun acc(prev: TrackPaging): List<TrackSimple> {
            return prev.items + (prev.next?.let {
                acc(
                    restTemplate.getForObject(
                        "$baseUrl/playlists/$playlistId/tracks",
                        TrackPaging::class.java,
                        args + mapOf("offset" to it)
                    ) ?: throw RuntimeException()
                )
            } ?: emptyList())
        }

        return acc(
            restTemplate.getForObject("$baseUrl/playlists/$playlistId/tracks", TrackPaging::class.java, args)
                ?: throw RuntimeException()
        )
    }

    private fun getAccessToken(authorizedClientService: OAuth2AuthorizedClientService) =
        authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            authentication.authorizedClientRegistrationId,
            authentication.name
        ).accessToken.tokenValue
}