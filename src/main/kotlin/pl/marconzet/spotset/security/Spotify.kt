package pl.marconzet.spotset.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.util.UriComponentsBuilder
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

    private val market: String by lazy { getProfile().country }

    fun getProfile(): SpotifyUserPrivate {
        return restTemplate.getForObject("$baseUrl/me", SpotifyUserPrivate::class.java) ?: throw RuntimeException()
    }

    fun getPlaylists(): List<PlaylistSimple> {
        return restTemplate.getForObject("$baseUrl/me/playlists", PlaylistPaging::class.java)?.items
            ?: throw RuntimeException()
    }

    fun getPlaylistsTracks(playlistId: String): List<Track> {
        val params = mapOf(
            "market" to market,
            "additional_types" to "track",
            "limit" to "100",
            "fields" to "items(track(name,id)),next"
        )
        val builder = UriComponentsBuilder.fromUriString("$baseUrl/playlists/$playlistId/tracks").apply {
            params.entries.forEach {
                queryParam(it.key, it.value)
            }
        }

        fun acc(url: String): List<PlaylistItem> {
            val res = restTemplate.getForObject(url, PlaylistTrackPaging::class.java)
                ?: throw RuntimeException()
            return res.items + (res.next?.let { s -> acc(s) } ?: emptyList())
        }

        return acc(builder.toUriString()).map { it.track }
    }

    private fun getAccessToken(authorizedClientService: OAuth2AuthorizedClientService) =
        authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            authentication.authorizedClientRegistrationId,
            authentication.name
        ).accessToken.tokenValue
}